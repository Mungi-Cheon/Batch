package com.study.batch;

import com.study.batch.job.StayInfoProcessor;
import com.study.batch.job.StayInfoReader;
import com.study.batch.job.StayInfoWriter;
import com.study.batch.listener.StayInfoStepListener;
import com.study.openapi.dto.OpenApiResponse;
import com.study.stay.entity.StayInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class StayInfoBatch {

    @Bean
    public Job stayInfoJob(JobRepository jobRepository, Step stayInfoStep) {
        return new JobBuilder("stayInfoJob", jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(stayInfoStep)
            .build();
    }

    @Bean
    public Step stayInfoStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, StepExecutionListener stayInfoStepListener) {
        return new StepBuilder("stayInfoStep", jobRepository)
            .<OpenApiResponse, List<StayInfo>>chunk(10, transactionManager)
            .reader(apiResponseReader())
            .processor(stayInfoProcessor())
            .writer(stayInfoWriter())
            .listener(stayInfoStepListener)
            .build();
    }

    @Bean
    public ItemReader<OpenApiResponse> apiResponseReader() {
        return new StayInfoReader();
    }

    @Bean
    public ItemProcessor<OpenApiResponse, List<StayInfo>> stayInfoProcessor() {
        return new StayInfoProcessor();
    }

    @Bean
    public StayInfoWriter stayInfoWriter() {
        return new StayInfoWriter();
    }

    @Bean
    public StayInfoStepListener stayInfoStepListener() {
        return new StayInfoStepListener();
    }
}