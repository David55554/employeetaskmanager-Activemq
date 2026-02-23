ActiveMQ Integration Plan

Goal

Make AI requests asynchronous using ActiveMQ so the API doesn't slow down.

What We Did

We added message queue processing. Users get instant responses with request IDs. Processing happens in the background.

Steps

### 1. Installed ActiveMQ
```
brew install apache-activemq
brew services start activemq
```

### 2. Added Dependency

Added Spring Boot ActiveMQ starter to pom.xml.

### 3. Configured Connection

Added to application.properties:
```
spring.activemq.broker-url=tcp://localhost:61616
spring.activemq.user=admin
spring.activemq.password=admin
spring.activemq.packages.trust-all=true
```

### 4. Created Message Model

Made LLMRequest class with request ID, prompt, employee ID, and type.

### 5. Built Producer

Created LLMProducer that sends messages to the "llm-requests" queue and returns request ID immediately.

### 6. Built Consumer

Created LLMConsumer with @JmsListener that automatically processes messages from the queue.

### 7. Created Async Endpoints

Made AsyncLLMController with endpoints that return HTTP 202 and queue requests for background processing.

### 8. Tested

Tested both endpoints. They return instantly and we see processing in console logs.

## How It Works

Call endpoint → Send to queue → Return request ID → Consumer processes → See results in console

## Benefits

- API stays fast
- Handles multiple requests
- Requests queue up instead of timing out

## What We Learned

We learned about message queues, the producer-consumer pattern, and asynchronous processing with Spring JMS.