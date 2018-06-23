package Plugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Application.java
 * 
 * @author David Tan
 **/
public class Application {

	/** Constructor **/
	public Application() {
	}
	
	/** Constructor **/
	public Application(String name) {
		this.name = name;
	}
	
	public void SetApplicationClass(Class<?> c) {
		this.clazz = c;
	}
	
	public void InitiateApplicationClass() {
		if(clazz == null)
			return;
		application = PluginAssistant.AsPlugin(clazz);
	}
	
	public void SetApplication(Plugin app) {
		this.application = app;
	}
	
	public Plugin GetApplication() {
		return this.application;
	}
	
	/** Add a class to this Driver **/
	public void Add(Class<?> c) {
		if(c == null)
			return;
		
		if(!clazzes.containsKey(c.getName()))
			clazzes.put(c.getName(), c);
	}
	
	/** Removes a class from this Driver **/
	public void Remove(Class<?> c) {
		if(c == null)
			return;
		if(clazzes.containsKey(c.getName()))
			clazzes.remove(c.getName());
	}
	
	/** Gets a class from this Application **/
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
	
	/** Returns the name of this Application **/
	public String Name() {
		return this.name;
	}
	
	/** Variables **/
	private String name = "";
	private Map<String,Class<?>> clazzes = new HashMap<>();
	private Class<?> clazz = null;
	private Plugin application = null;
}
