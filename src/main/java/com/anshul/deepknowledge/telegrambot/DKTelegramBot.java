package com.anshul.deepknowledge.telegrambot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.anshul.deepknowledge.telegrambot.core.Commands;
import com.anshul.deepknowledge.telegrambot.entity.Course;
import com.anshul.deepknowledge.telegrambot.entity.User;
import com.anshul.deepknowledge.telegrambot.service.CourseRepository;
import com.anshul.deepknowledge.telegrambot.service.UserRespository;

@Component
public class DKTelegramBot extends TelegramLongPollingBot{
	
	@Value("${telegrambot.token}")	  
	private String DK_TOKEN;
	
	@Value("${telegrambot.name}")
	private String DK_BOT_NAME;
	
	private static final Logger log = LoggerFactory.getLogger(DKTelegramBot.class);
	
	@Autowired
	private UserRespository userRespository;

	@Autowired
	private CourseRepository courseRepository;
	
	@Override
	public void onUpdateReceived(Update update) {	
		
		Long chatId;
		String mobileNumber = "";
		String userName = "";
		boolean isNewUser = true;
		boolean isAdmin= false;
		SendMessage message = new SendMessage() ;
		StringBuilder responseText = new StringBuilder("");
		
		if(update.hasMessage()) {
			
			chatId = update.getMessage().getChatId();			
			message.setParseMode(ParseMode.HTML).setChatId(chatId);
			
			User user = userRespository.findByChatId(chatId);			
			if(user!=null) {
				isNewUser = false;
				userName = user.getName();
				mobileNumber = user.getMobile();
				if(user.getRole().equalsIgnoreCase("ADMIN"))
					isAdmin = true;
			}
			
			/* If user has sent his mobile number and the chatId doesn't exist in the DB, then create/update the user in the DB. */
			if(isNewUser && update.getMessage().hasContact()) {				
				mobileNumber = update.getMessage().getContact().getPhoneNumber();
				userName = update.getMessage().getContact().getFirstName() + " " + update.getMessage().getContact().getLastName();
				
				user = mapUserChat(chatId, mobileNumber, userName);
				
				isNewUser = false;
				log.info("New user : "+user.getId()+ " | mobile: "+user.getMobile()+" has initiated chat.");
				
				responseText.append("Thank you for sharing your mobile number, ").append(userName).append("!").append("\n").append("\n");
				responseText.append("Please type <b>").append(Commands.HELP).append("</b> to know how to interact with me.");
			}
			
			if(update.getMessage().hasText() && !update.getMessage().getText().trim().isEmpty()) {
				String commandReceived = update.getMessage().getText().trim().toLowerCase();
				log.info("Command received : "+commandReceived);
				
				String[] commandList = commandReceived.split(" ");
				boolean isSingleCommand = true;
				String command = commandList[0]; 
				if(commandList.length>1) {
					isSingleCommand = false;
				}
				
				if(isSingleCommand && (Commands.START.equals(command) || Commands.START_NOSLASH.equals(command) )) {
					responseText = handleStart(isNewUser, message, responseText, user);
				}
				
				if(isSingleCommand && Commands.HELP.equals(command)) {
					responseText = showHelp(userName, isNewUser, isAdmin, responseText);					
				}
				
				if(isSingleCommand && Commands.ALL_COURSES.equals(command)) {					
					responseText = showAllCourses(isNewUser, responseText);
				}
				
				if(isSingleCommand && Commands.MY_COURSES.equals(command)) {
					responseText = showMyCourses(isNewUser, responseText, user);
				}
				
				if(isSingleCommand && Commands.SEND_NUMBER.equals(command)) {
					responseText = sendMyNumber(userName, isNewUser, message, responseText);
				}
				
				if(!isSingleCommand && Commands.COURSE.equals(command)) {	
					responseText = showCourseDetails(responseText, commandReceived);
				}
								
				if(isSingleCommand && isAdmin && Commands.ALL_SUBSCRIBERS.equals(command)) {		
					responseText = showAllUsers(responseText);
				}								
				
				if(!isSingleCommand && isAdmin && Commands.SUBSCRIBE.equals(command)) {		
					responseText = subscribeUser(responseText, commandReceived);
				}
				
				if(!isSingleCommand && isAdmin && Commands.UNSUBSCRIBE.equals(command)) {		
					responseText = unSubscribeUser(responseText, commandReceived);
				}

			}
			
			if(responseText.length() < 1) {
				responseText.append("I am sorry. I don't understand. Please type <b>").append(Commands.HELP).append("</b> or please contact Deep Knowledge Support at +91-9651160056");
			}
			
			message.setText(responseText.toString());
			
			try {
	            execute(message); // Call method to send the message
	        } catch (TelegramApiException e) {
	            e.printStackTrace();
	        }
		}

	}

