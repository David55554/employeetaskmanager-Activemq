package com.company.employeetaskmanager.queue;

import com.company.employeetaskmanager.llm.LLMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class LLMConsumer {

    @Autowired
    private LLMService llmService;

    @JmsListener(destination = "llm-requests")
    public void processLLMRequest(LLMRequest request) {
        System.out.println("Processing LLM request: " + request.getRequestId());
        System.out.println("Request type: " + request.getRequestType());
        System.out.println("Employee ID: " + request.getEmployeeId());

        String response = llmService.generateText(request.getPrompt());

        System.out.println("LLM Response generated for request: " + request.getRequestId());
        System.out.println("Response: " + response);
    }
}