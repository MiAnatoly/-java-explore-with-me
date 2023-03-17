package ru.practicum.client.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class BaseClient {
    protected final RestTemplate rest;
    private static String CACHE_HTTP;
    private static ResponseEntity<Object> CACHE_OBJECT;

    public BaseClient(RestTemplate rest) {
        this.rest = rest;
    }

    protected ResponseEntity<Object> get(String path, @Nullable Map<String, Object> parameters) {
        return makeAndSendRequest(HttpMethod.GET, path, parameters, null);
    }

    protected <T> ResponseEntity<Object> post(String path, T body) {
        return post(path, null, body);
    }

    protected <T> ResponseEntity<Object> post(String path,
                                              @Nullable Map<String, Object> parameters, T body) {
        return makeAndSendRequest(HttpMethod.POST, path, parameters, body);
    }



    private <T> ResponseEntity<Object> makeAndSendRequest(HttpMethod method, String path,
                                                          @Nullable Map<String, Object> parameters, @Nullable T body) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body);
        ResponseEntity<Object> statisticsServerResponse;

        String cache = saveCacheHttp(method, path, parameters);
        boolean methodGet = method.equals(HttpMethod.GET);
        if (cache.equals(CACHE_HTTP) && methodGet) {
            return CACHE_OBJECT;
        } else {
            CACHE_HTTP = cache;
        }
        try {
            if (parameters != null) {
                statisticsServerResponse = rest.exchange(path, method, requestEntity, Object.class, parameters);
            } else {
                statisticsServerResponse = rest.exchange(path, method, requestEntity, Object.class);
            }
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
        ResponseEntity<Object> result = prepareGatewayResponse(statisticsServerResponse);
        if (methodGet) {
            CACHE_OBJECT = result;
        }
        return result;
    }

    private static ResponseEntity<Object> prepareGatewayResponse(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }

        return responseBuilder.build();
    }

    private String saveCacheHttp(HttpMethod method, String path,
                                 @Nullable Map<String, Object> parameters) {
        if (parameters != null) {
            return method.name() + path + "/" + parameters.values().stream().map(Object::toString)
                    .collect(Collectors.joining("p"));
        } else {
            return method.name() + path + "/";
        }
    }
}
