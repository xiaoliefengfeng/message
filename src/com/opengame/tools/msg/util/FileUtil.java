package com.opengame.tools.msg.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {
	public static void createDir(File dir) {
		if ((!dir.exists()) && (!dir.mkdirs()))
			throw new RuntimeException("Can't create the dir [" + dir + "]");
	}

	public static void delete(File file) {
		if (file.isFile()) {
			file.delete();
		} else if (file.isDirectory()) {
			File[] _files = file.listFiles();
			for (File _f : _files) {
				delete(_f);
			}
			file.delete();
		}
	}

	public static void zip(File file, File dest) throws IOException {
		ZipOutputStream _zip1 = new ZipOutputStream(new FileOutputStream(dest));
		zipFiles(file, _zip1, file.getAbsolutePath());
		_zip1.close();
	}

	private static void zipFiles(File file, ZipOutputStream out, String root)
			throws IOException {
		String _entryName = file.getAbsolutePath();
		if (_entryName.equals(root)) {
			_entryName = "";
		} else {
			int _ri = _entryName.indexOf(root);
			_entryName = _entryName.substring(_ri + root.length() + 1);
			_entryName = _entryName.replace('\\', '/');
		}
		if (file.isFile()) {
			out.putNextEntry(new ZipEntry(_entryName));
			FileInputStream _fin = new FileInputStream(file);
			byte[] _b = new byte[1024];
			int len = 0;
			while ((len = _fin.read(_b)) != -1) {
				out.write(_b, 0, len);
			}
			_fin.close();
		} else {
			out.putNextEntry(new ZipEntry(_entryName + "/"));
			File[] _files = file.listFiles();
			for (File _f : _files)
				zipFiles(_f, out, root);
		}
	}
}
