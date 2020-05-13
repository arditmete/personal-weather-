package com.atis.personalWeather.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Daily {
	
	public String icon;
	
	@JsonProperty("data")
	public List<Data> data;

}
