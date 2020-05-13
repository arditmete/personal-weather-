package com.atis.personalWeather.model;

import java.util.Iterator;

// @formatter:on

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;

@Entity(name = "User")
@Table(name = "auth_user")
public class User {
	@Id
	@GeneratedValue
	@Column(name = "auth_user_id")
	private int userId;

	@Column(name = "name")
	private String name;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "username")
	private String username;

	@Email(message = "Email is invalid")
	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "reset_token")
	private String resetToken;

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "auth_user_role", joinColumns = @JoinColumn(name = "auth_user_id"), inverseJoinColumns = @JoinColumn(name = "auth_role_id"))
	private Set<Role> roles;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(updatable = true)
	City cityWeatherTop;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UsersCities> usersCities;

	@OneToMany(mappedBy = "favoriteUser", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<FavoriteCities> favoriteCities;

//////////////////////////////////////////////////////////////////
	public void addCity(City city) {
		UsersCities usersCity = new UsersCities(city, this);
		usersCities.add(usersCity);
		city.getUsersCities().add(usersCity);
	}

	public void removeCity(City city) {
		for (Iterator<UsersCities> iterator = usersCities.iterator(); iterator.hasNext();) {
			UsersCities usersCities = iterator.next();

			if (usersCities.getUser().equals(this) && usersCities.getCity().equals(city)) {
				iterator.remove();
				usersCities.getCity().getUsersCities().remove(usersCities);
				usersCities.setCity(null);
				usersCities.setUser(null);
				removeFavoriteCity(city);
			}
		}
	}

////////////////////////////////////////////////////////////////////
	public void addFavoriteCity(City city) {
		FavoriteCities favoriteCity = new FavoriteCities(city, this);
		favoriteCities.add(favoriteCity);
		city.getFavoriteCities().add(favoriteCity);
	}

	public void removeFavoriteCity(City city) {
		for (Iterator<FavoriteCities> iterator = favoriteCities.iterator(); iterator.hasNext();) {
			FavoriteCities favoriteCities = iterator.next();

			if (favoriteCities.getFavoriteUser().equals(this) && favoriteCities.getFavoriteCity().equals(city)) {
				iterator.remove();
				favoriteCities.getFavoriteCity().getFavoriteCities().remove(favoriteCities);
				favoriteCities.setFavoriteCity(null);
				favoriteCities.setFavoriteUser(null);
			}
		}
	}

////////////////////////////////////////////////////////////////////

	public User(String name) {
		this.name = name;
	}

	public City getCityWeatherTop() {
		return cityWeatherTop;
	}

	public void setCityWeatherTop(City cityWeatherTop) {
		this.cityWeatherTop = cityWeatherTop;
	}

	public Set<FavoriteCities> getFavoriteCities() {
		return favoriteCities;
	}

	public void setFavoriteCities(Set<FavoriteCities> favoriteCities) {
		this.favoriteCities = favoriteCities;
	}

	public Set<UsersCities> getUsersCities() {
		return usersCities;
	}

	public void setUsersCities(Set<UsersCities> usersCities) {
		this.usersCities = usersCities;
	}

	public void setCity(City city) {

	}

	public int getUserId() {
		return userId;
	}

	public User() {
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}