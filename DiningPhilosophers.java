import java.util.concurrent.locks.*;
public class DiningPhilosophers {

    // Number of philosophers
    static final int N = 5;

    // Forks represented as locks
    private static final Lock[] forks = new Lock[N];

    // Philosophers' thread function
    static class Philosopher extends Thread {
        private int id;

        public Philosopher(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    think();
                    pickUpForks();
                    eat();
                    putDownForks();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Philosopher thinking
        private void think() throws InterruptedException {
            System.out.println("Philosopher " + id + " is thinking.");
            Thread.sleep((long) (Math.random() * 1000)); // Simulate thinking time
        }

        // Philosopher picking up forks
        private void pickUpForks() {
            // Pick up forks in a consistent order to avoid deadlock
            int leftFork = id;
            int rightFork = (id + 1) % N;

            if (id % 2 == 0) {
                // Even philosophers pick up left fork first, then right
                forks[leftFork].lock();
                forks[rightFork].lock();
            } else {
                // Odd philosophers pick up right fork first, then left
                forks[rightFork].lock();
                forks[leftFork].lock();
            }

            System.out.println("Philosopher " + id + " picked up forks.");
        }

        // Philosopher eating
        private void eat() throws InterruptedException {
            System.out.println("Philosopher " + id + " is eating.");
            Thread.sleep((long) (Math.random() * 1000)); // Simulate eating time
        }

        // Philosopher putting down forks
        private void putDownForks() {
            int leftFork = id;
            int rightFork = (id + 1) % N;

            forks[leftFork].unlock();
            forks[rightFork].unlock();
            System.out.println("Philosopher " + id + " put down forks.");
        }
    }

    public static void main(String[] args) {
        // Initialize forks (locks)
        for (int i = 0; i < N; i++) {
            forks[i] = new ReentrantLock();
        }

        // Create philosopher threads
        Philosopher[] philosophers = new Philosopher[N];
        for (int i = 0; i < N; i++) {
            philosophers[i] = new Philosopher(i);
            philosophers[i].start();
        }
    }
}
