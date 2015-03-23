package de.sChat.server.data.events;

import de.joshuaschnabel.framework.eventbus.event.Event;
import de.sChat.server.data.messages.InternMessage;

public class IncommingMessageEvent extends Event {
	
	private InternMessage msg;

	public IncommingMessageEvent(InternMessage msg) {
		this.msg = msg;
	}

	public InternMessage getMsg() {
		return msg;
	}

}