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

import com.prototype.genapp.entity.Customer;
import com.prototype.genapp.payload.Login;
import com.prototype.genapp.payload.LoginRequest;
import com.prototype.genapp.service.CustomerService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/customer")
@Tag(name = "Customer Rest Api's")
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
public class CustomerController {

	private CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	// Customer Regestration

	@ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
	@PostMapping("/register")
	public ResponseEntity<Customer> register(@RequestBody Customer customer) {
		Customer result = customerService.register(customer);
		return new ResponseEntity<Customer>(result, HttpStatus.CREATED);

	}

	// Customer Login

	@ApiResponse(responseCode = "200", description = "Http Status 200 OK")
	@PostMapping("/login")
	public ResponseEntity<Login> login(@RequestBody LoginRequest loginRequest) {
		Login login = customerService.login(loginRequest);
		return ResponseEntity.ok(login);

	}

	// Get Customer Details

	@ApiResponse(responseCode = "200", description = "Http Status 200 OK")
	@SecurityRequirement(name = "basicScheme")
	@GetMapping("/{customernumber}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // Users with 'USER' or 'ADMIN' roles can access this method
	public ResponseEntity<Customer> getCustomer(@PathVariable int customernumber) {
		Customer customer = customerService.getCustomer(customernumber);
		return ResponseEntity.ok(customer);

	}

	// Get All Customer Details

	@ApiResponse(responseCode = "200", description = "Http Status 200 OK")
	@SecurityRequirement(name = "basicScheme")
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')") // Users 'ADMIN' roles can access this method
	public ResponseEntity<List<Customer>> getAllCustomers() {
		List<Customer> customers = customerService.getAllCustomers();
		return ResponseEntity.ok(customers);
	}

	// Update Customer Details

	@ApiResponse(responseCode = "200", description = "Http Status 200 OK")
	@SecurityRequirement(name = "basicScheme")
	@PutMapping("/update/{customernumber}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // Users with 'USER' or 'ADMIN' roles can access this method
	public ResponseEntity<Customer> updateCustomer(@PathVariable int customernumber,
			@RequestBody Customer updatedCustomer) {
		Customer updatedCustomerResult = customerService.updateCustomer(customernumber, updatedCustomer);
		return ResponseEntity.ok(updatedCustomerResult);
	}

	// Delete Customer Details

	@ApiResponse(responseCode = "204", description = "Http Status 204 NO_CONTENT")
	@SecurityRequirement(name = "basicScheme")
	@DeleteMapping("/delete/{customernumber}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // Users with 'USER' or 'ADMIN' roles can access this method
	public ResponseEntity<String> deleteCustomer(@PathVariable int customernumber) {
		customerService.deleteCustomer(customernumber);
		return ResponseEntity.noContent().build();
	}
}
