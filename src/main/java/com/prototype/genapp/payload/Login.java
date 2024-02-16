package com.prototype.genapp.payload;

import java.util.Set;

import com.prototype.genapp.entity.Role;

import lombok.Data;

@Data
public class Login {

	private String username;
	private int customernumber;
	private Set<Role> role;
	private String message;
	
}
