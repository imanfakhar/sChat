package de.sChat.client.view.chat.manageConnection;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ManagerWindowItemName implements ManagerWindowItem {

	public static final String NAME = "NAME";
	private JTextField txtName;
	private JPanel panel;

	public ManagerWindowItemName() {
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel label = new JLabel("Name: ");
		txtName = new JTextField(10);
		panel.add(label);
		panel.add(txtName);
		txtName = new JTextField();
	}

	@Override
	public Component getComponent() {
		return panel;
	}

	@Override
	public String getValueAsString() {
		return txtName.getText();
	}

	@Override
	public String getIdentifier() {
		return NAME;
	}

}
