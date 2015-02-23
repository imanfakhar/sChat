package de.sChat.client.view.chat;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;

public class ChatLog implements WindowComponent {
	private JLabel label = new JLabel();

	public ChatLog() {
		label.setBackground(Color.white);
	}

	@Override
	public Component getComponent() {
		return this.label;
	}
}
