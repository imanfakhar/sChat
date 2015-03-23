package de.sChat.server.shared.messageHub;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

import de.sChat.server.shared.messages.TextMessage;

@Entity
public class ChatClient {

	@Id
	private String name;
	
	@Column
	@Type(type="timestamp")
	private Date lastseen;
	
	@OneToMany(mappedBy="owner", cascade=CascadeType.ALL)
	private List<TextMessage> messages = new ArrayList<TextMessage>();
}
