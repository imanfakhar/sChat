package de.sChat.client.view.chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import de.sChat.client.connector.StreamWriter;

public class SendButtonListener implements ActionListener {

	private JTextField textfield;
	private StreamWriter writer;

	public SendButtonListener(JTextField textfield, StreamWriter writer) {
		this.textfield = textfield;
		this.writer = writer;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Senden: " + textfield.getText());
		writer.pushMessage(textfield.getText());
		textfield.setText("");
	}

}
