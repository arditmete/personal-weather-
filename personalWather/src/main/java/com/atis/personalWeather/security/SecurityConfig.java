package com.atis.personalWeather.security;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@PropertySource("classpath:data.properties")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private CustomLoginSuccessHandler sucessHandler;

	@Autowired
	private DataSource dataSource;

	@Value("${usersQuery}")
	private String usersQuery;

	@Value("${rolesQuery}")
	private String rolesQuery;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().usersByUsernameQuery(usersQuery).
		authoritiesByUsernameQuery(rolesQuery)
				.dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/login").permitAll()
		.antMatchers("/forgotPassword").permitAll()
		.antMatchers("/confirm-reset").permitAll()
		.antMatchers("/reset").permitAll()
		.antMatchers("/resetPassword").permitAll()
		.antMatchers("/register").permitAll()
		.antMatchers("/adminHome").hasAnyAuthority("SITE_ADMIN")
		.antMatchers("/addAdmin").hasAnyAuthority("SITE_ADMIN")
		.antMatchers("/addCity-admin/**").hasAnyAuthority("SITE_ADMIN")
		.antMatchers("/addCity/**").hasAnyAuthority("SITE_USER")
		.antMatchers("/home/**").hasAnyAuthority("SITE_USER")
		.antMatchers("/myCities/**").hasAnyAuthority("SITE_USER")
		.antMatchers("/myLocation/**").hasAnyAuthority("SITE_USER")
		.antMatchers("/favoriteCities/**").hasAnyAuthority("SITE_USER")
		.antMatchers("/controlMyCities/**").hasAnyAuthority("SITE_USER")
		.antMatchers("/settings/**").hasAnyAuthority("SITE_ADMIN")
		.antMatchers("/settings/changePassword/**").hasAnyAuthority("SITE_ADMIN")
		.antMatchers("/changePassword/**").hasAnyAuthority("SITE_ADMIN")
		.antMatchers("/user-settings/**").hasAnyAuthority("SITE_USER")
		.antMatchers("/user-settings/changePassword/**").hasAnyAuthority("SITE_USER")
		
		.anyRequest().authenticated()
		.and()
				// form login
				.csrf().disable().formLogin()
				.loginPage("/login")
				.failureUrl("/login?error=true")
				.successHandler(sucessHandler)
				.usernameParameter("email")
				.passwordParameter("password")
				.and()
				// logout
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login.html")
				.and()
				.exceptionHandling()
				.accessDeniedPage("/access-denied");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}

}
