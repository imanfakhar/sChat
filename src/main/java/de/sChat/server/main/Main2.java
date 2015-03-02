package de.sChat.server.main;

import de.sChat.server.chatserver.ChatServer;

public class Main2 {

	public static void main(String[] args) throws Exception 
	{
		ChatServer chatserver = new ChatServer(4320);
		chatserver.createClient("localhost", 4321);
	}
}
