package com.atis.personalWeather.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.atis.personalWeather.model.City;
import com.atis.personalWeather.repository.CityRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Service
public class CsvServiceImpl implements CsvService {

	@Autowired
	CityRepository cityRepository;

	@Override
	public List<City> uploadAndSaveCitiesCsv(MultipartFile file) throws IOException {
		List<City> cities =new ArrayList<>();
		// parse CSV file to create a list of `User` objects
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

			// create csv bean reader
			CsvToBean<City> csvToBean = new CsvToBeanBuilder<City>(reader).withType(City.class)
					.withIgnoreLeadingWhiteSpace(true).build();

			// convert `CsvToBean` object to list of users
			cities = csvToBean.parse();

			// delete all data in db
			cityRepository.deleteAll();

			// save new file
			cityRepository.saveAll(cities);
		} catch (Exception e) {
		}
		return cities;
	}
}
