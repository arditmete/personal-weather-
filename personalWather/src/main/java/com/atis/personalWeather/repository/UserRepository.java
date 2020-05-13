package com.atis.personalWeather.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atis.personalWeather.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByEmail(String email);

	public User findByUsername(String name);

	public User findByResetToken(String reset);
	
}
