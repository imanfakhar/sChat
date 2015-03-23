package de.sChat.server.data.messages.parser;

import de.sChat.server.data.messages.InternMessage;
import de.sChat.server.data.messages.TextMessage;
import json_parser.data_types.JSONObject;
import json_parser.data_types.JSONString;

public class MessageParser {
	
	public static InternMessage parseMessage(String message)
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

	public static String parseMessage(InternMessage message) {
		if(message instanceof TextMessage)
		{
			JSONObject txtMessage = new JSONObject();
			txtMessage.addValue("type", new JSONString("message"));
			JSONObject dataObject = new JSONObject();
			txtMessage.addValue("data", dataObject);
			dataObject.addValue("message", new JSONString(((TextMessage) message).getMessage()));
			dataObject.addValue("name", new JSONString(((TextMessage) message).getName()));
			return txtMessage.print();
		}
		return "";
	}

}
=======
package de.sChat.server.data.messages.parser;

import de.sChat.server.data.messages.InternMessage;
import de.sChat.server.data.messages.TextMessage;
import json_parser.data_types.JSONObject;
import json_parser.data_types.JSONString;

public class MessageParser {
	
	public static InternMessage parseMessage(String message)
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

	public static String parseMessage(InternMessage message) {
		if(message instanceof TextMessage)
		{
			JSONObject txtMessage = new JSONObject();
			txtMessage.addValue("type", new JSONString("message"));
			JSONObject dataObject = new JSONObject();
			txtMessage.addValue("data", dataObject);
			dataObject.addValue("message", new JSONString(((TextMessage) message).getMessage()));
			dataObject.addValue("name", new JSONString(((TextMessage) message).getName()));
			return txtMessage.print();
		}
		return "";
	}

}
