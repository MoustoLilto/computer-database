package com.excilys.computer.database.console.rest;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.excilys.computer.database.binding.dto.DTOComputer;
import com.excilys.computer.database.binding.mapper.MapperComputer;
import com.excilys.computer.database.core.modele.Computer;

@Component
public class ComputerRestClient {
	private static final String CBD_URL_COMPUTER = "http://localhost:8080/webapp/computers";
	private Client client = ClientBuilder.newClient();
	private final MapperComputer mapperComputer;
	
	public ComputerRestClient(MapperComputer mapperComputer) {
		this.mapperComputer = mapperComputer;
	}

	public List<Computer> getAllComputer(){
		List<DTOComputer> dtos = client
									.target(CBD_URL_COMPUTER)
									.request(MediaType.APPLICATION_JSON)
									.get(new GenericType<List<DTOComputer>> () {});
		return mapperComputer.listToComputer(dtos);
	}

	public Computer getComputer(long id) {
		DTOComputer dto = client
							.target(CBD_URL_COMPUTER)
							.path(String.valueOf(id))
							.request(MediaType.APPLICATION_JSON)
							.get(DTOComputer.class);
		return mapperComputer.toComputer(dto);
	}
	
	public int getNbreComputer() {
		return client
				.target(CBD_URL_COMPUTER)
				.path("nombre")
				.request(MediaType.APPLICATION_JSON)
				.get(Integer.class);
	}
	
	public List<Computer> getSomeComputer(int numTuple, int nbreTuples){
		List<DTOComputer> dtos = client
				.target(CBD_URL_COMPUTER)
				.path(String.valueOf(numTuple)+"/"+String.valueOf(nbreTuples))
				.request(MediaType.APPLICATION_JSON)
				.get(new GenericType<List<DTOComputer>> () {});
		return mapperComputer.listToComputer(dtos);
	}
	
	public int rmComputer(long id) {
		Response response = client
								.target(CBD_URL_COMPUTER)
								.path("delete/"+String.valueOf(id))
								.request(MediaType.APPLICATION_JSON)
								.delete();
		if (response.getStatus() == 200) {
			return 0;
		}
		return -1;
	}
	
	public int createComputer(Computer computer) {
		DTOComputer dtoComputer = mapperComputer.toDTO(computer);
		Response response = client
								.target(CBD_URL_COMPUTER)
								.path("create")
								.request(MediaType.APPLICATION_JSON)
								.post(Entity.entity(dtoComputer, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 200) {
		return 0;
		}
		return -1;
	}
	
	public int updateComputer(Computer computer) {
		DTOComputer dtoComputer = mapperComputer.toDTO(computer);
		Response response = client
								.target(CBD_URL_COMPUTER)
								.path("update")
								.request(MediaType.APPLICATION_JSON)
								.post(Entity.entity(dtoComputer, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 200) {
		return 0;
		}
		return -1;
	}
}