	private User mapUserChat(Long chatId, String mobileNumber, String userName) {
		User user;
		user = userRespository.findByMobileNumber(Long.parseLong(mobileNumber));
		if(user!=null) { //User has been created by admin. User never chatted before.
			user.setChat_id(chatId);
			user.setName(userName);					
		}else {
			user = new User(userName, "SUBSCRIBER", mobileNumber, chatId);
		}
		user = userRespository.save(user);
		return user;
	}

	private StringBuilder handleStart(boolean isNewUser, SendMessage message, StringBuilder responseText, User user) {
		responseText.append("Welcome ");
		if(isNewUser) {
			responseText.append("to Deep Knowledge!").append("\n").append("\n");
			responseText.append("It looks we're talking for the first time. I need your mobile number to identify you. Pressing the <b>Share My Number</b> will allow me to see your phone number.").append("\n").append("\n");
			
			message.setReplyMarkup(getShareMyNumberKeyboardMarkUp());
			
		}else {
			responseText.append(user.getName()).append("!").append("\n").append("\n");			
		}
		responseText.append("Please type <b>").append(Commands.HELP).append("</b> to know how to interact with me.");
		return responseText;
	}

	private StringBuilder showHelp(String userName, boolean isNewUser, boolean isAdmin, StringBuilder responseText) {
		if(isNewUser) {
			responseText.append("If you're talking to me for the first time, please type ").append("<b>").append(Commands.START).append("</b>.").append("\n").append("\n");
			responseText.append("Else, you can do following things here:").append("\n").append("\n");
			responseText.append("<i>1.</i> Type ").append("<b>").append(Commands.ALL_COURSES).append("</b>").append(" to know about all the available courses offered by Deep Knowledge.").append("\n");
			responseText.append("<i>2.</i> Type ").append("<b>").append(Commands.COURSE).append("</b>").append(" [course_code] to find out detals about a particular course. E.g. <b>").append(Commands.COURSE).append(" slc-01</b>").append("\n").append("\n");
			responseText.append("I need your mobile number to identify you. Please type ").append("<b>").append(Commands.SEND_NUMBER).append("</b>").append(" to allow me to get your number.");
		}else if(isAdmin) {
			responseText.append(userName).append("!").append(" You can do following things here:").append("\n").append("\n");
			responseText.append("<i>1.</i> Type ").append("<b>").append(Commands.ALL_COURSES).append("</b>").append(" to know about all the available courses offered by Deep Knowledge.").append("\n");
			responseText.append("<i>2.</i> Type <b>").append(Commands.SUBSCRIBE).append(" [mobile] [course_code]</b> to START a user's subscription for a course. E.g. <b>").append(Commands.SUBSCRIBE).append(" 919651160056 slc-01</b>").append("\n");
			responseText.append("<i>3.</i> Type <b>").append(Commands.UNSUBSCRIBE).append(" [mobile] [course_code]</b> to STOP a user's subscription for a course. E.g. <b>").append(Commands.UNSUBSCRIBE).append(" 919651160056 slc-01</b>").append("\n");
			responseText.append("<i>4.</i> ").append("To find out the course code, please type <b>").append(Commands.ALL_COURSES).append("</b>").append("\n");
			responseText.append("<i>5.</i> ").append("To find out all the users and their courses, please type <b>").append(Commands.ALL_SUBSCRIBERS).append("</b>").append("\n");
		}else {			
			responseText.append(userName).append("!").append(" You can do following things here:").append("\n").append("\n");
			responseText.append("<i>1.</i> Type ").append("<b>").append(Commands.ALL_COURSES).append("</b>").append(" to know about all the available courses offered by Deep Knowledge.").append("\n");
			responseText.append("<i>2.</i> Type ").append("<b>").append(Commands.COURSE).append("</b>").append(" [course_code] to find out detals about a particular course. E.g. <b>").append(Commands.COURSE).append(" slc-01</b>").append("\n");
			responseText.append("<i>3.</i> Type <b>").append(Commands.MY_COURSES).append("</b> to know about course you are subscribed to.").append("\n");
		}
		
		return responseText;
	}

