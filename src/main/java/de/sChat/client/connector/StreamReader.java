package de.sChat.client.connector;

import de.sChat.client.Message;

public interface StreamReader {

	boolean newIncomingMessage();

	Message getNewMessage();

}