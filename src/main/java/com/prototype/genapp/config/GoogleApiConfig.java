package com.prototype.genapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.v1.Customsearch;
import com.google.api.services.customsearch.v1.CustomsearchRequestInitializer;

@Configuration
public class GoogleApiConfig {

    @Value("${google.api.key}")
    private String apiKey;

    @Value("${google.search.engineId}")
    private String searchEngineId;

    @Bean
    public Customsearch customsearch() {
        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        return new Customsearch.Builder(httpTransport, jsonFactory, null)
                .setApplicationName("Genapp")
                .setGoogleClientRequestInitializer(new CustomsearchRequestInitializer(apiKey))
                .build();
    }

    @Bean
    public String searchEngineId() {
        return searchEngineId;
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
