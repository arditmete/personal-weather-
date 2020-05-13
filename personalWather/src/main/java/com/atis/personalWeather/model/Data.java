package com.atis.personalWeather.model;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.fasterxml.jackson.core.JsonProcessingException;

public class Data {
	public Long time;
	public String summary;
	public String icon;
	public Long sunriseTime;
	public Long sunsetTime;
	public int temperatureHigh;
	public Long temperatureHighTime;
	public Float temperatureLow;
	public int temperature;
	public Long temperatureLowTime;
	public Float humidity;
	public Float pressure;
	public Float windSpeed;
	public Float windBearing;
	public Float visibility;
	public Float temperatureMin;
	public Float temperatureMax;
    public Float precipIntensity;
    public Float precipIntensityError;
    public Float precipProbability;
    public String precipType;
    public WeatherDto weather=new WeatherDto();
    
    public float getTemperature() {
    	return (temperatureHigh + temperatureLow)/2;
    }
    public int getTemperatureInCelsius() {
		return (temperatureHigh - 32) * 5 / 9;
	}
    public DayOfWeek getTimeDay() throws IOException, JsonProcessingException {
    	 
    	LocalDateTime localDateTime =
    		    LocalDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneId.systemDefault());
    	localDateTime.getDayOfWeek();
        return localDateTime.getDayOfWeek();
    }
}
