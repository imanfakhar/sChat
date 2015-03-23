package de.sChat.server.data.chatClient;

import java.util.Comparator;

import de.sChat.server.data.messages.TextMessage;

public class CustomComparator implements Comparator<TextMessage> {
    @Override
    public int compare(TextMessage o1, TextMessage o2) {
        return o1.getCreationTime().compareTo(o2.getCreationTime());
    }
}