package com.study.batch.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.openapi.dto.OpenApiResponse;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
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
    private static final String KEY_PAGE_NO = "pageNo";
    private int pageNo = 1;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution){
        ExecutionContext context = stepExecution.getExecutionContext();

        if(context.containsKey(KEY_PAGE_NO)){
            pageNo = context.getInt(KEY_PAGE_NO);
        }else{
            context.put(KEY_PAGE_NO, pageNo);
        }
    }

    @Override
    public OpenApiResponse read() throws Exception{
        return getAllStayInfo();
    }

    public OpenApiResponse getAllStayInfo(){

        log.info("stay info read start.");

        try {
            URI uri = new URI(apiUri + "?serviceKey=" + authKey
                + "&numOfRows=" + 1000 + "&pageNo=" + pageNo + "&MobileOS=AND&MobileApp=TestApp&_type=json");

            ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

            if(responseEntity.getStatusCode() == HttpStatus.OK){
                log.info("stay info read complete.");
                return objectMapper.readValue(responseEntity.getBody(), OpenApiResponse.class);
            }
        } catch (URISyntaxException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new OpenApiResponse();
    }
}
