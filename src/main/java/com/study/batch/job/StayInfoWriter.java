package com.study.batch.job;

import com.study.stay.dto.StayInfoListResponse;
import com.study.stay.entity.StayInfo;
import com.study.stay.srevice.StayInfoService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class StayInfoWriter implements ItemWriter<List<StayInfo>> {

    @Autowired
    private StayInfoService stayInfoService;

    @Override
    public void write(Chunk<? extends List<StayInfo>> chunk){
        StayInfoListResponse response = this.stayInfoService.saveAll(chunk.getItems().get(0));
        log.info("Number of data written {}", response.getResponseList().size());
    }
}
