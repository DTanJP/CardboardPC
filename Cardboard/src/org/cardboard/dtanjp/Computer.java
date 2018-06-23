package org.cardboard.dtanjp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Plugin.Application;
import Plugin.Driver;
import Plugin.Plugin;
import Plugin.PluginAssistant;
import fileIO.FileUtil;
import fileIO.WritingWorker;

/**
 * Computer.java
 * 
 * @author David Tan
 **/
public class Computer {

	/** Constructor **/
	private Computer() {
	}
	
	/** Singleton **/
	public static Computer getInstance() {
		if(instance == null)
			instance = new Computer();
		return instance;
	}
	
	/** Start the system up **/
	protected void InitializeOS() {
		//Exit if no OS is set
		if(os == null) {
			MainApp.println("[Computer]: <InitializeOS>: Cannot find any OS plugins. Shutting down...");
			System.exit(0);
		}
		
		//Start up the operating system
		os.Initialize();
	}
	
	/** Scans the driver directory for driver plugins **/
	public void ScanDrivers() {
		drivers.clear();
		File files[] = FileUtil.DriverFiles();
		for(File file : files) {
			if(file == null)
				continue;
			if(!file.exists() || !file.isFile())
				continue;
			
			boolean isDriver = false;
			if(FileUtil.GetExtension(file).equals("jar")) {
				Driver driver = new Driver(file.getName());
				Class<?>[] clazz = PluginAssistant.ScanJar(file);
				for(Class<?> c : clazz) {
					if(c == null)
						continue;
					driver.Add(c);
					if(PluginAssistant.IsPlugin(c)) {
						driver.SetDriverClass(c);
						isDriver = true;
					}
				}
				if(isDriver)
					drivers.put(file.getName(), driver);
			} else if(FileUtil.GetExtension(file).equals("class")) {
				Driver driver = new Driver(file.getName());
				Class<?> clazz = PluginAssistant.LoadClassFile(file);
				driver.Add(clazz);
				if(PluginAssistant.IsPlugin(clazz)) {
					driver.SetDriverClass(clazz);
					isDriver = true;
				}
				if(isDriver)
					drivers.put(file.getName(), driver);
			}
		}
	}
	
	/** Scans the application directory for application plugins **/
	public void ScanApplications() {
		applications.clear();
		File files[] = FileUtil.AppFiles();
		for(File file : files) {
			if(file == null)
				continue;
			if(!file.exists() || !file.isFile())
				continue;
			boolean isApplication = false;
			if(FileUtil.GetExtension(file).equals("jar")) {
				Application app = new Application(file.getName());
				Class<?>[] clazz = PluginAssistant.ScanJar(file);
				for(Class<?> c : clazz) {
					if(c == null)
						continue;
					app.Add(c);
					if(PluginAssistant.IsPlugin(c)) {
						app.SetApplicationClass(c);
						isApplication = true;
					}
				}
				if(isApplication)
					applications.put(file.getName(), app);
			} else if(FileUtil.GetExtension(file).equals("class")) {
				Application app = new Application(file.getName());
				Class<?> clazz = PluginAssistant.LoadClassFile(file);
				app.Add(clazz);
				if(PluginAssistant.IsPlugin(clazz)) {
					app.SetApplicationClass(clazz);
					isApplication = true;
				}
				if(isApplication)
					applications.put(file.getName(), app);
			}
		}
	}
	
	/** Search the OS folder for a OS plugin **/
	protected void ScanOS() {
		List<Class<?>> osplugins = new ArrayList<>();
		
		//Check for the OS
		File[] osFiles = FileUtil.OSFiles();
		//println("[Computer]: <ScanOS>: "+osFiles.length+" files found.");
		
		//Don't continue. No files to check
		if(osFiles.length == 0)
			return;
		
		//Loop through the files
		for(File file : osFiles) {
			//File must be a .jar or a .class
			if(!FileUtil.GetExtension(file).equals("jar") && !FileUtil.GetExtension(file).equals("class"))
				continue;
			
			if(FileUtil.GetExtension(file).equals("jar")) {
				osName = file.getName();
				Class<?>[] clazz = PluginAssistant.ScanJar(file);
				for(Class<?> c : clazz) {
					if(PluginAssistant.IsPlugin(c)) {
						//println("[Computer]: <ScanOS>: Found valid plugin: ["+file.getName()+" :: "+c.getName()+"]");
						osplugins.add(c);
					} else
						osClasses.put(c.getName(), c);
				}
			} else if(FileUtil.GetExtension(file).equals("class")) {
				osName = file.getName();
				Class<?> clazz = PluginAssistant.LoadClassFile(file);
				if(clazz != null && PluginAssistant.IsPlugin(clazz)) {
					//println("[Computer]: <ScanOS>: Found valid plugin: ["+file.getName()+" :: "+clazz.getName()+"]");
					osplugins.add(clazz);
				} else
					osClasses.put(clazz.getName(), clazz);
			}
		}
		if(osplugins.size() > 1) {
			println("[Computer]: <ScanOS>: "+osplugins.size()+" plugins found in the OS folder. Only 1 is allowed.");
			System.exit(0);
		} else if(osplugins.size() == 1) {
			os = PluginAssistant.AsPlugin(osplugins.get(0));
			println("[Computer]: <ScanOS>: Found ["+osName+"]");
		}
	}
	
	/** Prints a debug msg + new line **/
	public static void println(Object line) {
		if(Config.DEBUG) {
			System.out.println(line.toString());
			if(Config.ENABLE_ERROR_LOG)
				WritingWorker.WriteFile(Config.OUTPUT_LOG, line.toString());
		}
	}
	
	/** Prints a debug msg **/
	public static void print(Object line) {
		if(Config.DEBUG) {
			System.out.print(line.toString());
			
			if(Config.ENABLE_ERROR_LOG)
				WritingWorker.WritelnFile(Config.OUTPUT_LOG, line.toString());
		}
	}
	
	/** Returns the OS instance **/
	public Plugin GetOS() {
		return os;
	}
	
	/** Gets the OS File name **/
	public String GetOSName() {
		return osName;
	}
	
	public Map<String, Driver> GetDrivers() {
		return drivers;
	}
	
	public Map<String, Application> GetApplications() {
		return applications;
	}
	
	public int FPS() {
		return Config.FPS;
	}
	
	public Double VERSION() {
		return Config.VERSION;
	}
	
	public String COMPUTER_NAME() {
		return Config.COMPUTER_NAME;
	}
	
	/** Variables **/
	private static Computer instance;
	public boolean REQUEST_SHUTDOWN = false;
	private String osName = "";
	private Plugin os = null;
	
	public final Map<String, Class<?>> osClasses = new HashMap<>();
	public final Map<String, Driver> drivers = new HashMap<>();
	public final Map<String, Application> applications = new HashMap<>();
}
