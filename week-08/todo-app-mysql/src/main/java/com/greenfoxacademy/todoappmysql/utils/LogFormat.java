package com.greenfoxacademy.todoappmysql.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class LogFormat {
    private static final String requestMarker = "Request";
    private static final ObjectMapper jacksonMapper = new ObjectMapper();

    public static String fromRequest(HttpServletRequest request) {
        return fromRequest(request, null);
    }

    public static String fromRequest(HttpServletRequest origRequest, Object postedObject) {
        ContentCachingRequestWrapper cachedRequest = new ContentCachingRequestWrapper(origRequest);
        cachedRequest.getParameterMap();

        String endpoint = cachedRequest.getServletPath();
        String requestMethod = cachedRequest.getMethod();

        String msg = MessageFormat.format("{0} {1} {2} ",  requestMarker, endpoint, requestMethod);

        String requestData = "";
        if ("get".equalsIgnoreCase(requestMethod)) {
            requestData = Objects.toString(cachedRequest.getQueryString(), requestData);
        } else if ("post".equalsIgnoreCase(requestMethod)) {
            String contentType = cachedRequest.getHeader("Content-Type");
            String requestBodyFallback = "Cannot get request body";

            String requestBody = "";
            switch (contentType) {
                case "application/x-www-form-urlencoded":
                    try {
                        requestBody = cachedRequest.getReader().lines().collect(Collectors.joining());
                    } catch (IOException e) {
                        requestBody = "";
                    }
                    if (requestBody.isEmpty() || postedObject != null) {
                        Map<String,Object> mappedObject = jacksonMapper.convertValue(postedObject,Map.class);
                        requestBody = mappedObject.toString();
                    }
                    break;
                case "application/json":
                    try {
                        requestBody = jacksonMapper.writeValueAsString(postedObject);
                    } catch (Exception e) {
                        requestBody = requestBodyFallback;
                    }
                    break;
                default:
                    requestBody = requestBodyFallback;
                    break;
            }
            requestData = requestBody;
        }

        return msg + requestData;
    }
}
