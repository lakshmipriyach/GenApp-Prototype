package com.prototype.genapp.service;

import com.prototype.genapp.payload.MotorPolicy;

public interface MotorPolicyService {

	// Policy Inquiry

	MotorPolicy getMotorPolicy(int policyNumber, int customerNumber);

	// Policy Add

	public MotorPolicy addMotorPolicy(MotorPolicy motorPolicy);

	// Policy Delete
	
	void deleteMotorPolicy(int policyNumber, int customerNumber);

	// Policy Update

	MotorPolicy updateMotorPolicy(int policyNumber, int customerNumber, MotorPolicy motorPolicy);
	
}
