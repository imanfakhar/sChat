package de.sChat.client.view.chat;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputField implements WindowComponent {
	private JPanel panel = new JPanel(new BorderLayout());
	private JTextField textfield;
	private JButton sendButton;

	public InputField() {
		textfield = new JTextField();
		sendButton = new JButton("Send");
		sendButton.addActionListener(new SendButtonListener(textfield));
		panel.add(textfield, BorderLayout.CENTER);
		panel.add(sendButton, BorderLayout.EAST);

	}

	@Override
	public Component getComponent() {
		return panel;
	}

}
