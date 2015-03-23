package de.sChat.server.shared.messageHub;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

@Entity
public class ChatClient {

	@Id
	private String name;
	
	@Column
	@Type(type="timestamp")
	private Date lastseen;
	
	List<TextMessage> = new Ar
}
