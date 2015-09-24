package com.opengame.tools.msg.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.opengame.tools.msg.util.StringUtils;

@XmlRootElement(name = "messages", namespace = MessagesConfig.NAMESPACE)
@XmlAccessorType(XmlAccessType.PROPERTY)
public class MessagesConfig {
	public static final String NAMESPACE = "http://www.opengame.org/game/dtd/messages";
	/** 包名字 */
	private String packages;
	private String module;

	/** 所有的消息 */
	private List<MessageObject> messages = new ArrayList<MessageObject>();
	/** 所有的消息bean */
	private List<MessageObject> macro = new ArrayList<MessageObject>();

	@XmlElement(name = "message", nillable = false, required = true, namespace = MessagesConfig.NAMESPACE)
	public List<MessageObject> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageObject> messages) {
		this.messages = messages;
	}

	@XmlElement(name = "macro", nillable = false, namespace = MessagesConfig.NAMESPACE)
	public List<MessageObject> getMacro() {
		return macro;
	}

	public void setMacro(List<MessageObject> macro) {
		this.macro = macro;
	}

	@XmlAttribute(required = true, name = "package")
	public String getPackages() {
		return packages + "." + getModule();
	}

	public void setPackages(String packages) {
		this.packages = packages;
	}

	@XmlAttribute(required = true, name = "module")
	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public boolean validator() {
		this.packages = StringUtils.trim(this.packages);
		for (MessageObject msg : this.messages) {
			msg.validator(this, false);
		}
		for (MessageObject m : this.macro) {
			m.validator(this, true);
		}
		// 判断定义的宏是否正确
		Map<String, MessageObject> msg = new HashMap<String, MessageObject>();
		for (MessageObject ms : this.getMacro()) {
			msg.put(ms.getFullName(), ms);
		}

		for (MessageObject ms : this.getMacro()) {
			for (FieldObject field : ms.getFields()) {
				if (field.isMacros()) {
					if (!msg.containsKey(field.getFullName())) {
						throw new RuntimeException("macro定义异常，找不到对应的类型:"
								+ field.getFullName());
					}
				}
			}
		}

		for (MessageObject ms : this.getMessages()) {
			for (FieldObject field : ms.getFields()) {
				if (field.isMacros()) {
					if (!msg.containsKey(field.getFullName())) {
						throw new RuntimeException("macro定义异常，找不到对应的类型:"
								+ field.getFullName());
					}
				}
			}
		}
		return true;

	}

	@Override
	public String toString() {
		return "MessagesConfig [packages=" + packages + ", message=" + messages
				+ ", macro=" + macro + "]";
	}

}
