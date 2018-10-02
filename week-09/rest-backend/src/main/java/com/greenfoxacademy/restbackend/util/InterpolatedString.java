package com.greenfoxacademy.restbackend.util;

import java.text.MessageFormat;

public class InterpolatedString {
    /** Message Format like get("Some String {0} / {1}", var, "thing"); */
    public static String get(String str, Object... args) {
        return new MessageFormat(str).format(args);
    }
}
