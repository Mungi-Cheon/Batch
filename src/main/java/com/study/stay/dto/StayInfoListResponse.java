package com.study.stay.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class StayInfoListResponse {

    private List<StayInfoResponse> responseList;

    public static StayInfoListResponse from(List<StayInfoResponse> list){
        return StayInfoListResponse.builder().responseList(list).build();
    }
}
