package de.sChat.client.view.chat;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChatWindowSimple implements ChatWindow {
	private JPanel contentPane;
	private JFrame window;

	public ChatWindowSimple() {
		window = new JFrame();
		contentPane = new JPanel(new BorderLayout());
		window.setContentPane(contentPane);
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setSize(600, 600);

	}

	public void setChat(WindowComponent component) {
		contentPane.add(component.getComponent(), BorderLayout.CENTER);
	}

	public void setInput(WindowComponent component) {
		contentPane.add(component.getComponent(), BorderLayout.SOUTH);
	}

	@Override
	public void publish() {
		window.setVisible(true);
	}

}
