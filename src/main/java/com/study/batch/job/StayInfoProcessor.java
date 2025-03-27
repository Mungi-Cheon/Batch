package com.study.batch.job;

import com.study.openapi.dto.OpenApiResponse;
import com.study.openapi.dto.OpenApiResponse.Item;
import com.study.stay.entity.StayInfo;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class StayInfoProcessor implements ItemProcessor<OpenApiResponse, List<StayInfo>> {

    @Override
    public List<StayInfo> process(OpenApiResponse response) {
        log.info("process start.");
        List<Item> itemList = response.getResponse().getBody().getItems().getItem();

        List<StayInfo> result = itemList.stream()
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
            .toList();

        log.info("process complete.");
        return result;
    }
}
