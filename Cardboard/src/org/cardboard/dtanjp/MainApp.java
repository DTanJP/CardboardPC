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
		
		//Make sure everything is set up
		if(SetupRequired()) {
			println("["+Config.COMPUTER_NAME+"]: Creating environment...");
			if(!Config.OS_DIR.exists())     Config.OS_DIR.mkdir();
			if(!Config.DRIVER_DIR.exists()) Config.DRIVER_DIR.mkdir();
			if(!Config.APP_DIR.exists())    Config.APP_DIR.mkdir();
			println("["+Config.COMPUTER_NAME+"]: Ready to restart.");
			return;
		}
		println("["+Config.COMPUTER_NAME+"]: Creating computer...");
		//Create computer instance
		computer = Computer.getInstance();
		
		println("["+Config.COMPUTER_NAME+"]: Scanning for OS...");
		//Search for a OS
		computer.ScanOS();
		
		println("["+Config.COMPUTER_NAME+"]: Scanning for Drivers...");
		//Search for drivers
		computer.ScanDrivers();
		
		println("["+Config.COMPUTER_NAME+"]: Scanning for Applications...");
		//Search for applications
		computer.ScanApplications();
		
		println("["+Config.COMPUTER_NAME+"]: Booting up OS...");
		//Start the OS
		computer.InitializeOS();
		println("=========[Start]==========");
		println("* Computer: "+Config.COMPUTER_NAME);
		println("* Version: "+Config.VERSION);
		println("* OS: "+computer.GetOSName());
		println("* Drivers: "+computer.GetDrivers().size());
		println("* Applications: "+computer.GetApplications().size());
		println("==========================");
		long lastLoopTime = System.nanoTime();
		final long OPTIMAL_TIME = 1000000000 / Config.FPS_TARGET_RATE;
	    int fps = 0;
		try {
			//Pass the control to OS to decide when to shutdown
			while(!computer.GetOS().RequestShutDown()) {
				long now = System.nanoTime();
			    long updateLength = now - lastLoopTime;
			    lastLoopTime = now;
			    Config.delta = updateLength / ((double)OPTIMAL_TIME);
			    
			    // update the frame counter
			    lastFpsTime += updateLength;
			    fps++;
			    // update our FPS counter if a second has passed since we last recorded
			    if (lastFpsTime >= 1000000000) {
			         lastFpsTime = 0;
			         Config.FPS = fps;
			         fps = 0;
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
		System.exit(0);
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
