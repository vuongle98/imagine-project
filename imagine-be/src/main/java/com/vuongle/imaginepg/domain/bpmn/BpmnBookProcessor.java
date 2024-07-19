package com.vuongle.imaginepg.domain.bpmn;

import com.vuongle.imaginepg.application.commands.CreateOrderCommand;
import com.vuongle.imaginepg.application.commands.LoanBookCommand;
import com.vuongle.imaginepg.application.dto.store.BookOrderDto;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BpmnBookProcessor {

    private final RuntimeService runtimeService;
    private final TaskService taskService;

    public BpmnBookProcessor(RuntimeService runtimeService, TaskService taskService) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
    }

    public ProcessInstance loanBook(CreateOrderCommand command) {
        VariableMap variables = Variables.createVariables();

        variables.put("bookLoanInfo", command.getBookOrderInfo());
        return runtimeService.createProcessInstanceByKey("book-loan-process")
                .setVariables(variables).execute();
    }

    public void returnBook(LoanBookCommand command) {
        VariableMap variables = Variables.createVariables();

        runtimeService.createProcessInstanceByKey("book-return-process")
                .setVariables(variables).execute();
    }

    public void handleUserTask(String processInstanceId,
                               String taskKey, String username,
                               VariableMap variables) {
        String taskId = taskService.createTaskQuery().processInstanceId(processInstanceId)
                .taskDefinitionKey(taskKey)
                .active().singleResult().getId();

        taskService.claim(taskId, username);
        taskService.complete(taskId, variables);
    }

}
