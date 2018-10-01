package com.greenfoxacademy.restbackend.model.dto;

import java.util.List;

public class ArrayWithAction {
    private String what;
    private List<Integer> numbers;

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Integer> numbers) {
        this.numbers = numbers;
    }
}
