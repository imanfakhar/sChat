package de.sChat.server.tcpServer.events;

import de.joshuaschnabel.framework.eventbus.event.Event;
import de.sChat.server.tcpServer.HandlerRunnable;

public class ClientConnectionClosedEvent extends Event {

	private HandlerRunnable handlerRunnable;


	public ClientConnectionClosedEvent(HandlerRunnable handlerRunnable2) {
		this.handlerRunnable = handlerRunnable2;
	}


	public HandlerRunnable getHandlerRunnable() {
		return handlerRunnable;
	}

}
