package Sahraoui_server;
public class ServerMain {
    public static void main(String[] args) {
        Server server = new Server();  
        new Thread(server).start();

        try {
            Thread.sleep(1000); // Ensure server is up and running
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

