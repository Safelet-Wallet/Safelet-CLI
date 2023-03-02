package com.safelet;

import java.util.Scanner;

/**
 * CLase que ejecuta la apicacion cliente con los menus
 * @author Jose Juan Pastor
 */
public class Main implements Runnable{
    private static final Scanner sc = new Scanner(System.in);

    private String token = "";
    private String address = "";

    private String opcion = "1";

    public Main() {
    }

    /**
     * Muestra el menú de inicio de sesión e inicia la sesión del usuario
     */
    public void start(){
        System.out.println("Bienvenido a Safelet Wallet");
        while(!opcion.equals("0") && token.equals("")){
            System.out.println("1- Crear cuenta:");
            System.out.println("2- Iniciar sesion:");
            System.out.println("0- Salir:");
            opcion = sc.nextLine();
            switch (opcion){
                case "1":
                    registrarse();
                    break;
                case "2":
                    iniciarSesion();
                    break;
            }
            if(!opcion.matches("^[0-2]$")){
                System.out.println("Opcion no valida");
            }
            if(!opcion.equals("0")){
                if (token.equals("")) {
                    System.out.println("Usuario incorrecto, intentalo otra vez");
                }
            }
        }
        if(!token.equals("")) {
            while (!opcion.equals("0")) {
                printMenu();
                if (!opcion.matches("^[0-3]$")) {
                    System.out.println("Opción no valida");
                }
            }
        }
        System.out.println("\nCerrando aplicación...");
    }

    private void registrarse() {
        System.out.println("\nNombre: ");
        String nombre = sc.nextLine();
        System.out.println("Contraseña: ");
        String contrasenya = sc.nextLine();
        System.out.println("\nRegistrando usuario...");
        Connection.registrarUser(nombre,contrasenya);
        System.out.println("OK");
        loginYAddress(nombre, contrasenya);
    }

    public void iniciarSesion(){
        String nombre = "";
        String contrasenya;
        System.out.println("\nNombre: ");
        nombre = sc.nextLine();
        System.out.println("Contraseña: ");
        contrasenya = sc.nextLine();
        loginYAddress(nombre, contrasenya);
    }

    /**
     * Loguea al usuario y le crea una address.
     * Este fragmento es ejecutado exactamente igual en registro e inicio de sesión.
     */
    private void loginYAddress(String nombre, String contrasenya) {
        System.out.println("\nIniciando sesión...");
        token = Connection.loginUser(nombre, contrasenya);
        System.out.println((!token.equals("") ? "OK" : "ERROR"));
//        System.out.println("token = " + token);
        System.out.println("\nCreando address...");
        address = Connection.crearAddress(token);
        System.out.println((!address.equals("Invalid AuthToken") ? "OK" : "ERROR") + "\n");
//        System.out.println("address = " + address);
    }

    /**
     * Muestra el menú principal de la aplicación y permite al usuario elegir que quiere hacer
     */
    public void printMenu(){
        System.out.println("1- Ver balance");
        System.out.println("2- Ver historial");
        System.out.println("3- Transefrir dinero");
        System.out.println("0- Salir");
        opcion = sc.nextLine();
        switch (opcion){
            case "1":
                checkBalance();
                break;
            case "2":
                checkTransactionRecord();
                break;
            case "3":
                transferMoney();
                break;
        }
    }

    /**
     * Muestra el balance actual
     */
    public void checkBalance(){
        String balance = Connection.obtenerBalance(address);
        System.out.println("\nBalance actual: " + balance + "\n");
    }

    /**
     * Muestra el historial de transacciones
     */
    public void checkTransactionRecord(){
        System.out.println("\nHistorial de transacciones");
        System.out.println("Enviadas");
        // TODO: mostrar transsacciones enviadas

        System.out.println("\nRecibidas");
        // TODO: mostrar transsacciones recibidas
        System.out.println();
    }

    /**
     * Muestra lo relacionado a una transacción a realizar
     */
    public void transferMoney(){
        System.out.println("\nTransferir");
        // TODO: Transferir cripto
        System.out.println();
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new Main());
        thread.start();
    }

    @Override
    public void run() {
        this.start();
    }
}
