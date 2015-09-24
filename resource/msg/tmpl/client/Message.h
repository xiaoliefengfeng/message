<#import "Class.ftl" as Class>
#ifndef  _${moduleUpperCase}_${classNameUpperCase}_H_
#define  _${moduleUpperCase}_${classNameUpperCase}_H_
#include "../../../core/gamecore.h"
<#list cppImports as field>
#include "${field}.h"
</#list>
/** 
 * @author Commuication Auto Maker
 * 
 * @version 1.0.0
 * 
 * ${comnent}
 */

class ${className}<#if !macro> : public IMessage </#if>{

public	:
	<#list fields as field>
	<#if field.list>
	/** ${field.comnent} */
	<#if !field.macros>
	std::vector<${.globals[field.type]!field.type}> ${field.name};
	<#else>
	std::vector<${field.type}> ${field.name};
	</#if>
	<#else>
	/** ${field.comnent} */
	${.globals[field.type]!field.type} ${field.name};
	</#if>
	</#list>

public	:
	${className}();

	<#if macro>virtual </#if>~${className}();

	virtual bool readImpl(Message& msg);

	virtual bool writeImpl(Message& msg);

	<#if !macro>
	virtual std::string getTypeName();

	virtual int16 getType();
	</#if>

};
#endif
