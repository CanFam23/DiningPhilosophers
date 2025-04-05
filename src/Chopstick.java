package src;

public class Chopstick {
    /** Could be useful for debugging */
    private int chopstickNum;

    private boolean inUse;

    public Chopstick(int chopstickNum) {
        this.chopstickNum = chopstickNum;
        inUse = false;
    }

    public boolean aquire() {
        return true;
    }

    public boolean release(){
        return true;
    }

}
