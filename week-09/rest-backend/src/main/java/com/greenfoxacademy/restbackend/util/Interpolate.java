package com.greenfoxacademy.restbackend.util;

import java.text.MessageFormat;

public class Interpolate {
    /** Message Format like string("Some String {0} / {1}", var, "thing"); */
    public static String string(String str, Object... args) {
        return new MessageFormat(str).format(args);
    }
}
