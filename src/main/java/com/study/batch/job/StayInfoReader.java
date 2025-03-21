package com.study.batch.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.openapi.dto.OpenApiResponse;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class StayInfoReader implements ItemReader<OpenApiResponse> {

    @Value("${api.uri}")
    private String apiUri;
    @Value("${api.auth-key}")
    private String authKey;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public OpenApiResponse read()
        throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return getAllStayInfo();
    }

    public OpenApiResponse getAllStayInfo(){
        try {
            URI uri = new URI(apiUri + "?serviceKey=" + authKey
                + "&numOfRows=" + 1000 + "&pageNo=" + 1+ "&MobileOS=AND&MobileApp=TestApp&_type=json");

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
