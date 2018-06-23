package Plugin;

/**
 * Plugin.java
 * 
 * @author David Tan
 **/
public interface Plugin {

	public void Initialize();
	public void OnEnable();
	public void Update();
	public void OnDisable();
	public void Destruct();
	public boolean RequestShutDown();
	
	/** Sends a message to another plugin **/
	//Plugin sender: The plugin that sent the request
	//String plugin: The plugin to receive the request
	//String key: The command
	//Object... value: The parameters
	public void Request(Plugin sender, String plugin, String key, Object... value);
}
