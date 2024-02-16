package com.prototype.genapp.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "motor")
public class Motor {
	
	@Id
	@Column(name = "policynumber")
	private int policynumber;
	
	@Column(name = "make",length = 15)
	private String make;
	
	@Column(name = "model",length = 15)
	private String model;
	
	@Column(name = "value")
	private int value;
	
	@Column(name = "regnumber", length = 7)
    private String regnumber;
	
	@Column(name = "colour",length = 8)
	private String colour;
	
	@Column(name = "cc")
	private int cc;
	
	@Column(name = "yearofmanufacture")
	@Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date yearofmanufacture;
	
	@Column(name = "premium")
	private int premium;
	
	@Column(name = "accidents")
	private int accidents;
 
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "policynumber", referencedColumnName = "policynumber", insertable = false, updatable = false)
	private Policy policy;
 
}