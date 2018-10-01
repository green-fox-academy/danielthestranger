package com.greenfoxacademy.restbackend.model.dto;

public class Result<T> {
    private T result;

    public Result() {
    }

    public Result(T result) {
        this();
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
