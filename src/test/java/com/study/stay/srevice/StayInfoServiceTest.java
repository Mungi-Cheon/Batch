package com.study.stay.srevice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.study.stay.dto.StayInfoListResponse;
import com.study.stay.dto.StayInfoResponse;
import com.study.stay.entity.StayInfo;
import com.study.stay.repository.StayInfoRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StayInfoServiceTest {

    @Mock
    private StayInfoRepository stayInfoRepository;

    @InjectMocks
    private StayInfoService stayInfoService;

    private StayInfo stayInfo1;
    private StayInfo stayInfo2;
    private List<StayInfo> stayInfoList;

    @BeforeEach
    void setUp() {
        stayInfo1 = new StayInfo(1L, "Stay1", "Addr1", "Addr2", "123", "456",
            "img1", "img2", 37.123, 127.456, 10, "010-1234-5678", 5, 4.5);
        stayInfo2 = new StayInfo(2L, "Stay2", "Addr3", "Addr4", "789", "987",
            "img3", "img4", 38.789, 128.987, 12, "010-9876-5432", 3, 4.0);
        stayInfoList = List.of(stayInfo1, stayInfo2);
    }

    @Test
    void saveAll_ShouldReturnResponseList() {
        when(stayInfoRepository.saveAll(stayInfoList)).thenReturn(stayInfoList);

        // When
        StayInfoListResponse response = stayInfoService.saveAll(stayInfoList);

        // Then
        assertNotNull(response);
        assertEquals(2, response.getResponseList().size());

        StayInfoResponse response1 = response.getResponseList().get(0);
        assertEquals(stayInfo1.getId(), response1.getId());
        assertEquals(stayInfo1.getTitle(), response1.getTitle());
        assertEquals(stayInfo1.getAddress1(), response1.getAddress1());
        assertEquals(stayInfo1.getLikeCount(), response1.getLikeCount());
        assertEquals(stayInfo1.getRating(), response1.getRating());

        StayInfoResponse response2 = response.getResponseList().get(1);
        assertEquals(stayInfo2.getId(), response2.getId());
        assertEquals(stayInfo2.getTitle(), response2.getTitle());
        assertEquals(stayInfo2.getAddress1(), response2.getAddress1());
        assertEquals(stayInfo2.getLikeCount(), response2.getLikeCount());
        assertEquals(stayInfo2.getRating(), response2.getRating());
    }
}