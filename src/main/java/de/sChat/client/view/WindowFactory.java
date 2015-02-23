package de.sChat.client.view;

import de.sChat.client.view.chat.ChatLog;
import de.sChat.client.view.chat.ChatWindow;
import de.sChat.client.view.chat.ChatWindowSimple;
import de.sChat.client.view.chat.InputField;

public class WindowFactory {

	public ChatWindow getDefaultChatWindow() {
		ChatWindowSimple simpleWindow = new ChatWindowSimple();
		ChatLog chat = new ChatLog();
		InputField input = new InputField();
		simpleWindow.setChat(chat);
		simpleWindow.setInput(input);
		return simpleWindow;
	}
}
