package de.sChat.client.connector;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

public class StreamWriterRunnable implements Runnable, StreamWriter {

	private PrintWriter printWriter;
	private Queue<String> outgoingQueue = new LinkedList<String>();

	public StreamWriterRunnable(PrintWriter writer) {
		this.printWriter = writer;
	}

	@Override
	public void run() {
		while (!isCloseRequested()) {
			if (outgoingMessageAvailable()) {
				printWriter.write(getNextMessage());
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

	public synchronized void pushMessage(String message) {
		outgoingQueue.add(message);
	}

	private synchronized String getNextMessage() {
		return outgoingQueue.poll();
	}

	private boolean outgoingMessageAvailable() {
		return outgoingQueue.size() != 0;
	}

	private boolean isCloseRequested() {
		Thread currendThread = Thread.currentThread();
		if (currendThread.getState() == Thread.State.TERMINATED) {
			return true;
		}
		return false;
	}
}
