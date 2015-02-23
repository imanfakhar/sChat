package de.sChat.client.view.chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class SendButtonListener implements ActionListener {

	private JTextField textfield;

	public SendButtonListener(JTextField textfield) {
		this.textfield = textfield;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Senden: " + textfield.getText());
		textfield.setText("");
	}

}
