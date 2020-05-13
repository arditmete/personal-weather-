package com.atis.personalWeather.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UsersCitiesKey implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "cityid")
    private int cityId;
 
    @Column(name = "auth_user_id")
    private int userId;

	

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public UsersCitiesKey(int cityId, int userId) {
		this.cityId = cityId;
		this.userId = userId;
	}


	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public UsersCitiesKey() {
	}
	

}
