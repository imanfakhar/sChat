package de.sChat.server.httpServer;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.joshuaschnabel.framework.eventbus.bus.EventBus;
import de.sChat.server.shared.events.IncommingMessageEvent;
import de.sChat.server.shared.messageHub.HubMessage;
import de.sChat.server.shared.messageHub.MessageHubWrapper;
import de.sChat.server.shared.messages.MessageParser;
 
@Path("/msg")
public class MsgPoint {
 
    @GET
    @Path("getMessage")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getMessage(@QueryParam("time") long time) {
    	String result = "[";
    	ArrayList<HubMessage> messages = MessageHubWrapper.getHub().getMessages(time);
    	for (HubMessage hubMessage : messages) {
    		if(result.length() > 2)
    			result += ",";
    		result += MessageParser.parseMessage(hubMessage.getMsg());
		}
    	return result + "]";
    }
    
    @POST
    @Path("sendMessage")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String sendMessage(String msg) {
    	MessageHubWrapper.getHub().publishIncommingMessage(MessageParser.parseMessage(msg));
        return "{}";
    }
}
