package mist.server.core;

import java.net.Socket;

public class ClientHandler implements Runnable {
	
	Account account;
	Socket clientSocket;
	boolean connected;
	
	public static String decryptPacket(String packet){
		return packet;
	}
	
	
	public ClientHandler(Socket clientSocket){
		this.clientSocket = clientSocket;
		connected = true;
	}
	
	public void closeConnection(){
		connected = false;
	}
	
	
	
	@Override
	public void run() {
		while(connected){
			
		}
	}
}
