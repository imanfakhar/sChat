package de.sChat.server.data.messages;

import javax.persistence.Entity;

import de.sChat.server.data.messages.parser.Message;

public class ErrorMessage extends Message {

	Integer errorcode = 0;
	String error;
	
	public ErrorMessage(Integer errorcode, String error) {
		super();
		this.errorcode = errorcode;
		this.error = error;
	}

	public Integer getErrorcode() {
		return errorcode;
	}

	public String getError() {
		return error;
	}	
}