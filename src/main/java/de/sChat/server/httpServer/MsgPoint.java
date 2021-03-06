package de.sChat.server.httpServer;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.sChat.server.data.chatClient.ChatClient;
import de.sChat.server.data.dao.DaoChatClient;
import de.sChat.server.data.dao.DaoTextMessage;
import de.sChat.server.data.events.IncommingMessageEvent;
import de.sChat.server.data.messages.AuthMessage;
import de.sChat.server.data.messages.TextMessage;
import de.sChat.server.data.messages.parser.Message;
import de.sChat.server.data.messages.parser.MessageParser;

@Path("/msg")
public class MsgPoint {

	@GET
	@Path("getMessage")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getMessage(@QueryParam("time") Long time, @QueryParam("name") String name, @QueryParam("auth") String auth) {
		DaoChatClient dao = new DaoChatClient(EntityManagerHolder.getEntityManager());
		DaoTextMessage daotm = new DaoTextMessage(EntityManagerHolder.getEntityManager());
		ChatClient cc = dao.getChatClient(name);
		boolean ok = false;
		for (AuthMessage auths : cc.getAuths()) {
			if(auths.getUid().equals(auth))
				ok = true;
		}
		if(!ok)
			return "{}";
		List<TextMessage> messages = daotm.getMessagesSince(cc, new Date(time * 1000));
		String result = "[";
		for (TextMessage textMessage : messages) {
			if(result.length() > 2)
				result += ",";
			result += MessageParser.parseMessage(textMessage);
		}
		result += "]";
		cc.setLastseen(new Date());
		dao.setChatClient(cc);
		return result;
	}

	@POST
	@Path("sendMessage")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String sendMessage(String msg) {
		Message message = MessageParser.parseMessage(msg);
		BusHolder.getBus().publishSync(new IncommingMessageEvent(message));
		return "{}";
	}
}