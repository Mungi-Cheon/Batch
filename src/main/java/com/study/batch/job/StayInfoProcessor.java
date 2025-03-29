package com.study.batch.job;

import com.study.openapi.dto.OpenApiResponse;
import com.study.openapi.dto.OpenApiResponse.Item;
import com.study.stay.entity.StayInfo;
import java.util.Iterator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class StayInfoProcessor implements ItemProcessor<OpenApiResponse, StayInfo> {

    private Iterator<StayInfo> stayInfoIterator;

    @Override
    public StayInfo process(OpenApiResponse response) {
        log.info("process start.");
        List<Item> itemList = response.getResponse().body().items().item();

        stayInfoIterator = itemList.stream()
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
            .iterator();

        log.info("process complete.");
        return stayInfoIterator.hasNext() ? stayInfoIterator.next() : null;
    }
}