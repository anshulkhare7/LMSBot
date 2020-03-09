package com.anshul.deepknowledge.telegrambot.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BotController {
	
	@GetMapping("/test")
	public List<String> testing(){
		return Arrays.asList("Testing 1", "Testing 2");
	}
	
}
