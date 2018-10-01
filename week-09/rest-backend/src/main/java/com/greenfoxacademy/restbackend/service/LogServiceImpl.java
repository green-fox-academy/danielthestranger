package com.greenfoxacademy.restbackend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.restbackend.model.Log;
import com.greenfoxacademy.restbackend.model.dto.LogsWithCount;
import com.greenfoxacademy.restbackend.repository.LogRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.stream.Collectors;

@Service
public class LogServiceImpl implements LogService {

    private LogRepository logRepository;
    private ObjectMapper jsonMapper;

    public LogServiceImpl() {
        jsonMapper = new ObjectMapper();
    }


    @Autowired
    public LogServiceImpl(LogRepository logRepository) {
        this();
        this.logRepository = logRepository;
    }

    @Override
    public Log save(Log log) {
        return logRepository.save(log);
    }

    @Override
    public LogsWithCount findAllWithCount() {
        LogsWithCount logsWithCount = new LogsWithCount();
        logsWithCount.setEntries(logRepository.findAll());
        return logsWithCount;
    }

    @Override
    public Log log(HttpServletRequest request) {
        return log(request, null);
    }

    @Override
    public Log log(HttpServletRequest request, Object postObject) {
        String endpoint = request.getServletPath();

        if ("get".equalsIgnoreCase(request.getMethod())) {
            return log(endpoint, request.getQueryString());
        } else if ("post".equalsIgnoreCase(request.getMethod())) {
            String requestBody = null;
            if (postObject != null) {
                try {
                    requestBody = jsonMapper.writeValueAsString(postObject);
                } catch (Exception e) {
                    requestBody = "Cannot get request body";
                }
            }
            return log(endpoint, requestBody);
        }

        return new Log();
    }

    @Override
    public Log log(String endpoint, String data) {
        Log log = new Log(endpoint, data);
        return save(log);
    }

}
