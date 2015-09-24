package com.opengame.tools.msg.domain;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;

import com.opengame.tools.msg.util.StringUtils;

/**
 * 消息的组成内容
 * 
 * @author fengfeng
 * 
 */
public class FieldObject {
	/** 变量名称 */
	private String name;
	/** 变量类型 */
	private String type;
	/** 变量注释 */
	private String comnent;
	/** 标记当前属性是否为 集合属性 */
	private boolean list = false;
	/** 宏定义，标记次字段是自定义类型 */
	private String macro;
	/** 完整的类型名字 */
	private String fullName;
	/** 变量的索引 */
	private int index;
	/** protobuf的数据类型 */
	private String protobuf = "optional";

	// protobuf使用的相关数据类型定义
	public static final int WIRETYPE_VARINT = 0;
	public static final int WIRETYPE_FIXED64 = 1;
	public static final int WIRETYPE_LENGTH_DELIMITED = 2;
	public static final int WIRETYPE_START_GROUP = 3;
	public static final int WIRETYPE_END_GROUP = 4;
	public static final int WIRETYPE_FIXED32 = 5;

	public static final String ByteStringType = "ByteString";
	public static final String ByteStringTypePackage = "com.google.protobuf.ByteString";

	public static final Map<String, String> SUPPORT_TYPE = new HashMap<String, String>();

	public static final Map<String, String> SUPPORT_PROTOBUF = new HashMap<String, String>();
	static {
		SUPPORT_TYPE.put(Byte.class.getSimpleName(), "byte");
		SUPPORT_TYPE.put(Short.class.getSimpleName(), "short");
		SUPPORT_TYPE.put(Character.class.getSimpleName(), "char");
		SUPPORT_TYPE.put(Integer.class.getSimpleName(), "int");
		SUPPORT_TYPE.put(Double.class.getSimpleName(), "double");
		SUPPORT_TYPE.put(Float.class.getSimpleName(), "float");
		SUPPORT_TYPE.put(Long.class.getSimpleName(), "long");
		SUPPORT_TYPE.put(Boolean.class.getSimpleName(), "boolean");
		SUPPORT_TYPE.put(String.class.getSimpleName(), "String");
		SUPPORT_TYPE.put(ByteStringType, "byte");

		SUPPORT_PROTOBUF.put("required", "required");// 字段是必须
		SUPPORT_PROTOBUF.put("optional", "optional");// 字段是可选
		SUPPORT_PROTOBUF.put("repeated", "repeated");// 字段是集合
	}

	@XmlAttribute(required = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute(required = true)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 标记是必须字段 protobuf用
	 * 
	 * @return
	 */
	public boolean getRequired() {
		return getProtobuf().equals("required");
	}

	@XmlAttribute(required = true)
	public String getComnent() {
		return comnent;
	}

	public void setComnent(String comnent) {
		this.comnent = comnent;
	}

	@XmlAttribute()
	public boolean getList() {
		return list;
	}

	public void setList(boolean list) {
		this.list = list;
	}

	@XmlAttribute()
	public String getMacro() {
		return macro;
	}

	public void setMacro(String macro) {
		this.macro = macro;
	}

	public boolean hasByteString() {
		return this.getType().equals(ByteStringType);
	}

	@XmlAttribute()
	public String getProtobuf() {
		return protobuf;
	}

	public void setProtobuf(String protobuf) {
		this.protobuf = protobuf;
	}

	public void validator(MessageObject msg) {
		this.type = StringUtils.trim(this.type);
		this.name = StringUtils.trim(this.name);
		this.comnent = StringUtils.trim(this.comnent);
		if (getList()) {
			this.protobuf = "repeated";
		}
		if (!StringUtils.isEmpty(getProtobuf())) {
			if (!SUPPORT_PROTOBUF.containsKey(getProtobuf())) {
				throw new IllegalArgumentException("no support protobuf"
						+ this.protobuf);
			}
		}
		if (FieldObject.SUPPORT_TYPE.containsKey(this.type)) {
			if (FieldObject.SUPPORT_TYPE.get(this.type).equals("char")) {
				throw new IllegalArgumentException("no support " + this.type);
			}
		}
		if (!FieldObject.SUPPORT_TYPE.containsKey(this.type)) {
			if (!isMacros()) {
				throw new RuntimeException("自定义类型 macro attr no exist type="
						+ this.type);
			}
			// 自定义类型，判断是否填写了完整包名字
			if (this.type.indexOf(".") != -1) {
				this.fullName = type;
				this.type = StringUtils.getClassName(this.type);
			} else {
				// 省略形式
				this.fullName = msg.getPackages() + MessageObject.BEAN + "."
						+ this.type;
			}
			msg.getImports().add(fullName);
		}
	}

	public String getFullName() {
		return fullName;
	}

	public boolean isMacros() {
		return !StringUtils.isEmpty(macro);
	}

	public boolean getMacros() {
		return isMacros();
	}

	public byte getTypeNum() {
		if (SUPPORT_TYPE.get(getType()) == "byte") {
			return 1;
		} else if (SUPPORT_TYPE.get(getType()) == "short") {
			return 2;
		} else if (SUPPORT_TYPE.get(getType()) == "int") {
			return 3;
		} else if (SUPPORT_TYPE.get(getType()) == "float") {
			return 4;
		} else if (SUPPORT_TYPE.get(getType()) == "long") {
			return 5;
		} else if (SUPPORT_TYPE.get(getType()) == "boolean") {
			return 6;
		} else if (SUPPORT_TYPE.get(getType()) == "String") {
			return 7;
		} else {
			// 自定义类型
			return 8;
		}
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getIndexMask() {
		return String.format("0x%07X", 1 << getIndex() - 1);
	}

	private final int TAG_TYPE_BITS = 3;

	private int makeTag(final int fieldNumber, final int wireType) {
		return (fieldNumber << TAG_TYPE_BITS) | wireType;
	}

	/**
	 * 获取标记属性的tag
	 * 
	 * @return
	 */
	public int getTag() {
		if (SUPPORT_TYPE.get(getType()) == "byte") {
			return makeTag(getIndex(), WIRETYPE_LENGTH_DELIMITED);
		} else if (SUPPORT_TYPE.get(getType()) == "short") {
			return makeTag(getIndex(), WIRETYPE_VARINT);
		} else if (SUPPORT_TYPE.get(getType()) == "int") {
			return makeTag(getIndex(), WIRETYPE_VARINT);
		} else if (SUPPORT_TYPE.get(getType()) == "float") {
			return makeTag(getIndex(), WIRETYPE_FIXED32);
		} else if (SUPPORT_TYPE.get(getType()) == "double") {
			return makeTag(getIndex(), WIRETYPE_FIXED64);
		} else if (SUPPORT_TYPE.get(getType()) == "long") {
			return makeTag(getIndex(), WIRETYPE_VARINT);
		} else if (SUPPORT_TYPE.get(getType()) == "boolean") {
			return makeTag(getIndex(), WIRETYPE_VARINT);
		} else if (SUPPORT_TYPE.get(getType()) == "String") {
			return makeTag(getIndex(), WIRETYPE_LENGTH_DELIMITED);
		}
		// 自定义类型
		return makeTag(getIndex(), WIRETYPE_LENGTH_DELIMITED);
	}
}
