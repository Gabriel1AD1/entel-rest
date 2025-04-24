package com.labotec.entelgatewayapi;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RestController
@RequestMapping("/condominios")
public class GatewayController {
    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }
    private static final Log log = LogFactory.getLog(GatewayController.class);
    private final RestTemplate restTemplate = new RestTemplate();

    @RequestMapping("/**")
    public ResponseEntity<?> redirection(HttpServletRequest request, HttpMethod method, @RequestBody(required = false) String body) {
        String path = request.getRequestURI().replaceFirst("/condominios", "");
        String query = request.getQueryString();
        String DESTINE = "http://localhost:4001";
        String url = DESTINE + path + (query != null ? "?" + query : "");

        HttpHeaders headers = new HttpHeaders();
        Collections.list(request.getHeaderNames())
                .forEach(header -> headers.add(header, request.getHeader(header)));

        HttpEntity<String> entity = new HttpEntity<>(body, headers);


            ResponseEntity<String> response = restTemplate.exchange(url, method, entity, String.class);

            return ResponseEntity.status(response.getStatusCode()).headers(response.getHeaders()).body(response.getBody());


    }


}
