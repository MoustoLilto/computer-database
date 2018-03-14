//package test.java.spring;
//
//import java.util.ResourceBundle;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//
//import com.zaxxer.hikari.HikariDataSource;
//
//@Configuration
//@ComponentScan("java.com.excilys.computer.database")
//public class SpringConfiguration {
//	
//	@Bean
//    public HikariDataSource dataSource() {
//		ResourceBundle bundle = ResourceBundle.getBundle("connect");
//		String url = bundle.getString("url");
//		String username = bundle.getString("username");
//		String password = bundle.getString("password");
//		String driver = bundle.getString("driver");
//		
//		HikariDataSource ds = new HikariDataSource();
//		ds.setJdbcUrl(url);
//		ds.setUsername(username);
//		ds.setPassword(password);
//		ds.setDriverClassName(driver);
//        return ds;
//    }
//}
