package com.study.batch;

import static org.assertj.core.api.Assertions.assertThat;

import com.study.batch.job.StayInfoWriter;
import com.study.batch.listener.StayInfoStepListener;
import com.study.openapi.dto.OpenApiResponse;
import com.study.stay.entity.StayInfo;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.transaction.PlatformTransactionManager;

@ExtendWith(MockitoExtension.class)
class StayInfoBatchTest {

    @Mock
    private JobRepository jobRepository;

    @Mock
    private PlatformTransactionManager transactionManager;

    @Mock
    private Step stayInfoStep;

    @Mock
    private ItemReader<OpenApiResponse> apiResponseReader;

    @Mock
    private ItemProcessor<OpenApiResponse, List<StayInfo>> stayInfoProcessor;

    @Mock
    private ItemWriter<List<StayInfo>> stayInfoWriter;

    @InjectMocks
    private StayInfoBatch stayInfoBatch;

    @Test
    void stayInfoJob_ShouldBeCreatedSuccessfully() {
        Job job = stayInfoBatch.stayInfoJob(jobRepository, stayInfoStep);

        assertThat(job).isNotNull();
        assertThat(job.getName()).isEqualTo("stayInfoJob");
    }

    @Test
    void stayInfoStep_ShouldBeCreatedSuccessfully() {
        StepBuilder stepBuilder = new StepBuilder("stayInfoStep", jobRepository);

        Step step = stayInfoBatch.stayInfoStep(jobRepository, transactionManager, new StayInfoStepListener());

        assertThat(step).isNotNull();
    }

    @Test
    void apiResponseReader_ShouldBeCreatedSuccessfully() {
        ItemReader<OpenApiResponse> reader = stayInfoBatch.apiResponseReader();

        assertThat(reader).isNotNull();
    }

    @Test
    void stayInfoProcessor_ShouldBeCreatedSuccessfully() {
        ItemProcessor<OpenApiResponse, List<StayInfo>> processor = stayInfoBatch.stayInfoProcessor();

        assertThat(processor).isNotNull();
    }

    @Test
    void stayInfoWriter_ShouldBeCreatedSuccessfully() {
        StayInfoWriter writer = stayInfoBatch.stayInfoWriter();

        assertThat(writer).isNotNull();
    }

    @Test
    void stayInfoStepListener_ShouldBeCreatedSuccessfully() {
        StayInfoStepListener listener = stayInfoBatch.stayInfoStepListener();

        assertThat(listener).isNotNull();
    }
}