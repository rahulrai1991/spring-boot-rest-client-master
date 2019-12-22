package com.spring.boot.curd.client.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.curd.client.pojo.Employee;
import com.spring.boot.curd.client.pojo.InputRequest;
import com.spring.boot.curd.client.pojo.Response;
import com.spring.boot.curd.client.util.RuleUtils;

@Service
public class EMSClientInvokerService {

	private Logger log = LoggerFactory.getLogger(EMSClientInvokerService.class);

	@Autowired
	private RuleUtils ruleUtils;

	public Employee addEmployee(Employee employee) throws IOException {
		
		Employee employee1=ruleUtils.add(employee);
		return employee1;
		
	}

	public List<Employee> getAllEmployees() {
		List<Employee> employees;
		
			employees = ruleUtils.fetchAllEmployees();
		 
		return employees;
	}

	public Employee getEmployee(int id) {
		return ruleUtils.getEmployee(id);
	}

	public Response delete(int id) {
		return ruleUtils.deleteEmployee(id);
	}
}
