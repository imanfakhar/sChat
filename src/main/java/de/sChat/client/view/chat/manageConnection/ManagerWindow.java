package de.sChat.client.view.chat.manageConnection;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ManagerWindow {
	private JFrame window;
	private JPanel panel;
	private ArrayList<ManagerWindowItem> items;

	public ManagerWindow() {
		window = new JFrame();
		panel = new JPanel(new GridLayout(0, 1));
		window.setContentPane(panel);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		items = new ArrayList<ManagerWindowItem>();
	}

	public void addItem(ManagerWindowItem item) {
		panel.add(item.getComponent());
		items.add(item);
	}

	public String getValueByName(String name) {
		for (ManagerWindowItem managerWindowItem : items) {
			if (managerWindowItem.getIdentifier().equals(name)) {
				return managerWindowItem.getValueAsString();
			}
		}
		throw new IllegalArgumentException(
				"No Item in Window found with name: " + name);
	}

	public void publish() {
		window.pack();
		window.setVisible(true);
	}

	public void dispose() {
		window.dispose();
	}
}
