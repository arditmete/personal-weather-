package com.atis.personalWeather.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.atis.personalWeather.model.City;
import com.atis.personalWeather.model.WeatherDto;

@Repository
public class WeatherRepositoryImpl implements WeatherRepository {

	@Override
	public WeatherDto getWeather(City city) {
		String apiUrl = "https://api.darksky.net/forecast/d65fe25c83471efa799a6fe819470a2c/" + city.getLat() + ","
				+ city.getLon();

		WeatherDto weatherModel = new RestTemplate().getForObject(apiUrl, WeatherDto.class);
		return weatherModel;
	}

	@Override
	public WeatherDto getWeather(Double latitude, Double longitude) {
		String apiUrl = "https://api.darksky.net/forecast/d65fe25c83471efa799a6fe819470a2c/" + latitude + ","
				+ longitude;

		WeatherDto weatherModel = new RestTemplate().getForObject(apiUrl, WeatherDto.class);
		return weatherModel;
	}


}
