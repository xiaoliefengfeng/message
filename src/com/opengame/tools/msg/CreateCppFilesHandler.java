package com.opengame.tools.msg;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;

import com.opengame.tools.msg.domain.MessageObject;
import com.opengame.tools.msg.domain.MessagesConfig;

import freemarker.template.Template;

/**
 * 构建c++的消息传递格式
 * 
 * @author fengfeng
 *
 */
public class CreateCppFilesHandler extends Handler {
	public CreateCppFilesHandler() throws Exception {
		super(CLIENTR_CPP_ROOT, ROOT + "msg" + File.separator + "tmpl"
				+ File.separator + "client", CPP_POSTFIX);
	}

	public void createMessageFile(MessagesConfig messages) throws Exception {
		System.out.println("create cpp message file start.........");
		createMessageFile(messages.getMacro(), true);
		createMessageFile(messages.getMessages(), false);
		System.out.println("create cpp message file start.........");
	}

	private void createMessageFile(List<MessageObject> excels, boolean macro)
			throws Exception {
		Template tmplh;
		Template tmplcpp;
		// if (macro) {
		// tmplh = getConfiguration().getTemplate("ExcelBean.h");
		// tmplcpp = getConfiguration().getTemplate("ExcelBean.cpp");
		// } else {
		tmplh = getConfiguration().getTemplate("Message.h");
		tmplcpp = getConfiguration().getTemplate("Message.cpp");
		// }
		Iterator<MessageObject> iter = excels.iterator();
		while (iter.hasNext()) {
			MessageObject message = iter.next();
			File fbean = new File(getSave() + File.separator
					+ message.getModule());
			if (!fbean.exists())
				fbean.mkdirs();

			// .h
			File beanFile = new File(fbean, message.getClassName()
					+ getPostfix());
			FileWriter file = new FileWriter(beanFile);
			tmplh.process(message, file);
			file.flush();
			file.close();

			// .cpp
			File cpp = new File(fbean, message.getClassName() + getCpp());
			FileWriter cppfile = new FileWriter(cpp);
			tmplcpp.process(message, cppfile);
			cppfile.flush();
			cppfile.close();

		}
	}

	private String getCpp() {
		return ".cpp";
	}
}
