package de.sChat.server.httpServer;

import java.util.regex.Pattern;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.sChat.server.data.chatClient.ChatClient;
import de.sChat.server.data.dao.DaoAuthMessage;
import de.sChat.server.data.dao.DaoChatClient;
import de.sChat.server.data.events.IncommingMessageEvent;
import de.sChat.server.data.messages.AuthMessage;
import de.sChat.server.data.messages.ErrorMessage;
import de.sChat.server.data.messages.LoginMessage;
import de.sChat.server.data.messages.RegisterMessage;
import de.sChat.server.data.messages.parser.Message;
import de.sChat.server.data.messages.parser.MessageParser;

@Path("/api")
public class ApiPoint {
	
	private static final Pattern rfc2822 = Pattern.compile(
	        "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$"
	);

	@POST
	@Path("login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String login(String msg) {
		Message message = MessageParser.parseMessage(msg);
		if(message instanceof LoginMessage)
			return MessageParser.parseMessage(processLoginMessage(message));
		return "{}";
	}
	
	@POST
	@Path("register")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String register(String msg) {
		Message message = MessageParser.parseMessage(msg);
		if(message instanceof RegisterMessage)
		{
			if(!rfc2822.matcher(((RegisterMessage) message).getUsername()).matches())
			{
				return MessageParser.parseMessage(new ErrorMessage(403, "Username not a E-Mail!"));
			}
			else if(!processRegisterMessage(message))
			{
				return MessageParser.parseMessage(new ErrorMessage(403, "Username exists!"));
			}
			else
			{
				BusHolder.getBus().publishSync(new IncommingMessageEvent(message));
			}
		}
		return "{}";
	}
	

	private Message processLoginMessage(Message message) {
		DaoChatClient daocc = new DaoChatClient(EntityManagerHolder.getEntityManager());
		DaoAuthMessage daoa = new DaoAuthMessage(EntityManagerHolder.getEntityManager());
		LoginMessage msg = (LoginMessage) message;
		ChatClient cc = daocc.getChatClient(msg.getUsername());
		if(cc.getPassword() != null && cc.getPassword().equals(msg.getPassword()))
		{
			AuthMessage amsg = new AuthMessage();
			amsg.generateUUID();
			amsg.setOwner(cc);
			cc.getAuths().add(amsg);
			daocc.setChatClient(cc);
			daoa.setAuthMessage(amsg);
			return amsg;
		}
		else
			return new ErrorMessage(403, "Login Ungültig");
	}

	private boolean processRegisterMessage(Message message) {
		DaoChatClient daocc = new DaoChatClient(EntityManagerHolder.getEntityManager());
		RegisterMessage msg = (RegisterMessage) message;
		ChatClient cl = daocc.getChatClient(msg.getUsername());
		if(cl.getPassword() == null)
		{
			cl.setPassword(msg.getPassword());
			daocc.setChatClient(cl);
			return true;
		}
		return false;
	}
}