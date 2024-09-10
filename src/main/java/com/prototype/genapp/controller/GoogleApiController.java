package com.prototype.genapp.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.customsearch.v1.Customsearch;
import com.google.api.services.customsearch.v1.model.Result;
import com.google.api.services.customsearch.v1.model.Search;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Google Search Api")
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
public class GoogleApiController {

    private static final Logger logger = LoggerFactory.getLogger(GoogleApiController.class);

    @Autowired
    private Customsearch customsearch;

    @Value("${google.api.key}")
    private String apiKey;

    @Value("${google.search.engineId}")
    private String searchEngineId;

    @GetMapping("/search")
    public ResponseEntity<String> search(@RequestParam("query") String query) {
        try {
            Customsearch.Cse.List list = customsearch.cse().list();
            list.setKey(apiKey);
            list.setQ(query); // Set the search query
            list.setCx(searchEngineId); // Set the search engine ID
            Search results = list.execute();
            
            // Extract snippets and concatenate into a single paragraph
            StringBuilder snippetsParagraph = new StringBuilder();
            if (results.getItems() != null) {
                for (Result result : results.getItems()) {
                    if (result.getSnippet() != null) {
                        snippetsParagraph.append(result.getSnippet()).append(" ");
                    }
                }
            }

            return ResponseEntity.ok(snippetsParagraph.toString());
        } catch (GoogleJsonResponseException e) {
            logger.error("Google API error: {}", e.getDetails());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (IOException e) {
            logger.error("IO error occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
