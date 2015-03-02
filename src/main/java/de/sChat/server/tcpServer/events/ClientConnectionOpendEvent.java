package de.sChat.server.tcpServer.events;

import de.joshuaschnabel.framework.eventbus.event.Event;
import de.sChat.server.tcpServer.ClientRunnable;
import de.sChat.server.tcpServer.HandlerRunnable;

public class ClientConnectionOpendEvent extends Event {

	private ClientRunnable handlerRunnable;


	public ClientConnectionOpendEvent(ClientRunnable handlerRunnable2) {
		this.handlerRunnable = handlerRunnable2;
	}


	public ClientRunnable getHandlerRunnable() {
		return handlerRunnable;
	}

}
