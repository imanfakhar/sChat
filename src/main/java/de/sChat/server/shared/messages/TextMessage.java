package de.sChat.server.shared.messages;

public class TextMessage extends Message {

	private String message;
	private String name;

	public TextMessage(String name, String nachricht) {
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
