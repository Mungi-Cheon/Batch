package com.study.stay.srevice;

import com.study.stay.dto.StayInfoListResponse;
import com.study.stay.dto.StayInfoResponse;
import com.study.stay.entity.StayInfo;
import com.study.stay.repository.StayInfoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StayInfoService {

    private final StayInfoRepository stayInfoRepository;

    public StayInfoListResponse saveAll(List<StayInfo> list){
        List<StayInfo> savedList = stayInfoRepository.saveAll(list);

        List<StayInfoResponse> stayInfoResponseList = savedList.stream()
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
            )).toList();

        return StayInfoListResponse.from(stayInfoResponseList);
    }
}
