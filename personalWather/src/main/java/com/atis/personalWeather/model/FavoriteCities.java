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
@Table(name = "Favorite_cities")
@IdClass(FavoriteCities.class)
public class FavoriteCities implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FavoriteCitiesKey id;

	private City favoriteCity;
	private User favoriteUser;

	@Id
	@ManyToOne
	@JoinColumn(name = "favorite_city")
	public City getFavoriteCity() {
		return favoriteCity;
	}

	@Id
	@ManyToOne
	@JoinColumn(name = "auth_user_id")
	public User getFavoriteUser() {
		return favoriteUser;
	}


	public void setFavoriteCity(City favoriteCity) {
		this.favoriteCity = favoriteCity;
	}

	public void setFavoriteUser(User favoriteUser) {
		this.favoriteUser = favoriteUser;
	}

	public FavoriteCities(City favoriteCity, User favoriteUser) {
		this.favoriteCity = favoriteCity;
		this.favoriteUser = favoriteUser;
	}

	public FavoriteCities() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((favoriteCity == null) ? 0 : favoriteCity.hashCode());
		result = prime * result + ((favoriteUser == null) ? 0 : favoriteUser.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		FavoriteCities other = (FavoriteCities) obj;
		if (favoriteCity == null) {
			if (other.favoriteCity != null)
				return false;
		} else if (!favoriteCity.equals(other.favoriteCity))
			return false;
		if (favoriteUser == null) {
			if (other.favoriteUser != null)
				return false;
		} else if (!favoriteUser.equals(other.favoriteUser))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
