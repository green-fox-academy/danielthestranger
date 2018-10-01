package com.greenfoxacademy.restbackend.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.restbackend.model.Log;

import javax.servlet.http.HttpServletRequest;

public interface LogService {
    public Log save(Log log);
    public Iterable<Log> findAll();
    public Log log(String endpoint, String data);
    public Log log(HttpServletRequest request);
    public Log log(HttpServletRequest request, Object postObject);
}
