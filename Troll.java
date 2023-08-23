package Sahraoui_client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Troll extends Thread {
    private String host;
    private int port;
    private int id;

    // Constructor
    public Troll(String host, int port, int id) {
        this.host = host;
        this.port = port;
        this.id = id;
    }

    @Override
    public void run() {
        // Try with resources to automatically close these resources after use
        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            // Send request for troll to arrive home
            sendRequest(out, "arriveHome " + id);
            // Get response from server and print it
            String response = in.readLine();
            System.out.println("Troll " + id + " arrived home, response: " + response);

            // Send request for troll to enter cabin
            sendRequest(out, "enterCabin " + id);
            response = in.readLine();
            System.out.println("Troll " + id + " entered cabin, response: " + response);

            // Send request for troll to watch movie
            sendRequest(out, "watchMovie " + id);
            response = in.readLine();
            System.out.println("Troll " + id + " watched movie, response: " + response);

            // Send request to indicate troll has finished watching movie
            sendRequest(out, "finishedWatching " + id);
            response = in.readLine();
            System.out.println("Troll " + id + " finished watching movie, response: " + response);

        } catch (IOException e) { // Handle any IO exceptions
            e.printStackTrace();
        }
    }

    // Function to send a request to the server
    private void sendRequest(PrintWriter out, String request) {
        // Use PrintWriter to send the request to server
        out.println("Troll " + request);
    }
}



