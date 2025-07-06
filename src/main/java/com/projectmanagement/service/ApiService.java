package com.projectmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {

    @Autowired
    private RestTemplate restTemplate;

    // GET without headers
    public String getWithoutHeaders(String url) {
        return restTemplate.getForObject(url, String.class);
    }

    // GET with headers
    public ResponseEntity<String> getWithHeaders(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer YOUR_TOKEN");
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    // POST without headers
    public String postWithoutHeaders(String url, Object requestPayload) {
        return restTemplate.postForObject(url, requestPayload, String.class);
    }

    // POST with headers
    public ResponseEntity<String> postWithHeaders(String url, Object requestPayload) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer YOUR_TOKEN");

        HttpEntity<Object> entity = new HttpEntity<>(requestPayload, headers);
        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }
}
