package com.prototype.genapp.controller;

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

import com.prototype.genapp.payload.MotorPolicy;
import com.prototype.genapp.service.MotorPolicyService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/motor")
@Tag(name = "MotorPolicy Rest Api's")
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
public class MotorPolicyController {

	private MotorPolicyService motorPolicyService;

	public MotorPolicyController(MotorPolicyService motorPolicyService) {
		this.motorPolicyService = motorPolicyService;
	}

	// Policy Inquiry
	@ApiResponse(responseCode = "200", description = "Http Status 200 OK")
	@SecurityRequirement(name = "basicScheme")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // Users with 'USER' or 'ADMIN' roles can access this method
	@GetMapping("/{policyNumber}/{customerNumber}")
	public ResponseEntity<MotorPolicy> getMotorPolicyByPolicyNumberAndCustomerNumber(@PathVariable int policyNumber,
			@PathVariable int customerNumber) {
		MotorPolicy motorPolicy = motorPolicyService.getMotorPolicy(policyNumber, customerNumber);
		return ResponseEntity.ok(motorPolicy);
	}

	// Policy Add
	@ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
	@SecurityRequirement(name = "basicScheme")
	@PostMapping("/addpolicy")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // Users with 'USER' or 'ADMIN' roles can access this method
	public ResponseEntity<MotorPolicy> addMotorPolicy(@RequestBody MotorPolicy motorPolicy) {
		MotorPolicy result = motorPolicyService.addMotorPolicy(motorPolicy);
		return new ResponseEntity<MotorPolicy>(result, HttpStatus.CREATED);
	}

	// Policy Delete
	@ApiResponse(responseCode = "204", description = "Http Status 204 NO CONTENT")
	@SecurityRequirement(name = "basicScheme")
	@DeleteMapping("/{policyNumber}/{customerNumber}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // Users with 'USER' or 'ADMIN' roles can access this method
	public ResponseEntity<String> deleteMotorPolicy(@PathVariable int customerNumber, @PathVariable int policyNumber) {
		motorPolicyService.deleteMotorPolicy(policyNumber, customerNumber);
		return ResponseEntity.noContent().build();
	}

	// Policy Update
	@ApiResponse(responseCode = "200", description = "Http Status 200 OK")
	@SecurityRequirement(name = "basicScheme")
	@PutMapping("/{policyNumber}/{customerNumber}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // Users with 'USER' or 'ADMIN' roles can access this method
	public ResponseEntity<MotorPolicy> updateMotorPolicy(@PathVariable int policyNumber,
			@PathVariable int customerNumber, @RequestBody MotorPolicy motorPolicy) {
		MotorPolicy updatedPolicy = motorPolicyService.updateMotorPolicy(policyNumber, customerNumber, motorPolicy);
		return ResponseEntity.ok(updatedPolicy);
	}

}
