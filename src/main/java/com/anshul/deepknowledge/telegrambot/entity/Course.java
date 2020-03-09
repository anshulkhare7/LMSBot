package com.anshul.deepknowledge.telegrambot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="course")
public class Course {

	@Id
	@GeneratedValue
	private Long id;
	private String code;
	private String name;
	private String details;
	private String status;
	
	@Column(columnDefinition="LONGVARCHAR")
	private String videos;	
	
	protected Course() {
		
	}
	
	public Course(String code, String name, String details, String status, String videos) {
		super();
		this.code = code;
		this.name = name;
		this.details = details;
		this.status = status;
		this.videos = videos;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getVideos() {
		return videos;
	}

	public void setVideos(String videos) {
		this.videos = videos;
	}


}
