package utils;

import java.io.File;

public class FileUtil {

	/**
	 * 目录不存在，则创建
	 * @param path
	 * @return
	 */
	public static boolean DirsNotExistMkdirs(String path) {
		File dir = new File(path);
		// 创建文件夹
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return true;
	}
	/***
	 * 删除文件
	 * @param filePath
	 * @return
	 */
	public static boolean deleteFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			return file.delete();
		}
		return false;
	}
	/***
	 * 判断文件是否存在
	 * @param filePath
	 * @return
	 */
	public static boolean fileExist(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			return true;
		}
		return false;
	}
}
