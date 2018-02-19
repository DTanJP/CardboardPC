package org.cardboard.dtanjp;

import java.io.File;
/**
 * FileUtil.java
 * 
 * @author David Tan
 **/
public class FileUtil {

	/** Get the file extension **/
	public static String GetExtension(File file) {
		if(file == null)
			return null;
		String fileName = file.getName();
		return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
	}
	
	public static File[] OSFiles() {
		return Config.OS_DIR.listFiles();
	}
	
	public static File[] DriverFiles() {
		return Config.DRIVER_DIR.listFiles();
	}
	
	public static File[] AppFiles() {
		return Config.APP_DIR.listFiles();
	}
	
	public static File[] APIFiles() {
		return Config.LIBRARY_DIR.listFiles();
	}
	
}
