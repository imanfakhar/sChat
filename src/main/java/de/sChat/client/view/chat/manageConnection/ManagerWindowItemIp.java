package de.sChat.client.view.chat.manageConnection;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ManagerWindowItemIp implements ManagerWindowItem {

	public static final String Identifier = "IP";
	private JPanel panel;
	private JTextField txtFieldIp1;
	private JTextField txtFieldIp2;
	private JTextField txtFieldIp3;
	private JTextField txtFieldIp4;

	public ManagerWindowItemIp() {
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel label = new JLabel("IP-Address: ");
		panel.add(label);
		txtFieldIp1 = new JTextField();
		panel.add(txtFieldIp1);
		txtFieldIp1.setColumns(3);
		JLabel ipDot = new JLabel(".");
		panel.add(ipDot);
		txtFieldIp2 = new JTextField();
		panel.add(txtFieldIp2);
		txtFieldIp2.setColumns(3);
		ipDot = new JLabel(".");
		panel.add(ipDot);
		txtFieldIp3 = new JTextField();
		panel.add(txtFieldIp3);
		ipDot = new JLabel(".");
		txtFieldIp3.setColumns(3);
		panel.add(ipDot);
		txtFieldIp4 = new JTextField();
		panel.add(txtFieldIp4);
		txtFieldIp4.setColumns(3);

	}

	@Override
	public Component getComponent() {
		return panel;
	}

	@Override
	public String getValueAsString() {
		String ip = txtFieldIp1.getText() + "." + txtFieldIp2.getText() + "."
				+ txtFieldIp3.getText() + "." + txtFieldIp4.getText();
		return ip;
	}

	@Override
	public String getIdentifier() {
		return Identifier;
	}

}
