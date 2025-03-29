package com.study.batch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.study.stay.entity.StayInfo;
import com.study.stay.repository.StayInfoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBatchTest
class StayInfoBatchTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private Job stayInfoJob;

    @MockitoBean
    private StayInfoRepository stayInfoRepository;

    @Test
    void testStayInfoJob() throws Exception {
        StayInfo mockStayInfo = new StayInfo();
        when(stayInfoRepository.save(any(StayInfo.class))).thenReturn(mockStayInfo);

        JobExecution jobExecution = jobLauncherTestUtils.getJobLauncher()
            .run(stayInfoJob, new JobParameters());

        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }
}
