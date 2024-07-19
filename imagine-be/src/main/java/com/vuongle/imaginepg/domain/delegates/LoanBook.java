package com.vuongle.imaginepg.domain.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class LoanBook implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        System.out.println(delegateExecution.getVariable("available"));
    }
}
