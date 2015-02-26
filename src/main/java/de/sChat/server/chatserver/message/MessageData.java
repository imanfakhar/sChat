package de.sChat.server.chatserver.message;

public class MessageData {

	private String nachricht;
	private String name;

	public MessageData(String name, String nachricht) {
		this.name = name;
		this.nachricht = nachricht;
	}

	public String getNachricht() {
		return nachricht;
	}

	public String getName() {
		return name;
	}

}
