package de.sChat.client.view.chat;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.sChat.client.Message;
import de.sChat.client.connector.StreamReader;

public class ChatLog implements WindowComponent {
	private JLabel label = new JLabel("<html>");
	private JPanel panel = new JPanel(new GridLayout(1, 1));
	private ChatColorManager colorManager = new ChatColorManager();

	public ChatLog(StreamReader streamReader) {
		label.setOpaque(false);
		panel.add(label);
		panel.setBackground(Color.WHITE);
		Thread updaterThread = new Thread(new ChatLogUpdaterRunnable(
				streamReader, this));
		updaterThread.start();

	}

	public void addMessage(Message message) {
		String text = label.getText();
		text += "<p Color=\""
				+ colorManager.getColorForPerson(message.getSender()) + "\">";
		text += message.getMessage();// TODO
		label.setText(text);
	}

	@Override
	public Component getComponent() {
		return this.panel;
	}
}
