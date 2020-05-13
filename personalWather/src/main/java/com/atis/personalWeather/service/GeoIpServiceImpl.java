package com.atis.personalWeather.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.atis.personalWeather.model.GeoIp;
import com.atis.personalWeather.model.WeatherDto;
import com.atis.personalWeather.repository.WeatherRepositoryImpl;
import com.maxmind.db.Reader;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
@Service
public class GeoIpServiceImpl implements GeoIpService {

	// Get database of IP Address
	public static final String GEO_LITE_DATABASE_LOCATION = "dbFile/GeoLite2-City.mmdb";
	@Autowired
	private WeatherRepositoryImpl weatherRepositoryImpl;

	private DatabaseReader dbReader;

	public WeatherDto findLocationIp(GeoIp geoIp, String ipAddress) throws IOException, GeoIp2Exception {
		
		InputStream databaseInputStream = new ClassPathResource(GEO_LITE_DATABASE_LOCATION).getInputStream();
		dbReader = new DatabaseReader.Builder(databaseInputStream).fileMode(Reader.FileMode.MEMORY).build();
		InetAddress inetAddress = InetAddress.getByName(ipAddress);
		Double geoLongitude = dbReader.city(inetAddress).getLocation().getLongitude();
		Double geoLatitude = dbReader.city(inetAddress).getLocation().getLatitude();
		return geoIp.weather = weatherRepositoryImpl.getWeather(geoLatitude, geoLongitude);

	}
}
