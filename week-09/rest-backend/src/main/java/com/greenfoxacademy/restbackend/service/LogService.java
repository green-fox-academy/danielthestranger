package com.greenfoxacademy.restbackend.service;

import com.greenfoxacademy.restbackend.model.Log;
import com.greenfoxacademy.restbackend.model.dto.LogsWithCount;

import javax.servlet.http.HttpServletRequest;

public interface LogService {
    public Log save(Log log);
    public LogsWithCount findAllWithCount();
    public Log log(String endpoint, String data);
    public Log log(HttpServletRequest request);
    public Log log(HttpServletRequest request, Object postObject);
}
