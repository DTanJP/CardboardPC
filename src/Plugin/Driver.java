package Plugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Driver.java
 * 
 * @author David Tan
 **/
public class Driver {

	/** Constructor **/
	public Driver() {
	}
	
	/** Constructor **/
	public Driver(String name) {
		this.name = name;
	}
	
	public void SetDriverClass(Class<?> c) {
		this.clazz = c;
	}
	
	public void InitiateDriverClass() {
		if(clazz == null)
			return;
		driver = PluginAssistant.AsPlugin(clazz);
	}
	
	public void SetDriver(Plugin driver) {
		this.driver = driver;
	}
	
	public Plugin GetDriver() {
		return this.driver;
	}
	
	/** Add a class to this Driver **/
	public void Add(Class<?> c) {
		if(c == null)
			return;
		
		clazzes.put(c.getName(), c);
	}
	
	/** Removes a class from this Driver **/
	public void Remove(Class<?> c) {
		if(c == null)
			return;
		
		clazzes.remove(c.getName());
	}
	
	/** Gets a class from this Driver **/
	public Class<?> Get(String name) {
		Class<?> result = null;
		//Must be valid name
		if(name == null || name.length() == 0)
			return  result;
		
		//Search for the name
		name = name.toLowerCase();
		for(String c : clazzes.keySet()) {
			if(c != null) {
				if(c.toLowerCase().equals(name))
					return clazzes.get(c);
			}
		}
		return result;
	}
	
	/** Returns the name of this Driver **/
	public String Name() {
		return this.name;
	}
	
	/** Variables **/
	private String name = "";
	private Map<String,Class<?>> clazzes = new HashMap<>();
	private Class<?> clazz = null; //The main plugin class
	private Plugin driver = null;
}
