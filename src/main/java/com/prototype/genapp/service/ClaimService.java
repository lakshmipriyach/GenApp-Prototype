package com.prototype.genapp.service;


import java.util.List;

import com.prototype.genapp.entity.Claim;

public interface ClaimService {
	
	Claim addClaim(Claim claim);
    Claim getClaim(int claimNumber);
    List<Claim> getAllClaims();
    Claim updateClaim(int claimNumber, Claim claim);
    void deleteClaim(int claimNumber);

}
