package com.anshul.deepknowledge.telegrambot.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;

@Entity(name="user")
public class User {
	
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String role;
	private String mobile;
	private Long chat_id;
	private String courses;
	
	protected User() {
		
	}
	
	public User(String name, String role, String mobile) {
		super();
		this.name = name;
		this.role = role;
		this.mobile = mobile;		
	}
	
	public User(String name, String role, String mobile, Long chatId) {
		super();
		this.name = name;
		this.role = role;
		this.mobile = mobile;		
		this.chat_id = chatId;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getChat_id() {
		return chat_id;
	}

	public void setChat_id(Long chat_id) {
		this.chat_id = chat_id;
	}

	public String getCourses() {
		return courses;
	}

	public void setCourses(String courses) {
		this.courses = courses;
	}

}
