package Plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import fileIO.FileUtil;

/**
 * PluginManager.java
 * 
 * @author David Tan
 **/
public class PluginAssistant {

	public static boolean IsPlugin(Class<?> clazz) {
		try {
			return (clazz.newInstance() instanceof Plugin);
		} catch (InstantiationException | IllegalAccessException | ClassCastException e) {
			return false;
		}
	}
	
	public static Plugin AsPlugin(Class<?> clazz) {
		try {
			return ((Plugin)clazz.newInstance());
		} catch (InstantiationException | IllegalAccessException | ClassCastException e) {
			return null;
		}
	}
	
	/** Scans a JAR for class files: Returns an array of class<?> **/
	public static Class<?>[] ScanJar(File jarFile) {
		//Check to make sure we are reading from a jar
		if(!FileUtil.GetExtension(jarFile).equals("jar"))
			return null;
		
		/** Variables **/
		Class<?>[] result = null;
		List<Class<?>> classes = new ArrayList<>();
		ClassLoader loader = null;
		String className = "";
		Class<?> clazz = null;
		JarEntry jarEntry = null;

		//Opening the .JAR and collecting every .class file
		try(JarInputStream file = new JarInputStream(new FileInputStream(jarFile))){

			//ClassLoader starts streaming here
			loader = new URLClassLoader(new URL[]{jarFile.toURI().toURL()});

			//Scan every file in the JAR file and see if it's a .class file
			while((jarEntry = file.getNextJarEntry()) != null) {
				clazz = null;
				if((className = jarEntry.getName()) != null && jarEntry.getName().endsWith(".class")){
					className = jarEntry.getName().substring(0, jarEntry.getName().length()-6).replaceAll("/", ".");
					try {
						clazz = loader.loadClass(className);
					} catch(NoClassDefFoundError | ClassNotFoundException e) {
					}
				}

				//Add the class file to the ArrayList
				if(clazz != null)
					classes.add(clazz);
			}
		} catch (IOException e) {
		}
		result = new Class<?>[classes.size()];
		classes.toArray(result);
		return result;
	}
	
	//File -> Class<?>
	@SuppressWarnings("resource")
	public static Class<?> LoadClassFile(File file) {
		if(file == null)
			return null;
		if(!file.exists() || file.isDirectory()) {
			return null;
		}

		ClassLoader classloader;
		try {
			classloader = new URLClassLoader(new URL[]{new URL("file:\\"+file.getAbsolutePath().replace(file.getName(), ""))});
			try {
				return classloader.loadClass(file.getName().substring(0, file.getName().length()-6));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}
