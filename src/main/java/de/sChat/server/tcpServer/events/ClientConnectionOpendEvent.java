package de.sChat.server.tcpServer.events;

import de.joshuaschnabel.framework.eventbus.event.Event;
import de.sChat.server.tcpServer.HandlerRunnable;

public class ClientConnectionOpendEvent extends Event {

	private HandlerRunnable handlerRunnable;


	public ClientConnectionOpendEvent(HandlerRunnable handlerRunnable2) {
		this.handlerRunnable = handlerRunnable2;
	}


	public HandlerRunnable getHandlerRunnable() {
		return handlerRunnable;
	}

}
