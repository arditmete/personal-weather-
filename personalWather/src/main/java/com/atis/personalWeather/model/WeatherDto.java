package com.atis.personalWeather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDto {
	
	public int weatherId;
	public String timezone;
	@JsonProperty("currently")
	public Currently currently = new Currently();
	@JsonProperty("daily")
	public Daily daily = new Daily();
	
	public WeatherDto() {
	}

}