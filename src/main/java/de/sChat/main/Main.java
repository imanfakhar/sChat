package de.sChat.main;

import de.sChat.server.ChatServer;
import de.sChat.server.shared.messages.Message;
import de.sChat.server.shared.messages.MessageParser;
import de.sChat.server.shared.messages.TextMessage;

public class Main {

	public static void main(String[] args) throws Exception 
	{
		System.out.println(MessageParser.parseMessage(new TextMessage("hallo", "Test")));
		ChatServer chatserver = new ChatServer(4321, 8080);
	}

}
