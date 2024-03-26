package com.prototype.genapp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.prototype.genapp.entity.Customer;
import com.prototype.genapp.entity.Motor;
import com.prototype.genapp.entity.Policy;
import com.prototype.genapp.exception.GenAppAPIException;
import com.prototype.genapp.payload.MotorPolicy;
import com.prototype.genapp.repository.CustomerRepository;
import com.prototype.genapp.repository.MotorRepository;
import com.prototype.genapp.repository.PolicyRepository;
import com.prototype.genapp.service.MotorPolicyService;

@Service
public class MotorPolicyServiceImpl implements MotorPolicyService {

	private CustomerRepository customerRepository;
	private MotorRepository motorRepository;
	private PolicyRepository policyRepository;

	public MotorPolicyServiceImpl(CustomerRepository customerRepository, MotorRepository motorRepository,
			PolicyRepository policyRepository) {
		this.customerRepository = customerRepository;
		this.motorRepository = motorRepository;
		this.policyRepository = policyRepository;
	}

	// Policy Inquiry
	@Override
	public MotorPolicy getMotorPolicy(int policyNumber, int customerNumber) {
		// Find the policy by policy number and customer number
		Policy policy = policyRepository.findByPolicynumberAndCustomernumber(policyNumber, customerNumber);
		if (policy == null) {
			throw new GenAppAPIException(HttpStatus.NOT_FOUND,
					"Policy not found for the provided policy number and customer number");
		}

		// Find the motor details by policy number
		Motor motor = motorRepository.findByPolicynumber(policyNumber);
		if (motor == null) {
			throw new GenAppAPIException(HttpStatus.NOT_FOUND,
					"Motor details not found for the provided policy number");
		}

		// Create a MotorPolicy object combining policy and motor details
		MotorPolicy motorPolicy = new MotorPolicy();
		motorPolicy.setPolicynumber(policy.getPolicynumber());
		motorPolicy.setCustomernumber(policy.getCustomernumber());
		motorPolicy.setIssuedate(policy.getIssuedate());
		motorPolicy.setExpirydate(policy.getExpirydate());
		motorPolicy.setCarmake(motor.getMake());
		motorPolicy.setCarmodel(motor.getModel());
		motorPolicy.setCarvalue(motor.getValue());
		motorPolicy.setRegistration(motor.getRegnumber());
		motorPolicy.setCarcolour(motor.getColour());
		motorPolicy.setCc(motor.getCc());
		motorPolicy.setManufacturedate(motor.getYearofmanufacture());
		motorPolicy.setPolicypremium(motor.getPremium());
		motorPolicy.setNoofaccidents(motor.getAccidents());

		return motorPolicy;
	}
	
	@Override
	public List<MotorPolicy> getAllMotorPoliciesByCustomerNumber(int customerNumber) {
	    List<MotorPolicy> allMotorPolicies = new ArrayList<>();

	    // Retrieve all policies for the given customer number from the repository
	    List<Policy> policies = policyRepository.findByCustomernumber(customerNumber);

	    // Iterate through each policy and retrieve its associated motor details
	    for (Policy policy : policies) {
	        // Find the motor details by policy number
	        Motor motor = motorRepository.findByPolicynumber(policy.getPolicynumber());
	        if (motor != null) {
	            // Create a MotorPolicy object combining policy and motor details
	            MotorPolicy motorPolicy = new MotorPolicy();
	            motorPolicy.setPolicynumber(policy.getPolicynumber());
	            motorPolicy.setCustomernumber(policy.getCustomernumber());
	            motorPolicy.setIssuedate(policy.getIssuedate());
	            motorPolicy.setExpirydate(policy.getExpirydate());
	            motorPolicy.setCarmake(motor.getMake());
	            motorPolicy.setCarmodel(motor.getModel());
	            motorPolicy.setCarvalue(motor.getValue());
	            motorPolicy.setRegistration(motor.getRegnumber());
	            motorPolicy.setCarcolour(motor.getColour());
	            motorPolicy.setCc(motor.getCc());
	            motorPolicy.setManufacturedate(motor.getYearofmanufacture());
	            motorPolicy.setPolicypremium(motor.getPremium());
	            motorPolicy.setNoofaccidents(motor.getAccidents());

	            allMotorPolicies.add(motorPolicy);
	        }
	    }

	    return allMotorPolicies;
	}



	// Policy Add

