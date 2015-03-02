package de.sChat.server.chatserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.HashSet;

import de.joshuaschnabel.framework.eventbus.bus.EventBus;
import de.joshuaschnabel.framework.eventbus.event.Handler;
import de.sChat.server.chatserver.event.IncommingMessageEvent;
import de.sChat.server.chatserver.event.OutGoingMessageEvent;
import de.sChat.server.chatserver.message.Message;
import de.sChat.server.chatserver.message.MessageParser;

public class UDPRunnable extends ClientRunnable{

	private EventBus eventbus;
	private DatagramSocket serverSocket;
	private HashSet<SocketAddress> addresses = new HashSet<SocketAddress>();

	public UDPRunnable(EventBus eventbus, Integer port) throws IOException 
	{
		this.eventbus = eventbus;
		this.serverSocket = new DatagramSocket(9876);
	}

	@Handler
	public void outgoingMessage(OutGoingMessageEvent event) 
	{
		byte[] sendData = new byte[1024*32];
		sendData = MessageParser.parseMessage(event.getMsg()).getBytes();
		for (SocketAddress addresse : addresses) {
			try {
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, addresse);
				serverSocket.send(sendPacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void run() 
	{
		try {
			byte[] receiveData = new byte[1024*32];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			String message = new String(receivePacket.getData());
			Message input = MessageParser.parseMessage(message);
			eventbus.publishSync(new IncommingMessageEvent(input));
			addresses.add(receivePacket.getSocketAddress());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
