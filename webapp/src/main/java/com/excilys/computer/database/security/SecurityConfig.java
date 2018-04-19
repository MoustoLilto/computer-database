package com.excilys.computer.database.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@SuppressWarnings("deprecation")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}
	
	@Autowired
	public void configAuthentication(DataSource dataSource, AuthenticationManagerBuilder auth, NoOpPasswordEncoder passwordEncoder) throws Exception {
	    auth.jdbcAuthentication().dataSource(dataSource)
	        .usersByUsernameQuery("select username,password, enabled from users where username=?")
	        .authoritiesByUsernameQuery("select username, role from user_roles where username=?")
	        .passwordEncoder(passwordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.csrf().ignoringAntMatchers("/companies**", "/companies/**", "/computers**", "/computers/**")
	    	.and().authorizeRequests()
	        .antMatchers("/", "/dashboard").permitAll()
	        .antMatchers("/addComputer").hasAnyRole("ADMIN", "USER")
	        .antMatchers("/editComputer").hasAnyRole("ADMIN")
	        .and().formLogin().loginPage("/login").defaultSuccessUrl("/dashboard")
	        .and().logout().logoutUrl("/logout").logoutSuccessUrl("/dashboard")
	        .permitAll()
	        .and().exceptionHandling().accessDeniedPage("/403");
	}
}
