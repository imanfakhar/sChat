package de.sChat.server.httpServer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.joshuaschnabel.framework.eventbus.bus.EventBus;
import de.sChat.server.shared.events.IncommingMessageEvent;
import de.sChat.server.shared.messages.MessageParser;
 
@Path("/msg")
public class MsgPoint {
 
    @GET
    @Path("getMessage")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getMessage() {
        return "{\"data\":{\"message\":\"Test\",\"name\":\"hallo\"},\"type\":\"message\"}";
    }
    
    @POST
    @Path("sendMessage")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String sendMessage(String msg) {
    	EventBusWrapper.getBus().publishSync(new IncommingMessageEvent(MessageParser.parseMessage(msg)));
        return "{}";
    }
}
