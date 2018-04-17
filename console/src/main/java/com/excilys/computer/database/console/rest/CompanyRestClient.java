package com.excilys.computer.database.console.rest;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.excilys.computer.database.binding.dto.DTOCompany;
import com.excilys.computer.database.binding.mapper.MapperCompany;
import com.excilys.computer.database.core.modele.Company;

@Component
public class CompanyRestClient {
	private static final String CBD_URL_COMPANY = "http://localhost:8080/webapp/companies";
	private Client client = ClientBuilder.newClient();
	final private MapperCompany mapperCompany;
	
	public CompanyRestClient(MapperCompany mapperCompany) {
		this.mapperCompany = mapperCompany;
	}
	
	public List<Company>getAllCompany(){
		List<DTOCompany> dtoCompanies = client
											.target(CBD_URL_COMPANY)
											.request(MediaType.APPLICATION_JSON)
											.get(new GenericType<List<DTOCompany>> () {});
		return mapperCompany.listToCompany(dtoCompanies);
	}
	
	public Company getCompany(long id) {
		DTOCompany dtoCompany = client
									.target(CBD_URL_COMPANY)
									.path(String.valueOf(id))
									.request(MediaType.APPLICATION_JSON)
									.get(DTOCompany.class);
		return mapperCompany.toCompany(dtoCompany);
	}
	
	public int getNbreCompany() {
		return client
				.target(CBD_URL_COMPANY)
				.path("nombre")
				.request(MediaType.APPLICATION_JSON)
				.get(Integer.class);
	}
	
	public int rmCompany(long id) {
		Response response = client
				.target(CBD_URL_COMPANY)
				.path("delete/"+String.valueOf(id))
				.request(MediaType.APPLICATION_JSON)
				.delete();
		if (response.getStatus() == 200) {
		return 0;
		}
		return -1;
	}
}
