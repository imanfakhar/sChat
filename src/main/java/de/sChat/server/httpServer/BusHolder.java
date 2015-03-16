package de.sChat.server.httpServer;

import de.joshuaschnabel.framework.eventbus.bus.EventBus;

public class BusHolder {
		
	private static EventBus bus;

	public static EventBus getBus() {
		return bus;
	}

	public static void setBus(EventBus bus) {
		BusHolder.bus = bus;
	}
}
