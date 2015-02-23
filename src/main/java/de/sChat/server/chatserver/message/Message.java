package de.sChat.server.chatserver.message;

public class Message {
	
	@Override
	public String toString() {
		return "Message [name=" + name + ", nachricht=" + nachricht + "]";
	}
	String name;
	String nachricht;
	public Message(String name, String nachricht) {
		super();
		this.name = name;
		this.nachricht = nachricht;
	}
	public String getName() {
		return name;
	}
	public String getNachricht() {
		return nachricht;
	}

}
