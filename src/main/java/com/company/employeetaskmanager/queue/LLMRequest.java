package com.company.employeetaskmanager.queue;

import java.io.Serializable;

public class LLMRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String requestId;
    private String prompt;
    private Long employeeId;
    private String requestType;

    public LLMRequest() {
    }

    public LLMRequest(String requestId, String prompt, Long employeeId, String requestType) {
        this.requestId = requestId;
        this.prompt = prompt;
        this.employeeId = employeeId;
        this.requestType = requestType;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
}