package com.safelet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.*;
import java.net.URLEncoder;
import java.util.Base64;

public class Connection {
	private static final Integer PORT = 8443;
	private static final String HOST_DIRECTION = "localhost";

	private static String encrypt(String password) {
		Base64.Encoder encoder = Base64.getEncoder();
		return new String(encoder.encode(password.getBytes()));
	}

	public static String loginUser(String username, String password) {
		return enviarHttpPost("/login", username, password);
	}

	public static void registrarUser(String username, String password) {
		enviarHttpPost("/register", username, password);
	}

	private static String enviarHttpPost(String path, String username, String password){

		try(Socket socket = new Socket(HOST_DIRECTION, PORT)){
			// Encriptamos la contraseña antes de enviarla
			password = encrypt(password);
			String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" +URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
			wr.write("POST " + path + " HTTP/1.0\n");
			wr.write("Host: " + HOST_DIRECTION+ "\n");
			wr.write("Content-Length: " + data.length() + "\n");
			wr.write("Content-Type: application/x-www-form-urlencoded\n");
			wr.write("\r\n");
			wr.write(data);
			wr.flush();

			BufferedReader buffReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String respuesta="";
			String line;

			while ((line = buffReader.readLine()) != null){
				respuesta=line;
			}

			return respuesta;

		} catch (IOException e){

			System.out.println("Error:" + e);
		}

		return null;
	}
}
