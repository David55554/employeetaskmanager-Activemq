package com.company.employeetaskmanager.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class LLMProducer {

    private static final String QUEUE_NAME = "llm-requests";

    @Autowired
    private JmsTemplate jmsTemplate;

    public String sendToQueue(String prompt, Long employeeId, String requestType) {
        String requestId = UUID.randomUUID().toString();
        LLMRequest request = new LLMRequest(requestId, prompt, employeeId, requestType);

        jmsTemplate.convertAndSend(QUEUE_NAME, request);

        return requestId;
    }
}