package com.study.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job stayInfoJob;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void runJob() {
        try {
            log.info("Batch job started.");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            String date = dateFormat.format(new Date());

            JobParameters jobParameters = new JobParametersBuilder()
                .addString("date", date)
                .toJobParameters();

            log.info("Batch job is start.");
            jobLauncher.run(stayInfoJob, jobParameters);

            log.info("Batch job completed.");
        } catch (Exception e) {
            log.error("Batch job failed.", e);
        }
    }
}