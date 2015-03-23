package de.sChat.main;

import de.sChat.server.ChatServer;
import de.sChat.server.data.messages.InternMessage;
import de.sChat.server.data.messages.TextMessage;
import de.sChat.server.data.messages.parser.MessageParser;

public class Main {

	public static void main(String[] args) throws Exception 
	{
		System.out.println(MessageParser.parseMessage(new TextMessage("hallo", "Test")));
		ChatServer chatserver = new ChatServer(4321, 8080);
	}

}
