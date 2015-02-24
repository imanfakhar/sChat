package de.sChat.client.view.chat.manageConnection;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ManagerWindowItemOk implements ManagerWindowItem {
	JPanel panel;

	public ManagerWindowItemOk(ActionListener actionListener) {
		panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton button = new JButton("Connect!");
		button.addActionListener(actionListener);
		panel.add(button);
	}

	@Override
	public Component getComponent() {
		return panel;
	}

	@Override
	public String getValueAsString() {
		return this.toString();
	}

	@Override
	public String getIdentifier() {
		return this.toString();
	}

}
