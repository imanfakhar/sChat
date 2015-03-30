package de.sChat.server.data.events;

import de.joshuaschnabel.framework.eventbus.event.Event;
import de.sChat.server.data.messages.parser.Message;

public class IncommingMessageEvent extends Event {
	
	private Message msg;

	public IncommingMessageEvent(Message msg) {
		this.msg = msg;
	}

	public Message getMsg() {
		return msg;
	}

}