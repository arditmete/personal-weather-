package com.atis.personalWeather.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.atis.personalWeather.model.GeoIp;
import com.atis.personalWeather.model.WeatherDto;
import com.atis.personalWeather.service.GeoIpService;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.maxmind.geoip2.exception.GeoIp2Exception;

@Controller
public class GeoIpController {

	// Get database of IP Address
	@Autowired
	private GeoIpService geoIpService;

	@RequestMapping(value = "/myLocation", method = RequestMethod.GET)
	public ModelAndView findLocationByIpAddress(GeoIp geoIp, ModelAndView modelAndView)
			throws IOException, GeoIp2Exception, AddressNotFoundException {

		String ipAddress = "206.190.36.45";

//		Get your actual ip address
//		WebAuthenticationDetails auth = (WebAuthenticationDetails) SecurityContextHolder.getContext()
//				.getAuthentication().getDetails();
//		System.out.println(auth.getRemoteAddress());

		WeatherDto weatherDto = geoIpService.findLocationIp(geoIp, ipAddress);

		modelAndView.addObject("weatherIp", weatherDto);
		modelAndView.addObject("weatherWeeklyIp", weatherDto.daily.data);
		modelAndView.setViewName("/user/myLocation");

		return modelAndView;
	}
}
