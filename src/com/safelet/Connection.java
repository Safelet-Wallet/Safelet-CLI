package com.safelet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Connection {
	private static final Integer PORT = 8443;
	private static final String HOST_DIRECTION = "localhost";

	private static String encrypt(String password) {
		Base64.Encoder encoder = Base64.getEncoder();
		return new String(encoder.encode(password.getBytes()));
	}

	public static String loginUser(String username, String password) {
		return enviarHttpPostUser("/login", username, password);
	}

	public static void registrarUser(String username, String password) {
		enviarHttpPostUser("/register", username, password);
	}

	public static String makeTransaction(String token, String adress, String cantidad){
		return crearTransaction(token, adress, cantidad);
	}

	// El login y registro funcionan de manera idéntica en cuanto a petición HTTP, excepto el path.
	// Por eso aprovechamos el mismo método para ambos.
	private static String enviarHttpPostUser(String path, String username, String password){
		try(Socket socket = new Socket(HOST_DIRECTION, PORT)){
			// Encriptamos la contraseña antes de enviarla
			password = encrypt(password);

			String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") +
					"&" +URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
			writer.write("POST " + path + " HTTP/1.0\n");
			writer.write("Host: " + HOST_DIRECTION + "\n");
			writer.write("Content-Length: " + data.length() + "\n");
			writer.write("Content-Type: application/x-www-form-urlencoded\n");
			writer.write("\r\n");
			writer.write(data);
			writer.flush();

			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String respuesta = "";
			String linea;

			while ((linea = reader.readLine()) != null){
				respuesta = linea;
			}

			return respuesta;
		} catch (IOException e){
			System.out.println("Error:" + e);
		}

		return null;
	}


	//** NUEVO MD5 DIGEST NECESITA HASH Y NONCE **
	public static String loginUserAuthCoded(String hash, String nonce) {
		return enviarHttpPostUserDigest("/login/auth", hash, nonce);
	}

	//** NUEVO MD5 DIGEST DEVUELVE NONCE **
	public static String loginUserAuth(String username, String password) {
		return enviarHttpPostUser("/login/auth/request", username, password);
	}

	//** NUEVO MD5 DIGEST DEVUELVE EL AUTHTOKEN**
	private static String enviarHttpPostUserDigest(String path, String hash, String nonce){
		try(Socket socket = new Socket(HOST_DIRECTION, PORT)){
			// Encriptamos la contraseña antes de enviarla

			String data = URLEncoder.encode("hash", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(hash, StandardCharsets.UTF_16) +
					"&" +URLEncoder.encode("nonce", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(nonce, StandardCharsets.UTF_8);

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
			writer.write("POST " + path + " HTTP/1.0\n");
			writer.write("Host: " + HOST_DIRECTION + "\n");
			writer.write("Content-Length: " + data.length() + "\n");
			writer.write("Content-Type: application/x-www-form-urlencoded\n");
			writer.write("\r\n");
			writer.write(data);
			writer.flush();

			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String respuesta = "";
			String linea;

			while ((linea = reader.readLine()) != null){
				respuesta = linea;
			}

			return respuesta;
		} catch (IOException e){
			System.out.println("Error:" + e);
		}

		return null;
	}

	public static String crearAddress(String token) {
		try(Socket socket = new Socket(HOST_DIRECTION, PORT)){
			String data = URLEncoder.encode("token", "UTF-8") + "=" + URLEncoder.encode(token, "UTF-8");

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
			writer.write("POST /address/new HTTP/1.0\n");
			writer.write("Host: " + HOST_DIRECTION + "\n");
			writer.write("Content-Length: " + data.length() + "\n");
			writer.write("Content-Type: application/x-www-form-urlencoded\n");
			writer.write("\r\n");
			writer.write(data);
			writer.flush();

			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String respuesta = "";
			String linea;

			while ((linea = reader.readLine()) != null){
				respuesta = linea;
			}

			return respuesta;
		} catch (IOException e){
			System.out.println("Error:" + e);
		}

		return null;
	}

	public static String obtenerBalance(String address){
		try(Socket socket = new Socket(HOST_DIRECTION, PORT)){
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
			writer.write("GET /" + address + " HTTP/1.0\n");
			writer.write("Host: " + HOST_DIRECTION + "\n");
			writer.write("\r\n");
			writer.flush();

			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String respuesta = "";
			String linea;

			while ((linea = reader.readLine()) != null){
				respuesta = linea;
			}

			return respuesta;

		} catch (IOException e){
			System.out.println("Error:" + e);
		}

		return null;
	}

	private static String crearTransaction(String token, String address, String cantidad) {
		try(Socket socket = new Socket(HOST_DIRECTION, PORT)){
			String data = URLEncoder.encode("recipient", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8") +
					"&" +URLEncoder.encode("amount", "UTF-8") + "=" + URLEncoder.encode(cantidad, "UTF-8") +
					"&" +URLEncoder.encode("token", "UTF-8") + "=" + URLEncoder.encode(token, "UTF-8");

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
			writer.write("POST /eth/send HTTP/1.0\n");
			writer.write("Host: " + HOST_DIRECTION + "\n");
			writer.write("Content-Length: " + data.length() + "\n");
			writer.write("Content-Type: application/x-www-form-urlencoded\n");
			writer.write("\r\n");
			writer.write(data);
			writer.flush();

			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String respuesta = "";
			String linea;

			while ((linea = reader.readLine()) != null){
				respuesta = linea;
			}

			return respuesta;
		} catch (IOException e){
			System.out.println("Error:" + e);
		}

		return null;
	}

	public static String getAddress(String token) {
		try(Socket socket = new Socket(HOST_DIRECTION, PORT)){
			String data = URLEncoder.encode("token", "UTF-8") + "=" + URLEncoder.encode(token, "UTF-8");

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
			writer.write("POST /address HTTP/1.0\n");
			writer.write("Host: " + HOST_DIRECTION + "\n");
			writer.write("Content-Length: " + data.length() + "\n");
			writer.write("Content-Type: application/x-www-form-urlencoded\n");
			writer.write("\r\n");
			writer.write(data);
			writer.flush();

			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String respuesta = "";
			String linea;

			while ((linea = reader.readLine()) != null){
				respuesta = linea;
			}

			return respuesta;
		} catch (IOException e){
			System.out.println("Error:" + e);
		}

		return null;
	}
}
