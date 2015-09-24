package com.opengame.tools.msg.util;

public class StringUtils {

	/**
	 * 解析包名称，获取包名称最后一个'.'之后的字符串
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getClassName(String fullName) {
		return fullName.substring(fullName.lastIndexOf(".") + 1,
				fullName.length());
	}

	public static String getPackageName(String fullName) {
		return fullName.substring(0, fullName.lastIndexOf("."));
	}

	/**
	 * 把字符串的首字母变成大写 如 age 变成 Age
	 * 
	 * @param fname
	 * @return
	 */
	public static String getFirstBigName(String fname) {
		return fname.substring(0, 1).toUpperCase()
				+ fname.substring(1, fname.length());
	}

	/**
	 * 根据分割符构建一个文件名称 如CG_USER_LOGOUT ,结果是 CGUserLogout
	 * 
	 * @param fileName
	 * @return
	 */
	public static String buildFileName(String fileName) {
		fileName = trim(fileName);
		String[] names = fileName.split("_");
		StringBuffer sb = new StringBuffer(names[0]);
		for (int i = 1; i < names.length; i++) {
			sb.append(names[i].substring(0, 1)).append(
					names[i].substring(1, names[i].length()).toLowerCase());
		}
		return sb.toString();
	}

	/**
	 * 判断字符串是否为null
	 * 
	 * @param src源字符串
	 * @return true表示为null，false表示不为null
	 */
	public static boolean isEmpty(String src) {
		if (trim(src).length() <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 字符串去空格
	 * 
	 * @param src
	 * @return
	 */
	public static String trim(String src) {
		if (src == null || src == "null" || src.length() <= 0) {
			return "";
		}
		return src.trim();
	}

	/**
	 * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
	 * 例如：HelloWorld->HELLO_WORLD
	 * 
	 * @param name
	 *            转换前的驼峰式命名的字符串
	 * @return 转换后下划线大写方式命名的字符串
	 */
	public static String underscoreName(String name) {
		StringBuilder result = new StringBuilder();
		if (name != null && name.length() > 0) {
			// 将第一个字符处理成大写
			result.append(name.substring(0, 1).toUpperCase());
			// 循环处理其余字符
			for (int i = 1; i < name.length(); i++) {
				String s = name.substring(i, i + 1);
				// 在大写字母前添加下划线
				if (s.equals(s.toUpperCase())
						&& !Character.isDigit(s.charAt(0))) {
					result.append("_");
				}
				// 其他字符直接转成大写
				result.append(s.toUpperCase());
			}
		}
		return result.toString();
	}

	/**
	 * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
	 * 例如：HELLO_WORLD->HelloWorld
	 * 
	 * @param name
	 *            转换前的下划线大写方式命名的字符串
	 * @return 转换后的驼峰式命名的字符串
	 */
	public static String camelName(String name) {
		StringBuilder result = new StringBuilder();
		// 快速检查
		if (name == null || name.isEmpty()) {
			// 没必要转换
			return "";
		} else if (!name.contains("_")) {
			// 不含下划线，仅将首字母小写
			return name.substring(0, 1).toLowerCase() + name.substring(1);
		}
		// 用下划线将原始字符串分割
		String camels[] = name.split("_");
		for (String camel : camels) {
			// 跳过原始字符串中开头、结尾的下换线或双重下划线
			if (camel.isEmpty()) {
				continue;
			}
			// 处理真正的驼峰片段
			if (result.length() == 0) {
				// 第一个驼峰片段，全部字母都小写
				result.append(camel.toLowerCase());
			} else {
				// 其他的驼峰片段，首字母大写
				result.append(camel.substring(0, 1).toUpperCase());
				result.append(camel.substring(1).toLowerCase());
			}
		}
		return result.toString();
	}
}
