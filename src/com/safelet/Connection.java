package com.safelet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.*;
import java.net.URLEncoder;

public class Connection {

	private static final Integer PORT = 8443;
	private static final String HOST_DIRECTION = "http://localhost";

	public static String loginUser(String username, String password){

		try(Socket socket = new Socket(HOST_DIRECTION, PORT)){
			String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" +URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
			String path = "/login";
			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
			wr.write("POST " + path + " HTTP/1.0\n");
			wr.write("Content-Length: " + data.length() + "\n");
			wr.write("Content-Type: application/x-www-form-urlencoded\n");
			wr.write("\r\n");
			wr.write(data);
			wr.flush();

			BufferedReader buffReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String total="";
			String line;

			while ((line = buffReader.readLine()) != null){
				total+=line;
			}

			return total;

		} catch (IOException e){

			System.out.println("Error:" + e);
		}

		return null;
	}
}
