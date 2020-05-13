package com.atis.personalWeather.repository;

import com.atis.personalWeather.model.City;
import com.atis.personalWeather.model.WeatherDto;

public interface WeatherRepository {

	public WeatherDto getWeather(City city);

	public WeatherDto getWeather(Double latitude, Double longitude);

}
