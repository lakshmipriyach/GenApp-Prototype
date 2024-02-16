package com.prototype.genapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "ksdspoly")
public class Ksdspoly {
	
	@Id
	@Column(name = "pkey")
	private byte[] pkey;
	
	@Column(name = "record")
	private byte[] record;
 
}