package com.safelet;

import com.safelet.model.Transaction;
import com.safelet.model.User;
import com.safelet.model.Wallet;

import java.util.Scanner;

/**
 * CLase que ejecuta la apicacion cliente con los menus
 * @author Jose Juan Pastor
 */
public class Main implements Runnable{

    private static final Scanner sc = new Scanner(System.in);

    private User user;

    private String opcion = "1";

    public Main() {
    }

    /**
     * Muestra el menú de inicio de sesión e inicia la sesión del usuario
     */
    public void start(){
        System.out.println("Bienvenido a Safelet Wallet");
        System.out.println("Inicia sesion:");
        String nombre = "";
        String contrasenya;
        while(user==null){
            System.out.println("Nombre: ");
            nombre = sc.nextLine();
            System.out.println("Contraseña: ");
            contrasenya = sc.nextLine();
            String token = Connection.loginUser(nombre, contrasenya);
            if(user==null) {
                System.out.println("Usuario incorrecto, intentalo otra vez");
            }
        }
        System.out.println("Bienevenido "+nombre);
        while(!opcion.equals("0")){
            printMenu(user);
            if(!opcion.matches("^[0-3]$")){
                System.out.println("Opcion no valida");
            }
        }
        System.out.println("Cerrando sesion...");
    }

    /**
     * Muestra el menú principal de la aplicación y permite al usuario elegir que quiere hacer
     * @return devuelve la opción seleccionada
     */
    public String printMenu(User user){
        System.out.println("1- Ver balance");
        System.out.println("2- Ver historial");
        System.out.println("3- Transefrir dinero");
        System.out.println("0- Salir");
        opcion = sc.nextLine();
        switch (opcion){
            case "1":
                checkBalance(user);
                break;
            case "2":
                checkTransactionRecord(user);
                break;
            case "3":
                transferMoney(user);
                break;
        }
        return opcion;
    }

    /**
     * Muestra el balance actual
     */
    public void checkBalance(User user){
        System.out.println("Balance actual");
        for (Wallet wallet:user.getWallets()) {
            System.out.println("Balance de la cartera de "+wallet.getCoin().getName()+": "+wallet.getBalance());
        }
    }

    /**
     * Muestra el historial de transacciones
     */
    public void checkTransactionRecord(User user){
        System.out.println("Historial de transacciones");
        System.out.println("Enviadas");
        for (Transaction transaction:user.getSent()) {
            System.out.println(transaction.toString());
        }
        System.out.println("Recibidas");
        for (Transaction transaction:user.getReceived()) {
            System.out.println(transaction.toString());
        }
    }

    /**
     * Muestra lo relacionado a una transacción a realizar
     */
    public void transferMoney(User user){
        System.out.println("Transferir");

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
