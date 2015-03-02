package de.sChat.server.shared.messageHub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import de.joshuaschnabel.framework.eventbus.bus.EventBus;
import de.joshuaschnabel.framework.eventbus.event.Handler;
import de.sChat.server.shared.events.IncommingMessageEvent;
import de.sChat.server.shared.messages.Message;

public class MessageHub {

	private Vector<HubMessage> messages = new Vector<HubMessage>();
	private EventBus bus;

	public MessageHub(EventBus bus) {
		this.bus = bus;
	}

	public void publishIncommingMessage(Message msg)
	{
		this.bus.publishSync(new IncommingMessageEvent(msg));
	}

	@Handler(canVeto = true)
	public void incommingMessage(IncommingMessageEvent event) {
		messages.add(new HubMessage(event.getMsg()));
		Collections.sort(messages);
	} 

	public ArrayList<HubMessage> getMessages(long timestamp)
	{
		ArrayList<HubMessage> result = new ArrayList<HubMessage>();
		for (int i = 0; i < messages.size(); i++) {
			HubMessage msg = messages.get(i);
			if(msg.getTimestamp() >= timestamp)
				result.add(msg);
			else
				break;
		}
		return result;
	}

}
