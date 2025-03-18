package com.study.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.batch.dto.OpenApiResponse;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class StayInfoUtils {

    @Value("${api.uri}")
    private String apiUri;
    @Value("${api.auth-key}")
    private String authKey;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RestTemplate restTemplate;

    public OpenApiResponse getAllStayInfo(int pageNo){
        try {
            URI uri = new URI(apiUri + "?serviceKey=" + authKey
                + "&numOfRows=" + 50 + "&pageNo=" + pageNo+ "&MobileOS=AND&MobileApp=TestApp&_type=json");

            ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

            if(responseEntity.getStatusCode() == HttpStatus.OK){
                return objectMapper.readValue(responseEntity.getBody(), OpenApiResponse.class);
            }
        } catch (URISyntaxException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new OpenApiResponse();
    }
}
