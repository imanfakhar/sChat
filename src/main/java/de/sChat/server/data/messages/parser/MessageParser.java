package de.sChat.server.data.messages.parser;

import de.sChat.server.data.messages.AuthMessage;
import de.sChat.server.data.messages.ErrorMessage;
import de.sChat.server.data.messages.LoginMessage;
import de.sChat.server.data.messages.RegisterMessage;
import de.sChat.server.data.messages.TextMessage;
import de.sChat.server.data.messages.parser.Message;
import json_parser.data_types.JSONInteger;
import json_parser.data_types.JSONObject;
import json_parser.data_types.JSONString;

public class MessageParser {
	
	public static Message parseMessage(String message)
	{
		JSONObject jsonMessage = new JSONObject();
		jsonMessage.parse(message);
		String type = ((JSONString) jsonMessage.getValue("type")).getValue();
		switch (type) {
			case "message":
			return parseTextmessage(jsonMessage);
			case "login":
			return parseLoginmessage(jsonMessage);
			case "register":
			return parseRegistermessage(jsonMessage);
		}
		return null;
	}

	private static Message parseTextmessage(JSONObject jsonMessage) {
		JSONObject dataObject = (JSONObject) jsonMessage.getValue("data");
		String nachricht = ((JSONString) dataObject.getValue("message")).getValue();
		String name = ((JSONString) dataObject.getValue("name")).getValue();
		TextMessage tm = new TextMessage(name, nachricht);
		tm.setUid(((JSONString) jsonMessage.getValue("auth")).getValue());
		return tm;
	}
	
	private static Message parseLoginmessage(JSONObject jsonMessage) {
		JSONObject dataObject = (JSONObject) jsonMessage.getValue("login");
		String username = ((JSONString) dataObject.getValue("username")).getValue();
		String password = ((JSONString) dataObject.getValue("password")).getValue();
		return new LoginMessage(username, password);
	}
	
	private static Message parseRegistermessage(JSONObject jsonMessage) {
		JSONObject dataObject = (JSONObject) jsonMessage.getValue("register");
		String username = ((JSONString) dataObject.getValue("username")).getValue();
		String password = ((JSONString) dataObject.getValue("password")).getValue();
		return new RegisterMessage(username, password);
	}

	public static String parseMessage(Message message) {
		if(message instanceof TextMessage)
			return parseTextmessage(message);
		if(message instanceof AuthMessage)
			return parseAuthmessage(message);
		if(message instanceof ErrorMessage)
			return parseErrormessage(message);
		if(message instanceof RegisterMessage)
			return parseEgistermessage(message);
		return "";
	}
	
	private static String parseEgistermessage(Message message) {
		JSONObject txtMessage = new JSONObject();
		txtMessage.addValue("type", new JSONString("register"));
		JSONObject dataObject = new JSONObject();
		txtMessage.addValue("register", dataObject);
		dataObject.addValue("password", new JSONString(((RegisterMessage) message).getPassword()));
		dataObject.addValue("username", new JSONString(((RegisterMessage) message).getUsername()));
		return txtMessage.print();
	}

	private static String parseErrormessage(Message message) {
		JSONObject txtMessage = new JSONObject();
		txtMessage.addValue("type", new JSONString("error"));
		JSONObject dataObject = new JSONObject();
		txtMessage.addValue("data", dataObject);
		dataObject.addValue("error", new JSONString(((ErrorMessage) message).getError()));
		dataObject.addValue("errorid", new JSONInteger(((ErrorMessage) message).getErrorcode()));
		return txtMessage.print();
	}

	private static String parseTextmessage(Message message) {
		JSONObject txtMessage = new JSONObject();
		txtMessage.addValue("type", new JSONString("message"));
		JSONObject dataObject = new JSONObject();
		txtMessage.addValue("data", dataObject);
		dataObject.addValue("message", new JSONString(((TextMessage) message).getMessage()));
		dataObject.addValue("name", new JSONString(((TextMessage) message).getName()));
		return txtMessage.print();
	}
	
	private static String parseAuthmessage(Message message) {
		JSONObject txtMessage = new JSONObject();
		txtMessage.addValue("type", new JSONString("auth"));
		JSONObject dataObject = new JSONObject();
		txtMessage.addValue("data", dataObject);
		dataObject.addValue("uid", new JSONString(((AuthMessage) message).getUid()));
		return txtMessage.print();
	}
}
