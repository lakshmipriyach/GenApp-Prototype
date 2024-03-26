package com.prototype.genapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prototype.genapp.entity.Claim;
import com.prototype.genapp.service.ClaimService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/claims")
@Tag(name = "Claims Rest Api's")
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
public class ClaimController {

	private ClaimService claimService;

	public ClaimController(ClaimService claimService) {
		this.claimService = claimService;
	}

	@ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
	@SecurityRequirement(name = "basicScheme")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // Users with 'USER' or 'ADMIN' roles can access this method
	@PostMapping("/addclaim")
	public ResponseEntity<Claim> addClaim(@RequestBody Claim claim) {
		Claim addedClaim = claimService.addClaim(claim);
		return new ResponseEntity<>(addedClaim, HttpStatus.CREATED);
	}

	@ApiResponse(responseCode = "200", description = "Http Status 200 OK")
	@SecurityRequirement(name = "basicScheme")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // Users with 'USER' or 'ADMIN' roles can access this method
	@GetMapping("/{claimNumber}")
	public ResponseEntity<Claim> getClaim(@PathVariable int claimNumber) {
		Claim claim = claimService.getClaim(claimNumber);
		return new ResponseEntity<>(claim, HttpStatus.OK);
	}

	@ApiResponse(responseCode = "200", description = "Http Status 200 OK")
	@SecurityRequirement(name = "basicScheme")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // Users with 'USER' or 'ADMIN' roles can access this method
	@GetMapping
	public ResponseEntity<List<Claim>> getAllClaims() {
		List<Claim> claims = claimService.getAllClaims();
		return new ResponseEntity<>(claims, HttpStatus.OK);
	}

	@ApiResponse(responseCode = "200", description = "Http Status 200 OK")
	@SecurityRequirement(name = "basicScheme")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // Users with 'USER' or 'ADMIN' roles can access this method
	@PutMapping("/{claimNumber}")
	public ResponseEntity<Claim> updateClaim(@PathVariable int claimNumber, @RequestBody Claim claim) {
		Claim updatedClaimResult = claimService.updateClaim(claimNumber, claim);
		return ResponseEntity.ok(updatedClaimResult);

	}

	@ApiResponse(responseCode = "204", description = "Http Status 204 NO CONTENT")
	@SecurityRequirement(name = "basicScheme")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // Users with 'USER' or 'ADMIN' roles can access this method
	@DeleteMapping("/{claimNumber}")
	public ResponseEntity<String> deleteClaim(@PathVariable int claimNumber) {
		claimService.deleteClaim(claimNumber);
		return ResponseEntity.noContent().build();

	}
}