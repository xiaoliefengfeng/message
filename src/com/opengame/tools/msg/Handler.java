package com.opengame.tools.msg;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;

import com.opengame.tools.msg.domain.MessageObject;
import com.opengame.tools.msg.domain.MessagesConfig;
import com.opengame.tools.msg.util.FileUtil;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public abstract class Handler {
	public static final File SERVERROOT = new File("out" + File.separator
			+ "server" + File.separator + "java");
	public static final File SERVER_PROTOBUF_ROOT = new File("out"
			+ File.separator + "server" + File.separator + "google");
	public static final File CLIENTRROOT = new File("out" + File.separator
			+ "client");
	public static final File CLIENTR_CPP_ROOT = new File("out" + File.separator
			+ "client" + File.separator + "cpp");
	public static final File CLIENTR_LUA_ROOT = new File("out" + File.separator
			+ "client" + File.separator + "lua");
	public static final String AS_POSTFIX = ".as";
	public static final String LUA_POSTFIX = ".lua";
	public static final String CPP_POSTFIX = ".h";
	public static final String JAVA_POSTFIX = ".java";
	/** 程序运行的根目录 */
	public static final String ROOT = Thread.currentThread()
			.getContextClassLoader().getResource(".").getPath();

	private Configuration configuration;
	private File save;
	private String postfix;

	public Handler(File save, String tmpl, String postfix) throws Exception {
		this.save = save;
		this.postfix = postfix;
		this.configuration = new Configuration();
		this.configuration.setDefaultEncoding("UTF-8");
		java.io.File path = new java.io.File(tmpl);
		this.configuration.setDirectoryForTemplateLoading(path);
		this.configuration.setObjectWrapper(new DefaultObjectWrapper());
		if (!save.exists())
			save.mkdirs();
		FileUtil.delete(save);
	}

	public void createMessageFile(MessagesConfig messages) throws Exception {
		String ed = getPostfix().substring(1, getPostfix().length());
		System.out.println("create " + ed + " message file start.........");
		createMessageFile(messages.getMacro(), true);
		createMessageFile(messages.getMessages(), false);
		createProtobufFile(messages);
		System.out.println("create " + ed + " message file end.........");
	}

	/**
	 * 创建google的protobuf文件
	 * 
	 * @param messages
	 * @throws Exception
	 */
	protected void createProtobufFile(MessagesConfig messages) throws Exception {

	}

	private void createMessageFile(List<MessageObject> messages, boolean macro)
			throws Exception {
		Template temp;
		if (macro) {
			temp = getConfiguration().getTemplate("Bean.ftl");
		} else {
			temp = getConfiguration().getTemplate("Message.ftl");
		}
		Iterator<MessageObject> iter = messages.iterator();
		while (iter.hasNext()) {
			MessageObject message = iter.next();
			File fbean = new File(getSave() + File.separator
					+ message.getPackages().replace(".", File.separator));

			if (!fbean.exists())
				fbean.mkdirs();
			File beanFile = new File(fbean, message.getClassName()
					+ getPostfix());
			FileWriter file = new FileWriter(beanFile);
			temp.process(message, file);
			file.flush();
			file.close();

		}
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public File getSave() {
		return save;
	}

	public String getPostfix() {
		return postfix;
	}

}
