package de.sChat.client.connector;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

import de.sChat.client.Message;

public class StreamWriterRunnable implements Runnable, StreamWriter {

	private PrintWriter printWriter;
	private Queue<String> outgoingQueue = new LinkedList<String>();

	public StreamWriterRunnable(PrintWriter writer) {
		this.printWriter = writer;
	}

	@Override
	public void run() {
		while (!isCloseRequested()) {
			writeMessages();
		}
	}

	private void writeMessages() {
		if (outgoingMessageAvailable()) {
			String message = getNextMessage();
			System.out.println("Writer is writing to Server: " + message);

			BufferedWriter writer = new BufferedWriter(printWriter);
			try {
				System.out.println("writing: " + message);
				writer.write(message);
				writer.newLine();
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			sleep();
		}
	}

	private void sleep() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void pushMessage(Message message) {
		outgoingQueue.add(message.getJSONString());
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
