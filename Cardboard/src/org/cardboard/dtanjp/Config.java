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
	public static boolean ENABLE_ERROR_LOG = false;
	
	public final static File OUTPUT_LOG = new File("./OUTPUT.txt");
	/** Environment directories **/
	public final static File OS_DIR = new File("./OS/");
	public final static File DRIVER_DIR = new File("./Drivers/");
	public final static File APP_DIR = new File("./Applications/");
	
	/** Settings **/
	public final static String COMPUTER_NAME = "Cardboard";
	public final static double VERSION = 1.0D;
	
	/** Update FPS **/
	public static final int FPS_TARGET_RATE = 60;
	public static int FPS = 0;
	public static double delta = 0;
}
