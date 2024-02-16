package com.prototype.genapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prototype.genapp.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Optional<Role> findByName(String name);

}
