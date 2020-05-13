package com.atis.personalWeather.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.atis.personalWeather.model.City;
import com.atis.personalWeather.model.User;
import com.atis.personalWeather.service.CityService;
import com.atis.personalWeather.service.CsvService;
import com.atis.personalWeather.service.HomeService;
import com.atis.personalWeather.service.UserService;
import com.atis.personalWeather.service.dto.HomeDto;
import com.google.common.collect.ImmutableMap;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.opencsv.exceptions.CsvException;

@Controller
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	BCryptPasswordEncoder encoder;
	@Autowired
	HomeService homeService;
	@Autowired
	CityService cityService;
	@Autowired
	CsvService csvService;

	public UserController() {
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home() {

		// 1-Call the service and get the data needed
		HomeDto homeDto = homeService.getHomePageData();
		List<City> cities = cityService.findByUsersCities_User();

		// 2-Assign the data fetched with the GUI components
		ModelAndView modelAndView = new ModelAndView("/user/user-dashboard");

		modelAndView.addObject("weatherTop", homeDto);
		modelAndView.addObject("weatherWeekly", homeDto.getWeatherDto().daily.data);
		modelAndView.addObject("cities", cities);
		return modelAndView;
	}

	// USER CITIES
	@GetMapping("/myCities")
	public String myCitiesPage(Model model) throws IOException, GeoIp2Exception, URISyntaxException, CsvException {

		List<City> cities = cityService.showUserCities();
		model.addAttribute("city", cities);

		return "/user/showMyCities";
	}

	// USER FAVORITE CITIES
	@GetMapping("/favoriteCities")
	public String favoriteCities(Model model) {

		List<City> cities = cityService.showUserFavoriteCities();
		model.addAttribute("city", cities);

		return "/user/favoriteCities";
	}

	// ADD FAVORITE CITIES
	@Transactional
	@PostMapping(value = "/controlMyCities", params = "favoriteCities")
	public String addFavoriteCities(@RequestParam("favoriteCities") Integer favCity) {

		cityService.addFavoriteCities(favCity);

		return "redirect:/myCities";
	}

	@Transactional
	@PostMapping(value = "/controlMyCities", params = "deleteCity")
	public String deleteCity(@RequestParam("deleteCity") Integer cityId) {

		cityService.deleteUserCity(cityId);

		return "redirect:/myCities";
	}

	@Transactional
	@PostMapping(value = "/deleteFavoriteCity")
	public String deleteFavoriteCity(@RequestParam("deleteFavoriteCity") Integer cityId) {

		cityService.deleteFavoriteUserCity(cityId);
		
		return "redirect:/favoriteCities";
	}

	// SAVE WEATHERTOP CITY
	@PostMapping(value = "/controlMyCities", params = "weatherTop")
	public String setWeatherTop(@RequestParam(value = "weatherTop", required = false) Integer weatherTopId) {

		cityService.setWeatherTop(weatherTopId);

		return "redirect:/myCities";
	}

	@GetMapping("/user-settings")
	public String getUserSettings(Model model) {

		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findByEmail(auth);
		
		model.addAttribute("name", user.getName());
		model.addAttribute("surname", user.getLastName());
		model.addAttribute("email", user.getEmail());

		return "/user/user-settings";
	}

	// Change user settings if validated
	@RequestMapping(value = "/user-settings", params = "save")
	public String editUserSettings(@RequestParam("name") String name, @RequestParam("surname") String surname,
			Model model) {

		userService.editUserSettings(name, surname);

		return "redirect:/user-settings";
	}

	// RESET USER SETTINGS
	@RequestMapping(value = "/user-settings", params = "reset")
	public String resetUserSettings(Model model) {

		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findByEmail(auth);
		model.addAttribute("name", user.getName());
		model.addAttribute("surname", user.getLastName());
		model.addAttribute("email", user.getEmail());

		return "/user/user-settings";
	}

	@GetMapping("/user-settings/changePassword")
	public String getUserPassword(Model model) {

		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findByEmail(auth);
		model.addAttribute("user", user);

		return "/user/changePasswordUser";
	}

	// SAVE USER PASSWORD
	@PostMapping("user-changePassword")
	public ModelAndView saveUserPassword(User user, Model model, @RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword,
			@RequestParam("confirmNewPassword") String confirmNewPassword) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		user = userService.findByEmail(auth.getName());

		if (user != null) {
			if (!encoder.matches(oldPassword, user.getPassword())) {
				return new ModelAndView("user/changePasswordUser",
						ImmutableMap.of("message", "Old password is not correct!"));
			}
			if (newPassword == null || newPassword == "") {
				return new ModelAndView("user/changePasswordUser",
						ImmutableMap.of("message", "Please enter new password"));
			}
			if (!newPassword.equalsIgnoreCase(confirmNewPassword)) {
				return new ModelAndView("user/changePasswordUser",
						ImmutableMap.of("message", "Passwords do not match"));
			}
			if (newPassword.equalsIgnoreCase(confirmNewPassword)) {
				userService.savePassword(user, confirmNewPassword);
				return new ModelAndView("user/changePasswordUser",
						ImmutableMap.of("message", "Password is changed successfully!"));
			}

		} else {
			return new ModelAndView("user/changePasswordUser", ImmutableMap.of("message", "User do not match"));
		}

		return new ModelAndView("redirect:/user-settings/changePassword");
	}

	@RequestMapping("/addCity")
	public String addCityPage(Model model, City city) throws IOException {
		return "user/addSelectedCity";
	}

	@PostMapping("/saveCity")
	@Transactional
	public String addCity(@RequestParam("term") String term, Model model) throws IOException {

		cityService.addCitySelected(term);
		
		return "redirect:/addCity";
	}
///////////////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "search", method = RequestMethod.GET)
	@ResponseBody
	public List<String> search(HttpServletRequest request) {
		List<City> result = cityService.search(request.getParameter("term"));
		List<String> citiesResult = new ArrayList<>();
		for (City city : result) {
			citiesResult.add(city.getCity());
		}
		return citiesResult;
	}
}
