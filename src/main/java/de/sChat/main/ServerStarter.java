package de.sChat.main;

import de.sChat.server.ChatServer;


public class ServerStarter {

	public static void main(String[] args) throws Exception 
	{
		int httport = 8080;
		int tcport = 44444;
		String serveraddress = "";
		int serverport = 0;
		
		if(args.length >= 1)
			httport = Integer.parseInt(args[0]);
		if(args.length >= 2)
			tcport = Integer.parseInt(args[1]);
		if(args.length >= 3)
			serveraddress = args[2];
		if(args.length >= 4)
			serverport = Integer.parseInt(args[3]);
		
		ChatServer chatserver = new ChatServer(tcport, httport);
		chatserver.starttcp();
		if(!serveraddress.equals(""))
			chatserver.connect(serveraddress,serverport);
		chatserver.starthttp();
	}

}
