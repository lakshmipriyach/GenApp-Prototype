package com.prototype.genapp.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prototype.genapp.entity.Customer;
import com.prototype.genapp.entity.Role;
import com.prototype.genapp.exception.GenAppAPIException;
import com.prototype.genapp.payload.Login;
import com.prototype.genapp.payload.LoginRequest;
import com.prototype.genapp.repository.CustomerRepository;
import com.prototype.genapp.repository.RoleRepository;
import com.prototype.genapp.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository customerRepository;
	private PasswordEncoder passwordEncoder;
	private RoleRepository roleRepository;

	public CustomerServiceImpl(CustomerRepository customerRepository, PasswordEncoder passwordEncoder,
			RoleRepository roleRepository) {
		this.customerRepository = customerRepository;
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
	}

	// Customer Registration

	@Override
	public Customer register(Customer customer) {

		// Check if a user with the same username already exists
		Customer existingUser = customerRepository.findByEmailaddress(customer.getEmailaddress());
		if (existingUser != null) {
			throw new GenAppAPIException(HttpStatus.BAD_REQUEST, "User exists!");
		}

		// Validate password length
		if (customer.getPassword().length() < 8) {
			throw new GenAppAPIException(HttpStatus.BAD_REQUEST, "Password must be at least 8 characters long");
		}
		String generatedUsername = generateUsername(customer.getFirstname(), customer.getLastname());
		Customer addUser = new Customer();

		addUser.setCustomernumber(customer.getCustomernumber());
		addUser.setFirstname(customer.getFirstname());
		addUser.setLastname(customer.getLastname());
		addUser.setDateofbirth(customer.getDateofbirth());
		addUser.setHousename(customer.getHousename());
		addUser.setHousenumber(customer.getHousenumber());
		addUser.setPostcode(customer.getPostcode());
		addUser.setPhonehome(customer.getPhonehome());
		addUser.setPhonemobile(customer.getPhonemobile());
		addUser.setEmailaddress(customer.getEmailaddress());
		addUser.setUsername(generatedUsername);
		addUser.setPassword(passwordEncoder.encode(customer.getPassword()));

		// Set the user's role (e.g., "ROLE_USER")
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(
				() -> new GenAppAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "ROLE_USER role not found."));
		roles.add(userRole);
		addUser.setRoles(roles);

		Customer user = customerRepository.save(addUser);

		return user;
	}

	private String generateUsername(String firstname, String lastname) {
		return firstname + lastname;
	}

	// Customer Login
	
	@Override
	public Login login(LoginRequest loginRequest) {
	    String loginIdentifier = login(loginRequest.getEmail(), loginRequest.getCustomernumber());
	    Customer customer;

	    if (loginIdentifier == null || loginIdentifier.trim().isEmpty()) {
	        throw new GenAppAPIException(HttpStatus.BAD_REQUEST, "Login identifier is empty");
	    }

	    if (loginIdentifier.contains("@")) {
	        // Find the user by email address
	        customer = customerRepository.findByEmailaddress(loginIdentifier);
	    } else {
	        // Try finding by customernumber
	        try {
	            int customernumber = Integer.parseInt(loginIdentifier);
	            customer = customerRepository.findByCustomernumber(customernumber);
	        } catch (NumberFormatException ex) {
	            throw new GenAppAPIException(HttpStatus.BAD_REQUEST, "Invalid login identifier: " + loginIdentifier);
	        }
	    }

	    if (customer == null) {
	        throw new GenAppAPIException(HttpStatus.BAD_REQUEST, "User does not exist!");
	    }

	    // Validate the password
	    if (!passwordEncoder.matches(loginRequest.getPassword(), customer.getPassword())) {
	        throw new GenAppAPIException(HttpStatus.UNAUTHORIZED, "Incorrect password");
	    }

	    Login loginResponse = new Login();
	    loginResponse.setCustomernumber(customer.getCustomernumber());
	    loginResponse.setUsername(customer.getUsername());
	    loginResponse.setRole(customer.getRoles());
	    loginResponse.setMessage("Login Successful");

	    return loginResponse;
	}

	private String login(String email, int customernumber) {
	    if (email != null && !email.isEmpty()) {
	        return email;
	    } else if (customernumber > 0) {
	        return String.valueOf(customernumber);
	    } else {
	        return null;
	    }
	}

	// Get Customer Details
	
	@Override
	public Customer getCustomer(int customernumber) {
	    Customer customer = customerRepository.findByCustomernumber(customernumber);
	    if (customer == null) {
	        throw new GenAppAPIException(HttpStatus.NOT_FOUND, "Customer not found");
	    }

	    // Populate customerDetails with the properties of the fetched customer
	    Customer customerDetails = new Customer();
	    customerDetails.setCustomernumber(customer.getCustomernumber());
	    customerDetails.setUsername(customer.getUsername());
	    customerDetails.setFirstname(customer.getFirstname());
	    customerDetails.setLastname(customer.getLastname());
	    customerDetails.setDateofbirth(customer.getDateofbirth());
	    customerDetails.setHousename(customer.getHousename());
	    customerDetails.setHousenumber(customer.getHousenumber());
	    customerDetails.setPostcode(customer.getPostcode());
	    customerDetails.setPhonehome(customer.getPhonehome());
	    customerDetails.setPhonemobile(customer.getPhonemobile());
	    customerDetails.setEmailaddress(customer.getEmailaddress());
	    customerDetails.setPassword("");
	    customerDetails.setRoles(customer.getRoles());

	    return customerDetails;
	}

	
	// Get All Customer Details
	
	@Override
	public List<Customer> getAllCustomers() {
		List<Customer> customers = customerRepository.findAll();
		return customers;
	}
	
	// Update Customer

	@Override
	public Customer updateCustomer(int customernumber, Customer updatedCustomer) {
		// Check if the customer exists
		Customer existingCustomer = customerRepository.findByCustomernumber(customernumber);
		if (existingCustomer == null) {
			throw new GenAppAPIException(HttpStatus.NOT_FOUND, "Customer not found!");
		}

		// Update customer details
		existingCustomer.setFirstname(updatedCustomer.getFirstname());
		existingCustomer.setLastname(updatedCustomer.getLastname());
		existingCustomer.setDateofbirth(updatedCustomer.getDateofbirth());
		existingCustomer.setHousename(updatedCustomer.getHousename());
		existingCustomer.setHousenumber(updatedCustomer.getHousenumber());
		existingCustomer.setPostcode(updatedCustomer.getPostcode());
		existingCustomer.setPhonehome(updatedCustomer.getPhonehome());
		existingCustomer.setPhonemobile(updatedCustomer.getPhonemobile());
		existingCustomer.setEmailaddress(updatedCustomer.getEmailaddress());
		existingCustomer.setUsername(generateUsername(updatedCustomer.getFirstname(), updatedCustomer.getLastname()));

		// Validate and update password if provided
		String newPassword = updatedCustomer.getPassword();
		if (newPassword != null && !newPassword.isEmpty()) {
			// Validate password length
			if (newPassword.length() < 8) {
				throw new GenAppAPIException(HttpStatus.BAD_REQUEST, "Password must be at least 8 characters long");
			}
			existingCustomer.setPassword(passwordEncoder.encode(newPassword));
		}

		customerRepository.save(existingCustomer);

		// Save the updated customer
		return existingCustomer;
	}

	// Delete Customer
	
	@Override
	public String deleteCustomer(int customernumber) {
		// Check if the customer exists
		Customer existingCustomer = customerRepository.findByCustomernumber(customernumber);
		if (existingCustomer != null) {
		existingCustomer.getRoles().clear();
		// Delete the customer
		customerRepository.delete(existingCustomer);
		
		return "User Deleted Successfully";
	} else {
		throw new GenAppAPIException(HttpStatus.NOT_FOUND, "User not found");
	}
	}


}
