package de.sChat.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import de.sChat.client.connector.Connection;
import de.sChat.client.connector.Connector;
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
		System.out.println("Connection to: " + window.getValueByName("IP")
				+ " with port: " + window.getValueByName("PORT"));
		Connector connector = new Connector(window.getValueByName("IP"),
				window.getValueByName("PORT"));
		Connection connection = null;
		try {
			connection = connector.connectWithName("TestTest");
			ChatWindow chatWindow = new WindowFactoryAndDrecorator()
					.getDefaultChatWindow(connection.getStreamwriter(),
							connection.getStreamReader());
			chatWindow.publish();
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}// TODO

	}
}
