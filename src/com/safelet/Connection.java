package com.safelet;

import com.google.gson.GsonBuilder;
import com.safelet.model.User;

import java.io.*;
import java.net.Socket;

public class Connection {

	private final Integer PORT = 8443;
	private final String HOST_DIRECTION = "localhost";

	public User loginUser(String username, String password){

		try(Socket s = new Socket(HOST_DIRECTION, PORT)){

			OutputStreamWriter osw = new OutputStreamWriter(s.getOutputStream());
			osw.write("GET / HTTP/1.1\nHost: " + HOST_DIRECTION + "\n");
			osw.flush();

			BufferedReader buffReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
			String total="";
			String line;

			while ((line = buffReader.readLine()) != null){
				total+=line;
			}

			return new GsonBuilder().create().fromJson(total,User.class);

		} catch (IOException e){

			System.out.println("Error error");
		}

		return null;
	}
}
