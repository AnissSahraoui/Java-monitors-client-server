package Sahraoui_server;
import java.util.concurrent.atomic.AtomicInteger;

public class Cabin {
    private final int numTrolls;
    private final AtomicInteger arrivedTrolls = new AtomicInteger(0);
    private final AtomicInteger eatingTrolls = new AtomicInteger(0);
    private final AtomicInteger watchingTrolls = new AtomicInteger(0);
    private final AtomicInteger sleepingTrolls = new AtomicInteger(0);
    private final AtomicInteger waitingTrolls = new AtomicInteger(0);
    private final AtomicInteger finishedWatchingTrolls = new AtomicInteger(0);

    // Cabin constructor
    public Cabin(int numTrolls) {
        this.numTrolls = numTrolls;
    }

    // Method for troll arrival
    public synchronized void arriveHome(int id) {
        System.out.println("Troll-" + id + ": Arrived at home");
        arrivedTrolls.incrementAndGet();
        // Wait until all trolls have arrived
        while (arrivedTrolls.get() != numTrolls) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        notifyAll();
    }

    // Method for trolls entering the cabin
    public synchronized void enterCabin(int id) {
        System.out.println("Troll-" + id + ": Entering the cabin");
    }

    // Increment the number of trolls eating
    public void incrementEating() {
        eatingTrolls.incrementAndGet();
    }

    // Decrement the number of trolls eating
    public void decrementEating() {
        eatingTrolls.decrementAndGet();
    }

    // Get the current number of trolls eating
    public int getEating() {
        return eatingTrolls.get();
    }

    // Start dinner
    public synchronized void startDinner() {
        System.out.println("Princess: Starting dinner");
    }

    // Method for waiting for the movie to start
    public synchronized void waitMovie() {
        while (watchingTrolls.get() < 3) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Method for watching movie
    public synchronized boolean watchMovie(int id) {
        waitingTrolls.incrementAndGet();
        while(watchingTrolls.get() >= 3 || (id - 1) % 3 != finishedWatchingTrolls.get() % 3) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        watchingTrolls.incrementAndGet();
        System.out.println("Troll-" + id + ": Watching movie");

        return true;
    }

    // Method for finishing movie watching
    public synchronized void finishedWatching(int id) {
        watchingTrolls.decrementAndGet();
        finishedWatchingTrolls.incrementAndGet();
        if(watchingTrolls.get() == 0) {
            notifyAll();
        }
        System.out.println("Troll-" + id + ": Finished watching movie");
    }

    // End the movie after all trolls have finished watching
    public synchronized void endMovie() {
        while (finishedWatchingTrolls.get() != numTrolls) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Princess: Movie ended");
    }

    // Wait until all trolls have gone to sleep
    public synchronized void goToSleep() {
        while (sleepingTrolls.get() != numTrolls) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        notifyAll();
    }

    // Wake up all trolls and send them to work
    public synchronized void wakeUpAndGoToWork() {
        System.out.println("Princess: Wake up and go to work");
    }
}
