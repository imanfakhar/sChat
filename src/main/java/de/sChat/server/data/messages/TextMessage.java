package de.sChat.server.data.messages;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import de.sChat.server.data.chatClient.ChatClient;

@Entity
public class TextMessage extends InternMessage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id = null;
 
	@ManyToOne
	private ChatClient owner;
	
	private String message;
	
	private String name;
	
	private Date creationTime = new Date();
	
	public TextMessage() {
	}

	public TextMessage(String name, String nachricht) {
		this.name = name;
		this.message = nachricht;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ChatClient getOwner() {
		return owner;
	}

	public void setOwner(ChatClient owner) {
		this.owner = owner;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
}