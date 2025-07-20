package com.taehoonkang.moodmate.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

@Service
public class ChatService {
    private final RestTemplate restTemplate = new RestTemplate();
    // restTemplate is a tool that allows HTTP requests to be sent from Spring.

    public String getRandomZenQuote() {

        // Parsing will be done in the order: json -> String -> json
        // Reason:
        // JSONArray, JSONObject are classes that allow JSON arrays and objects to be handled in Java.
        // restTemplate is a class that receives JSON in DTO or String form.
        // In other words, restTemplate cannot directly handle JSON arrays and objects in Java.

        String url = "https://zenquotes.io/api/random";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        // Function to send a Get request to an external server: restTemplate.getForEntity(request_URL, response_Type);
        // ResponseEntity is used when receiving a response from the server (body+header+status code)
        // The received value is stored in the response variable

        JSONArray arr = new JSONArray(response.getBody());
        // Convert JSON string → JSON object
        JSONObject obj = arr.getJSONObject(0);
        String quote = obj.getString("q");
        String author = obj.getString("a");
        return quote + " - " + author;
    }

    public String askChatGPT(String prompt, String apiKey) {
        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        // HttpEntity is used when creating a request to send from the client to the server (body+header)

        headers.setContentType(MediaType.APPLICATION_JSON);
        // The meaning is that the body content is json
        headers.setBearerAuth(apiKey);
        // apikey for authentication

        JSONObject body = new JSONObject();
        body.put("model", "gpt-3.5-turbo");
        JSONArray messages = new JSONArray();
        JSONObject userMsg = new JSONObject();
        userMsg.put("role", "user");
        userMsg.put("content", prompt);
        messages.put(userMsg);
        body.put("messages", messages);
        // Create the json structure of the body using arrays and objects

        HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);
        // The body is json and HttpEntity<String> is String, so toString() operation is needed
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        // Function to send a Post request to an external server: restTemplate.postForEntity(request_URL, request_object, response_Type);
        // ResponseEntity is used when receiving a response from the server (body+header+status code)
        // The received value is stored in the response variable

        JSONObject respObj = new JSONObject(response.getBody());
        // Convert JSON string → JSON object
        JSONArray choices = respObj.getJSONArray("choices");
        String content = choices.getJSONObject(0).getJSONObject("message").getString("content");
        return content.trim();
    }
} 