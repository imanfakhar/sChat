package de.sChat.client;

import de.sChat.client.view.WindowFactory;
import de.sChat.client.view.chat.ChatWindow;

public class View {
	public View() {
		ChatWindow window = new WindowFactory().getDefaultChatWindow();
		window.publish();
	}

	public static void main(String[] args) {
		new View();
	}
}
