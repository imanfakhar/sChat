package de.sChat.client.connector;

public interface StreamReader {

	boolean newIncomingLine();

	String getNewLine();

}