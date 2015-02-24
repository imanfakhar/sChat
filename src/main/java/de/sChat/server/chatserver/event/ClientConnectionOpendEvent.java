package de.sChat.server.chatserver.event;

import de.joshuaschnabel.framework.eventbus.event.Event;
import de.sChat.server.chatserver.HandlerRunnable;

public class ClientConnectionOpendEvent extends Event {

	private HandlerRunnable handlerRunnable;


	public ClientConnectionOpendEvent(HandlerRunnable handlerRunnable2) {
		this.handlerRunnable = handlerRunnable2;
	}


	public HandlerRunnable getHandlerRunnable() {
		return handlerRunnable;
	}

}
