package org.cardboard.dtanjp;

import fileIO.WritingWorker;

/**
 * MainApp.java
 * 
 * @author David Tan
 **/
public class MainApp {

	/** Constructor **/
	private MainApp() {
	}
	
	/** Main method **/
	public static void main(String[] args) {
		instance = new MainApp();
		instance.Initialize();
	}
	
	/** Methods **/
	private void Initialize() {
		if(Config.ENABLE_ERROR_LOG && Config.DEBUG) {
			if(Config.OUTPUT_LOG.exists() && Config.OUTPUT_LOG.isFile())
				WritingWorker.ClearFile(Config.OUTPUT_LOG);
		}
		println("["+Config.COMPUTER_NAME+"]: Initializing...");
		if(SetupRequired()) {
			println("["+Config.COMPUTER_NAME+"]: Creating environment...");
			if(!Config.OS_DIR.exists())     Config.OS_DIR.mkdir();
			if(!Config.DRIVER_DIR.exists()) Config.DRIVER_DIR.mkdir();
			if(!Config.APP_DIR.exists())    Config.APP_DIR.mkdir();
			if(!Config.LIBRARY_DIR.exists())Config.LIBRARY_DIR.mkdir();
			println("["+Config.COMPUTER_NAME+"]: Ready to restart.");
			return;
		}
		println("["+Config.COMPUTER_NAME+"]: Creating computer...");
		
		//Create computer instance
		computer = Computer.getInstance();
		
		println("["+Config.COMPUTER_NAME+"]: Scanning for APIs...");
		//Search for libraries
		computer.ScanLibraries();
		
		println("["+Config.COMPUTER_NAME+"]: Scanning for OS...");
		//Search for a OS
		computer.ScanOS();
		
		//Start the OS
		computer.InitializeOS();
		println("["+Config.COMPUTER_NAME+"]: Booting up OS...");
		println("=========[Start]==========");
		println("* Computer: "+Config.COMPUTER_NAME);
		println("* Version: "+Config.VERSION);
		println("* OS: "+computer.GetOSName());
		println("* APIs: "+computer.GetLibrary().size());
		println("==========================");
		long lastLoopTime = System.nanoTime();
		final long OPTIMAL_TIME = 1000000000 / Config.FPS_TARGET_RATE;
		try {
			while(!computer.REQUEST_SHUTDOWN) {
			    long now = System.nanoTime();
			    long updateLength = now - lastLoopTime;
			    lastLoopTime = now;
			    Config.delta = updateLength / ((double)OPTIMAL_TIME);
			    
			    // update the frame counter
			    lastFpsTime += updateLength;
			    Config.FPS++;
			      
			    // update our FPS counter if a second has passed since we last recorded
			    if (lastFpsTime >= 1000000000) {
			         lastFpsTime = 0;
			         Config.FPS = 0;
			    }
			    
			    //Update
			    computer.GetOS().Update();
			    
			    long sleepTime = (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000;
				if(sleepTime > 0)
					Thread.sleep(sleepTime);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		computer.GetOS().Destruct();
		println("==========[End]===========");
	}
	
	/** Check to see if anything needs to be setup **/
	private boolean SetupRequired() {
		int errors = 0;
		if(!Config.OS_DIR.exists() || !Config.OS_DIR.isDirectory())
			errors++;
		if(!Config.DRIVER_DIR.exists() || !Config.DRIVER_DIR.isDirectory())
			errors++;
		if(!Config.APP_DIR.exists() || !Config.APP_DIR.isDirectory())
			errors++;
		if(!Config.LIBRARY_DIR.exists() || !Config.LIBRARY_DIR.isDirectory())
			errors++;
		return errors > 0;
	}
	
	/** Prints a debug msg + new line **/
	public static void println(Object line) {
		if(Config.DEBUG) {
			System.out.println(line.toString());
			if(Config.ENABLE_ERROR_LOG)
				WritingWorker.WritelnFile(Config.OUTPUT_LOG, line.toString());
		}
	}
	
	/** Prints a debug msg **/
	public static void print(Object line) {
		if(Config.DEBUG) {
			System.out.print(line.toString());
			
			if(Config.ENABLE_ERROR_LOG)
				WritingWorker.WriteFile(Config.OUTPUT_LOG, line.toString());
		}
	}
	
	/** Variables **/
	private static MainApp instance = null;
	private static Computer computer = null;
	private long lastFpsTime = 0;
}
