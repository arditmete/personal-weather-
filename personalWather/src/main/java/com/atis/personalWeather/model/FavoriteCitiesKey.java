package com.atis.personalWeather.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FavoriteCitiesKey implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "favorite_city")
    private int favoriteCityId;
 
    @Column(name = "auth_user_id")
    private int favoriteUserId;

	public int getFavoriteCityId() {
		return favoriteCityId;
	}

	public void setFavoriteCityId(int favoriteCityId) {
		this.favoriteCityId = favoriteCityId;
	}

	public int getFavoriteUserId() {
		return favoriteUserId;
	}

	public void setFavoriteUserId(int favoriteUserId) {
		this.favoriteUserId = favoriteUserId;
	}

	public FavoriteCitiesKey(int favoriteCityId, int favoriteUserId) {
		this.favoriteCityId = favoriteCityId;
		this.favoriteUserId = favoriteUserId;
	}

	public FavoriteCitiesKey() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + favoriteCityId;
		result = prime * result + favoriteUserId;
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
		FavoriteCitiesKey other = (FavoriteCitiesKey) obj;
		if (favoriteCityId != other.favoriteCityId)
			return false;
		if (favoriteUserId != other.favoriteUserId)
			return false;
		return true;
	}
 

}