package de.sChat.server.shared.events;

import de.joshuaschnabel.framework.eventbus.event.Event;
import de.sChat.server.shared.messages.Message;

public class OutGoingMessageEvent extends Event {
	
	private Message msg;

	public OutGoingMessageEvent(Message msg) {
		this.msg = msg;
	}

	public Message getMsg() {
		return msg;
	}

}
