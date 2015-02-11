package de.sChat.server.main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.session.HashSessionIdManager;
import org.eclipse.jetty.server.session.HashSessionManager;
import org.eclipse.jetty.server.session.SessionHandler;

import de.sChat.server.pages.*;

public class Main {

	public static void main(String[] args) throws Exception 
	{
		MyDumpHandler dump = new MyDumpHandler("test");
		MyDumpHandler dump2 = new MyDumpHandler("hallo");
		
        Server server = new Server(8910);
        
        // Specify the Session ID Manager
        HashSessionIdManager idmanager = new HashSessionIdManager();
        server.setSessionIdManager(idmanager);
        
        // Sessions are bound to a context.
        ContextHandler context = new ContextHandler("./");
        server.setHandler(context);
        
        ContextHandler context2 = new ContextHandler("/hallo/");
        server.setHandler(context2);
        
        // Create the SessionHandler (wrapper) to handle the sessions
        HashSessionManager manager = new HashSessionManager();
        
        SessionHandler sessions = new SessionHandler(manager);
        context.setHandler(sessions);
        
        SessionHandler sessions2 = new SessionHandler(manager);
        context2.setHandler(sessions2);
        
        sessions.setHandler(dump);
        sessions2.setHandler(dump2);
        
        server.start();
        server.join();
	}

}
