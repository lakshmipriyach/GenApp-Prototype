package com.prototype.genapp.service;

import java.util.List;

import com.prototype.genapp.entity.Customer;
import com.prototype.genapp.payload.Login;
import com.prototype.genapp.payload.LoginRequest;

public interface CustomerService {

	// Customer Registration
	
	Customer register(Customer customer);

	// Customer Login
	
	Login login(LoginRequest loginRequest);

	// Get Customer Details

	Customer getCustomer(int customernumber);
	
	// Get All Customer Details
	
	List<Customer> getAllCustomers();

	// Update Customer Details

	Customer updateCustomer(int customernumber, Customer updatedCustomer);

	// Delete Customer Details

	String deleteCustomer(int customernumber);

}
