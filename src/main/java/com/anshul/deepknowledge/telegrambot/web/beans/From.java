package com.anshul.deepknowledge.telegrambot.web.beans;

public class From {
	
	private Long id;
	private boolean is_bot;
	private String first_name;
	private String last_name;
	private String user_name;
	private String language_code;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isIs_bot() {
		return is_bot;
	}
	public void setIs_bot(boolean is_bot) {
		this.is_bot = is_bot;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getLanguage_code() {
		return language_code;
	}
	public void setLanguage_code(String language_code) {
		this.language_code = language_code;
	}
		
}
