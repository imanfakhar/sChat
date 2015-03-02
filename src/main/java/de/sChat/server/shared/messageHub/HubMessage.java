package de.sChat.server.shared.messageHub;

import de.sChat.server.shared.messages.Message;

public class HubMessage implements Comparable<HubMessage> {
	
	private long timestamp;
	private Message msg;
	
	public HubMessage(Message msg) {
		this.msg = msg;
		this.timestamp = getActualTimeStamp();
	}
	
	private long getActualTimeStamp()
	{
		return System.currentTimeMillis() / 1000L;
	}

	@Override
	public int compareTo(HubMessage comp) {
		return Math.round(comp.getTimestamp() - this.getTimestamp());
	}

	public long getTimestamp() {
		return timestamp;
	}

	public Message getMsg() {
		return msg;
	}

	@Override
	public String toString() {
		return "HubMessage [timestamp=" + timestamp + ", msg=" + msg + "]";
	}

}
