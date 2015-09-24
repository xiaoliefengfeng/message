package com.opengame.tools.msg;

import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.opengame.tools.msg.domain.MessagesConfig;
import com.opengame.tools.msg.util.JAXBUtil;
import com.opengame.tools.msg.util.MessageConstants;

public class GenerateMessages {
	private static final String XML_XSD = Handler.ROOT + "msg/messages.xsd";
	private static MessagesConfig messages = null;
	private static GenerateMessages generateMessages = null;

	public static void main(String[] args) throws Exception {
		System.out.println("start GenerateMessages ...");
		generateMessages = new GenerateMessages();
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		ScriptEngine scriptEngine = scriptEngineManager
				.getEngineByName("JavaScript");
		scriptEngine.put("engine", generateMessages);
		InputStreamReader reader = null;
		reader = new InputStreamReader(new FileInputStream(Handler.ROOT
				+ "msg/message_generator.js"), "UTF-8");
		scriptEngine.eval(reader);
		System.out.println("end GenerateMessages ...");
	}

	public void createMessageFiles(String file) throws Exception {
		createMessageFiles(file, null);
	}

	public void createMessageFiles(String file, String prop) throws Exception {

		String msgXml = Handler.ROOT + "msg/" + file;
		MessageConstants.init(prop);
		messages = generateMessages.parseMessages(msgXml);
		if (messages == null) {
			System.err.println(new StringBuilder()
					.append("Failed to parse the message xml [").append(msgXml)
					.append("]").toString());
		}
		messages.validator();

		// CreateJavaFilesHandler java = new CreateJavaFilesHandler();
		// java.createMessageFile(messages);

		CreateProtobufFilesHandler protobuf = new CreateProtobufFilesHandler();
		protobuf.createMessageFile(messages);

		// CreateAsFilesHandler as = new CreateAsFilesHandler();
		// as.createMessageFile(messages);

		CreateCppLuaFilesHandler lua = new CreateCppLuaFilesHandler();
		lua.createMessageFile(messages);

		CreateCppFilesHandler cpp = new CreateCppFilesHandler();
		cpp.createMessageFile(messages);
	}

	private MessagesConfig parseMessages(String config) {
		MessagesConfig messagesConfig = null;
		messagesConfig = (MessagesConfig) JAXBUtil.read(MessagesConfig.class,
				XML_XSD, config);
		return messagesConfig;
	}

}
