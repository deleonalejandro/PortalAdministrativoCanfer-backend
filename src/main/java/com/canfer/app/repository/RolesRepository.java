package com.canfer.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.canfer.app.model.Roles;

public interface RolesRepository extends JpaRepository<Roles, Long>{
	
	Roles findByRol(String rol);
	
}


