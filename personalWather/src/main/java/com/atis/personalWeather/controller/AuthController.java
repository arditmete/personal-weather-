package com.atis.personalWeather.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.atis.personalWeather.model.User;
import com.atis.personalWeather.service.UserService;
import com.google.common.collect.ImmutableMap;

@Controller
public class AuthController {

	@Autowired
	private UserService userService;

	// LOGIN
	@RequestMapping({"/","/login"})
	public ModelAndView login(User user) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/auth/login");
		
		return modelAndView;
	}

	// LOGOUT
	@RequestMapping(value = "/logout")
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}

		return "redirect:/login";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView registerPage() {

		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("/auth/register");

		return modelAndView;
	}

	// SAVE USER
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerUser(@Valid User user, BindingResult bindingResult, ModelMap modelMap)
			throws IOException {
		ModelAndView modelAndView = new ModelAndView();
		// Check for the validations
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("successMessage", "Please correct the errors in form!");
			modelMap.addAttribute("bindingResult", bindingResult);

		} else if (userService.isUserAlreadyPresent(user)) {
			modelAndView.addObject("successMessage", "User is already registered!");
		}
		// we will save the user if, no binding errors
		else {
			userService.registerUser(user);
			// Create Token and Save
			modelAndView.addObject("successMessage", "User is registered successfully!");
		}
		modelAndView.addObject("user", new User());
		modelAndView.setViewName("/auth/register");
		return modelAndView;
	}

	@RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
	public ModelAndView ResetPasswordPage(ModelAndView modelAndView, User user) {

		modelAndView.addObject("user", user);
		modelAndView.setViewName("/auth/forgotPassword");

		return modelAndView;
	}

	// Receive the address and send an email
	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
	public ModelAndView forgotUserPassword(@RequestParam("email") String email, ModelAndView modelAndView) {

		User existingUser = userService.findByEmail(email);
		if (existingUser != null) {
			userService.sendEmail(existingUser);
			modelAndView.addObject("token", existingUser.getResetToken());
			return new ModelAndView("/auth/forgotPassword", ImmutableMap.of("message", "Check your email!"));
		} else {
			return new ModelAndView("/auth/forgotPassword", ImmutableMap.of("message", "Email is not valid!"));
		}
	}

	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token) {

		User user = userService.findUserByResetToken(token);
		if (user != null) { // Token found in DB
			modelAndView.setViewName("/auth/resetPassword");
		}
		return modelAndView;
	}

	@PostMapping("/reset")
	public ModelAndView savePassword(User user, @RequestParam("password") String password,
			@RequestParam("passwordConfirmation") String passwordConfirmation, @RequestParam("token") String token) {

		// Find the user by token
		user = userService.findUserByResetToken(token);
		if (user != null) {

			if (token == null || token == "") {
				return new ModelAndView("/auth/resetPassword", ImmutableMap.of("message", "Please enter token"));
			}
			if (user != null)
				if (password == null || password == "") {
					return new ModelAndView("/auth/resetPassword", ImmutableMap.of("message", "Please enter password"));
				}
			if (!password.equalsIgnoreCase(passwordConfirmation)) {
				return new ModelAndView("/auth/resetPassword", ImmutableMap.of("message", "Passwords do not match"));
			}
			userService.savePassword(user, password);
		} else {
			return new ModelAndView("/auth/resetPassword", ImmutableMap.of("message", "Token do not match"));
		}

		return new ModelAndView("/auth/resetPassword", ImmutableMap.of("message", "Password changed successfully!"));
	}
}