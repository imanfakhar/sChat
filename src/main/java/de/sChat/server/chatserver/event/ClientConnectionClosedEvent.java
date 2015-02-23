package de.sChat.server.chatserver.event;

import de.joshuaschnabel.framework.eventbus.event.Event;
import de.sChat.server.chatserver.HandlerRunnable;

public class ClientConnectionClosedEvent extends Event {

	private HandlerRunnable handlerRunnable;

	public ClientConnectionClosedEvent(HandlerRunnable handlerRunnable) {
		this.handlerRunnable = handlerRunnable;
	}

	public HandlerRunnable getHandlerRunnable() {
		return handlerRunnable;
	}

}