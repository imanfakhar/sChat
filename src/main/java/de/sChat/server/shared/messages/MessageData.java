package de.sChat.server.shared.messages;

public class MessageData {

	private String message;
	private String name;

	public MessageData(String name, String nachricht) {
		this.name = name;
		this.message = nachricht;
	}

	public String getNachricht() {
		return message;
	}

	public String getName() {
		return name;
	}

}
