package de.sChat.server.data.chatClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

import de.sChat.server.data.messages.TextMessage;

@Entity
public class ChatClient {
	private String name;
	
	private Date lastseen;
	
	private List<TextMessage> messages = new ArrayList<TextMessage>();

	@Id
	@Column(name="name")
	public String getName() {
		return name;
	}

	@Column(name="lastseen")
	@Type(type="timestamp")
	public Date getLastseen() {
		return lastseen;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
	public List<TextMessage> getMessages() {
		return messages;
	}
}

