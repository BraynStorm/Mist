package mist.client.engine;

import java.util.HashMap;

public class Config {
	
	private static HashMap<String, Object> defaultProps;
	private HashMap<String, Object> properties;
	
	public Config(String filename){
		if(defaultProps == null){
			defaultProps = new HashMap<String, Object>();
			setDefaultProps();
		}
		properties = new HashMap<String, Object>();
	}
	
	private static void setDefaultProps(){
		defaultProps.put("fov_LOGIN", 80.0f);
		defaultProps.put("fov_CHARSELECT", 80.0f);
		defaultProps.put("fov_INWORLD", 90.0f);
		defaultProps.put("fov_LOADING", 80.0f);
		
		defaultProps.put("zNear_LOGIN", 0.1f);
		defaultProps.put("zNear_CHARSELECT", 0.1f);
		defaultProps.put("zNear_INWORLD", 0.1f);
		defaultProps.put("zNear_LOADING", 0.1f);
		
		defaultProps.put("zFar_LOGIN", 1000f);
		defaultProps.put("zFar_CHARSELECT", 1000f);
		defaultProps.put("zFar_INWORLD", 1000f);
		defaultProps.put("zFar_LOADING", 1000f);
		
		
		defaultProps.put("mouse_speed", 5f);
	}
	
	public Object get(String name){
		return properties.getOrDefault(name, defaultProps.get(name));
	}
}
