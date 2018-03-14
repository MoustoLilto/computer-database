package main.java.com.excilys.computer.database.spring;

import java.util.ResourceBundle;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan("main.java.com.excilys.computer.database")
public class SpringConfiguration {
	
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
}
