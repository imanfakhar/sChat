package de.sChat.client.view;

import java.awt.event.ActionListener;

import de.sChat.client.connector.StreamReader;
import de.sChat.client.connector.StreamWriter;
import de.sChat.client.view.chat.ChatLog;
import de.sChat.client.view.chat.ChatWindow;
import de.sChat.client.view.chat.ChatWindowSimple;
import de.sChat.client.view.chat.InputField;
import de.sChat.client.view.chat.manageConnection.ManagerWindow;
import de.sChat.client.view.chat.manageConnection.ManagerWindowItem;
import de.sChat.client.view.chat.manageConnection.ManagerWindowItemIp;
import de.sChat.client.view.chat.manageConnection.ManagerWindowItemOk;
import de.sChat.client.view.chat.manageConnection.ManagerWindowItemSimple;
import de.sChat.client.view.chat.manageConnection.ManagerWindowItemText;

public class WindowFactoryAndDrecorator {

	public ChatWindow getDefaultChatWindow(StreamWriter writer,
			StreamReader reader) {
		ChatWindowSimple simpleWindow = new ChatWindowSimple();
		ChatLog chat = new ChatLog(reader);
		InputField input = new InputField(writer);
		simpleWindow.setChat(chat);
		simpleWindow.setInput(input);
		return simpleWindow;
	}

	public ManagerWindow addDefaultItemsToManagerWindow(
			ActionListener listener, ManagerWindow managerWindow) {
		ManagerWindowItem ipItem = new ManagerWindowItemIp();
		ManagerWindowItem nameItem = new ManagerWindowItemSimple("Name:",
				"NAME");
		ManagerWindowItem portItem = new ManagerWindowItemSimple("Port:",
				"PORT");

		ManagerWindowItem itemWelcome = new ManagerWindowItemText(
				"Welcome to our chat !");
		ManagerWindowItem itemWelcomePersonalData = new ManagerWindowItemText(
				"Your Profile:");

		ManagerWindowItem itemConnectionData = new ManagerWindowItemText(
				"Connection: ");

		ManagerWindowItem itemOk = new ManagerWindowItemOk(listener);

		managerWindow.addItem(itemWelcome);
		managerWindow.addItem(itemWelcomePersonalData);
		managerWindow.addItem(nameItem);
		managerWindow.addItem(itemConnectionData);
		managerWindow.addItem(ipItem);
		managerWindow.addItem(portItem);
		managerWindow.addItem(itemOk);
		return managerWindow;
	}
}
