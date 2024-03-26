package com.prototype.genapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.prototype.genapp.entity.Claim;
import com.prototype.genapp.entity.Policy;
import com.prototype.genapp.exception.GenAppAPIException;
import com.prototype.genapp.repository.ClaimRepository;
import com.prototype.genapp.repository.PolicyRepository;
import com.prototype.genapp.service.ClaimService;

@Service
public class ClaimServiceImpl implements ClaimService {

	private ClaimRepository claimRepository;
	private PolicyRepository policyRepository;

	public ClaimServiceImpl(ClaimRepository claimsRepository, PolicyRepository policyRepository) {
		this.claimRepository = claimsRepository;
		this.policyRepository = policyRepository;
	}

	@Override
	public Claim addClaim(Claim claim) {
		// Check if the policy exists
		Policy policy = policyRepository.findByPolicynumber(claim.getPolicynumber());
		if (policy == null) {
			throw new GenAppAPIException(HttpStatus.NOT_FOUND, "Policy not found for the provided policy number");
		}

		// Check if claims with the same details already exist for the provided policy
		// number
		List<Claim> existingClaims = claimRepository
				.findByPolicynumberAndClaimdateAndPaidAndValueAndCauseAndObservations(claim.getPolicynumber(),
						claim.getClaimdate(), claim.getPaid(), claim.getValue(), claim.getCause(),
						claim.getObservations());

		if (!existingClaims.isEmpty()) {
			throw new GenAppAPIException(HttpStatus.BAD_REQUEST,
					"A claim with the same details already exists for this policy number");
		}

		// Create and save the new claim
		Claim newClaim = new Claim();
		newClaim.setPolicynumber(policy.getPolicynumber());
		newClaim.setClaimdate(claim.getClaimdate());
		newClaim.setPaid(claim.getPaid());
		newClaim.setValue(claim.getValue());
		newClaim.setCause(claim.getCause());
		newClaim.setObservations(claim.getObservations());

		claimRepository.save(newClaim);

		return newClaim;
	}

	@Override
	public Claim getClaim(int claimNumber) {

		Claim claim = claimRepository.findByClaimnumber(claimNumber);
		if (claim == null) {
			throw new GenAppAPIException(HttpStatus.NOT_FOUND, "Claim Number not found");
		}

		Claim getClaim = new Claim();
		getClaim.setClaimnumber(claim.getClaimnumber());
		getClaim.setPolicynumber(claim.getPolicynumber());
		getClaim.setClaimdate(claim.getClaimdate());
		getClaim.setPaid(claim.getPaid());
		getClaim.setValue(claim.getValue());
		getClaim.setCause(claim.getCause());
		getClaim.setObservations(claim.getObservations());

		return getClaim;
	}

	@Override
	public List<Claim> getAllClaims() {
		List<Claim> claims = claimRepository.findAll();

		if (claims.isEmpty()) {
			throw new GenAppAPIException(HttpStatus.NOT_FOUND, "No claims found");
		}
		List<Claim> retrievedClaims = new ArrayList<>();
		for (Claim claim : claims) {
			Claim allClaims = new Claim();
			allClaims.setClaimnumber(claim.getClaimnumber());
			allClaims.setPolicynumber(claim.getPolicynumber());
			allClaims.setClaimdate(claim.getClaimdate());
			allClaims.setPaid(claim.getPaid());
			allClaims.setValue(claim.getValue());
			allClaims.setCause(claim.getCause());
			allClaims.setObservations(claim.getObservations());

			retrievedClaims.add(allClaims);
		}

		return retrievedClaims;
	}

	@Override
	public Claim updateClaim(int claimNumber, Claim claim) {
		Claim existingClaim = claimRepository.findByClaimnumber(claimNumber);

		// Check if the claim exists
		if (existingClaim == null) {
			throw new GenAppAPIException(HttpStatus.NOT_FOUND, "Claim not found");
		}

		// Update the fields of the existing claim with the new values
		existingClaim.setClaimdate(claim.getClaimdate());
		existingClaim.setPaid(claim.getPaid());
		existingClaim.setValue(claim.getValue());
		existingClaim.setCause(claim.getCause());
		existingClaim.setObservations(claim.getObservations());

		// Save the updated claim to the database
		Claim savedClaim = claimRepository.save(existingClaim);

		return savedClaim;
	}

	@Override
	public void deleteClaim(int claimNumber) {
		Claim existingClaim = claimRepository.findByClaimnumber(claimNumber);
		if (existingClaim == null) {
			throw new GenAppAPIException(HttpStatus.NOT_FOUND, "Claim Number not found");
		}
		claimRepository.delete(existingClaim);
	}

}
