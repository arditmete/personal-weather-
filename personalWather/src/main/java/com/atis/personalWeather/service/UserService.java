package com.atis.personalWeather.service;

import java.util.List;

import org.springframework.mail.SimpleMailMessage;

import com.atis.personalWeather.model.User;

public interface UserService {

	public User findByUsername(String name);

	public User findByResetToken(String reset);

	public boolean isUserAlreadyPresent(User user);

	public User findUserByResetToken(String resetToken);

	public User findByEmail(String email);

	public void savePassword(User user, String password);

	public void save(User user);

	public User editUserSettings(String name, String surname);
	
	public List<User> findAll();
	
	public User getOne(int id);

	public User setUser(User user, int userId);

	public	User setAdmin(User user, int adminId);

	public	SimpleMailMessage sendEmail(User existingUser);
	
	public User registerUser(User user);

}
