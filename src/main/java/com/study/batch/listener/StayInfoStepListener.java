package com.study.batch.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;

public class StayInfoStepListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        ExecutionContext executionContext = stepExecution.getExecutionContext();
        int pageNo = executionContext.getInt("pageNo", 1);
        executionContext.put("pageNo", pageNo + 1);
        return ExitStatus.COMPLETED;
    }
}
