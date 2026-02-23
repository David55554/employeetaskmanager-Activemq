package com.company.employeetaskmanager.llm;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GeminiLLMService implements LLMService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final HttpClient httpClient;
    private final Gson gson;

    public GeminiLLMService() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    @Override
    public String generateText(String prompt) {
        try {
            JsonObject requestBody = buildRequestBody(prompt);
            String jsonRequest = gson.toJson(requestBody);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl + "?key=" + apiKey))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return parseResponse(response.body());
            } else {
                return "Error calling LLM API: " + response.statusCode();
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private JsonObject buildRequestBody(String prompt) {
        JsonObject requestBody = new JsonObject();
        JsonArray contents = new JsonArray();
        JsonObject content = new JsonObject();
        JsonArray parts = new JsonArray();
        JsonObject part = new JsonObject();

        part.addProperty("text", prompt);
        parts.add(part);
        content.add("parts", parts);
        contents.add(content);
        requestBody.add("contents", contents);

        return requestBody;
    }

    private String parseResponse(String responseBody) {
        JsonObject jsonResponse = gson.fromJson(responseBody, JsonObject.class);
        JsonArray candidates = jsonResponse.getAsJsonArray("candidates");

        if (candidates != null && candidates.size() > 0) {
            JsonObject firstCandidate = candidates.get(0).getAsJsonObject();
            JsonObject content = firstCandidate.getAsJsonObject("content");
            JsonArray parts = content.getAsJsonArray("parts");

            if (parts != null && parts.size() > 0) {
                JsonObject firstPart = parts.get(0).getAsJsonObject();
                return firstPart.get("text").getAsString();
            }
        }

        return "No response generated";
    }
}