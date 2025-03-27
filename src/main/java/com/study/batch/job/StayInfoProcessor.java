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
        List<Item> itemList = response.getResponse().body().items().item();

        List<StayInfo> result = itemList.stream()
            .map(item -> StayInfo.from(
                item.title(),
                item.addr1(),
                item.addr2(),
                item.areacode(),
                item.sigungucode(),
                item.firstimage(),
                item.firstimage2(),
                item.mapy(),
                item.mapx(),
                item.mlevel(),
                item.tel(),
                item.likeCount(),
                item.rating()
            ))
            .toList();

        log.info("process complete.");
        return result;
    }
}
