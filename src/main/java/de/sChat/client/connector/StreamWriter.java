package de.sChat.client.connector;

import de.sChat.client.Message;

public interface StreamWriter {

	void pushMessage(Message message);

}