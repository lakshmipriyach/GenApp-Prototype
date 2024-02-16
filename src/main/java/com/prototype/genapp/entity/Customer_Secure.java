package com.prototype.genapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
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
@Table(name = "customer_secure")
public class Customer_Secure {
	
	@Id
    @Column(name = "customernumber")
	private int customernumber;
	
	@Column(name = "customerPass", length = 32)
	private String customerpassword;
	
	@Column(name = "state_indicator", length = 1)
	private String state_indicate;
	
	@Column(name = "pass_changes")
	private int pass_change;

	@OneToOne
	@MapsId
	@JoinColumn(name = "customernumber")
	private Customer customer;
	

}
