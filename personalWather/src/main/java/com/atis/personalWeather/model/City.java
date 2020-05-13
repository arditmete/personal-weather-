package com.atis.personalWeather.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalIdCache;

import com.opencsv.bean.CsvBindByName;

@Entity(name = "City")
@Table(name = "cities")
@NaturalIdCache
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class City {

	@Id
	@GeneratedValue
	@Column(name = "cityid")
	private int cityId;
	@CsvBindByName
	@Column(name = "city")
	private String city;
	@CsvBindByName
	@Column(name = "lon")
	private Float lon;
	@CsvBindByName
	@Column(name = "lat")
	private Float lat;
	@CsvBindByName
	@Column(name = "country")
	private String country;
	@CsvBindByName
	@Column(name = "alpha2code")
	private String alpha2code;

	@OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UsersCities> usersCities;
	@OneToMany(mappedBy = "favoriteCity", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<FavoriteCities> favoriteCities;

	@Transient
	public WeatherDto weather = new WeatherDto();

	public Set<FavoriteCities> getFavoriteCities() {
		return favoriteCities;
	}

	public void setFavoriteCities(Set<FavoriteCities> favoriteCities) {
		this.favoriteCities = favoriteCities;
	}

	public City(int i, double d, double e) {
	}

	public City(int cityId, Float lon, Float lat) {
		this.cityId = cityId;
		this.lon = lon;
		this.lat = lat;
	}

	public WeatherDto getWeather() {
		return weather;
	}

	public City() {
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public Float getLon() {
		return lon;
	}

	public void setLon(Float lon) {
		this.lon = lon;
	}

	public Float getLat() {
		return lat;
	}

	public void setLat(Float lat) {
		this.lat = lat;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAlpha2code() {
		return alpha2code;
	}

	public void setAlpha2code(String alpha2code) {
		this.alpha2code = alpha2code;
	}

	public void setWeather(WeatherDto weather) {
		this.weather = weather;
	}

	public Set<UsersCities> getUsersCities() {
		return usersCities;
	}

	public void setUsersCities(Set<UsersCities> usersCities) {
		this.usersCities = usersCities;
	}

}