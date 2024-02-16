package com.prototype.genapp.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "policy")
public class Policy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "policynumber")
	private int policynumber;

	@Column(name = "customernumber", nullable = false)
	private int customernumber;

	@Column(name = "issuedate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date issuedate;

	@Column(name = "expirydate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expirydate;

	@Column(name = "policytype", length = 1)
	private String policytype;

	@Column(name = "lastchanged", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastchanged;

	@Column(name = "brokerid")
	private int brokerid;

	@Column(name = "brokersreference", length = 10)
	private String brokersreference;

	@Column(name = "payment")
	private int payment;

	@Column(name = "commission")
	private int commission;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customernumber", referencedColumnName = "customernumber", insertable = false, updatable = false)
	private Customer customer;
}