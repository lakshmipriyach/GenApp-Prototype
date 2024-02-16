package com.prototype.genapp.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGenerator {
	public static void main(String[] args) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		// Password to encode
		String password = "admin";

		// Encode the password
		String encodedPassword = passwordEncoder.encode(password);

		System.out.println("Encoded Password: " + encodedPassword);
	}

}
