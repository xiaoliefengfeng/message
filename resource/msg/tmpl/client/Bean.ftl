<#import "Class.ftl" as Class>
package ${packages}-----{
	<#if imports??>
	<#list imports as import>
	import ${import};
	</#list>
	</#if>
	import net.Bean;
	
	/** 
	 * @author Commuication Auto Maker
	 * 
	 * @version 1.0.0
	 * 
	 * ${comnent}
	 */
	public class ${className} extends Bean {
	
		<#list fields as field>
		<#if field.list>
		//${field.comnent}
		private var _${field.name}: Vector.<${.globals[field.type]!field.type}> = new Vector.<${.globals[field.type]!field.type}>();
		<#else>
		//${field.comnent}
		private var _${field.name}: ${.globals[field.type]!field.type};
		</#if>
		</#list>
		/**
		 * 写入字节缓存
		 */
		override protected function writing(): Boolean{
			<#list fields as field>
			<#if field.list>
			writeShort(_${field.name}.length);
			for (var i: int = 0; i < _${field.name}.length; i++) {
				<#if field.type=="int">
				writeInt(_${field.name}[i]);
				<#elseif field.type=="short">
				writeShort(_${field.name}[i]);
				<#elseif field.type=="float">
				writeFloat(_${field.name}[i]);
				<#elseif field.type=="long">
				writeLong(_${field.name}[i]);
				<#elseif field.type=="byte">
				writeByte(_${field.name}[i]);
				<#elseif field.type=="String">
				writeString(_${field.name}[i]);
				<#else>
				writeBean(_${field.name}[i]);
				</#if>
			}
			<#else>
			<#if field.type=="int">
			writeInt(_${field.name});
			<#elseif field.type=="short">
			writeShort(_${field.name});
			<#elseif field.type=="float">
			writeFloat(_${field.name});
			<#elseif field.type=="long">
			writeLong(_${field.name});
			<#elseif field.type=="byte">
			writeByte(_${field.name});
			<#elseif field.type=="String">
			writeString(_${field.name});
			<#else>
			writeBean(_${field.name});
			</#if>
			</#if>
			</#list>
			return true;
		}
		
		/**
		 * 读取字节缓存
		 */
		override protected function reading(): Boolean{
			<#list fields as field>
			<#if field.list>
			var ${field.name}_length : int = readShort();
			for (var i: int = 0; i < ${field.name}_length; i++) {
				<#if field.type=="int">
				_${field.name}[i] = readInt();
				<#elseif field.type=="short">
				_${field.name}[i] = readShort();
				<#elseif field.type=="float">
				_${field.name}[i] = readFloat();
				<#elseif field.type=="long">
				_${field.name}[i] = readLong();
				<#elseif field.type=="byte">
				_${field.name}[i] = readByte();
				<#elseif field.type=="String">
				_${field.name}[i] = readString();
				<#else>
				_${field.name}[i] = readBean(${field.type}) as ${field.type};
				</#if>
			}
			<#else>
			<#if field.type=="int">
			_${field.name} = readInt();
			<#elseif field.type=="short">
			_${field.name} = readShort();
			<#elseif field.type=="float">
			_${field.name} = readFloat();
			<#elseif field.type=="long">
			_${field.name} = readLong();
			<#elseif field.type=="byte">
			_${field.name} = readByte();
			<#elseif field.type=="String">
			_${field.name} = readString();
			<#else>
			_${field.name} = readBean(${field.type}) as ${field.type};
			</#if>
			</#if>
			</#list>
			return true;
		}
		
		<#list fields as field>
		<#if field.list>
		public function get ${field.name}(): Vector.<${.globals[field.type]!field.type}>{
			return _${field.name};
		}
		
		public function set ${field.name}(value: Vector.<${.globals[field.type]!field.type}>): void{
			this._${field.name} = value;
		}
		
		<#else>
		public function get ${field.name}(): ${.globals[field.type]!field.type}{
			return _${field.name};
		}
		
		public function set ${field.name}(value: ${.globals[field.type]!field.type}): void{
			this._${field.name} = value;
		}
		
		</#if>
		</#list>
	}
}