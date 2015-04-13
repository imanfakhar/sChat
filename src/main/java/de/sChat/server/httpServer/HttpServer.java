package de.sChat.server.httpServer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import de.joshuaschnabel.framework.eventbus.bus.EventBus;

public class HttpServer {
	
	public HttpServer(EventBus bus, EntityManager manager, Integer httpport) 
	{
		BusHolder.setBus(bus);
		EntityManagerHolder.setEntityManager(manager);
		
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
 
        Server jettyServer = new Server(httpport);
        
        HandlerCollection handlerCollection = new HandlerCollection();
        handlerCollection.setHandlers(new Handler[] {context});
                
        ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/msg/*");
        jerseyServlet.setInitOrder(0); 
        jerseyServlet.setInitParameter("jersey.config.server.provider.classnames", MsgPoint.class.getCanonicalName());
  
        ServletHolder jerseyServlet2 = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/api/*");
        jerseyServlet2.setInitOrder(1); 
        jerseyServlet2.setInitParameter("jersey.config.server.provider.classnames", ApiPoint.class.getCanonicalName());
        
        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(false);
        resource_handler.setWelcomeFiles(new String[]{ "index.html" });
        resource_handler.setResourceBase("./src/main/resources/de/sChat/staticResources/");
        
        ContextHandler ctx = new ContextHandler("/my-files"); /* the server uri path */
        ctx.setHandler(resource_handler);
        
        handlerCollection.setHandlers(new Handler[] {ctx,context});
        
        jettyServer.setHandler(handlerCollection);
        
        try {
            jettyServer.start();
            jettyServer.join();
        } catch (Exception e) {
			e.printStackTrace();
		} finally {
            jettyServer.destroy();
        }
	}

}