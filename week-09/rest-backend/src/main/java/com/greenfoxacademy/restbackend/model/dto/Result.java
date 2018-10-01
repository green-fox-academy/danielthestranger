package com.greenfoxacademy.restbackend.model.dto;

public class Result {
    private Integer result;

    public Result() {
    }

    public Result(Integer result) {
        this();
        this.result = result;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
