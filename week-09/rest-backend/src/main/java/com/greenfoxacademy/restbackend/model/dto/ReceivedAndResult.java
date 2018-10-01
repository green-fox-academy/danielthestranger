package com.greenfoxacademy.restbackend.model.dto;

public class ReceivedAndResult {

    private Double received;
    private Double result;

    public ReceivedAndResult() {
    }

    public ReceivedAndResult(Double received, Double result) {
        this();
        this.received = received;
        this.result = result;
    }

    public Double getReceived() {
        return received;
    }

    public void setReceived(Double received) {
        this.received = received;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }
}
