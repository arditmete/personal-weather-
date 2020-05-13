package com.atis.personalWeather.model;

import javax.persistence.Transient;

public class GeoIp {
    private String ipAddress;
    private String city;
	private Double  latitude;
	private Double longitude;

	@Transient
	public WeatherDto weather=new WeatherDto();

	
	public GeoIp() {
	}

	public GeoIp(String ipAddress, String city, Double latitude2, Double longitude2) {
		this.ipAddress = ipAddress;
		this.city = city;
		this.latitude = latitude2;
		this.longitude = longitude2;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

}
