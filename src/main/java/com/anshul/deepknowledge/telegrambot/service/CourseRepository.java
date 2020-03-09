package com.anshul.deepknowledge.telegrambot.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.anshul.deepknowledge.telegrambot.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{
	
	 @Query("SELECT c FROM course c WHERE c.code = ?1")
	 public Course findByCode(String code);
}
