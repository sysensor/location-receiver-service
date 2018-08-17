package com.sysensor.app.common;

import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;


public class APIUtility {

    public static final String APPLICATION_JSON = "application/json";

    public static void printHeaders(HttpHeaders headers, Logger log) {
        headers.forEach((k, v) ->
                log.info(k + "-" + v)
        );
    }
}
