package com.greenfoxacademy.restbackend.util;

public class DoUntilActions {
    public int sum(int collector, int second) {
        return collector + second;
    }
    public int factor(int collector, int second) {
        if (collector == 0)
            return 1;
        return collector * second;
    }

}
