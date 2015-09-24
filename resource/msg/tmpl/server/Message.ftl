<#import "Class.ftl" as Class>
package ${packages};

<#if imports??>
<#list imports as import>
import ${import};
</#list>
</#if>
<#list fields as field>
<#if field.list>
import java.util.List;
import java.util.ArrayList;
<#break>
</#if>
</#list>

/** 
 * @author Commuication Auto Maker
 * 
 * @version 1.0.0
 * 
 * ${comnent}
 */
public class ${className} extends ${parentName}{

	<#list fields as field>
	<#if field.list>
	/** ${field.comnent} */
	private List<${field.type}> ${field.name} = new ArrayList<${field.type}>();
	<#else>
	/** ${field.comnent} */
	private ${.globals[field.type]!field.type} ${field.name};
	</#if>
	</#list>
	
	public ${className} (){
	}
	
	<#if fields?size!=0>
	public ${className} (
	<#list fields as field>
	<#if field.list>
		List<${field.type}> ${field.name}<#if field_has_next>,<#else> ){</#if>
	<#else>
		${.globals[field.type]!field.type} ${field.name}<#if field_has_next>,<#else> ){</#if>
	</#if>
	</#list>
	<#list fields as field>
		this.${field.name} = ${field.name};
	</#list>	
	}
	</#if>
	
	public boolean writeImpl(){
		<#list fields as field>
		<#if field.list>
		writeShort(${field.name}.size());
		for (int i = 0; i < ${field.name}.size(); i++) {
			<#if field.type=="Integer">
			writeInteger(${field.name}.get(i));
			<#elseif field.type=="Short">
			writeShort(${field.name}.get(i));
			<#elseif field.type=="Float">
			writeFloat(${field.name}.get(i));
			<#elseif field.type=="Double">
			writeDouble(${field.name}.get(i));
			<#elseif field.type=="Long">
			writeLong(${field.name}.get(i));
			<#elseif field.type=="Byte">
			writeByte(${field.name}.get(i));
			<#elseif field.type=="Character">
			writeCharacter(this.${field.name});
			<#elseif field.type=="String">
			writeString(${field.name}.get(i));
			<#elseif field.type=="Boolean">
			writeBoolean(${field.name}.get(i));
			<#else>
			writeBean(${field.name}.get(i));
			</#if>
		}
		<#else>
		<#if field.type=="Integer">
		writeInteger(this.${field.name});
		<#elseif field.type=="Short">
		writeShort(this.${field.name});
		<#elseif field.type=="Float">
		writeFloat(this.${field.name});
		<#elseif field.type=="Double">
		writeDouble(this.${field.name});
		<#elseif field.type=="Long">
		writeLong(this.${field.name});
		<#elseif field.type=="Byte">
		writeByte(this.${field.name});
		<#elseif field.type=="Character">
		writeCharacter(this.${field.name});
		<#elseif field.type=="String">
		writeString(this.${field.name});
		<#elseif field.type=="Boolean">
		writeBoolean(this.${field.name});
		<#else>
		writeBean(this.${field.name});
		</#if>
		</#if>
		</#list>
		return true;
	}
	
	public boolean readImpl(){
		<#list fields as field>
		<#if field.list>
		int ${field.name}Len = readShort();
		for (int i = 0; i < ${field.name}Len; i++) {
			<#if field.type=="Integer">
			${field.name}.add(readInteger());
			<#elseif field.type=="Short">
			${field.name}.add(readShort());
			<#elseif field.type=="Float">
			${field.name}.add(readFloat());
			<#elseif field.type=="Double">
			${field.name}.add(readDouble());
			<#elseif field.type=="Long">
			${field.name}.add(readLong());
			<#elseif field.type=="Byte">
			${field.name}.add(readByte());
			<#elseif field.type=="Character">
			${field.name}.add(readCharacter());
			<#elseif field.type=="String">
			${field.name}.add(readString());
			<#elseif field.type=="Boolean">
			${field.name}.add(readBoolean());
			<#else>
			${field.name}.add((${field.type})readBean(${field.type}.class));
			</#if>
		}
		<#else>
		<#if field.type=="Integer">
		this.${field.name} = readInteger();
		<#elseif field.type=="Short">
		this.${field.name} = readShort();
		<#elseif field.type=="Float">
		this.${field.name} = readFloat();
		<#elseif field.type=="Double">
		this.${field.name} = readDouble();
		<#elseif field.type=="Long">
		this.${field.name} = readLong();
		<#elseif field.type=="Byte">
		this.${field.name} = readByte();
		<#elseif field.type=="Character">
		this.${field.name} = readCharacter();
		<#elseif field.type=="String">
		this.${field.name} = readString();
		<#elseif field.type=="Boolean">
		this.${field.name} = readBoolean();
		<#else>
		this.${field.name} = (${field.type})readBean(${field.type}.class);
		</#if>
		</#if>
		</#list>
		return true;
	}
	
	<#list fields as field>
	<#if field.list>
	public List<${field.type}> get${field.name?cap_first}(){
		return ${field.name};
	}
	
	public void set${field.name?cap_first}(List<${field.type}> ${field.name}){
		this.${field.name} = ${field.name};
	}
	
	<#else>
	public ${.globals[field.type]!field.type} get${field.name?cap_first}(){
		return ${field.name};
	}
	
	public void set${field.name?cap_first}(${.globals[field.type]!field.type} ${field.name}){
		this.${field.name} = ${field.name};
	}
	
	</#if>
	</#list>
	<#if messageQueue??>
	@Override
	public String getQueue() {
		return ${messageQueue};
	}
	
	</#if>
	<#if messageServer??>
	@Override
	public String getServer() {
		return ${messageServer};
	}
	
	</#if>
	@Override
	public short getType(){
		return MessageType.${name};
	}
	
	@Override
	public String getTypeName(){
		return "${name}";
	}

}