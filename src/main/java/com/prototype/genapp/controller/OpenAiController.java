/*
 * package com.prototype.genapp.controller;
 * 
 * import org.apache.http.HttpEntity; import
 * org.apache.http.client.methods.CloseableHttpResponse; import
 * org.apache.http.client.methods.HttpPost; import
 * org.apache.http.entity.StringEntity; import
 * org.apache.http.impl.client.CloseableHttpClient; import
 * org.apache.http.impl.client.HttpClients; import
 * org.springframework.beans.factory.annotation.Value; import
 * org.springframework.web.bind.annotation.CrossOrigin; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RequestBody; import
 * org.springframework.web.bind.annotation.RestController;
 * 
 * import io.swagger.v3.oas.annotations.tags.Tag;
 * 
 * import java.io.IOException;
 * 
 * @RestController
 * 
 * @Tag(name = "OpenAi Search Api")
 * 
 * @CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
 * public class OpenAiController {
 * 
 * @Value("${openai.api.key}") private String apiKey;
 * 
 * @Value("${openai.api.url}") private String apiUrl;
 * 
 * @PostMapping("/chat") public String chatWithGPT(@RequestBody String prompt) {
 * try (CloseableHttpClient httpClient = HttpClients.createDefault()) { HttpPost
 * httpPost = new HttpPost(apiUrl); httpPost.addHeader("Authorization",
 * "Bearer " + apiKey); httpPost.addHeader("Content-Type", "application/json");
 * 
 * String requestBody = "{\"prompt\": \"" + prompt + "\", \"max_tokens\": 150}";
 * httpPost.setEntity(new StringEntity(requestBody));
 * 
 * try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
 * HttpEntity entity = response.getEntity(); if (entity != null) { return
 * entity.getContent().toString(); } } } catch (IOException e) {
 * e.printStackTrace(); } return null; } }
 */