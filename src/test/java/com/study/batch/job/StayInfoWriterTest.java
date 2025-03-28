package com.study.batch.job;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.study.stay.dto.StayInfoListResponse;
import com.study.stay.dto.StayInfoResponse;
import com.study.stay.entity.StayInfo;
import com.study.stay.srevice.StayInfoService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.Chunk;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class StayInfoWriterTest {

    @Mock
    private StayInfoService stayInfoService;

    @InjectMocks
    private StayInfoWriter stayInfoWriter;

    private List<StayInfo> stayInfoList;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(stayInfoWriter, "stayInfoService", stayInfoService);

        // 테스트 데이터 생성
        StayInfo stayInfo1 = new StayInfo(1L, "Stay1", "Addr1", "Addr2", "123", "456",
            "img1", "img2", 37.123, 127.456, 10, "010-1234-5678", 5, 4.5);
        StayInfo stayInfo2 = new StayInfo(2L, "Stay2", "Addr3", "Addr4", "789", "987",
            "img3", "img4", 38.789, 128.987, 12, "010-9876-5432", 3, 4.0);
        stayInfoList = List.of(stayInfo1, stayInfo2);
    }

    @Test
    void write_ShouldCallSaveAllAndLogNumberOfEntries() {
        // Given
        List<StayInfoResponse> responseList = stayInfoList.stream()
            .map(stayInfo -> StayInfoResponse.from(
                stayInfo.getId(),
                stayInfo.getTitle(),
                stayInfo.getAddress1(),
                stayInfo.getAddress2(),
                stayInfo.getAreacode(),
                stayInfo.getSigungucode(),
                stayInfo.getFirstimage(),
                stayInfo.getFirstimage2(),
                stayInfo.getMapY(),
                stayInfo.getMapX(),
                stayInfo.getMapLevel(),
                stayInfo.getTel(),
                stayInfo.getLikeCount(),
                stayInfo.getRating()
            ))
            .toList();

        StayInfoListResponse listResponse = StayInfoListResponse.from(responseList);

        when(stayInfoService.saveAll(stayInfoList)).thenReturn(listResponse);

        Chunk<List<StayInfo>> mockChunk = mock(Chunk.class);
        when(mockChunk.getItems()).thenReturn(List.of(stayInfoList));

        // When
        stayInfoWriter.write(mockChunk);

        // Then
        verify(stayInfoService, times(1)).saveAll(stayInfoList);
    }
}