	@Override
	public MotorPolicy addMotorPolicy(MotorPolicy motorPolicy) {

		// Check if the customer exists
	    Customer customer = customerRepository.findByCustomernumber(motorPolicy.getCustomernumber());
	    if (customer == null) {
	        throw new GenAppAPIException(HttpStatus.BAD_REQUEST, "Customer does not exist!");
	    }

	    // Check if a policy with the same issue date and expiry date exists for the same customer
	    Policy existingPolicy = policyRepository.findByCustomernumberAndIssuedateAndExpirydate(
	            motorPolicy.getCustomernumber(), motorPolicy.getIssuedate(), motorPolicy.getExpirydate());

	    // If a policy exists for the same customer with the same details, throw an exception
	    if (existingPolicy != null) {
	        throw new GenAppAPIException(HttpStatus.BAD_REQUEST, "Policy already exists for this customer!");
	    }

		// Create and save policy
		Policy policy = new Policy();
		policy.setCustomernumber(customer.getCustomernumber());
		policy.setIssuedate(motorPolicy.getIssuedate());
		policy.setExpirydate(motorPolicy.getExpirydate());
		policy.setPolicytype("");
		policy.setLastchanged(motorPolicy.getIssuedate());
		policy.setBrokerid(0);
		policy.setBrokersreference("");
		policy.setPayment(0);
		policy.setCommission(0);

		Policy savedPolicy = policyRepository.save(policy);

		// Check if motor details exist for the same policy
	    Motor existingMotor = motorRepository.findByPolicynumber(savedPolicy.getPolicynumber());

	    // If motor details exist for the same policy, throw an exception
	    if (existingMotor != null) {
	        throw new GenAppAPIException(HttpStatus.BAD_REQUEST, "Motor details already exist for the policy!");
	    }


		// Create and save motor details
		Motor motor = new Motor();
		motor.setPolicynumber(savedPolicy.getPolicynumber());
		motor.setMake(motorPolicy.getCarmake());
		motor.setModel(motorPolicy.getCarmodel());
		motor.setValue(motorPolicy.getCarvalue());
		motor.setRegnumber(motorPolicy.getRegistration());
		motor.setColour(motorPolicy.getCarcolour());
		motor.setCc(motorPolicy.getCc());
		motor.setYearofmanufacture(motorPolicy.getManufacturedate());
		motor.setPremium(motorPolicy.getPolicypremium());
		motor.setAccidents(motorPolicy.getNoofaccidents());

		motorRepository.save(motor);

		// Update the policynumber field of the MotorPolicy object before returning it
		motorPolicy.setPolicynumber(savedPolicy.getPolicynumber());

		return motorPolicy;

	}

	// Policy Delete
	@Override
	public void deleteMotorPolicy(int policyNumber, int customerNumber) {
		// Find all policies for the customer
		List<Policy> policies = policyRepository.findByCustomernumber(customerNumber);

		// Filter the list of policies to find the one with the specified policy number
		Optional<Policy> optionalPolicy = policies.stream().filter(policy -> policy.getPolicynumber() == policyNumber)
				.findFirst();

		// Check if the policy exists
		if (optionalPolicy.isPresent()) {
			Policy existingPolicy = optionalPolicy.get();

			// Delete the policy
			policyRepository.delete(existingPolicy);

			// Find and delete associated motor details
			Motor existingMotor = motorRepository.findByPolicynumber(existingPolicy.getPolicynumber());
			if (existingMotor != null) {
				motorRepository.delete(existingMotor);
			}
		} else {
			// Policy not found for the given customer number and policy number
			throw new GenAppAPIException(HttpStatus.NOT_FOUND,
					"Policy not found for the given customer number and policy number");
		}
	}

	// Policy Update

	@Override
	public MotorPolicy updateMotorPolicy(int policyNumber, int customerNumber, MotorPolicy motorPolicy) {
		// Retrieve the existing policy
		Policy existingPolicy = policyRepository.findByPolicynumberAndCustomernumber(policyNumber, customerNumber);

		if (existingPolicy == null) {
			throw new GenAppAPIException(HttpStatus.NOT_FOUND, "Policy not found");
		}

		// Update the policy details
		existingPolicy.setIssuedate(motorPolicy.getIssuedate());
		existingPolicy.setExpirydate(motorPolicy.getExpirydate());
		existingPolicy.setLastchanged(motorPolicy.getIssuedate()); // Assuming you want to update the last changed
																	// date/time

		// Save the updated policy
		Policy updatedPolicy = policyRepository.save(existingPolicy);

		// Retrieve the existing motor details associated with the policy
		Motor existingMotor = motorRepository.findByPolicynumber(updatedPolicy.getPolicynumber());
		if (existingMotor != null) {
			// Update the motor details
			existingMotor.setMake(motorPolicy.getCarmake());
			existingMotor.setModel(motorPolicy.getCarmodel());
			existingMotor.setValue(motorPolicy.getCarvalue());
			existingMotor.setRegnumber(motorPolicy.getRegistration());
			existingMotor.setColour(motorPolicy.getCarcolour());
			existingMotor.setCc(motorPolicy.getCc());
			existingMotor.setYearofmanufacture(motorPolicy.getManufacturedate());
			existingMotor.setPremium(motorPolicy.getPolicypremium());
			existingMotor.setAccidents(motorPolicy.getNoofaccidents());

			// Save the updated motor details
			motorRepository.save(existingMotor);
		}

		return motorPolicy;
	}

}
