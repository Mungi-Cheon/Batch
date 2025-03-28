package com.study.batch.job;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.openapi.dto.OpenApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.net.URISyntaxException;

@ExtendWith(MockitoExtension.class)
class StayInfoReaderTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private StayInfoReader stayInfoReader;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(stayInfoReader, "apiUri", "http://example.com/api");
        ReflectionTestUtils.setField(stayInfoReader, "authKey", "test-key");
    }
    @Test
    void read_ShouldCallGetAllStayInfo() throws Exception {
        StayInfoReader spyReader = spy(stayInfoReader);
        doReturn(new OpenApiResponse()).when(spyReader).getAllStayInfo();

        OpenApiResponse response = spyReader.read();

        assertThat(response).isNotNull();
        verify(spyReader, times(1)).getAllStayInfo();
    }

    @Test
    void getAllStayInfo_ShouldReturnValidResponse() throws URISyntaxException, JsonProcessingException {
        String mockJsonResponse = "{\"response\": {\"body\": {\"items\": {\"item\": []}}}}";
        URI expectedUri = new URI("http://example.com/api?serviceKey=test-key&numOfRows=1000&pageNo=1&MobileOS=AND&MobileApp=TestApp&_type=json");

        ResponseEntity<String> mockResponseEntity = new ResponseEntity<>(mockJsonResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(expectedUri, String.class)).thenReturn(mockResponseEntity);
        when(objectMapper.readValue(mockJsonResponse, OpenApiResponse.class)).thenReturn(new OpenApiResponse());

        OpenApiResponse response = stayInfoReader.getAllStayInfo();

        assertThat(response).isNotNull();
        verify(restTemplate, times(1)).getForEntity(expectedUri, String.class);
        verify(objectMapper, times(1)).readValue(mockJsonResponse, OpenApiResponse.class);
    }
}
