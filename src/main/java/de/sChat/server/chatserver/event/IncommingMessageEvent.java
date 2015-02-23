package de.sChat.server.chatserver.event;

import de.joshuaschnabel.framework.eventbus.event.Event;
import de.sChat.server.chatserver.message.Message;

public class IncommingMessageEvent extends Event {
	
	private Message msg;

	public IncommingMessageEvent(Message msg) {
		this.msg = msg;
	}

	public Message getMsg() {
		return msg;
	}

}
