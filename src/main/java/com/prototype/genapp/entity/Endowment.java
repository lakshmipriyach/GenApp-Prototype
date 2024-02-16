package com.prototype.genapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
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
@Table(name = "endowment")
public class Endowment {

	@Id
	@Column(name = "policynumber")
	private int policynumber;
	
	@Column(name = "equities", length = 1)
	private String equities;
	
	@Column(name = "withprofits", length = 1)
	private String withprofits;
	
	@Column(name = "managedfund", length = 1)
	private String managedfund;
	
	@Column(name = "fundname", length = 10)
	private String fundname;
	
	@Column(name = "term")
	private int term;
	
	@Column(name = "sumassured")
	private int sumassured;
	
	@Column(name = "lifeassured", length = 31)
	private String lifeassured;
	
	@Lob
	@Column(name = "paddingData")
	private String paddingdata;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "policynumber", referencedColumnName = "policynumber", insertable = false, updatable = false)
	private Policy policy;

}