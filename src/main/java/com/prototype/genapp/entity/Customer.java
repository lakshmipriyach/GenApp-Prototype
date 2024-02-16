package com.prototype.genapp.entity;

import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customernumber")
	private int customernumber;
	
	@Column(name = "username", length = 50)
	private String username;

	@Column(name = "firstname", length = 20)
	private String firstname;

	@Column(name = "lastname", length = 20)
	private String lastname;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "dateofbirth")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dateofbirth;

	@Column(name = "housename", length = 20)
	private String housename;

	@Column(name = "housenumber", length = 20)
	private String housenumber;

	@Column(name = "postcode", length = 8)
	private String postcode;

	@Column(name = "phonehome", length = 20)
	private String phonehome;

	@Column(name = "phonemobile", length = 20)
	private String phonemobile;

	@Column(name = "emailaddress", length = 100)
	private String emailaddress;

	@Column(name = "password")
	@NotNull(message = "Password is mandatory")
	@Size(min = 8, message = "Password must be at least 8 characters long")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$", message = "Password must contain at least one lowercase letter, one uppercase letter, and one digit")
	private String password;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Role> roles;
	
	@PrePersist
    public void generateUsername() {
        // Generate username based on firstname and lastname
        this.username = this.firstname + this.lastname;
    }


}
