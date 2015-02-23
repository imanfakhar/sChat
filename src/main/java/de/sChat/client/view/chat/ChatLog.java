package de.sChat.client.view.chat;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;

import de.sChat.client.connector.StreamReader;

public class ChatLog implements WindowComponent {
	private JLabel label = new JLabel("<html>");

	public ChatLog(StreamReader streamReader) {
		label.setBackground(Color.white);
		Thread updaterThread = new Thread(new ChatLogUpdaterRunnable(
				streamReader, this));
		updaterThread.start();

	}

	public void addLine(String newLine) {
		System.out.println("Schreibe neue Zeile ins fenster: " + newLine);
		String text = label.getText();
		text += "<p>";
		text += newLine;
		label.setText(text);
	}

	@Override
	public Component getComponent() {
		return this.label;
	}
}
