package com.opengame.tools.msg;

import java.io.File;

/**
 * AS的消息生成器未实现
 * 
 * @author fengfeng
 *
 */
public class CreateAsFilesHandler extends Handler {
	public CreateAsFilesHandler() throws Exception {
		super(CLIENTRROOT, ROOT + "msg" + File.separator + "tmpl"
				+ File.separator + "client", AS_POSTFIX);
	}
}
