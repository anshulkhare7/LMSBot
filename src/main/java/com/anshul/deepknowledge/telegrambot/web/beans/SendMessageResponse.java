package com.anshul.deepknowledge.telegrambot.web.beans;

public class SendMessageResponse {
	private String ok;
	private Result result;
	
	public SendMessageResponse() {
		super();
	}
	
	public SendMessageResponse(String ok, Result result) {		
		this.ok = ok;
		this.result = result;
	}
	
	public String getOk() {
		return ok;
	}
	public void setOk(String ok) {
		this.ok = ok;
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	
	
}
