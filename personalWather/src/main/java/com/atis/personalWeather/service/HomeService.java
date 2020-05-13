package com.atis.personalWeather.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.atis.personalWeather.model.User;
import com.atis.personalWeather.model.WeatherDto;
import com.atis.personalWeather.repository.UserRepository;
import com.atis.personalWeather.repository.WeatherRepository;
import com.atis.personalWeather.service.dto.HomeDto;

@Service
public class HomeService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WeatherRepository weatherRepository;

	public HomeDto getHomePageData() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByEmail(auth.getName());
		// 1- Get the need data from repository
		WeatherDto weatherDto = weatherRepository.getWeather(user.getCityWeatherTop());

//		 2- Manipulate(Transform) the data to the format you need
		// 3- Insert all the data in the dto
		HomeDto homeDto = new HomeDto();
		homeDto.setWeatherDto(weatherDto);
		homeDto.setCity(user.getCityWeatherTop());
		return homeDto;
	}

}
