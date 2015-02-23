package de.sChat.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.sChat.client.view.WindowFactoryAndDrecorator;
import de.sChat.client.view.chat.ChatWindow;
import de.sChat.client.view.chat.manageConnection.ManagerWindow;

public class ClientStarterListener implements ActionListener {
	private ManagerWindow window;

	public ClientStarterListener(ManagerWindow window) {
		this.window = window;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		window.dispose();
		ChatWindow chatWindow = new WindowFactoryAndDrecorator()
				.getDefaultChatWindow();
		chatWindow.publish();

	}

}
