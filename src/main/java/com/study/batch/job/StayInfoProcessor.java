package com.study.batch.job;

import com.study.openapi.dto.OpenApiResponse;
import com.study.openapi.dto.OpenApiResponse.Item;
import com.study.stay.entity.StayInfo;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.batch.item.ItemProcessor;

public class StayInfoProcessor implements ItemProcessor<OpenApiResponse, List<StayInfo>> {

    @Override
    public List<StayInfo> process(OpenApiResponse response) throws Exception {
        List<Item> itemList = response.getResponse().getBody().getItems().getItem();
        return response.getResponse().getBody().getItems().getItem().stream()
            .map(item -> StayInfo.from(
                item.getTitle(),
                item.getAddr1(),
                item.getAddr2(),
                item.getAreacode(),
                item.getSigungucode(),
                item.getFirstimage(),
                item.getFirstimage2(),
                item.getMapy(),
                item.getMapx(),
                item.getMlevel(),
                item.getTel(),
                item.getLikeCount(),
                item.getRating()
            ))
            .collect(Collectors.toList());
    }
}
