package com.spring.boot.curd.client.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.curd.client.commons.Constants;
import com.spring.boot.curd.client.pojo.Employee;
import com.spring.boot.curd.client.pojo.Response;

@Component
@PropertySource(value = "classpath:ems_api.properties")
public class RuleUtils {

	
	@Autowired
	private Environment environment;

	private RestTemplate template;
	String url = "http://localhost:8080/product/getEmployees";
	
	ObjectMapper mapper = null;
	HttpHeaders headers = null;
	
    @PostConstruct
	public void init() {
		template = new RestTemplate();
		mapper = new ObjectMapper();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		template.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());
		template.getMessageConverters().add(new StringHttpMessageConverter());
	}

	public Employee add(Employee employee) throws IOException {
		String URL = environment.getProperty(Constants.ADD_EMPLOYEE);
		Response response = null;
		Employee employee1;
		String input = mapper.writeValueAsString(employee);
		HttpEntity<String> entity = new HttpEntity<String>(input, headers);
		employee1 = template.postForObject(URL, entity, Employee.class);
		//employee1 = mapper.readValue(results, Response.class);
		return employee1;
	}

	public List<Employee> fetchAllEmployees() {
		List<Employee> listOfEmp ;
		RestTemplate template = new RestTemplate();
		@SuppressWarnings("unchecked")
		//List<Employee> string = (List<Employee>) template.getForObject(url, Employee.class);
		ResponseEntity<List<Employee>> response = (ResponseEntity<List<Employee>>) template.exchange(url, HttpMethod.GET, null,new ParameterizedTypeReference<List<Employee>>() {}); 
		listOfEmp = response.getBody();
		System.out.println("listvalue "+listOfEmp);
		return listOfEmp;
		
		
	}
	
	public List<Employee> getAllEmployee()
	{
		List<Employee> listOfEmp ;
		RestTemplate template = new RestTemplate();
		@SuppressWarnings("unchecked")
		//List<Employee> string = (List<Employee>) template.getForObject(url, Employee.class);
		ResponseEntity<List<Employee>> response = (ResponseEntity<List<Employee>>) template.exchange(url, HttpMethod.GET, null,new ParameterizedTypeReference<List<Employee>>() {}); 
		listOfEmp = response.getBody();
		System.out.println("listvalue "+listOfEmp);
		return listOfEmp;
	}

	public Employee getEmployee(int id) {
		Employee employee = null;
		String URL = environment.getProperty(Constants.GET_EMPLOYEE_BY_ID)
				+ "/{id}";
		Map<String, String> params = new HashMap<>();
		params.put("id", String.valueOf(id));
		employee = template.getForObject(URL, Employee.class, params);
		return employee;
	}

	public Response deleteEmployee(int id) {
		String URL = environment.getProperty(Constants.DELETE_EMPLOYEE)
				+ "/{id}";
		Map<String, String> params = new HashMap<>();
		params.put("id", String.valueOf(id));
		template.delete(URL, params);
		return new Response(true, "Deleted Successfully");
	}
}
