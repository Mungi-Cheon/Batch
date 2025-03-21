package com.study.batch;

import com.study.batch.job.StayInfoProcessor;
import com.study.batch.job.StayInfoReader;
import com.study.batch.job.StayInfoWriter;
import com.study.openapi.dto.OpenApiResponse;
import com.study.stay.entity.StayInfo;
import com.study.stay.reposirory.StayInfoRepository;
import java.util.List;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class StayInfoBatch {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    private final StayInfoReader stayInfoReader;
    private final StayInfoRepository stayInfoRepository;

    public StayInfoBatch(JobRepository jobRepository,
        PlatformTransactionManager platformTransactionManager, StayInfoReader stayInfoReader,
        StayInfoRepository stayInfoRepository) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
        this.stayInfoReader = stayInfoReader;
        this.stayInfoRepository = stayInfoRepository;
    }

    @Bean
    public Job stayInfoJob(){
        return new JobBuilder("stayInfoJob", jobRepository)
            .start(stayInfoStep())
            .build();
    }

    @Bean
    public Step stayInfoStep(){
        return new StepBuilder("stayInfoStep", jobRepository)
            .<OpenApiResponse, List<StayInfo>> chunk(10, platformTransactionManager)
            .reader(apiResponseReader())
            .processor(stayInfoProcessor())
            .writer(stayInfoWriter())
            .build();
    }

    @Bean
    public ItemReader<OpenApiResponse> apiResponseReader(){
        return new StayInfoReader();
    }

    @Bean
    public ItemProcessor<OpenApiResponse, List<StayInfo>> stayInfoProcessor(){
        return new StayInfoProcessor();
    }

    @Bean
    public StayInfoWriter stayInfoWriter() {
        return new StayInfoWriter();
    }
}
