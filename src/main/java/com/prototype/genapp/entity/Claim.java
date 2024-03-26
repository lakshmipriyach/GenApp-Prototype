package com.prototype.genapp.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "claim")
public class Claim {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "claimnumber")
	private int claimnumber;
	
	@Column(name = "policynumber", nullable = false)
	private int policynumber;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "claimdate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date claimdate;
	
	@Column(name = "paid")
	private int paid;
	
	@Column(name = "value")
	private int value;
	
	@Column(name = "cause", length = 255)
	private String cause;
	
	@Column(name = "observations", length = 255)
	private String observations;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "policynumber", referencedColumnName = "policynumber", insertable = false, updatable = false)
	private Policy policy;

}
