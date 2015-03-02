package de.sChat.main;

import de.sChat.server.ChatServer;

public class Main {

	public static void main(String[] args) throws Exception 
	{
		ChatServer chatserver = new ChatServer(4321, 8080);
	}

}
