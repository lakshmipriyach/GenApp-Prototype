package com.prototype.genapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prototype.genapp.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Customer findByEmailaddress(String emailaddress);

	Customer findByCustomernumber(int customernumber);

}
