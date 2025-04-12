package src;

public class DiningTable {
    public static void main(String[] args){
        final Philosopher[] philosophers = new Philosopher[5];
        final Chopstick[] chopsticks = new Chopstick[5];

        for(int i = 0; i < 5; i++){
            chopsticks[i] = new Chopstick(i+1);
        }

        for(int i = 0; i < 5; i++){
            if(i < 4){
                if(i % 2 == 0){
                    philosophers[i] = new Philosopher(i+1, chopsticks[i+1], chopsticks[i]);
                }else{
                    philosophers[i] = new Philosopher(i+1, chopsticks[i], chopsticks[i+1]);
                }
            } else{
                philosophers[i] = new Philosopher(i+1, chopsticks[i], chopsticks[0]);
            }
        }

        for(int i = 0; i < 5; i++){
            philosophers[i].start();
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Time to stop");
        for(int i = 0; i < 5; i++){
            philosophers[i].stop();
            System.out.println("Philosopher " + i + " ate " + philosophers[i].getTimesEaten() + " times");
        }
    }
}
