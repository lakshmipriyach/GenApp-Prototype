package com.prototype.genapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
@Tag(name = "Google Maps Api")
public class GoogleMapsController {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${google.places.search.api.url}")
	private String placesSearchApiUrl;

	@Value("${google.api.key}")
	private String apiKey;

	@Value("${google.search.engineId}")
	private String searchEngineId;

	@GetMapping("/location")
	public String searchPlaces(@RequestParam String location) {
        String url = placesSearchApiUrl + "?key=" + apiKey + "&cx=" + searchEngineId + "&q=" + location;
        String response = restTemplate.getForObject(url, String.class);
		return response;

	}
}