	private StringBuilder showAllCourses(boolean isNewUser, StringBuilder responseText) {
		responseText.append("Currently, Deep Knowledge offers following courses:").append("\n").append("\n");
		List<Course> allCourses = courseRepository.findAll();
		allCourses.forEach(action -> {
			responseText.append(action.getName()).append(" [Code: ").append(action.getCode()).append("] ").append("\n");
		});			
		responseText.append("\n");
		
		if(isNewUser) {
			responseText.append("I need your mobile number to identify you. Please type <b>").append(Commands.SEND_NUMBER).append("</b> to allow me to get your number.");
		}
		
		return responseText;
	}

	private StringBuilder showMyCourses(boolean isNewUser, StringBuilder responseText, User user) {
		if(isNewUser) {
			responseText.append("I need your mobile number to identify you. Please type <b>").append(Commands.SEND_NUMBER).append("</b> to allow me to get your number.").append("\n");
		}else {
			String userCourses = user.getCourses();
			if(userCourses!=null && !userCourses.isEmpty()) {
				List<String> courseList = Arrays.asList(userCourses.split(","));
				for(String userCourse:courseList) {
					Long courseId = Long.parseLong(userCourse.split("##")[0]);			    				
						Course course = courseRepository.findOne(courseId);
						responseText.append(course.getName()).append(" | ").append(course.getCode()).append("\n");
				}
			}else {
				responseText.append("You aren't subscribed to any courses. Please contact Deep Knowledge support at +91-9651160056 to know how to subscribe to our courses.").append("\n").append("\n");
				responseText.append("Please type <b>").append(Commands.ALL_COURSES).append("</b> to know about all the available courses offered by Deep Knowledge.").append("\n");
			}
		}
		
		return responseText;
	}

	private StringBuilder sendMyNumber(String userName, boolean isNewUser, SendMessage message, StringBuilder responseText) {
		if(isNewUser) {
			responseText.append("Press the <b>Share My Number</b> button to allow me to see your mobile number.").append("\n").append("\n");
			message.setReplyMarkup(getShareMyNumberKeyboardMarkUp());
		}else {
			responseText.append("Thanks, ").append(userName).append("!").append(" I already have your number.").append("\n").append("\n");						
		}
		
		return responseText;
	}

	private StringBuilder showCourseDetails(StringBuilder responseText, String text) {
		if(text.split(" ").length != 2) {
			responseText.append("You haven't typed the command properly.").append("\n");
			responseText.append("Type <b>").append(Commands.COURSE).append(" [course_code]</b> to know the course details. E.g. <b>").append(Commands.COURSE).append(" slc-01</b>").append("\n");
		}else {
			String queriedCourseCode = text.split(" ")[1];
			Course queriedCourse = courseRepository.findByCode(StringUtils.upperCase(queriedCourseCode));
			if(queriedCourse!=null) {
				responseText.append("<b>").append(queriedCourse.getName()).append("</b> | ").append(queriedCourse.getCode()).append("\n").append("\n");							
				responseText.append(queriedCourse.getDetails()).append("\n");
			}
		}
		
		return responseText;
	}

