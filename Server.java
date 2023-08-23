package Sahraoui_server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    // Define a ServerSocket which will listen for incoming connections.
    private ServerSocket serverSocket;

    // The running flag is used to control the server's main listening loop. 
    private boolean running = false;

    // A Cabin instance shared by all client helpers.
    private Cabin cabin;

    public Server() {
        // We're initializing the Cabin with 11 Trolls. 
        this.cabin = new Cabin(11); // Assume 11 as the number of trolls
    }

    @Override
    public void run() {
        running = true;
        try {
            // Here we set up the ServerSocket to listen on port 5000
            serverSocket = new ServerSocket(5000);
            System.out.println("Server is running...");

            // As long as the server is running, we're accepting incoming client connections
            // For each client connection, we're starting a new ClientHelper thread
            while (running) {
                Socket clientSocket = serverSocket.accept();
                new ClientHelper(clientSocket, cabin).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // When the server stops running, we should close the server socket
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopServer() {
        running = false;
        try {
            // Close the server socket when stopping the server
            if (serverSocket != null) {
                serverSocket.close();
                System.out.println("Server is stopped...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



