package com.prototype.genapp.payload;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class MotorPolicy {
	
	private int policynumber;
	private int customernumber;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
	private Date issuedate;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
	private Date expirydate;
	private String carmake;
	private String carmodel;
	private int carvalue;
	private String registration;
	private String carcolour;
	private int cc;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
	private Date manufacturedate;
	private int noofaccidents;
	private int policypremium;
	

}
