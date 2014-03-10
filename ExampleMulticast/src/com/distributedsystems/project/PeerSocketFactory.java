package com.distributedsystems.project;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;


public class PeerSocketFactory {

	private static PeerSocketFactory instance = null;
	
	protected PeerSocketFactory() {	}
	
	public static PeerSocketFactory getSocketFactory() {
		if (instance == null) {
			instance = new PeerSocketFactory();
		}
		
		return instance;
	}
	
	public SocketInterface makeSocket(String host, int port) throws IOException {
		SocketInterface mySocket = new PeerSocket(host, port);
		return mySocket;
	}

	public SocketInterface makeSocket(Socket socket) throws SocketException {
		SocketInterface mySocket = new PeerSocket(socket);
		return mySocket;
	}
}
