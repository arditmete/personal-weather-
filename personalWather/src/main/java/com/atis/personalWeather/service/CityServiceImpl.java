package com.atis.personalWeather.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atis.personalWeather.model.City;
import com.atis.personalWeather.model.User;
import com.atis.personalWeather.repository.CityRepository;
import com.atis.personalWeather.repository.UserRepository;
import com.atis.personalWeather.repository.WeatherRepository;
import com.atis.personalWeather.service.dto.HomeDto;
@Transactional
@Service("citytService")
public class CityServiceImpl implements CityService {

	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WeatherRepository weatherRepository;
	@Autowired
	private UserService userService;

	@Override
	public City addCityAdmin(User user, City city, Float lat, Float lon) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		user = userRepository.findByEmail(auth.getName());
		city.setLat(lat);
		city.setLon(lon);

		return cityRepository.save(city);
	}

	@Override
	public List<City> findAll() {
		return cityRepository.findAll();
	}

	@Override
	public User addCitySelected(String term) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByEmail(auth.getName());
		List<City> cities = cityRepository.findAll();
		for (City city : cities) {
			if (city.getCity().contains(term)) {
				user.addCity(city);
			}
		}
		return user;
	}

	@Override
	public List<City> showUserCities() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByEmail(auth.getName());

		List<City> cities = cityRepository.findByUsersCities_User(user);
		for (int i = 0; i < cities.size(); i++) {
			cities.get(i).weather = weatherRepository.getWeather(cities.get(i));
		}
		return cities;

	}

	@Override
	public List<City> showUserFavoriteCities() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByEmail(auth.getName());

		List<City> cities = cityRepository.findByFavoriteCities_FavoriteUser(user);
		for (int i = 0; i < cities.size(); i++) {
			cities.get(i).weather = weatherRepository.getWeather(cities.get(i));
		}
		return cities;

	}

	@Override
	public User addFavoriteCities(Integer favCityId) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByEmail(auth.getName());
		City citySelected = cityRepository.getOne(favCityId);
		user.addFavoriteCity(citySelected);
		return user;

	}

	@Override
	public User deleteUserCity(Integer cityId) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByEmail(auth.getName());
		System.out.println(user.getEmail());
		City citySelected = cityRepository.getOne(cityId);
		user.removeCity(citySelected);
		userRepository.save(user);

		return user;

	}

	@Override
	public User setWeatherTop(Integer cityId) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByEmail(auth.getName());
		City city = cityRepository.getOne(cityId);
		user.setCityWeatherTop(city);
		userRepository.save(user);

		return user;
	}

	@Override
	public List<City> getAllCities() {

		HomeDto homeDto = new HomeDto();
		// 1- Get the need data from repository
		List<City> cities = cityRepository.findAll();
		for (int i = 0; i < cities.size(); i++) {
			cities.get(i).weather = weatherRepository.getWeather(cities.get(i));
			homeDto.setWeatherDto(cities.get(i).weather);
		}
		return cities;
	}

	@Override
	public User deleteFavoriteUserCity(Integer cityId) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByEmail(auth.getName());
		City citySelected = cityRepository.getOne(cityId);
		user.removeFavoriteCity(citySelected);
		userRepository.save(user);

		return user;
	}

	@Override
	public List<City> findByUsersCities_User() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByEmail(auth.getName());

		HomeDto homeDto = new HomeDto();
		List<City> cities = cityRepository.findByUsersCities_User(user);
		for (int i = 0; i < cities.size(); i++) {
			cities.get(i).weather = weatherRepository.getWeather(cities.get(i));
			homeDto.setWeatherDto(cities.get(i).weather);
		}
		return cities;
	}

	@Override
	public List<City> findByFavoriteCities_FavoriteUser() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByEmail(auth.getName());

		return cityRepository.findByFavoriteCities_FavoriteUser(user);
	}

	 @Override
	    public List<City> search(String term) {
	        return cityRepository.findByCityStartingWith(term);
	    }


}
