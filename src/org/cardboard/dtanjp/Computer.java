package org.cardboard.dtanjp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Plugin.API;
import Plugin.Plugin;
import Plugin.PluginAssistant;

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
	protected static Computer getInstance() {
		if(instance == null)
			instance = new Computer();
		return instance;
	}
	
	/** Start the system up **/
	protected void InitializeOS() {
		//Exit if no OS is set
		if(os == null) {
			MainApp.println("[Computer]: <ScanOS>: Cannot find any OS plugins. Shutting down...");
			System.exit(0);
		}
		
		//Start up the operating system
		os.Initialize();
	}
	
	/** Search the Libraries folder for any APIs **/
	public void ScanLibraries() {
		library.clear();
		File[] apiFiles = FileUtil.APIFiles();
		//println("[Computer]: <ScanLibraries()>: "+apiFiles.length+" files found.");
		for(File file : apiFiles) {
			if(file == null || !file.exists())
				continue;
			
			if(file.isDirectory()) {
				ScanLibraries(file);
				continue;
			}
			
			if(FileUtil.GetExtension(file).equals("jar")) {
				//println("[Computer]: <ScanLibraries()>: Found Jar: ["+file.getName()+"]");
				API api = new API(file.getName());
				Class<?>[] clazz = PluginAssistant.ScanJar(file);
				for(Class<?> c : clazz) {
					if(c == null)
						continue;
					api.Add(c);
				}
				library.put(file.getName(), api);
			} else if(FileUtil.GetExtension(file).equals("class")) {
				//println("[Computer]: <ScanLibraries()>: Found Class: ["+file.getName()+"]");
				API api = new API(file.getName());
				Class<?> clazz = PluginAssistant.LoadClassFile(file);
				api.Add(clazz);
				library.put(file.getName(), api);
			}
		}
	}
	
	private void ScanLibraries(File directory) {
		if(directory == null)
			return;
		
		if(!directory.isDirectory() || !directory.exists())
			return;
		
		File[] apiFiles = directory.listFiles();
		//println("[Computer]: <ScanLibraries(File)>: "+apiFiles.length+" files found.");
		for(File file : apiFiles) {
			if(file == null || !file.exists())
				continue;
			
			//Recursively scan all directories for class files
			if(file.isDirectory()) {
				ScanLibraries(file);
				continue;
			}
			
			if(FileUtil.GetExtension(file).equals("jar")) {
				//println("[Computer]: <ScanLibraries(File)>: Found Jar: ["+file.getName()+"]");
				API api = new API(file.getName());
				Class<?>[] clazz = PluginAssistant.ScanJar(file);
				for(Class<?> c : clazz) {
					if(c == null)
						continue;
					api.Add(c);
				}
				library.put(file.getName(), api);
			} else if(FileUtil.GetExtension(file).equals("class")) {
				//println("[Computer]: <ScanLibraries(File)>: Found Jar: ["+file.getName()+"]");
				API api = new API(file.getName());
				Class<?> clazz = PluginAssistant.LoadClassFile(file);
				api.Add(clazz);
				library.put(file.getName(), api);
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
					}
				}
			} else if(FileUtil.GetExtension(file).equals("class")) {
				osName = file.getName();
				Class<?> clazz = PluginAssistant.LoadClassFile(file);
				if(clazz != null && PluginAssistant.IsPlugin(clazz)) {
					//println("[Computer]: <ScanOS>: Found valid plugin: ["+file.getName()+" :: "+clazz.getName()+"]");
					osplugins.add(clazz);
				}
			}
		}
		if(osplugins.size() > 1) {
			println("[Computer]: <ScanOS>: "+osplugins.size()+" plugins found in the OS folder. Only 1 is allowed.");
			System.exit(0);
		} else if(osplugins.size() == 1)
			os = PluginAssistant.AsPlugin(osplugins.get(0));
	}
	
	/** Prints a debug msg + new line **/
	public static void println(Object line) {
		if(Config.DEBUG)
			System.out.println(line.toString());
	}
	
	/** Prints a debug msg **/
	public static void print(Object line) {
		if(Config.DEBUG)
			System.out.print(line.toString());
	}
	
	/** Returns the OS instance **/
	public Plugin GetOS() {
		return os;
	}
	
	/** Gets the OS File name **/
	public String GetOSName() {
		return osName;
	}
	
	/** Searches for a API **/
	public API GetAPI(String name) {
		return library.get(name);
	}
	
	public Map<String, API> GetLibrary() {
		return library;
	}
	
	/** Variables **/
	private static Computer instance;
	public boolean REQUEST_SHUTDOWN = false;
	public int sleepMS = 1; //Milliseconds of sleep
	
	//Plugins
	private String osName = "";
	private Plugin os = null;
	private Map<String,API> library = new HashMap<>();
}
