package com.hln.test1;

import java.net.*;
import java.io.*;

public class ChatClient {
	private Socket socket = null;
	//输入流，标准输入
	private DataInputStream console = null;
	//套接字的标准输出
	private DataOutputStream streamOut = null;

	public ChatClient(String serverName, int serverPort) {
		System.out.println("Establishing connection. Please wait ...");
		try {
			socket = new Socket(serverName, serverPort);
			System.out.println("Connected: " + socket);
			start();
		} catch (UnknownHostException uhe) {
			System.out.println("Host unknown: " + uhe.getMessage());
		} catch (IOException ioe) {
			System.out.println("Unexpected exception: " + ioe.getMessage());
		}
		String line = "";
		while (!line.equals(".bye")) {
			try {
				//读取标准输入
				line = console.readLine();
				//写入套接字输出流
				streamOut.writeUTF(line);
				streamOut.flush();
			} catch (IOException ioe) {
				System.out.println("Sending error: " + ioe.getMessage());
			}
		}
	}
	//初始化标准输入流和套接字标准输出流
	public void start() throws IOException {
		console = new DataInputStream(System.in);
		streamOut = new DataOutputStream(socket.getOutputStream());
	}

	public void stop() {
		try {
			if (console != null)
				console.close();
			if (streamOut != null)
				streamOut.close();
			if (socket != null)
				socket.close();
		} catch (IOException ioe) {
			System.out.println("Error closing ...");
		}
	}

	public static void main(String args[]) {
		ChatClient client = null;
		if (args.length != 2)
			System.out.println("Usage: java ChatClient host port");
		else
			client = new ChatClient(args[0], Integer.parseInt(args[1]));
	}
}