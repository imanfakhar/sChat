package de.sChat.client.view.chat.manageConnection;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ManagerWindowItemSimple implements ManagerWindowItem {

	private String id;
	private JTextField txtName;
	private JPanel panel;

	public ManagerWindowItemSimple(String text, String id) {
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel label = new JLabel(text);
		txtName = new JTextField(10);
		panel.add(label);
		panel.add(txtName);
		this.id = id;
	}

	@Override
	public Component getComponent() {
		return panel;
	}

	@Override
	public String getValueAsString() {
		System.out.println("ID::" + id + "  value:" + txtName.getText());
		return txtName.getText();
	}

	@Override
	public String getIdentifier() {
		return id;
	}

}
