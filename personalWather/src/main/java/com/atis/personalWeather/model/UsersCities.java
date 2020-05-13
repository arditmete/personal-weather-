package com.atis.personalWeather.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "userscities")
@IdClass(UsersCities.class)
public class UsersCities implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UsersCitiesKey id;

	private City city;
	private User user;

	@Id
	@ManyToOne
	@JoinColumn(name = "cityid")
	public City getCity() {
		return city;
	}

	@Id
	@ManyToOne
	@JoinColumn(name = "auth_user_id")
	public User getUser() {
		return user;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UsersCities(City city, User user) {
		this.city = city;
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsersCities other = (UsersCities) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	public UsersCities() {
	}

}
