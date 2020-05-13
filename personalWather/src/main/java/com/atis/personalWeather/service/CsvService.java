package com.atis.personalWeather.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.atis.personalWeather.model.City;

public interface CsvService {

	public List<City> uploadAndSaveCitiesCsv(MultipartFile file) throws IOException;

}
