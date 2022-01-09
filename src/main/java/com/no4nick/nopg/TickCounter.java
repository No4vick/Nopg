package com.no4nick.nopg;

public class TickCounter extends Thread {
    private static int tick = 0;
    private static int frac = 100;

    public static synchronized int Tick(){
        return tick;
    }

    public TickCounter(int newFrac){
        frac = newFrac;
    }

    public TickCounter(){
    }

    public static synchronized boolean didTickChange(int prevtick){
        if (tick == 0 && prevtick != 0)
            return true;
        else
            return tick > prevtick;
    }

    public void run(){
        tick = 0;
        long time = System.currentTimeMillis();
        while (true) {
            if (System.currentTimeMillis() - time >= frac){
                tick++;
                time = System.currentTimeMillis();
            }
            if (tick == 100) {
                tick = 0;
            }
            //System.out.println(System.currentTimeMillis() - time);
        }
    }
}
