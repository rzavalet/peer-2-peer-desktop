package com.distributedsystems.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class PeerSocket implements SocketInterface{

	private Socket socket;
	private boolean debug = false;
	
	public PeerSocket(Socket socket) throws SocketException{
		this.socket = socket;
		socket.setTcpNoDelay(true);
		socket.setPerformancePreferences(1, 0, 0);
		socket.setReceiveBufferSize(256);
		socket.setSendBufferSize(256);
	}
	
	public PeerSocket(String host, int port) throws IOException{
		InetAddress address;
		address = InetAddress.getByName(host);

		socket = new Socket(address, port);
		socket.setTcpNoDelay(true);
		socket.setPerformancePreferences(1, 0, 0);
		socket.setReceiveBufferSize(256);
		socket.setSendBufferSize(256);
	}

	/*
	@Override
	public int read() {
		InputStream in = null;
		int size = -1;
		
		try {
			in = socket.getInputStream();
			size = in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return size;
	}*/

	@Override
	public int read(byte[] data) throws IOException{
		InputStream in = null;
		int size = -1;
		
		in = socket.getInputStream();
		size = in.read(data);
		Debug.print("...Received bytes: " + new String(data), debug);

        return size;
	}
	
	public String read() throws IOException{
	
		String data = null;		
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		data = inFromServer.readLine();
		Debug.print("...Received bytes: " + data, debug);
		return data;
	}
	
	@Override
	public void write(byte[] data) throws IOException{		
		OutputStream out = null;
		
		out = socket.getOutputStream();
		//Debug.print("...Sending bytes: " + new String(data), debug);
		out.write(data);
		out.flush();
		//out.close();
	}

	public void write(String data) throws IOException {
		PrintWriter outToServer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		Debug.print("...Sending bytes: " + new String(data), debug);
		outToServer.print(data + "\n");
		outToServer.flush();
		//out.close();
	}
	
	@Override
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
