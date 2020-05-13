package com.atis.personalWeather.service;

import java.util.List;

import com.atis.personalWeather.model.City;
import com.atis.personalWeather.model.User;
import com.opencsv.exceptions.CsvException;

public interface CityService {

	public City addCityAdmin(User user, City city, Float lat, Float lon);

	public List<City> findAll();

	public User addCitySelected(String term);

	List<City> findByUsersCities_User();

	List<City> findByFavoriteCities_FavoriteUser();

	public List<City> showUserFavoriteCities();

	public List<City> showUserCities();

	public User addFavoriteCities(Integer favCity);

	public User deleteUserCity(Integer cityId);

	public User deleteFavoriteUserCity(Integer cityId);

	public User setWeatherTop(Integer weatherTopId);

	public List<City> getAllCities();

	List<City> search(String keyword);

}
