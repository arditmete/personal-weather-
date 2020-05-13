package com.atis.personalWeather.service.dto;

import com.atis.personalWeather.model.City;
import com.atis.personalWeather.model.WeatherDto;

public class HomeDto {

	private WeatherDto weatherDto;

	private City city;

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	// Getters and setters
	public WeatherDto getWeatherDto() {
		return weatherDto;
	}

	public void setWeatherDto(WeatherDto weatherDto) {
		this.weatherDto = weatherDto;
	}

}