	private StringBuilder showAllUsers(StringBuilder responseText) {
		List<User> allUsers = userRespository.findByRole("SUBSCRIBER");
		if(allUsers.isEmpty()) {
			responseText.append("No subscribers found.");
		}else {
			responseText.append("Following users exist in the system: ").append("\n").append("\n");
			allUsers.forEach(userItem -> {
				responseText.append("Name: ").append(userItem.getName()).append("\n");
				responseText.append("Mobile: ").append(userItem.getMobile()).append("\n");							
				if(userItem.getCourses()==null || userItem.getCourses().trim().isEmpty()) {
					responseText.append("<b>No courses assigned.</b>").append("\n");
				}else {
					responseText.append("Courses Assigned: ").append("\n");
					List<String> courseList = Arrays.asList(userItem.getCourses().split(","));
					courseList.forEach(courseItem -> {
						Long courseId = Long.parseLong(courseItem.split("##")[0]);
						Course courseObj = courseRepository.findOne(courseId);
						responseText.append(courseObj.getCode()).append(" | ").append(courseObj.getName()).append("\n");
					});
				}
				responseText.append("\n");
			});
		}
		return responseText;
	}
	
	private StringBuilder unSubscribeUser(StringBuilder responseText, String text) {
		if(text.split(" ").length != 3) {
			responseText.append("You haven't typed the command properly.").append("\n").append("\n");
			responseText.append("Type <b>").append(Commands.UNSUBSCRIBE).append(" [mobile] [course_code]</b> to start a user's subscription. E.g. <b>").append(Commands.UNSUBSCRIBE).append(" 919898909890 slc-01</b>\"").append("\n");
		}else {
			String mobileNum = text.split(" ")[1];
			String courseCodeToUnassign = text.split(" ")[2];
			Course courseToUnAssign = courseRepository.findByCode(StringUtils.upperCase(courseCodeToUnassign));
			if(mobileNum.length()==12) {
				try {
					User userToUpdate = userRespository.findByMobileNumber(Long.parseLong(mobileNum));
					if(userToUpdate == null) {						
						responseText.append("There is no subscriber with mobile number: <b>"+mobileNum+"</b>");
					}else if(userToUpdate.getCourses()==null || userToUpdate.getCourses().isEmpty()) {
						responseText.append("This user isn't assigned to any course.").append("\n");
					}else if(courseToUnAssign==null){
						responseText.append("Invalid course code: ").append("<b>").append(courseCodeToUnassign).append("</b>").append("\n").append("\n");
						responseText.append("To find out the correct course code for the user, please type <b>").append(Commands.ALL_SUBSCRIBERS).append("</b>").append("\n");
					} else if(userToUpdate.getCourses().indexOf(String.valueOf(courseToUnAssign.getId()))==-1){
						responseText.append("This user <strong>isn't assigned</strong> to the course: <b>").append(courseCodeToUnassign).append("</b>").append("\n");
					}else {
							List<String> existingCourses = Arrays.asList(userToUpdate.getCourses().split(","));
							StringBuilder newCourses = new StringBuilder();
							existingCourses.forEach(courseItem -> {
								if(courseItem.indexOf(String.valueOf(courseToUnAssign.getId()))==-1) {
									newCourses.append(courseItem).append(",");
								}
							});						
							userToUpdate.setCourses(newCourses.toString());
							userRespository.save(userToUpdate);
							
							responseText.append("The user with mobile: ").append("<b>").append(userToUpdate.getMobile()).append("</b>");
							responseText.append(" Unsubscribed from the course :").append(" [").append("<b>").append(courseToUnAssign.getCode()).append("] ").append("</b>");
							responseText.append(courseToUnAssign.getName()).append("\n");						
					}					
				}catch(Exception e) {
					log.debug("Exception in parsing the mobile number: "+mobileNum);
					responseText.append("The mobile number format is incorrect. Please prefix 91 to the 10-digit mobile number. And there should be no alphabets in the mobile number. E.g. to subscribe a user with mobile number 919898909890 for course slc-01, type <b>/unsubscribe 919898909890 slc-01</b>\"").append("\n");
				}
			}
		}
		
		return responseText;
	}
	
