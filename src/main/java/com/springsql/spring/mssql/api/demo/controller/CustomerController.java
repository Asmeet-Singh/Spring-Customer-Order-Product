package com.springsql.spring.mssql.api.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.springsql.spring.mssql.api.demo.dto.CustomerRequest;
import com.springsql.spring.mssql.api.demo.model.Order;
import com.springsql.spring.mssql.api.demo.service.CustomerService;
import com.springsql.spring.mssql.api.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springsql.spring.mssql.api.demo.dao.CustomerDao;
import com.springsql.spring.mssql.api.demo.model.Customer;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
	
	@Autowired
	private CustomerDao dao;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private OrderService orderService;

	@PostMapping("")
	public String bookCustomer(@Valid @RequestBody CustomerRequest customer, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return bindingResult.getAllErrors()
					.stream()
					.map(ObjectError::getDefaultMessage)
					.collect(Collectors.joining(", "));
		}
		Customer custSave = customerService.createCustomer(customer);
		return "Customer created with Id { " + custSave.getId() +" }";
	}
	
	@GetMapping("")
	public List<Customer> getCustomers(){
		return(List<Customer>) dao.findAll();
	}
	
	@GetMapping("/{id}")
	public Customer getCustomers(@PathVariable("id") int id) {
		return dao.findById(id).get(); 
	}

	@GetMapping("/OrderId/{id}")
	public List<Order> getAllOrderCustId(@PathVariable("id") int id) {
		return orderService.findOrdersByCustId(id);
	}


//	@PutMapping("/updateCustomers/{id}")
//	private Customer update(@RequestBody Customer customers)
//	{
//	dao.save(customers);
//	return customers;
//	}
	@PutMapping("/{id}")
	private String update(@PathVariable("id") int id, @Valid @RequestBody CustomerRequest customerRequest,
						  BindingResult bindingResult)
	{
		if (bindingResult.hasErrors()) {
			return bindingResult.getAllErrors()
					.stream()
					.map(ObjectError::getDefaultMessage)
					.collect(Collectors.joining(", "));
		}
		Customer customer = customerService.update(id, customerRequest);
		return "Updated customer having Id {" +customer.getId() +"}";	}

	@DeleteMapping("/{id}")
	public String deleteCustomer(@PathVariable("id") int id) {
		 dao.deleteById(id);
		return "Deleted customer with Id { " +id +" }";
	}

}
