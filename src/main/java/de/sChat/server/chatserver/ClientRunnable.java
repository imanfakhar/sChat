package de.sChat.server.chatserver;

import de.joshuaschnabel.framework.eventbus.event.Handler;
import de.sChat.server.chatserver.event.OutGoingMessageEvent;

public abstract class ClientRunnable implements Runnable{

	@Handler
	public abstract void outgoingMessage(OutGoingMessageEvent event);

}