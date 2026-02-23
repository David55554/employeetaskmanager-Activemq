Employee Task Manager - ActiveMQ Integration

Spring Boot REST API with asynchronous message queue processing.

What This Does

This app manages employees and tasks. We added ActiveMQ to make AI requests asynchronous so the API stays fast.

## Running the App
```
./mvnw spring-boot:run
```

Open http://localhost:8080/swagger-ui.html

## ActiveMQ Setup

Install ActiveMQ:
```
brew install apache-activemq
brew services start activemq
```

Check it's running: http://localhost:8161/admin (admin/admin)

## Async Endpoints

**POST /api/async-llm/generate** - Queue custom prompt

**POST /api/async-llm/tax-info/{id}** - Queue tax calculation

These return immediately with a request ID. Check console logs for results.

## How It Works

1. Call async endpoint
2. Get instant response with request ID (HTTP 202)
3. Request goes to ActiveMQ queue
4. Background consumer processes it
5. Results appear in console

## Technologies

- Spring Boot
- ActiveMQ
- Google Gemini API
- H2 Database


## Claude AI Assistance

We used Claude AI to help with this project.

**What Claude helped with:**

Claude showed us how to use @JmsListener to process messages from ActiveMQ automatically. We didn't understand how the consumer worked without manually polling the queue. You can see this in **LLMConsumer.java** where we added comments explaining what Claude taught us.

Claude helped us understand how to send messages using JmsTemplate and the producer pattern. We learned about generating unique request IDs with UUID. This is explained in the comments at the top of **LLMProducer.java**.

Claude explained why async processing is better than making users wait. We learned the difference between HTTP 202 and 200 status codes for async responses. We added comments in **AsyncLLMController.java** to document this.

**Code files where Claude helped:**
- `LLMConsumer.java` - Understanding @JmsListener and automatic message processing
- `LLMProducer.java` - Learning JmsTemplate and the producer pattern
- `AsyncLLMController.java` - Understanding async responses and HTTP 202 status

**What we did ourselves:**

We set up the project, configured ActiveMQ, tested all endpoints in Swagger, created Jira tasks, and packaged everything for submission.