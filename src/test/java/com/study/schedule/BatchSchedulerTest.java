package com.study.schedule;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;

@ExtendWith(MockitoExtension.class)
class BatchSchedulerTest {

    private static final Logger log = LoggerFactory.getLogger(BatchSchedulerTest.class);

    @Mock
    private JobLauncher jobLauncher;

    @Mock
    private Job stayInfoJob;

    @InjectMocks
    private BatchScheduler batchScheduler;

    private JobParameters jobParameters;

    @BeforeEach
    void setUp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String date = dateFormat.format(new Date());

        jobParameters = new JobParametersBuilder()
            .addString("date", date)
            .toJobParameters();
    }

    @Test
    void runJob_ShouldExecuteBatchJobSuccessfully() throws Exception {
        JobExecution mockJobExecution = mock(JobExecution.class);
        when(jobLauncher.run(eq(stayInfoJob), any(JobParameters.class))).thenReturn(mockJobExecution);

        batchScheduler.runJob();

        verify(jobLauncher, times(1)).run(eq(stayInfoJob), any(JobParameters.class));
        log.info("Batch job execution verified successfully.");
    }

    @Test
    void runJob_ShouldHandleExceptionGracefully() throws Exception {
        doThrow(new RuntimeException("Job failed")).when(jobLauncher).run(any(Job.class), any(JobParameters.class));

        batchScheduler.runJob(); // 예외가 발생해도 테스트가 중단되지 않음

        verify(jobLauncher, times(1)).run(eq(stayInfoJob), any(JobParameters.class));
        log.info("Exception handling verified successfully.");
    }
}