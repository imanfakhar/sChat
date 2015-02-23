package de.sChat.client;

import de.sChat.client.view.WindowFactory;
import de.sChat.client.view.chat.ChatWindow;
import de.sChat.client.view.chat.manageConnection.ManagerWindow;

public class Client {
	public Client() {
		ManagerWindow managerWindow = new WindowFactory()
				.getDefaultManagerWindow();
		managerWindow.publish();

		ChatWindow window = new WindowFactory().getDefaultChatWindow();
		window.publish();
	}

	public static void main(String[] args) {
		new Client();
	}
}
