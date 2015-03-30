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
import de.sChat.server.data.dao.DaoAuthMessage;
import de.sChat.server.data.dao.DaoChatClient;
import de.sChat.server.data.dao.DaoTextMessage;
import de.sChat.server.data.events.IncommingMessageEvent;
import de.sChat.server.data.messages.AuthMessage;
import de.sChat.server.data.messages.ErrorMessage;
import de.sChat.server.data.messages.LoginMessage;
import de.sChat.server.data.messages.RegisterMessage;
import de.sChat.server.data.messages.TextMessage;
import de.sChat.server.data.messages.parser.Message;
import de.sChat.server.data.messages.parser.MessageParser;

@Path("/msg")
public class MsgPoint {

	@GET
	@Path("getMessage")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getMessage(@QueryParam("time") Long time, @QueryParam("name") String name) {
		DaoChatClient dao = new DaoChatClient(EntityManagerHolder.getEntityManager());
		DaoTextMessage daotm = new DaoTextMessage(EntityManagerHolder.getEntityManager());
		ChatClient cc = dao.getChatClient(name);
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
		if(message instanceof RegisterMessage)
			processRegisterMessage(message);
		else if(message instanceof LoginMessage)
			return MessageParser.parseMessage(processLoginMessage(message));
		else
			if(!BusHolder.getBus().publishSync(new IncommingMessageEvent(message)))		
				return MessageParser.parseMessage(new ErrorMessage(403, "Username not possible"));
		return "{}";
	}
	

	private Message processLoginMessage(Message message) {
		DaoChatClient daocc = new DaoChatClient(EntityManagerHolder.getEntityManager());
		DaoAuthMessage daoa = new DaoAuthMessage(EntityManagerHolder.getEntityManager());
		LoginMessage msg = (LoginMessage) message;
		ChatClient cc = daocc.getChatClient(msg.getUsername());
		if(cc.getPassword().equals(msg.getPassword()))
		{
			AuthMessage amsg = new AuthMessage();
			amsg.setOwner(cc);
			cc.getAuths().add(amsg);
			return amsg;
		}
		else
			return new ErrorMessage(403, "Login Ungültig");
	}

	private boolean processRegisterMessage(Message message) {
		DaoChatClient daocc = new DaoChatClient(EntityManagerHolder.getEntityManager());
		RegisterMessage msg = (RegisterMessage) message;
		ChatClient cl = daocc.getChatClient(msg.getUsername());
		if(cl == null)
		{
			ChatClient newc = new ChatClient(msg.getUsername());
			newc.setPassword(msg.getPassword());
			daocc.setChatClient(newc);
			return true;
		}
		return false;
	}
}