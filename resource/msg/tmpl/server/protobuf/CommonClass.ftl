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
	
	public boolean writeImpl0() throws IOException{
		<#list fields as field>
		<#if field.list>
		for (int i = 0; i < ${field.name}.size(); i++) {
			<#if field.type=="Integer">
			writeInteger(${field.index},${field.name}.get(i),true);
			<#elseif field.type=="Short">
			writeShort(${field.index},${field.name}.get(i),true);
			<#elseif field.type=="Float">
			writeFloat(${field.index},${field.name}.get(i),true);
			<#elseif field.type=="Double">
			writeDouble(${field.index},${field.name}.get(i),true);
			<#elseif field.type=="Long">
			writeLong(${field.index},${field.name}.get(i),true);
			<#elseif field.type=="ByteString">
			writeBytes(${field.index},${field.name}.get(i),true);
			<#elseif field.type=="Character">
			writeCharacter(${field.index},this.${field.name},true);
			<#elseif field.type=="String">
			writeString(${field.index},${field.name}.get(i),true);
			<#elseif field.type=="Boolean">
			writeBoolean(${field.index},${field.name}.get(i),true);
			<#else>
			writeBean(${field.index},${field.name}.get(i),true);
			</#if>
		}
		<#else>
		<#if field.type=="Integer">
		writeInteger(${field.index},this.${field.name});
		<#elseif field.type=="Short">
		writeShort(${field.index},this.${field.name});
		<#elseif field.type=="Float">
		writeFloat(${field.index},this.${field.name});
		<#elseif field.type=="Double">
		writeDouble(${field.index},this.${field.name});
		<#elseif field.type=="Long">
		writeLong(${field.index},this.${field.name});
		<#elseif field.type=="ByteString">
		writeBytes(${field.index},this.${field.name});
		<#elseif field.type=="Character">
		writeCharacter(${field.index},this.${field.name});
		<#elseif field.type=="String">
		writeString(${field.index},this.${field.name});
		<#elseif field.type=="Boolean">
		writeBoolean(${field.index},this.${field.name});
		<#else>
		writeBean(${field.index},this.${field.name});
		</#if>
		</#if>
		</#list>
		return true;
	}
	
	public int getSerializedSize(){
		int size = memoizedSerializedSize;
		if (size != -1)
			return size;
		size = 0;
		<#list fields as field>
		<#if field.list>
		{
			int dataSize =0;
			for (int i = 0; i < ${field.name}.size(); i++) {
				<#if field.type=="Integer">
				dataSize += computeInt32SizeNoTag(${field.name}.get(i));
				<#elseif field.type=="Short">
				dataSize += computeInt32SizeNoTag(${field.name}.get(i));
				<#elseif field.type=="Float">
				dataSize += 4*${field.name}.size();
				<#elseif field.type=="Double">
				dataSize += 8*${field.name}.size();
				<#elseif field.type=="Long">
				dataSize += computeInt64SizeNoTag(${field.name}.get(i));
				<#elseif field.type=="ByteString">
				dataSize += computeBytesSizeNoTag(${field.name}.get(i));
				<#elseif field.type=="Character">
				dataSize += computeInt32Size(${field.name}.get(i));
				<#elseif field.type=="String">
				dataSize += computeStringSize(${field.index},${field.name}.get(i));
				<#elseif field.type=="Boolean">
				dataSize += 1*${field.name}.size();
				<#else>
				size += computeBeanSize(${field.index},${field.name}.get(i));
				</#if>
			}
			size += dataSize;
			<#if !field.macros>
			size += 1*${field.name}.size();
			</#if>
		}
		<#else>
		if(has${field.name?cap_first}()){
			<#if field.type=="Integer">
			size += computeInt32Size(${field.index},this.${field.name});
			<#elseif field.type=="Short">
			size += computeInt32Size(${field.index},this.${field.name});
			<#elseif field.type=="Float">
			size += computeFloatSize(${field.index},this.${field.name});
			<#elseif field.type=="Double">
			size += computeDoubleSize(${field.index},this.${field.name});
			<#elseif field.type=="Long">
			size += computeInt64Size(${field.index},this.${field.name});
			<#elseif field.type=="ByteString">
			size += computeBytesSize(${field.index},this.${field.name});
			<#elseif field.type=="Character">
			size += computeInt32Size(${field.index},this.${field.name});
			<#elseif field.type=="String">
			size += computeStringSize(${field.index},this.${field.name});
			<#elseif field.type=="Boolean">
			size += computeBoolSize(${field.index},this.${field.name});
			<#else>
			size += computeBeanSize(${field.index},this.${field.name});
			</#if>
		}
		</#if>
		</#list>
		return size;			
	}
	
	public boolean readImpl0() throws IOException{
		boolean done = false;
		while(!done){
			int tag = readTag();
			switch(tag){
			case 0:
				 done = true;
				 break;
			<#list fields as field>
			case ${field.tag}:
				bitField |= ${field.indexMask};
				<#if field.list>
				<#if field.type=="Integer">
				${field.name}.add(readInt32());
				<#elseif field.type=="Short">
				${field.name}.add(readShortp());
				<#elseif field.type=="Float">
				${field.name}.add(readFloatp());
				<#elseif field.type=="Double">
				${field.name}.add(readDoublep());
				<#elseif field.type=="Long">
				${field.name}.add(readInt64());
				<#elseif field.type=="ByteString">
				${field.name}.add(readBytes());
				<#elseif field.type=="Character">
				${field.name}.add(readCharacter());
				<#elseif field.type=="String">
				${field.name}.add(readStringp());
				<#elseif field.type=="Boolean">
				${field.name}.add(readBool());
				<#else>
				${field.name}.add((${field.type})readBean0(${field.type}.class));
				</#if>
				<#else>
				<#if field.type=="Integer">
				this.${field.name} = readInt32();
				<#elseif field.type=="Short">
				this.${field.name} = readShortp();
				<#elseif field.type=="Float">
				this.${field.name} = readFloatp();
				<#elseif field.type=="Double">
				this.${field.name} = readDoublep();
				<#elseif field.type=="Long">
				this.${field.name} = readInt64();
				<#elseif field.type=="ByteString">
				this.${field.name} = readBytes();
				<#elseif field.type=="Character">
				this.${field.name} = readCharacter();
				<#elseif field.type=="String">
				this.${field.name} = readStringp();
				<#elseif field.type=="Boolean">
				this.${field.name} = readBool();
				<#else>
				this.${field.name} = (${field.type})readBean0(${field.type}.class);
				</#if>
				</#if>
				break;
			</#list>	 
			}
		}
		return true;
	}
	
	public boolean isInitialized(){
	<#list fields as field>
	<#if field.required>
		if(!has${field.name?cap_first}()){
			throw new UninitializedMessageException("${field.name}");
		}
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
	public boolean has${field.name?cap_first}(){
		return hasValueIndex(${field.indexMask});
	}
	
	public ${.globals[field.type]!field.type} get${field.name?cap_first}(){
		return ${field.name};
	}
	
	public void set${field.name?cap_first}(${.globals[field.type]!field.type} ${field.name}){
		bitField |= ${field.indexMask};
		this.${field.name} = ${field.name};
	}
	
	</#if>
	</#list>