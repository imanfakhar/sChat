package de.sChat.server.chatserver.event;

import de.joshuaschnabel.framework.eventbus.event.Event;
import de.sChat.server.chatserver.ClientRunnable;

public class ClientConnectionClosedEvent extends Event {

	private ClientRunnable handlerRunnable;


	public ClientConnectionClosedEvent(ClientRunnable handlerRunnable2) {
		this.handlerRunnable = handlerRunnable2;
	}


	public ClientRunnable getHandlerRunnable() {
		return handlerRunnable;
	}

}