	private StringBuilder subscribeUser(StringBuilder responseText, String text) {
		if(text.split(" ").length != 3) {
			responseText.append("You haven't typed the command properly.").append("\n").append("\n");
			responseText.append("Type <b>").append(Commands.SUBSCRIBE).append(" [mobile] [course_code]</b> to start a user's subscription. E.g. <b>").append(Commands.SUBSCRIBE).append(" 919898909890 slc-01</b>\"").append("\n");
		}else {
			String mobileNum = text.split(" ")[1];
			if(mobileNum.length()==12) {
				try {
					User userToUpdate = userRespository.findByMobileNumber(Long.parseLong(mobileNum));
					String courseCode = text.split(" ")[2];		
					Course courseToAssign = courseRepository.findByCode(StringUtils.upperCase(courseCode));
					if(courseToAssign!=null) {
						if(userToUpdate == null) {
							userToUpdate = userRespository.save(new User("New Subscriber", "SUBSCRIBER", mobileNum));
							log.info("New user created for mobile: "+mobileNum+ " with user_id : "+userToUpdate.getId());
						}
									
						if(userToUpdate.getCourses()!=null && userToUpdate.getCourses().indexOf(String.valueOf(courseToAssign.getId())) != - 1) {
							responseText.append("The user is already subscribed to this course.").append("\n");
						}else {
							String existingCourses = userToUpdate.getCourses()==null ? "" : userToUpdate.getCourses();
							String newCourses = courseToAssign.getId() + "##0," + existingCourses;															
							userToUpdate.setCourses(newCourses);
							userRespository.save(userToUpdate);
							responseText.append("The user with mobile: ").append("<b>").append(userToUpdate.getMobile()).append("</b>").
										append(" assigned to course :").append(" [").append("<b>").append(courseToAssign.getCode()).append("] ").append("</b>").
										append(courseToAssign.getName()).append("\n");
						}
					}else {
						responseText.append("Invalid course code: ").append("<b>").append(courseCode).append("</b>").append("\n").append("\n");
						responseText.append("To find out the correct course code, please type <b>").append(Commands.ALL_COURSES).append("</b>").append("\n");
					}
				}catch(Exception e) {
					log.debug("Exception in parsing the mobile number: "+mobileNum);
					responseText.append("The mobile number format is incorrect. Please prefix 91 to the 10-digit mobile number. And there should be no alphabets in the mobile number. E.g. to subscribe a user with mobile number 919898909890 for course slc-01, type <b>/subscribeuser 919898909890 slc-01</b>\"").append("\n");
				}
			}
		}
		
		return responseText;
	}

	private ReplyKeyboardMarkup getShareMyNumberKeyboardMarkUp() {
		KeyboardButton button = new KeyboardButton("Share My Number");
		button.setRequestContact(true);
		KeyboardRow row = new KeyboardRow();
		row.add(button);
		List<KeyboardRow> keyboard = new ArrayList<>(); 
		keyboard.add(row);
		
		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(); // Create the keyboard (list of keyboard rows)	        
		keyboardMarkup.setKeyboard(keyboard);
		keyboardMarkup.setOneTimeKeyboard(true);
		
		return keyboardMarkup;
	}

	@Override
	public String getBotUsername() {
		return DK_BOT_NAME;
	}

	@Override
	public String getBotToken() {
		return DK_TOKEN;
	}
}
