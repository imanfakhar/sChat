package de.sChat.server.data.messages;

import de.sChat.server.data.messages.parser.Message;

public class ServerConnectMessage extends Message {

	Integer time = 0;
	
	public ServerConnectMessage(Integer time) {
		super();
		this.time = time;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}
}