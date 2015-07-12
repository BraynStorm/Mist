package mist.server.core;

import java.awt.EventQueue;

public class StartServer {

	public static void main(String[] args) {
		
		Server.getInstance().start();
		
		//Show GUI
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server.getInstance().gui = new GUIServer();
					Server.getInstance().gui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		
	}

}
