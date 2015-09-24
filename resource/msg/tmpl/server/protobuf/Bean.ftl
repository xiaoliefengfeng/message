<#import "Class.ftl" as Class>
package ${packages};

import java.io.IOException;
<#if requiredField>
import com.google.protobuf.UninitializedMessageException;
</#if>
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
 * @author Commuication Auto Maker Google Protobuf
 * 
 * @version 1.0.0
 * 
 * ${comnent}
 */
public class ${className} extends ${parentName} {
<#include "CommonClass.ftl">
	
}