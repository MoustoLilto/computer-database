package com.excilys.computer.database.security;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.excilys.computer.database.persistence.dao,"
		+ " com.excilys.computer.database.services")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean
    public DataSource getDataSource() {
		ResourceBundle bundle = ResourceBundle.getBundle("connect");
		String url = bundle.getString("url");
		String username = bundle.getString("username");
		String password = bundle.getString("password");
		String driver = bundle.getString("driver");
		
		HikariDataSource ds = new HikariDataSource();
		ds.setJdbcUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);
		ds.setDriverClassName(driver);
        return ds;
    }
	
	@SuppressWarnings("deprecation")
	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth, @SuppressWarnings("deprecation") NoOpPasswordEncoder passwordEncoder) throws Exception {
	    auth.jdbcAuthentication().dataSource(getDataSource())
	        .usersByUsernameQuery("select username,password, enabled from users where username=?")
	        .authoritiesByUsernameQuery("select username, role from user_roles where username=?")
	        .passwordEncoder(passwordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.authorizeRequests()
	        .antMatchers("/", "/dashboard").permitAll()
	        .antMatchers("/addComputer", "/editComputer").hasAnyRole("ADMIN", "USER")
	        .and().formLogin().loginPage("/login").defaultSuccessUrl("/dashboard")
	        .and().logout().logoutUrl("/logout").logoutSuccessUrl("/dashboard")
	        .permitAll();
	}
	
	Properties hibernateProperties() {
		ResourceBundle bundle = ResourceBundle.getBundle("hibernate");		
		return new Properties() {
			private static final long serialVersionUID = 1L;
			{
	            setProperty("hibernate.dialect", bundle.getString("dialect"));
	            setProperty("hibernate.show_sql",bundle.getString("show_sql"));
	         }
		};
	}
	
	@Bean
	public LocalSessionFactoryBean getSessionFactory(DataSource datasource) {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(datasource);
		sessionFactory.setPackagesToScan(new String[] { "com.excilys.computer.database.core.modele" });
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}
	
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager manager = new HibernateTransactionManager();
		manager.setSessionFactory(sessionFactory);
		return manager;	
	}
}
