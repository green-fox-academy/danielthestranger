package com.greenfoxacademy.restbackend.util;

public class DoUntilActions {
    public int sum(int first, int second) {
        return first + second;
    }
    public int factor(int first, int second) {
        if (first == 0 || second == 0)
            return 1;

        return first * second;
    }

}
