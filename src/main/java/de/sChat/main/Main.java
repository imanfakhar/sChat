package de.sChat.main;

import de.sChat.server.ChatServer;
import de.sChat.server.data.messages.ServerConnectMessage;
import de.sChat.server.data.messages.TextMessage;
import de.sChat.server.data.messages.parser.MessageParser;

public class Main {

	public static void main(String[] args) throws Exception 
	{
		System.out.println(MessageParser.parseMessage(new ServerConnectMessage(0)));
		ChatServer chatserver = new ChatServer(44444, 8080);
	}

}
