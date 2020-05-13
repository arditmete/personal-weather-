package com.atis.personalWeather.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atis.personalWeather.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	public Role findByRole(String role);

}
