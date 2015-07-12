package mist.server.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class Server {
	
	public boolean running;
	public ArrayList<ClientHandler> connections = new ArrayList<ClientHandler>();
	public GUIServer gui;
	
	static final int SERVER_PORT = 8202;
	static Server instance;
	static ServerSocket socket;
	//Update Pleayr liost
	
	public void start(){
		getInstance();
		running = true;
		
		try {
			socket = new ServerSocket(SERVER_PORT);
			socket.setSoTimeout(1000);
			
			while(running){
				try{
					Socket clientSocket = socket.accept();
					(new Thread(new ClientHandler(clientSocket))).start();
				}catch(SocketTimeoutException e){
					System.out.println("[N] SocketTimeout");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void stop(){
		running = false;
		for(ClientHandler c : connections){
			c.closeConnection();
		}
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static Server getInstance(){
		if(instance == null)
			instance = new Server();
		return instance;
	}
}
