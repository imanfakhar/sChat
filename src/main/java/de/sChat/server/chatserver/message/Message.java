package de.sChat.server.chatserver.message;

public class Message {
	
	MessageData data;
	String type;
	
	public Message(String name, String nachricht) {
		this(name,nachricht,"message");
	}
	
	public Message(String name, String nachricht, String type) {
		data = new MessageData(name,nachricht);
		this.type = type;
	}
	
	public String getName() {
		return data.getName();
	}
	
	public String getNachricht() {
		return data.getNachricht();
	}

	public String getType() {
		return type;
	}

	
}
