package com.study.batch;

import com.study.batch.job.StayInfoProcessor;
import com.study.batch.job.StayInfoReader;
import com.study.batch.listener.StayInfoStepListener;
import com.study.openapi.dto.OpenApiResponse;
import com.study.stay.entity.StayInfo;
import com.study.stay.repository.StayInfoRepository;
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
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class StayInfoBatch {

    private final StayInfoRepository stayInfoRepository; // ✅ Repository 주입

    @Bean
    public Job stayInfoJob(JobRepository jobRepository, Step stayInfoStep) {
        return new JobBuilder("stayInfoJob", jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(stayInfoStep)
            .build();
    }

    @Bean
    public Step stayInfoStep(
        JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        StepExecutionListener stayInfoStepListener,
        RepositoryItemWriter<StayInfo> stayInfoWriter // ✅ RepositoryItemWriter 사용
    ) {
        return new StepBuilder("stayInfoStep", jobRepository)
            .<OpenApiResponse, StayInfo>chunk(10, transactionManager)
            .reader(apiResponseReader())
            .processor(stayInfoProcessor())
            .writer(stayInfoWriter)  // ✅ 직접 repository.saveAll() 호출하지 않음
            .listener(stayInfoStepListener)
            .build();
    }

    @Bean
    public ItemReader<OpenApiResponse> apiResponseReader() {
        return new StayInfoReader();
    }

    @Bean
    public ItemProcessor<OpenApiResponse, StayInfo> stayInfoProcessor() {
        return new StayInfoProcessor();
    }

    @Bean
    public RepositoryItemWriter<StayInfo> stayInfoWriter() {
        return new RepositoryItemWriterBuilder<StayInfo>()
            .repository(stayInfoRepository)
            .methodName("save")
            .build();
    }

    @Bean
    public StayInfoStepListener stayInfoStepListener() {
        return new StayInfoStepListener();
    }
}
