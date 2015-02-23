package de.sChat.client.view;

import de.sChat.client.view.chat.ChatLog;
import de.sChat.client.view.chat.ChatWindow;
import de.sChat.client.view.chat.ChatWindowSimple;
import de.sChat.client.view.chat.InputField;
import de.sChat.client.view.chat.manageConnection.ManagerWindow;
import de.sChat.client.view.chat.manageConnection.ManagerWindowItem;
import de.sChat.client.view.chat.manageConnection.ManagerWindowItemIp;
import de.sChat.client.view.chat.manageConnection.ManagerWindowItemName;

public class WindowFactory {

	public ChatWindow getDefaultChatWindow() {
		ChatWindowSimple simpleWindow = new ChatWindowSimple();
		ChatLog chat = new ChatLog();
		InputField input = new InputField();
		simpleWindow.setChat(chat);
		simpleWindow.setInput(input);
		return simpleWindow;
	}

	public ManagerWindow getDefaultManagerWindow() {
		ManagerWindow managerWindow = new ManagerWindow();
		ManagerWindowItem ipItem = new ManagerWindowItemIp();
		ManagerWindowItem nameItem = new ManagerWindowItemName();
		managerWindow.addItem(nameItem);
		managerWindow.addItem(ipItem);
		return managerWindow;
	}

}
