package Sahraoui_server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHelper extends Thread {
    private Socket clientSocket; // To handle the client's socket
    private PrintWriter out; // To send data to the client
    private BufferedReader in; // To read data from the client
    private Cabin cabin; // Represents the shared cabin resource

    // Constructor
    public ClientHelper(Socket socket, Cabin cabin) {
        this.clientSocket = socket;
        this.cabin = cabin;
        System.out.println("New client connection established: " + clientSocket);
    }

    @Override
    public void run() {
        try {
            // Initialize the PrintWriter and BufferedReader with the client's socket streams
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;
            // Keep reading from the client until the client closes the connection
            while ((inputLine = in.readLine()) != null) {
                // Split the input into parts
                String[] parts = inputLine.split(" ");
                // If there are at least 2 parts (should be type and method)
                if (parts.length >= 2) {
                    // Determine the type and method
                    String type = parts[0];
                    String method = parts[1];
                    // Handle requests based on type
                    if ("Princess".equalsIgnoreCase(type)) {
                        handlePrincessRequest(parts, method);
                    } else if ("Troll".equalsIgnoreCase(type)) {
                        handleTrollRequest(parts, method);
                    }
                }
            }

            // Close the streams and the socket once done
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            // Print the stack trace for debugging purposes in case of an exception
            e.printStackTrace();
        }
    }

    // Method to handle requests from Princess
    private void handlePrincessRequest(String[] parts, String method) {
        switch (method) {
            case "startDinner":
                // Call the startDinner method on the cabin object and send a response
                cabin.startDinner();
                out.println("Dinner started");
                break;
            case "waitMovie":
                // Call the waitMovie method on the cabin object and send a response
                cabin.waitMovie();
                out.println("Waiting for movie");
                break;
            case "endMovie":
                // Call the endMovie method on the cabin object and send a response
                cabin.endMovie();
                out.println("Movie ended");
                break;
            case "wakeUpAndGoToWork":
                // Call the wakeUpAndGoToWork method on the cabin object and send a response
                cabin.wakeUpAndGoToWork();
                out.println("Woke up and going to work");
                break;
            default:
                // Send a message if the method is not valid
                out.println("Invalid method");
                break;
        }
    }

    // Method to handle requests from Troll
    private void handleTrollRequest(String[] parts, String method) {
        // Parse the Troll's id from the input
        int trollId = Integer.parseInt(parts[2]);
        switch (method) {
            case "arriveHome":
                // Call the arriveHome method on the cabin object and send a response
                cabin.arriveHome(trollId);
                out.println("Arrived at home");
                break;
            case "enterCabin":
                // Call the enterCabin method on the cabin object and send a response
                cabin.enterCabin(trollId);
                out.println("Entered the cabin");
                break;
            case "watchMovie":
                // Call the watchMovie method on the cabin object, get the response, and send it to the client
                boolean canWatch = cabin.watchMovie(trollId);
                out.println(canWatch ? "Watching movie" : "Cannot watch movie");
                break;
            case "finishedWatching":
                // Call the finishedWatching method on the cabin object and send a response
                cabin.finishedWatching(trollId);
                out.println("Finished watching movie");
                break;
            default:
                // Send a message if the method is not valid
                out.println("Invalid method");
                break;
        }
    }
}

