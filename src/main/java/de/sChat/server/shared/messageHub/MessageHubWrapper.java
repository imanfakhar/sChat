package de.sChat.server.shared.messageHub;


public class MessageHubWrapper {
	
	static private MessageHub hub;

	public static MessageHub getHub() {
		return hub;
	}

	public static void setHub(MessageHub hub) {
		MessageHubWrapper.hub = hub;
	}

}
