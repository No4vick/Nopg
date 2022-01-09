package com.no4nick.nopg;

public class ResetContainer {
    private static boolean reset = false;

    public static synchronized void switchReset(){
        reset = !reset;
    }
    public static synchronized boolean isReset(){
        return reset;
    }
}
