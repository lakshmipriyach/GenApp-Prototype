package com.prototype.genapp.payload;

import lombok.Data;

@Data
public class LoginRequest {

	private String email;
	private int customernumber;
	private String password;
}
