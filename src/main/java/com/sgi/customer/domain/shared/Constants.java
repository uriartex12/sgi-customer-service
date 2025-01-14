package com.sgi.customer.domain.shared;

import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

/**
 * Utility class for defining constants and helper methods used throughout the application.
 * This class includes static constants and a method to generate unique card numbers.
 */
public class Constants {

    public static final String EXTERNAL_REQUEST_SUCCESS_FORMAT = "Request to {} succeeded: {}";
    public static final String EXTERNAL_REQUEST_ERROR_FORMAT = "Error during request to {}";
    public static final String KAFKA_MESSAGE = "Mensaje enviado: {}";
    public static final String ERROR_KAFKA_MESSAGE = "Error al enviar mensaje: ";

    /**
     * Builds a URL with query parameters from the provided base domain, endpoint, and parameters map.
     *
     * @param domain the base URL (e.g., "https://api.example.com")
     * @param url the endpoint (e.g., "/v1/accounts")
     * @param params query parameters to be appended to the URL
     * @return a fully constructed URL with query parameters
     */
    public static String urlParamsComponentBuilder(String domain, String url, Map<String, Object> params) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(domain.concat(url));
        params.forEach(builder::queryParam);
        return builder.toUriString();
    }
}
