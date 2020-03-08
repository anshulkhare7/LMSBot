package com.anshul.deepknowledge.telegrambot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegrambotCommandlineRunner implements CommandLineRunner{

	private static final Logger log = LoggerFactory.getLogger(TelegrambotCommandlineRunner.class);

	@Autowired
	private DKTelegramBot dkTelegramBot;
	
	public void run(String...arg0) throws Exception{
		
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		
        try {
            telegramBotsApi.registerBot(dkTelegramBot);
        } catch (TelegramApiException e) {
        		log.error("TelegramAPI Exception", e);
        }
	}

}