package com.prototype.genapp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prototype.genapp.entity.Claim;

public interface ClaimRepository extends JpaRepository<Claim, Long> {

	List<Claim> findByPolicynumberAndClaimdateAndPaidAndValueAndCauseAndObservations(
            int policynumber, Date claimdate, int paid, int value, String cause, String observations);

	Claim findByClaimnumber(int claimNumber);

}
