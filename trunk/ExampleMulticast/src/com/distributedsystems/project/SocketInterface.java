package com.distributedsystems.project;

import java.io.IOException;

public interface SocketInterface {

	//public int read();
	public String read() throws IOException;
	public int read(byte[] data) throws IOException;
	public void write(String data) throws IOException;
	public void write(byte[] data) throws IOException;
	public void close();
	
}