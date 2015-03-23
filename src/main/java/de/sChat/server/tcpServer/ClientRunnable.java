package de.sChat.server.tcpServer;

import de.joshuaschnabel.framework.eventbus.event.Handler;
import de.sChat.server.data.events.OutGoingMessageEvent;

public abstract class ClientRunnable implements Runnable{

	@Handler
	public abstract void outgoingMessage(OutGoingMessageEvent event);

}