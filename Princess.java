package Sahraoui_client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

public class Princess extends Thread {
    private Socket socket;  // Socket for communication with server
    private PrintWriter out;  // PrintWriter for sending requests to the server

    // Constructor
    public Princess(String host, int port) {
        setName("Princess");  // Set thread name as "Princess"
        try {
            // Try to establish a socket connection to the server
            this.socket = new Socket(host, port);
            // Initialize the PrintWriter with the socket's output stream
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        serveDinner();  // Princess starts serving dinner
        sendRequest("startDinner");  // Princess sends a start dinner request to the server
        watchMovie();  // Princess starts watching the movie
        sendRequest("endMovie");  // Princess sends an end movie request to the server
        goToSleep();  // Princess goes to sleep
        sendRequest("wakeUpAndGoToWork");  // Princess sends a wake up and go to work request to the server
    }

    // Method for serving dinner
    private void serveDinner() {
        msg("Start serving dinner");
        sleepRandomTime();
        msg("Finished serving dinner");
    }

    // Method that makes the thread sleep for a random time between 500 and 1500 milliseconds
    private void sleepRandomTime() {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(500, 1500));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Method for watching a movie
    private void watchMovie() {
        sendRequest("waitMovie");  // Princess sends a wait movie request to the server
        msg("Start watching movie");
        sleepRandomTime();
        msg("Finished watching movie");
    }

    // Method for the Princess to go to sleep
    private void goToSleep() {
        msg("Going to bed");
        sleepRandomTime();
    }

    // Method for sending a request to the server
    private void sendRequest(String request) {
        out.println("Princess " + request);
    }

    // Method for printing out messages with the timestamp and thread name
    public void msg(String m) {
        System.out.println("["+(System.currentTimeMillis()-ClientMain.time)+"] "+getName()+": "+m);
    }
}




