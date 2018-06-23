package Plugin;

import java.util.HashMap;
import java.util.Map;

/**
 * API.java
 * 
 * @author David Tan
 **/
public class API {

	/** Constructor **/
	public API() {
	}
	
	/** Constructor **/
	public API(String name) {
		this.name = name;
	}
	
	/** Add a class to this API **/
	public void Add(Class<?> c) {
		if(c == null)
			return;
		
		if(clazz == null) {
			clazz = c;
			clazzes.put(c.getName(), c);
		} else
			clazzes.put(c.getName(), c);
	}
	
	/** Removes a class from this API **/
	public void Remove(Class<?> c) {
		if(c == null)
			return;
		
		if(clazz == c) {
			clazz = null;
			clazzes.remove(c.getName());
		} else
			clazzes.remove(c.getName());
	}
	
	/** Gets a class from this API **/
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
	
	/** Returns the name of this API **/
	public String Name() {
		return this.name;
	}
	
	/** Variables **/
	private String name = "";
	private Map<String,Class<?>> clazzes = new HashMap<>();
	private Class<?> clazz = null;
}
