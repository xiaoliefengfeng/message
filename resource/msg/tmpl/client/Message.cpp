<#import "Class.ftl" as Class>
#include "${className}.h"

/** 
 * @author Commuication Auto Maker
 * 
 * @version 1.0.0
 * 
 * ${comnent}
 */

${className}::${className}(){
<#list fields as field>
<#if !field.list>
	<#if field.type=="Integer">
	${field.name} = 0;
	<#elseif field.type=="Short">
	${field.name} = 0;
	<#elseif field.type=="Float">
	${field.name} = 0;
	<#elseif field.type=="Double">
	${field.name} = 0;
	<#elseif field.type=="Long">
	${field.name} = 0;
	<#elseif field.type=="Byte">
	${field.name} = 0;
	<#elseif field.type=="Character">
	${field.name} = 0;
	<#elseif field.type=="String">
	${field.name} = "";
	<#elseif field.type=="Boolean">
	${field.name} = 0;
	<#else>
	//${field.name} = NUll;
	</#if>
</#if>
</#list>
}

${className}::~${className}(){
<#list fields as field>
<#if !field.list>
	<#if field.type=="Integer">
	${field.name} = 0;
	<#elseif field.type=="Short">
	${field.name} = 0;
	<#elseif field.type=="Float">
	${field.name} = 0;
	<#elseif field.type=="Double">
	${field.name} = 0;
	<#elseif field.type=="Long">
	${field.name} = 0;
	<#elseif field.type=="Byte">
	${field.name} = 0;
	<#elseif field.type=="Character">
	${field.name} = 0;
	<#elseif field.type=="String">
	${field.name} = "";
	<#elseif field.type=="Boolean">
	${field.name} = 0;
	<#else>
	//${field.name} = NUll;
	</#if>
</#if>
</#list>
}

bool ${className}::readImpl(Message& msg){
	<#list fields as field>
	<#if field.list>
	int16 ${field.name}Len;
	msg>>${field.name}Len;
	for (int i = 0; i < ${field.name}Len; i++) {
		<#if field.macros>
		${field.type} ${field.name}Tmp;
		${field.name}Tmp.readImpl(msg);
		${field.name}.push_back(${field.name}Tmp);
		<#else>
		${.globals[field.type]!field.type} ${field.name}Tmp;
		msg>>${field.name}Tmp;
		${field.name}.push_back(${field.name}Tmp);
		</#if>
	}
	<#else>
	<#if field.macros>
	${field.name}.readImpl(msg);
	<#else>
	msg>>${field.name};
	</#if>
	</#if>
	</#list>
	return true;
}

bool ${className}::writeImpl(Message& msg){
	<#list fields as field>
	<#if field.list>
	int16 ${field.name}Len = ${field.name}.size();
	msg<<${field.name}Len;
	for (int i = 0; i < ${field.name}Len; i++) {
		<#if field.macros>
		${field.type} ${field.name}Tmp = ${field.name}.at(i);
		${field.name}Tmp.writeImpl(msg);
		<#else>
		${.globals[field.type]!field.type} ${field.name}Tmp = ${field.name}.at(i);
		<#if field.type=="String">
		msg<<${field.name}Tmp.c_str();
		<#else>
		msg<<${field.name}Tmp;
		</#if>
		</#if>
	}
	<#else>
	<#if field.macros>
	${field.name}.writeImpl(msg);
	<#else>
	<#if field.type=="String">
	msg<<${field.name}.c_str();
	<#else>
	msg<<${field.name};
	</#if>
	</#if>
	</#if>
	</#list>
	return true;
}

<#if !macro>
std::string ${className}::getTypeName(){
	return "${className}";
}

int16 ${className}::getType(){
	return ${className};
}
</#if>
