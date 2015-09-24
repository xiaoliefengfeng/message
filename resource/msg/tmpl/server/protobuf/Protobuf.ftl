<#global "Integer"="int32">
<#global "Float"="float">
<#global "Long"="int64">
<#global "Byte"="bytes">
<#global "ByteString"="bytes">
<#global "Short"="int32">
<#global "Boolean"="bool">
<#global "Double"="double">
<#global "String"="string">
/** 
 * @author Commuication Auto Maker Google Protobuf
 * 
 * @version 1.0.0
 * 
 */
 
<#list macro as mc>
/** 
 *${mc.comnent}
 */
message	${mc.className}{
	<#list mc.fields as field>
	${field.protobuf}    ${.globals[field.type]!field.type}		 	${field.name} = ${field.index} ;        // ${field.comnent}
	</#list>
}
</#list>

<#list messages as msg>
/** 
 *${msg.comnent}
 */
message	${msg.className}{
	<#list msg.fields as field>
	${field.protobuf}    ${.globals[field.type]!field.type}		 	${field.name} = ${field.index} ;        // ${field.comnent}
	</#list>
}
</#list>