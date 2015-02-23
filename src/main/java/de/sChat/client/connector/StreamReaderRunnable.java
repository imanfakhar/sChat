package de.sChat.client.connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class StreamReaderRunnable implements Runnable {
	private InputStream is;
	private Queue<String> que = new LinkedList<String>();

	public StreamReaderRunnable(InputStream is) {
		this.is = is;
	}

	@Override
	public void run() {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		while (!isCloseRequested()) {
			try {
				String line = br.readLine();
				putStringIfNeccessaryToQueue(line);
			} catch (IOException e) {
				e.printStackTrace();
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

	private void putStringIfNeccessaryToQueue(String string) {
		if (string != null) {
			putString(string);
		}
	}

	private synchronized void putString(String s) {
		que.add(s);
	}

	public boolean newIncomingLine() {
		return que.size() == 0;
	}

	public synchronized String getNewLine() {
		return que.poll();
	}

}
