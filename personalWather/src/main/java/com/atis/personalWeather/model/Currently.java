package com.atis.personalWeather.model;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

public class Currently {
	public String summary;
	public Long time;
	public String icon;
	public Float humidity;
	public Float pressure;
	public Float windSpeed;
	public Float windBearing;
	public Float precipIntensity;
	public int temperature;

	@JsonProperty("data")
	public List<Data> data;

	public int getTemperatureInCelsius() {
		return (temperature - 32) * 5 / 9;
	}

	public DayOfWeek getTimeDay() throws IOException, JsonProcessingException {

		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneId.systemDefault());
		return localDateTime.getDayOfWeek();
	}
}
