package com.atis.personalWeather.Util;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.atis.personalWeather.model.City;

public class Utils {

	public static List<City> filterListByTerm(List<City> cities, String term) throws IOException {

		List<City> result = cities.stream().filter(city -> city.getCity().matches(term)).collect(Collectors.toList());

		return result;

	}
}
