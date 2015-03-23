package de.sChat.server.httpServer;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.sChat.server.data.chatClient.ChatClient;
import de.sChat.server.data.chatClient.ChatClientComperator;
import de.sChat.server.data.dao.DaoChatClient;
import de.sChat.server.data.events.IncommingMessageEvent;
import de.sChat.server.data.messages.InternMessage;
import de.sChat.server.data.messages.TextMessage;
import de.sChat.server.data.messages.parser.MessageParser;
 
@Path("/msg")
public class MsgPoint {
 
    @GET
    @Path("getMessage")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getMessage(@QueryParam("time") long time, @QueryParam("name") String name) {
    	DaoChatClient dao = new DaoChatClient(EntityManagerHolder.getEntityManager());
    	ChatClient cc = dao.getChatClient(name);
    	String result = "[";
    	List<TextMessage> messages = ChatClientComperator.getSinceLastSeenMessages(cc.getMessages(), cc.getLastseen());
    	for (TextMessage textMessage : messages) {
    		if(result.length() > 2)
    			result += ",";
    		result += MessageParser.parseMessage(textMessage);
		}
    	return result + "]";
    }
    
    @POST
    @Path("sendMessage")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String sendMessage(String msg) {
    	InternMessage message = MessageParser.parseMessage(msg);
    	message.setSender(this);
    	BusHolder.getBus().publishSync(new IncommingMessageEvent(message));
        return "{}";
    }
}
=======
package de.sChat.server.httpServer;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.sChat.server.data.chatClient.ChatClient;
import de.sChat.server.data.chatClient.ChatClientComperator;
import de.sChat.server.data.dao.DaoChatClient;
import de.sChat.server.data.events.IncommingMessageEvent;
import de.sChat.server.data.messages.InternMessage;
import de.sChat.server.data.messages.TextMessage;
import de.sChat.server.data.messages.parser.MessageParser;
 
@Path("/msg")
public class MsgPoint {
 
    @GET
    @Path("getMessage")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getMessage(@QueryParam("time") long time, @QueryParam("name") String name) {
    	DaoChatClient dao = new DaoChatClient(EntityManagerHolder.getEntityManager());
    	ChatClient cc = dao.getChatClient(name);
    	String result = "[";
    	List<TextMessage> messages = ChatClientComperator.getSinceLastSeenMessages(cc.getMessages(), cc.getLastseen());
    	for (TextMessage textMessage : messages) {
    		if(result.length() > 2)
    			result += ",";
    		result += MessageParser.parseMessage(textMessage);
		}
    	return result + "]";
    }
    
    @POST
    @Path("sendMessage")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String sendMessage(String msg) {
    	InternMessage message = MessageParser.parseMessage(msg);
    	message.setSender(this);
    	BusHolder.getBus().publishSync(new IncommingMessageEvent(message));
        return "{}";
    }
}