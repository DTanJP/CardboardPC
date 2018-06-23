package Plugin;

/**
 * PluginCommand.java
 * Gives some pre-defined commands for plugins to communicate together
 * 
 * Not enforceable by CardboardPC.
 * Provide only as a guideline
 * @author David Tan
 **/
public enum PluginCommand {

	REQUEST_CLOSE("requestClose"), 			 //Request that the plugin be terminated
	REQUEST_DRIVERLIST("requestDriverList"), //Request a list of drivers loaded into the OS
	REQUEST_APPLIST("requestAppList"),		 //Request a list of applications
	REQUEST_OS("requestOS"),				 //Request something from the OS
	REQUEST_DRIVER("requestDriver");		 //Request something from a driver
	
	/** Constructor **/
	PluginCommand(String cmd) {
		command = cmd;
	}
	
	/** Returns the command value **/
	public String Value() {
		return command;
	}
	
	/** Variables **/
	private String command = "";
}
