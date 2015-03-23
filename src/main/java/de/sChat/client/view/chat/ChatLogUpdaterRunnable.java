package de.sChat.client.view.chat;

import de.sChat.client.connector.StreamReader;

public class ChatLogUpdaterRunnable implements Runnable {
	private StreamReader streamReader;
	private ChatLog chatLog;

	public ChatLogUpdaterRunnable(StreamReader streamReader, ChatLog chatLog) {
		this.streamReader = streamReader;
		this.chatLog = chatLog;
	}

	@Override
	public void run() {
		while (!isCloseRequested()) {
			if (streamReader.newIncomingMessage()) {
				System.out.println("Neue zeile gefunden.");
				chatLog.addMessage(streamReader.getNewMessage());
			} else {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	private boolean isCloseRequested() {
		Thread currendThread = Thread.currentThread();
		if (currendThread.getState() == Thread.State.TERMINATED) {
			return true;
		}
		return false;
	}
}
