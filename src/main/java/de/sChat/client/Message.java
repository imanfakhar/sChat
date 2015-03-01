package de.sChat.client;

import json_parser.data_types.JSONObject;
import json_parser.data_types.JSONString;

public class Message {

	private String sender;
	private String message;

	public Message(String jsonString) {
		JSONObject jsonMessage = new JSONObject();
		jsonMessage.parse(jsonString);
		JSONObject dataObject = (JSONObject) jsonMessage.getValue("data");
		JSONString jsonSender = (JSONString) dataObject.getValue("sender");
		this.sender = jsonSender.getValue();

		JSONString jsonMessageData = (JSONString) dataObject
				.getValue("message");
		this.message = jsonMessageData.getValue();
	}

	public Message(String sender, String message) {
		this.sender = sender;
		this.message = message;
	}

	public String getSender() {
		return sender;
	}

	public String getMessage() {
		return message;
	}

	public String getJSONString() {
		JSONObject jsonMessage = new JSONObject();

		JSONString messageString = new JSONString("message");
		jsonMessage.addValue("type", messageString);

		JSONObject dataObject = new JSONObject();

		JSONString senderString = new JSONString(sender);
		dataObject.addValue("sender", senderString);
		JSONString messageDataString = new JSONString(message);
		dataObject.addValue("message", messageDataString);

		jsonMessage.addValue("data", dataObject);

		return jsonMessage.print();
	}
}
