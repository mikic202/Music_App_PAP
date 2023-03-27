package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		ServerSocket server = new ServerSocket(8000);
		try {
			System.out.println("Server has started on 127.0.0.1:8000.\r\nWaiting for a connection…");
			boolean quit = false;
			while(!quit) {
				Socket client = server.accept();
				System.out.println("A client connected.");
				InputStream in = client.getInputStream();
				OutputStream out = client.getOutputStream();
				Scanner s = new Scanner(in, "UTF-8");
				String data;
				while(s.useDelimiter("\\n").hasNext() && !quit) {
					data = s.useDelimiter("\\n").next();
					if(data.length() != 0 && data.charAt(0) == 'q') {
						quit = true;
					}
					System.out.println(data);
					data += "\n";
					out.write("message: ".getBytes());
					out.write(data.getBytes());
				}
			}
			
		}
		finally {
			System.out.println("Server stopped.");
			server.close();
		}
	}
}