package com.atis.personalWeather.service;

import java.io.IOException;

import com.atis.personalWeather.model.GeoIp;
import com.atis.personalWeather.model.WeatherDto;
import com.maxmind.geoip2.exception.GeoIp2Exception;

public interface GeoIpService {
	
	public WeatherDto findLocationIp(GeoIp geoIp, String ipAddress) throws IOException, GeoIp2Exception;

}
