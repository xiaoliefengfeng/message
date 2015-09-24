package com.opengame.tools.msg;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import com.opengame.tools.msg.domain.FieldObject;
import com.opengame.tools.msg.domain.MessageObject;
import com.opengame.tools.msg.domain.MessagesConfig;

/**
 * 构建使用lua来进行消息传递的消息格式
 * 
 * @author fengfeng
 *
 */
public class CreateCppLuaFilesHandler extends Handler {
	public CreateCppLuaFilesHandler() throws Exception {
		super(CLIENTR_LUA_ROOT, ROOT + "msg" + File.separator + "tmpl"
				+ File.separator + "client", LUA_POSTFIX);
	}

	@Override
	public void createMessageFile(MessagesConfig messages) throws Exception {
		System.out.println("create lua message file start.........");
		List<MessageObject> mo = messages.getMessages();
		for (MessageObject m : mo) {
			if (!getSave().exists()) {
				getSave().mkdirs();
			}
			File f = new File(getSave(), m.getClassName() + getPostfix());

			FileOutputStream fio = new FileOutputStream(f);
			DataOutputStream dos = new DataOutputStream(fio);
			createClientMsg(m, dos, messages);
			dos.flush();
			fio.flush();
			dos.close();
			fio.close();
		}
		System.out.println("create lua message file end.........");
	}

	private void createClientMsg(MessageObject m, DataOutputStream dos,
			MessagesConfig messages) throws Exception {
		// 消息的名称
		byte bn[] = m.getName().getBytes("UTF-8");
		dos.writeShort(bn.length);
		dos.write(bn);

		dos.writeByte(m.getFields().size());// 设置包含几个数据定义
		for (FieldObject f : m.getFields()) {
			// 定义字段名字
			byte b[] = f.getName().getBytes("UTF-8");
			dos.writeShort(b.length);
			dos.write(b);
			// 字段类型
			dos.writeByte(f.getTypeNum());
			// 是否为list
			dos.writeByte(f.getList() ? 1 : 0);
			if (f.isMacros()) {
				// 解析宏定义
				for (MessageObject macro : messages.getMacro()) {
					if (f.getMacro().equals(macro.getName())) {
						// 找到了宏定义
						createClientMsg(macro, dos, messages);
					}
				}
			}
		}
	}

}
