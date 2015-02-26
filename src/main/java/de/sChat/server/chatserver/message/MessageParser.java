package de.sChat.server.chatserver.message;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class MessageParser {
	
	private static XStream xstream;

	private static XStream getXStream()
	{
        xstream = new XStream(new JettisonMappedXmlDriver());
        xstream.setMode(XStream.NO_REFERENCES);
        xstream.alias("data", MessageData.class);
        xstream.alias("msg", Message.class);
		return xstream;
	}
	
	public static Message parseMessage(String message)
	{
		XStream xstream = getXStream();
		return (Message) xstream.fromXML("{\"msg\":"+message+"}");
	}

	public static String parseMessage(Message message) {
		String tmp = getXStream().toXML(message);
		tmp = tmp.replace("{\"msg\":", "");
		tmp = tmp.substring(0, tmp.length()-1);
		return tmp;
	}

}
