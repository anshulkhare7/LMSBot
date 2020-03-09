package com.anshul.deepknowledge.telegrambot.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.anshul.deepknowledge.telegrambot.entity.User;

@Repository
public interface UserRespository extends JpaRepository<User, Long>{

	@Query(value = "SELECT * FROM user WHERE chat_id = ?1", nativeQuery = true)
	User findByChatId(Long chatId);
	
	@Query(value = "SELECT * FROM user WHERE mobile = ?1", nativeQuery = true)
	User findByMobileNumber(Long mobile);
	
	@Query(value = "SELECT * FROM user WHERE role = ?1", nativeQuery = true)
	List<User> findByRole(String role);	
}
