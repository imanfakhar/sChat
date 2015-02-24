package de.sChat.client;

import de.sChat.client.view.WindowFactoryAndDrecorator;
import de.sChat.client.view.chat.manageConnection.ManagerWindow;

public class Client {
	public Client() {
		ManagerWindow managerWindow = new ManagerWindow();
		ClientStarterListener listener = new ClientStarterListener(
				managerWindow);
		new WindowFactoryAndDrecorator().addDefaultItemsToManagerWindow(
				listener, managerWindow);
		managerWindow.publish();

	}

	public static void main(String[] args) {
		new Client();
	}
}
