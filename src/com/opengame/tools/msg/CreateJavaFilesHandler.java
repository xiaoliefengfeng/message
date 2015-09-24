package com.opengame.tools.msg;

import java.io.File;

public class CreateJavaFilesHandler extends Handler {
	public CreateJavaFilesHandler() throws Exception {
		super(SERVERROOT, ROOT + "msg" + File.separator + "tmpl"
				+ File.separator + "server", JAVA_POSTFIX);
	}

}
