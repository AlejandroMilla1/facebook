package sockets.server;


import java.io.IOException;
import java.net.ServerSocket;

public class LoginServer {
    private static int numClients = 0;

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println(" # Usage: LoginServer [5000]");
            System.exit(1);
        }

        int serverPort = Integer.parseInt(args[0]);

        try (ServerSocket tcpServerSocket = new ServerSocket(serverPort)) {
            System.out.println(" - LoginServer: Waiting for connections on port " + serverPort);

            while (true) {
                new LoginService(tcpServerSocket.accept());
                System.out.println(" - LoginServer: New client connected. Client number: " + ++numClients);
            }
        } catch (IOException e) {
            System.err.println("# LoginServer: IO error: " + e.getMessage());
        }
    }
}
