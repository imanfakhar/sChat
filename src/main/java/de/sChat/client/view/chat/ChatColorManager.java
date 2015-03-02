package de.sChat.client.view.chat;

import java.util.HashMap;

public class ChatColorManager {
	private HashMap<String, String> personColorBinding;
	private ChatColor managedChatColor = new ChatColor();

	public ChatColorManager() {
		personColorBinding = new HashMap<String, String>();
	}

	public String getColorForPerson(String personName) {
		if (personColorBinding.containsKey(personName)) {
			return personColorBinding.get(personName);
		}
		personColorBinding.put(personName, managedChatColor.getColor());
		managedChatColor.switchToNextColor();
		return personColorBinding.get(personName);
	}
}
