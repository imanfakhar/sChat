package de.sChat.server.shared.messages;

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
				JSONObject dataObject = (JSONObject) jsonMessage.getValue("data");
				String nachricht = ((JSONString) dataObject.getValue("message")).getValue();
				String name = ((JSONString) dataObject.getValue("name")).getValue();
				return new TextMessage(name, nachricht);
		}
		return null;
	}

	public static String parseMessage(Message message) {
		if(message instanceof TextMessage)
		{
			JSONObject txtMessage = new JSONObject();
			txtMessage.addValue("type", new JSONString("message"));
			JSONObject dataObject = new JSONObject();
			txtMessage.addValue("data", dataObject);
			dataObject.addValue("message", new JSONString(((TextMessage) message).getNachricht()));
			dataObject.addValue("name", new JSONString(((TextMessage) message).getName()));
			return txtMessage.toString();
		}
		return "";
	}

}
