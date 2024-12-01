package sockets.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class LoginService extends Thread {
    private DataInputStream in;
    private DataOutputStream out;
    private Socket tcpSocket;

    // Simulación de una base de datos de usuarios
   // private static final HashMap<String, String> users = new HashMap<>();
    private static final ArrayList<String> emails = new ArrayList<>();

    static {
        //users.put("admin", "1234");
        //users.put("user", "password");
        emails.add("santamariacalvete@gmail.com");
    }

    public LoginService(Socket socket) {
        try {
            this.tcpSocket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.start();
        } catch (IOException e) {
            System.err.println("# LoginService: Error initializing service: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            String data = this.in.readUTF();
            System.out.println(" - LoginService: Received data -> " + data);

            String response = validateLogin(data);

            this.out.writeUTF(response);
            System.out.println(" - LoginService: Sent response -> " + response);
        } catch (IOException e) {
            System.err.println("# LoginService: IO error: " + e.getMessage());
        } finally {
            try {
                tcpSocket.close();
            } catch (IOException e) {
                System.err.println("# LoginService: Error closing socket: " + e.getMessage());
            }
        }
    }

    private String validateLogin(String msg) {
        StringTokenizer tokenizer = new StringTokenizer(msg, "#");
        //String username = tokenizer.nextToken();
        //String password = tokenizer.nextToken();
        String correo = tokenizer.nextToken();

        //if (users.containsKey(username) && users.get(username).equals(password)) {
        //    return "OK#Login Exitoso";
        //} else {
        //    return "ERR#Credenciales Inválidas";
        //}
        if (emails.contains(correo)) {
            return "OK#Login Exitoso";
        } else {
            return "ERR#Credenciales Inválidas";
        }
    }
}
