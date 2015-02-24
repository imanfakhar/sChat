package de.sChat.client.connector;

import java.io.BufferedWriter;
import java.io.IOException;
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
				String message = getNextMessage();
				System.out.println("Writer is writing to Server: " + message);

				BufferedWriter writer = new BufferedWriter(printWriter);
				try {
					writer.write(message);
					writer.newLine();
					writer.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				;
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