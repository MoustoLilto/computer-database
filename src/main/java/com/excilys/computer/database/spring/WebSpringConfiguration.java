package main.java.com.excilys.computer.database.spring;

import java.util.ResourceBundle;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan("main.java.com.excilys.computer.database.dao,"
		+ " main.java.com.excilys.computer.database.mapper,"
		+ " main.java.com.excilys.computer.database.services,"
		+ " main.java.com.excilys.computer.database.servlet,"
		+ " main.java.com.excilys.computer.database.validator")
public class WebSpringConfiguration {
	
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
	
	@Bean
	public JdbcTemplate getJdbcTemplate(DataSource datasource) {
		return new JdbcTemplate(datasource);
	}
}
