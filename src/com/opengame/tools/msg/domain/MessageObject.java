package com.opengame.tools.msg.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.opengame.tools.msg.util.MessageConstants;
import com.opengame.tools.msg.util.StringUtils;

/**
 * 消息实体bean
 * 
 * @author fengfeng
 * 
 */
public class MessageObject {
	public static final String MSG = ".msg";
	public static final String BEAN = MSG + ".bean";
	public static final String STOS = ".stos";
	/** 消息的名称,类名 */
	private String name;
	/** 消息注释 */
	private String comnent;
	/** 此xml解析出来消息对应的Fields */
	private List<FieldObject> fields = new ArrayList<FieldObject>();
	/** 继承的父类，完整类名字 */
	private String parent;
	private String packages;
	/** 是否为消息bean */
	private boolean macro = false;

	private String messageQueue;
	private String messageServer;

	/** 判断是否有必须的字段 */
	private boolean requiredField;

	private Set<String> imports = new HashSet<String>();
	private String module;

	public void setPackages(String packages) {
		this.packages = packages;
	}

	@XmlAttribute(required = true, name = "package")
	public String getPackages() {
		return packages;
	}

	@XmlAttribute(required = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute(required = true)
	public String getComnent() {
		return comnent;
	}

	public void setComnent(String comnent) {
		this.comnent = comnent;
	}

	@XmlElement(name = "field", namespace = MessagesConfig.NAMESPACE)
	public List<FieldObject> getFields() {
		return fields;
	}

	public void setFields(List<FieldObject> fields) {
		this.fields = fields;
	}

	@XmlAttribute
	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getParentName() {
		return this.parent.substring(this.parent.lastIndexOf(".") + 1,
				this.parent.length());
	}

	public void validator(MessagesConfig msg, boolean macro) {
		this.parent = StringUtils.trim(this.parent);
		this.name = StringUtils.trim(this.name);
		this.comnent = StringUtils.trim(this.comnent);
		this.packages = StringUtils.trim(this.packages);
		this.macro = macro;
		this.module = StringUtils.trim(msg.getModule());
		if (StringUtils.isEmpty(this.parent)) {
			if (isMacro()) {
				this.parent = MessageConstants.getMessageBean();
			} else {
				if (name.toUpperCase().startsWith("CG")) {
					this.parent = MessageConstants.getCGMessageG();
				} else {
					this.parent = MessageConstants.getGCMessageC();
				}

			}

		}

		boolean pkEmpty = StringUtils.isEmpty(this.packages);
		if (pkEmpty) {
			this.packages = msg.getPackages();
		}
		// 验证属性名字是否一样
		Set<String> names = new HashSet<String>();
		int i = 0;
		for (FieldObject field : this.fields) {
			field.validator(this);
			if (names.contains(field.getName())) {
				throw new IllegalArgumentException(getName() + "里面定义的属性一样name="
						+ field.getName());
			}
			if (field.getIndex() <= 0) {
				field.setIndex(++i);
			}
			if (field.getIndex() > 32) {
				throw new IllegalArgumentException("field max index 32");
			}
			names.add(field.getName());

			if (field.hasByteString()) {
				imports.add(FieldObject.ByteStringTypePackage);
			}
			if (field.getRequired()) {
				requiredField = field.getRequired();
			}
		}
		// 加入默认的import
		imports.add(parent);
		if (!isMacro()) {
			imports.add(MessageConstants.getMessageType());
		}

		// 此行代码不能上移，不然出错
		this.packages = this.packages + (pkEmpty ? (macro ? BEAN : MSG) : "");
	}

	public String getClassName() {
		if (isMacro()) {
			return this.name;
		}
		return StringUtils.buildFileName(this.name);
	}

	public boolean isMacro() {
		return macro;
	}

	public boolean getMacro() {
		return macro;
	}

	public String getMessageQueue() {
		return messageQueue;
	}

	public void setMessageQueue(String messageQueue) {
		this.messageQueue = messageQueue;
	}

	public String getMessageServer() {
		return messageServer;
	}

	public void setMessageServer(String messageServer) {
		this.messageServer = messageServer;
	}

	public Set<String> getImports() {
		return imports;
	}

	public String getFullName() {
		return this.packages + "." + getClassName();
	}

	public String getModule() {
		return module;
	}

	public String getModuleUpperCase() {
		return module.toUpperCase();
	}

	public String getClassNameUpperCase() {
		return getClassName().toUpperCase();
	}

	public Set<String> getCppImports() {
		Set<String> im = new HashSet<String>();
		for (FieldObject f : fields) {
			if (f.isMacros()) {
				im.add(f.getFullName().substring(
						f.getFullName().lastIndexOf(".") + 1,
						f.getFullName().length()));
			}
		}
		return im;
	}

	public boolean getRequiredField() {
		return requiredField;
	}

}
