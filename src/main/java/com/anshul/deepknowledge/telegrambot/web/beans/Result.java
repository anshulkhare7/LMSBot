package com.anshul.deepknowledge.telegrambot.web.beans;

public class Result {
	
	private Long message_id;
	private From from;
	private Chat chat;
	private Long date;
	private String text;
	
	public Result() {
		super();
	}
	
	public Result(Long message_id) {
		this.message_id = message_id;
	}
	
	public Long getMessage_id() {
		return message_id;
	}
	
	public void setMessage_id(Long message_id) {
		this.message_id = message_id;
	}
	public From getFrom() {
		return from;
	}
	public void setFrom(From from) {
		this.from = from;
	}
	public Chat getChat() {
		return chat;
	}
	public void setChat(Chat chat) {
		this.chat = chat;
	}
	public Long getDate() {
		return date;
	}
	public void setDate(Long date) {
		this.date = date;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}	
	
	
}
