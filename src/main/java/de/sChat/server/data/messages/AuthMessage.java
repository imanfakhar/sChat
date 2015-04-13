package de.sChat.server.data.messages;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import de.sChat.server.data.chatClient.ChatClient;
import de.sChat.server.data.messages.parser.Message;

@Entity
public class AuthMessage extends Message {
	
	@Id
	private String uid = null;
 
	@ManyToOne
	private ChatClient owner;
	
	private Date creationDate = new Date();
	
	public AuthMessage() {
		// TODO Auto-generated constructor stub
	}

	public AuthMessage(ChatClient owner) {
		super();
		this.owner = owner;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public ChatClient getOwner() {
		return owner;
	}

	public void setOwner(ChatClient owner) {
		this.owner = owner;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public void generateUUID()
	{
		this.uid = UUID.randomUUID().toString();
	}

}