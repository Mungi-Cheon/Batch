package com.study.batch.job;

import com.study.stay.entity.StayInfo;
import com.study.stay.reposirory.StayInfoRepository;
import com.study.stay.srevice.StayInfoService;
import java.util.List;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

public class StayInfoWriter implements ItemWriter<List<StayInfo>> {

    @Autowired
    private StayInfoService stayInfoService;

    @Autowired
    private StayInfoRepository stayInfoRepository;

    @Override
    public void write(Chunk<? extends List<StayInfo>> chunk) throws Exception {
        this.stayInfoService.saveAll(chunk.getItems().get(0));
    }
}
