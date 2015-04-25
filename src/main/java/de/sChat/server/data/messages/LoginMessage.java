package de.sChat.server.data.messages;

import de.sChat.server.data.messages.parser.Message;

public class LoginMessage extends Message {

	private String username;
	private String password;
	
	public LoginMessage(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}