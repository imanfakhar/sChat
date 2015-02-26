package de.sChat.client;

public class Message {

	private String sender;
	private String message;

	public Message(String jsonString) {
		String name = jsonString.substring(jsonString.indexOf("sender\":\""));
		this.sender = name.substring(0, name.indexOf("\""));
		String message = jsonString.substring(jsonString
				.indexOf("message\":\""));
		this.message = message.substring(0, message.indexOf("\""));
	}

	public Message(String sender, String message) {
		this.sender = sender;
		this.message = message;
	}

	public String getSender() {
		return sender;
	}

	public String getMessage() {
		return message;
	}

	public String getJSONString() {
		return "{\"type\":\"message\" \"data\":{\"sender\":\"" + sender
				+ "\" \"message\":\"" + message + "\"}}";
	}
}
