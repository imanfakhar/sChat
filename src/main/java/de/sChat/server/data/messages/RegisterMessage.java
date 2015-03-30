package de.sChat.server.data.messages;

import javax.persistence.Entity;

import de.sChat.server.data.messages.parser.Message;

public class RegisterMessage extends Message {
	
	String username;
	String password;
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public RegisterMessage(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}	
}