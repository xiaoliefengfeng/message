package com.opengame.tools.msg;

import java.io.File;
import java.io.FileWriter;

import com.opengame.tools.msg.domain.MessagesConfig;

import freemarker.template.Template;

/**
 * 生成java的 protobuf消息文件
 * 
 * @author fengfeng
 *
 */
public class CreateProtobufFilesHandler extends Handler {
	public CreateProtobufFilesHandler() throws Exception {
		super(SERVER_PROTOBUF_ROOT, ROOT + "msg" + File.separator + "tmpl"
				+ File.separator + "server" + File.separator + "protobuf",
				JAVA_POSTFIX);
	}

	@Override
	protected void createProtobufFile(MessagesConfig messages) throws Exception {
		Template temp = null;
		temp = getConfiguration().getTemplate("Protobuf.ftl");
		File fbean = new File(getSave() + File.separator);

		if (!fbean.exists())
			fbean.mkdirs();
		File beanFile = new File(fbean, messages.getModule() + ".proto");
		FileWriter file = new FileWriter(beanFile);
		temp.process(messages, file);
		file.flush();
		file.close();
	}

}
