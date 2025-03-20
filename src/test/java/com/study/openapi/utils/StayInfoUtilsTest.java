package com.study.openapi.utils;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.study.openapi.dto.OpenApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StayInfoUtilsTest {

    @Autowired
    private StayInfoUtils stayInfoUtils; // 실제 Bean 주입

    @Test
    public void testGetAllStayInfo() {
        OpenApiResponse response = stayInfoUtils.getAllStayInfo(1);
        assertNotNull(response);
    }
}