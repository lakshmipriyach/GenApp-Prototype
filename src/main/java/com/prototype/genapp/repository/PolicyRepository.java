package com.prototype.genapp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prototype.genapp.entity.Policy;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {

	Policy findByPolicynumberAndCustomernumber(int policyNumber, int customerNumber);

	List<Policy> findByCustomernumber(int customerNumber);

	Policy findByCustomernumberAndIssuedateAndExpirydate(int customernumber, Date issuedate, Date expirydate);

}

