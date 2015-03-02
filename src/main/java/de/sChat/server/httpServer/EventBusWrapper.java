package de.sChat.server.httpServer;

import de.joshuaschnabel.framework.eventbus.bus.EventBus;

public class EventBusWrapper {
	
	static private EventBus bus;

	public static EventBus getBus() {
		return bus;
	}

	public static void setBus(EventBus bus) {
		EventBusWrapper.bus = bus;
	}

}
