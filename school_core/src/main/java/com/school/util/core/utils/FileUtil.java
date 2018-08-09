package com.school.util.core.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * 文件工具类
 *
 */
public class FileUtil {

	/**
	 * 关闭
	 * @param closeable 被关闭的对象
	 */
	public static void close(Closeable closeable){
		if(closeable == null) return;
		try {
			closeable.close();
		} catch (IOException e) {
		}
	}

}
