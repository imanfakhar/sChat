package de.sChat.server.data.messages;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

import de.sChat.server.data.chatClient.ChatClient;

@Entity
public class TextMessage extends InternMessage {
	
	private Long id;

	private ChatClient owner;
	
	private String message;
	
	private String name;
	
	private Date creationTime;

	public TextMessage(String name, String nachricht) {
		this.name = name;
		this.message = nachricht;
	}

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	@ManyToOne 
	@JoinColumns({ 
	@JoinColumn(name = "TextMessage", referencedColumnName = "Id", nullable = false), 
	@JoinColumn(name = "ChatClient", referencedColumnName = "Name", nullable = false)})  
	public ChatClient getOwner() {
		return owner;
	}

	@Column(name="message")
	public String getMessage() {
		return message;
	}

	@Column(name="name")
	public String getName() {
		return name;
	}

	@Column(name="timestamp")
	@Type(type="timestamp")
	public Date getCreationTime() {
		return creationTime;
	}

}