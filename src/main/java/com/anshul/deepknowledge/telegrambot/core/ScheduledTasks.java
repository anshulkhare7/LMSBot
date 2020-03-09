package com.anshul.deepknowledge.telegrambot.core;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import com.anshul.deepknowledge.telegrambot.entity.Course;
import com.anshul.deepknowledge.telegrambot.entity.User;
import com.anshul.deepknowledge.telegrambot.service.CourseRepository;
import com.anshul.deepknowledge.telegrambot.service.UserRespository;
import com.anshul.deepknowledge.telegrambot.web.beans.SendMessageResponse;

@Component
public class ScheduledTasks {

	
	@Value("${telegrambot.token}")	  
	private String DK_TOKEN;
	
	@Value("${telegrambot.name}")
	private String DK_BOT_NAME;
	
	@Value("${telegrambot.api}")
	private String TELEGRAM_API_BASE_URL;

	@Value("${cron.telegrambot.hearbeat.active}")
	private String isHeartBeatActive;
	
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
	@Autowired
	private UserRespository userRespository;

	@Autowired
	private CourseRepository courseRepository;
	
    @Scheduled(cron = "${cron.telegrambot.hearbeat}")
    public void sendHeartBeatToAdmin() {
    	
    		String sendMessageUrl = TELEGRAM_API_BASE_URL + "bot" + DK_TOKEN + "/sendMessage";
    		if(Boolean.parseBoolean(isHeartBeatActive)) {
	    		User adminUser = userRespository.findByMobileNumber(919980066866l);
	    		Long chatId = adminUser.getChat_id();
	    		if(chatId==null) {
	    			log.info("Admin hasn't initiated the chat : "+adminUser.getMobile());
	    		}else {
	    			RestTemplate restTemplate = new RestTemplate();
			    	
			    	SendMessage sendMessage = new SendMessage();		    	
			    	StringBuilder messageText = new StringBuilder();
			    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ssZ");
			    	messageText.append("DK Chatbot server is alive at: "+simpleDateFormat.format(new Date()));
			    	sendMessage.setChatId(chatId).setText(messageText.toString());
			    	try {
				    	SendMessageResponse response = restTemplate.postForObject(sendMessageUrl, sendMessage, SendMessageResponse.class);	    	
				    	
				    	log.info("Heart Beat Response from client — OK Status: "+response.getOk());
				    	if(Boolean.parseBoolean(response.getOk())) {
				    		log.info("Heartbeat sent successfully.");
				    	}else {
				    		log.info("Failed to send the heartbeat.");
				    	}
			    	}catch(Exception e) {		    		
			    		log.info("Exception in sending the heartbeat to admin."+e.getMessage());
			    	}
	    		}
    		}
    }
    
    @Scheduled(cron = "${cron.telegrambot.dailylesson}")
    public void sendDailyLessons() {
    		
    		String sendMessageUrl = TELEGRAM_API_BASE_URL + "bot" + DK_TOKEN + "/sendMessage";
	    	List<User> userList = userRespository.findAll();
	    	for(User user: userList) {	    		
		    	try {
		    		if(user.getRole().equalsIgnoreCase("ADMIN"))
		    			continue;
		    		String userCourses = user.getCourses();
		    		Long chatId = user.getChat_id();
		    		if(chatId==null) {
		    			log.info("User hasn't initiated the chat : "+user.getMobile());
		    			continue;
		    		}
		    		
		    		if(userCourses!=null) {		    			
		    			List<String> courseList = Arrays.asList(userCourses.split(","));
		    			StringBuilder courseListUpdated = new StringBuilder();
		    			
		    			for(String userCourse:courseList) {
		    				Long courseId = Long.parseLong(userCourse.split("##")[0]);
		    				
		    				Course course = courseRepository.findOne(courseId);
		    				String[] videos = course.getVideos().split("##");
		    				
		    				int nextLessonToSend = Integer.parseInt(userCourse.split("##")[1]);		    				
		    				String videoToSend = videos[nextLessonToSend];
		    				
		    				
		    				log.info("Sending to user: "+user.getId()+" | Course: "+courseId + " Video Lesson: ["+nextLessonToSend+"] youtube: "+videoToSend);
			    		    	RestTemplate restTemplate = new RestTemplate();
			    		    	
			    		    	SendMessage sendMessage = new SendMessage();
			    		    	StringBuilder messageText = new StringBuilder();
			    		    	messageText.append("As part of your subscription to Course : <b>").append(course.getName()).append("</b> , here's your next lesson - ").append("\n").append("\n");
			    		    	messageText.append(videoToSend);
			    		    	sendMessage.setChatId(chatId).setParseMode(ParseMode.HTML).setText(messageText.toString());			    		    	
			    		    	SendMessageResponse response = restTemplate.postForObject(sendMessageUrl, sendMessage, SendMessageResponse.class);	    	
			    		    	
			    		    	log.info("Response from client: OK Status: "+response.getOk());
			    		    	
			    		    	if(Boolean.parseBoolean(response.getOk())) {
				    		    	nextLessonToSend = (nextLessonToSend+1 < videos.length) ? nextLessonToSend+1 : 0;
			    				String userCourseUpdated = courseId + "##" + nextLessonToSend;
			    				courseListUpdated.append(userCourseUpdated).append(",");
			    		    	}else {
			    		    		log.info("Failed to send the video for user: "+user.getId()+" Course: "+courseId+ " Video: "+videoToSend);
			    		    	}
		    			}
		    			user.setCourses(courseListUpdated.toString());
		    			userRespository.save(user);
		    		}
		    	}catch(Exception e) {
		    		log.debug("Exception in sending videos for user : "+user.getId());		    		
		    		e.printStackTrace();
		    	}
	    	}
	    	
	    	/*
	    	  Get all the users subscribed to 
	    	 */
	    	  
    }
}