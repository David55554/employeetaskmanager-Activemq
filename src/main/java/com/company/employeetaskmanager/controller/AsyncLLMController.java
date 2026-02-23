package com.company.employeetaskmanager.controller;

import com.company.employeetaskmanager.model.Employee;
import com.company.employeetaskmanager.queue.LLMProducer;
import com.company.employeetaskmanager.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/async-llm")
public class AsyncLLMController {

    @Autowired
    private LLMProducer llmProducer;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/generate")
    public ResponseEntity<AsyncResponse> generateAsync(@RequestBody PromptRequest request) {
        String requestId = llmProducer.sendToQueue(request.getPrompt(), null, "CUSTOM");

        AsyncResponse response = new AsyncResponse(
                requestId,
                "Request queued successfully",
                "Processing asynchronously"
        );

        return ResponseEntity.accepted().body(response);
    }

    @PostMapping("/tax-info/{id}")
    public ResponseEntity<AsyncResponse> getTaxInfoAsync(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);

        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        String prompt = "For an employee earning $" + employee.getSalary() +
                " annually in the United States:\n" +
                "1) Estimate their federal tax bracket\n" +
                "2) Approximate total tax percentage\n" +
                "3) Estimated take-home pay\n" +
                "4) Tax planning tips";

        String requestId = llmProducer.sendToQueue(prompt, id, "TAX_INFO");

        AsyncResponse response = new AsyncResponse(
                requestId,
                "Tax information request queued",
                "Check console logs for results"
        );

        return ResponseEntity.accepted().body(response);
    }

    static class PromptRequest {
        private String prompt;

        public String getPrompt() {
            return prompt;
        }

        public void setPrompt(String prompt) {
            this.prompt = prompt;
        }
    }

    static class AsyncResponse {
        private String requestId;
        private String status;
        private String message;

        public AsyncResponse(String requestId, String status, String message) {
            this.requestId = requestId;
            this.status = status;
            this.message = message;
        }

        public String getRequestId() {
            return requestId;
        }

        public String getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
    }
}