package mist.client_old.core;

import java.net.URISyntaxException;



public class StartClient {
	
	public static String pathToJar;
	public static Window guiWindow;
	
	
	public static void main(String[] args) {
		
		try {
			pathToJar = StartClient.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return;
		}
		
		guiWindow = new Window();
		
		
		
	}

}
