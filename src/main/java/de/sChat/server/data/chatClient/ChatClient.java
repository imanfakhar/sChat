package de.sChat.server.data.chatClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import de.sChat.server.data.messages.AuthMessage;
import de.sChat.server.data.messages.TextMessage;

@Entity
public class ChatClient {
	
	@Id
	private String name;
	
	private Date lastseen;
	
	private String password;
	
	@OneToMany(mappedBy="owner" )
	private List<TextMessage> messages = new ArrayList<TextMessage>();
	
	@OneToMany(mappedBy="owner" )
	private List<AuthMessage> auths = new ArrayList<AuthMessage>();
	
	public ChatClient() {
	}
	
	public ChatClient(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getLastseen() {
		return lastseen;
	}

	public void setLastseen(Date lastseen) {
		this.lastseen = lastseen;
	}

	public List<TextMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<TextMessage> messages) {
		this.messages = messages;
	}

	public List<AuthMessage> getAuths() {
		return auths;
	}

	public void setAuths(List<AuthMessage> auths) {
		this.auths = auths;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
