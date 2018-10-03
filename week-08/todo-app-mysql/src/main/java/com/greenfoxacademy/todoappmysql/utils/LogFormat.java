package com.greenfoxacademy.todoappmysql.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;

public class LogFormat {
    private static final String requestMarker = "Request";
    private static final String requestDataFallback = "Cannot get request body";
    private static final ObjectMapper jacksonMapper = new ObjectMapper();


    public static String fromRequest(HttpServletRequest request) {
        return fromRequest(request, null);
    }

    public static String fromRequest(HttpServletRequest request, Object postedObject) {
        ContentCachingRequestWrapper cachedRequest = new ContentCachingRequestWrapper(request);
        cachedRequest.getParameterMap();

        String endpoint = cachedRequest.getServletPath();
        String requestMethod = cachedRequest.getMethod();

        String msg = MessageFormat.format("{0} {1} {2} ",  requestMarker, endpoint, requestMethod);

        String requestData = null;
        if ("get".equalsIgnoreCase(requestMethod)) {
            requestData = Objects.toString(cachedRequest.getQueryString(), "");
        } else if ("post".equalsIgnoreCase(requestMethod)) {
            try {
                requestData = readPayload(cachedRequest);
            } catch (IOException e) {
                requestData = "";
            }

            if (requestData.isEmpty() && postedObject != null) {
                String contentType = cachedRequest.getHeader("Content-Type");
                requestData = getDataStringFromObject(contentType, postedObject);
            }
        }

        return msg + requestData;
    }

    public static String readPayload(final HttpServletRequest request) throws IOException {
        String payloadData = null;
        ContentCachingRequestWrapper contentCachingRequestWrapper = new ContentCachingRequestWrapper(request);
        contentCachingRequestWrapper.getParameterMap();
        if (null != contentCachingRequestWrapper) {
            byte[] buf = contentCachingRequestWrapper.getContentAsByteArray();
            if (buf.length > 0) {
                payloadData = new String(buf, 0, buf.length, contentCachingRequestWrapper.getCharacterEncoding());
            }
        }
        return payloadData;
    }

    private static String getDataStringFromObject(String contentType, Object postedObject) {
        try {
            switch (contentType) {
                case "application/x-www-form-urlencoded":
                    Map<String,Object> mappedObject = jacksonMapper.convertValue(postedObject,Map.class);
                    return mappedObject.toString();
                case "application/json":
                    return jacksonMapper.writeValueAsString(postedObject);
                default:
                    return requestDataFallback;
            }
        } catch (Exception e) {
            return requestDataFallback;
        }
    }
}
