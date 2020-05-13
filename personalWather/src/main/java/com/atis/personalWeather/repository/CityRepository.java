package com.atis.personalWeather.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atis.personalWeather.model.City;
import com.atis.personalWeather.model.User;

public interface CityRepository extends JpaRepository<City, Integer> {

	public List<City> findByUsersCities_User(User user);

	public List<City> findByFavoriteCities_FavoriteUser(User user);

	public City findByCity(String city);

	public List<City> findByCityStartingWith(String city);

	
}
