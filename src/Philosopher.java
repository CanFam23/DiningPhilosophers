package src;

public class Philosopher implements Runnable {
    private final int eatTime = 1; // Make static? or different for each philosopher?

    private final int thinkTime = 1; // Make static? or different for each philosopher?

    private boolean running = false;

    private State state;

    private int timesEaten = 0;

    /** Could be useful for debugging */
    private int philNum;

    private final Chopstick leftStick;
    private final Chopstick rightStick;

    private final Thread thread;

    public Philosopher(int philNum, Chopstick leftStick, Chopstick rightStick) {
        this.state = State.Thinking;
        this.philNum = philNum;
        this.leftStick = leftStick;
        this.rightStick = rightStick;
        this.thread = new Thread(this, "Philosopher " + philNum);
    }

    public void start(){
        this.running = true;
        this.thread.start();
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        /*
        while(running)
            wait to pick up first chopstick
            if picked up
                try to pick up second chopstick
                got 2nd chopstick?
                yes
                    eat
                    wait
                no
                  drop first chopstick
                  wait to try again
         */
        this.state = State.Hungry;
//        System.out.println("Philosopher " + philNum + " is running");

        while(running) {
//            System.out.println("Philosopher " + philNum + " is waiting for left stick");
            synchronized (leftStick) {
                leftStick.aquire();
//                System.out.println("Philosopher " + philNum + " got left stick");
//                System.out.println("Philosopher " + philNum + " is waiting for right stick");
                if(rightStick.isInUse()){
//                    System.out.println("Philosopher " + philNum + " stick NOT AVAILABLE");
                    try{
                        leftStick.wait(100);
                    }catch(InterruptedException ignored){}
                    continue;
                }
                synchronized (rightStick) {
                    rightStick.aquire();
//                    System.out.println(rightStick.isInUse());

//                    System.out.println("Philosopher " + philNum + " TIME TO EAT!!!");

                    try{
                        rightStick.wait(100);
                    }catch(InterruptedException ignored){}

                    timesEaten++;

                    rightStick.release();
                    rightStick.notifyAll();
                }

                leftStick.release();
                leftStick.notifyAll();
            }
        }

//        while(running) {
//            System.out.println("Philosopher " + philNum + " is running");
//            synchronized (leftStick) {
//                boolean acquiredLeftChopstick = leftStick.aquire();
//                if(acquiredLeftChopstick) {
//
//                    synchronized (rightStick) {
//                        boolean acquiredRightChopstick = rightStick.aquire();
//                        if(acquiredRightChopstick) {
//                            System.out.println("Philosopher " + philNum + " acquired "+rightStick.toString());
//                            System.out.println("Philosopher " + philNum + " TIME TO EAT!!!");
//                            rightStick.release();
//                        }
//                        rightStick.notify();
//                    }
//                    leftStick.release();
//                }
//                leftStick.notify();
//            }
//        }
    }

    public void stop(){
        running = false;
    }

    public void grabChopstick(){
        return;
    }

    public void eat(){
        return;
    }

    public void think(){
        return;
    }

    public int getTimesEaten(){
        return timesEaten;
    }
}
