package com.atis.personalWeather.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.atis.personalWeather.model.City;
import com.atis.personalWeather.model.User;
import com.atis.personalWeather.model.WeatherDto;
import com.atis.personalWeather.service.CsvService;
import com.atis.personalWeather.service.UserService;

@Controller
public class AdminController {

	@Autowired
	private UserService userService;
	@Autowired
	private CsvService csvService;

	@RequestMapping(value = "/adminHome")
	public ModelAndView adminhome(ModelAndView modelAndView, WeatherDto weather) {

		modelAndView = new ModelAndView("/admin/adminHome");

		return modelAndView;
	}

	@GetMapping("/addCity-admin")
	public String addCityPage() {

		return "/admin/addCityAdmin";
	}

	// SAVE CITY/
	@PostMapping("/addCity-admin")
	public String addCity(@RequestParam("file") MultipartFile file, Model model, City cityDb) throws IOException {

		if (file.isEmpty()) {
		} else {
			List<City> cities = csvService.uploadAndSaveCitiesCsv(file);
			model.addAttribute("users", cities);

		}

		return "/admin/addCityAdmin";

	}

	@GetMapping("/addAdmin")
	public String addAdminPage(Model model) {

		List<User> user = userService.findAll();
		model.addAttribute("user", user);

		return "/admin/addAdmin";
	}

	@RequestMapping(value = "/controlUser", params = "adminId")
	public String createAdmin(User user, @RequestParam("adminId") Integer adminId) {

		userService.setAdmin(user, adminId);
		return "redirect:/addAdmin";

	}

	@RequestMapping(value = "/controlUser", params = "userId")
	public String createUser(User user, @RequestParam("userId") Integer userId) {

		userService.setUser(user, userId);

		return "redirect:/addAdmin";
	}
}
