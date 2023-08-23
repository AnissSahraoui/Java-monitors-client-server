package Sahraoui_client;
public class ClientMain {
    public static long time = System.currentTimeMillis();
    private static final int NUM_TROLLS = 11;

    public static void main(String[] args) {
        Thread princess = new Princess("localhost", 5000);
        Thread[] trolls = new Thread[NUM_TROLLS];

        for (int i = 0; i < NUM_TROLLS; i++) {
            trolls[i] = new Troll("localhost", 5000, i+1);
        }

        for (int i = 0; i < NUM_TROLLS; i++) {
            trolls[i].start();
        }

        princess.start();

        try {
            for (int i = 0; i < NUM_TROLLS; i++) {
                trolls[i].join();
            }
            princess.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

