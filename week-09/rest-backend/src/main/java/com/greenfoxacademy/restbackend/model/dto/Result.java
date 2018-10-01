package com.greenfoxacademy.restbackend.model.dto;

public class Result {
    private Double result;

    public Result() {
    }

    public Result(Double result) {
        this();
        this.result = result;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }
}
