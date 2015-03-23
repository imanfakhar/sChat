package de.sChat.server.data.chatClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import de.sChat.server.data.messages.TextMessage;

public class ChatClientComperator implements Comparator<TextMessage> {
    @Override
    public int compare(TextMessage o1, TextMessage o2) {
        return o1.getCreationTime().compareTo(o2.getCreationTime());
    }
    
	public static List<TextMessage> getSinceLastSeenMessages(List<TextMessage> messages, Date lastseen) {
		ArrayList<TextMessage> subarray = new ArrayList<TextMessage>();
		Collections.sort(messages, new ChatClientComperator());
		for (int i = 0; i < messages.size(); i++) {
			if(messages.get(i).getCreationTime().compareTo(lastseen) > 0)
				break;
			subarray.add(messages.get(i));
		}
		return subarray;
	}
}