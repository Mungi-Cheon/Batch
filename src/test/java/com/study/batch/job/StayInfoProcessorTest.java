package com.study.batch.job;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.study.openapi.dto.OpenApiResponse;
import com.study.openapi.dto.OpenApiResponse.Body;
import com.study.openapi.dto.OpenApiResponse.Item;
import com.study.openapi.dto.OpenApiResponse.Items;
import com.study.openapi.dto.OpenApiResponse.Response;
import com.study.stay.entity.StayInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

class StayInfoProcessorTest {

    private static final Logger log = LoggerFactory.getLogger(StayInfoProcessorTest.class);

    private StayInfoProcessor stayInfoProcessor;

    @BeforeEach
    void setUp() {
        stayInfoProcessor = new StayInfoProcessor();
    }

    @Test
    void process_ShouldConvertOpenApiResponseToStayInfoList() {
        Item item1 = new Item(1L,"Stay1", "Addr1", "Addr2", "123", "456",
            "","img1", "img2", 37.123, 127.456, 10, "010-1234-5678", 5, 4.5);
        Item item2 = new Item(2L,"Stay2", "Addr3", "Addr4", "789", "987",
            "","img3", "img4", 38.789, 128.987, 12, "010-9876-5432", 3, 4.0);
        List<Item> items = List.of(item1, item2);

        OpenApiResponse mockResponse = mock(OpenApiResponse.class);
        Response response = mock(Response.class);
        Body body = mock(Body.class);
        Items mockItems = mock(Items.class);

        when(mockResponse.getResponse()).thenReturn(response);
        when(response.body()).thenReturn(body);
        when(body.items()).thenReturn(mockItems);
        when(mockItems.item()).thenReturn(items);

        // When
        List<StayInfo> result = stayInfoProcessor.process(mockResponse);

        // Then
        assertThat(result).isNotNull().hasSize(2);
        assertThat(result.get(0).getTitle()).isEqualTo("Stay1");
        assertThat(result.get(1).getTitle()).isEqualTo("Stay2");

        log.info("StayInfoProcessor process method verified successfully.");
    }

    @Test
    void process_ShouldHandleEmptyResponse() {
        // Given
        OpenApiResponse mockResponse = mock(OpenApiResponse.class);
        Response response = mock(Response.class);
        Body body = mock(Body.class);
        Items mockItems = mock(Items.class);

        when(mockResponse.getResponse()).thenReturn(response);
        when(response.body()).thenReturn(body);
        when(body.items()).thenReturn(mockItems);
        when(mockItems.item()).thenReturn(List.of());

        // When
        List<StayInfo> result = stayInfoProcessor.process(mockResponse);

        // Then
        assertThat(result).isNotNull().isEmpty();

        log.info("StayInfoProcessor process method handled empty response successfully.");
    }
}