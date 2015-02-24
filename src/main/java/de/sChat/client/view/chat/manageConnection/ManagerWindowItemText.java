package de.sChat.client.view.chat.manageConnection;

import java.awt.Component;

import javax.swing.JLabel;

public class ManagerWindowItemText implements ManagerWindowItem {

	private JLabel label;

	public ManagerWindowItemText(String text) {
		label = new JLabel(text);
	}

	@Override
	public Component getComponent() {
		return label;
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
