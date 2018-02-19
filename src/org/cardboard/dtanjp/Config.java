package org.cardboard.dtanjp;

import java.io.File;

/**
 * Config.java
 * 
 * @author David Tan
 **/
public class Config {

	/** DEBUG **/
	public static boolean DEBUG = true;
	
	/** Environment directories **/
	public final static File OS_DIR = new File("./OS/");
	public final static File DRIVER_DIR = new File("./Drivers/");
	public final static File APP_DIR = new File("./Applications/");
	public final static File LIBRARY_DIR = new File("./Library/");
	
	/** Settings **/
	public final static String COMPUTER_NAME = "Cardboard";
	public final static double VERSION = 0.1D;
}
