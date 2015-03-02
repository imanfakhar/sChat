package de.sChat.server.chatserver.event;

import de.joshuaschnabel.framework.eventbus.event.Event;
import de.sChat.server.chatserver.ClientRunnable;
import de.sChat.server.chatserver.HandlerRunnable;

public class ClientConnectionOpendEvent extends Event {

	private ClientRunnable handlerRunnable;


	public ClientConnectionOpendEvent(ClientRunnable handlerRunnable2) {
		this.handlerRunnable = handlerRunnable2;
	}


	public ClientRunnable getHandlerRunnable() {
		return handlerRunnable;
	}

}
