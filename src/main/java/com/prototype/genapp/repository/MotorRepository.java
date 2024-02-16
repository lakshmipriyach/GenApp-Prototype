package com.prototype.genapp.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prototype.genapp.entity.Motor;

public interface MotorRepository extends JpaRepository<Motor, Long> {

	Motor findByPolicynumber(int policyNumber);

	Motor findByMakeAndModelAndValueAndRegnumberAndColourAndCcAndYearofmanufactureAndAccidentsAndPremium(String carmake,
			String carmodel, int carvalue, String registration, String carcolour, int cc, Date manufacturedate,
			int noofaccidents, int policypremium);




}

