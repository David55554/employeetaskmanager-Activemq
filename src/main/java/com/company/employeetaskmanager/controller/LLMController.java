package com.company.employeetaskmanager.controller;

import com.company.employeetaskmanager.llm.LLMService;
import com.company.employeetaskmanager.model.Employee;
import com.company.employeetaskmanager.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/llm")
public class LLMController {

    @Autowired
    private LLMService llmService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateText(@RequestBody PromptRequest request) {
        String generatedText = llmService.generateText(request.getPrompt());
        return ResponseEntity.ok(generatedText);
    }

    @GetMapping("/employee-analysis/{id}")
    public ResponseEntity<String> analyzeEmployee(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);

        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        String prompt = "Analyze this employee profile and provide insights:\n" +
                "Name: " + employee.getName() + "\n" +
                "Department: " + employee.getDepartment() + "\n" +
                "Salary: $" + employee.getSalary() + "\n\n" +
                "Provide: 1) Professional strengths based on their role, " +
                "2) Potential career growth opportunities, " +
                "3) Skill development recommendations.";

        String analysis = llmService.generateText(prompt);
        return ResponseEntity.ok(analysis);
    }

    @GetMapping("/tax-info/{id}")
    public ResponseEntity<String> getTaxInformation(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);

        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        String prompt = "For an employee earning $" + employee.getSalary() +
                " annually in the United States:\n" +
                "1) Estimate their federal tax bracket\n" +
                "2) Approximate total tax percentage including federal, state, and FICA\n" +
                "3) Estimated take-home pay after taxes\n" +
                "4) Tax planning tips for this income level\n" +
                "Keep the response practical and concise.";

        String taxInfo = llmService.generateText(prompt);
        return ResponseEntity.ok(taxInfo);
    }

    @GetMapping("/location-benefits/{id}")
    public ResponseEntity<String> getLocationBasedBenefits(@PathVariable Long id, @RequestParam String location) {
        Employee employee = employeeService.getEmployeeById(id);

        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        String prompt = "For an employee in " + location + " with:\n" +
                "Salary: $" + employee.getSalary() + "\n" +
                "Department: " + employee.getDepartment() + "\n\n" +
                "Provide information about:\n" +
                "1) Cost of living in " + location + "\n" +
                "2) State and local tax rates\n" +
                "3) How their salary compares to the local market\n" +
                "4) Relocation benefits or adjustments that might be appropriate";

        String locationInfo = llmService.generateText(prompt);
        return ResponseEntity.ok(locationInfo);
    }

    @GetMapping("/salary-comparison/{id}")
    public ResponseEntity<String> compareSalary(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);

        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        String prompt = "For a " + employee.getDepartment() + " professional earning $" +
                employee.getSalary() + " in the United States:\n" +
                "1) Is this salary competitive for the role?\n" +
                "2) What is the typical salary range for this position?\n" +
                "3) Recommendations for salary negotiation or raises";

        String comparison = llmService.generateText(prompt);
        return ResponseEntity.ok(comparison);
    }

    @GetMapping("/task-tips/{id}")
    public ResponseEntity<String> generateTaskTips(@PathVariable Long id) {
        String prompt = "Provide 3 practical productivity tips for completing work tasks efficiently. " +
                "Focus on time management and priority setting.";
        String tips = llmService.generateText(prompt);
        return ResponseEntity.ok(tips);
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
}