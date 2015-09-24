package com.opengame.tools.msg.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MessageConstants {
	private static final String DEFAULT_NAME = "default.properties";
	public static final String GC_MESSAGE_C = "GC_MESSAGE_C";
	public static final String CG_MESSAGE_G = "CG_MESSAGE_G";
	public static final String MESSAGE_BEAN = "MESSAGE_BEAN";
	public static final String MESSAGE_TYPE = "MESSAGE_TYPE";

	private static Properties properties = null;

	public static void init(String fileName) {
		if (properties == null) {
			if (!StringUtils.isEmpty(fileName)) {
				fileName = StringUtils.trim(fileName);
			} else {
				fileName = DEFAULT_NAME;
			}
			try {
				properties = new Properties();
				InputStream is = Thread.currentThread().getContextClassLoader()
						.getResourceAsStream(fileName);
				properties.load(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static String getGCMessageC() {
		return properties.getProperty(GC_MESSAGE_C);
	}

	public static String getCGMessageG() {
		return properties.getProperty(CG_MESSAGE_G);
	}

	public static String getMessageType() {
		return properties.getProperty(MESSAGE_TYPE);
	}

	public static String getMessageBean() {
		return properties.getProperty(MESSAGE_BEAN);
	}
}
