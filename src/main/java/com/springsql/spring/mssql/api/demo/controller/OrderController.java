package com.springsql.spring.mssql.api.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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
import com.springsql.spring.mssql.api.demo.dao.OrderDao;
import com.springsql.spring.mssql.api.demo.dto.OrderRequest;
import com.springsql.spring.mssql.api.demo.model.Order;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

	@Autowired
	private CustomerDao custdao;

	@Autowired
	private OrderDao dao;

	@Autowired
	private OrderService orderService;

	@PostMapping("")
	public String bookOrder(@Valid @RequestBody OrderRequest request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return bindingResult.getAllErrors()
					.stream()
					.map(ObjectError::getDefaultMessage)
					.collect(Collectors.joining(", "));
		}
		Order order = orderService.createOrder(request);
		return "Order created having Id { " +order.getId() +" }";
	}


	@GetMapping("")
	public List<Order> getOrder(){
		return(List<Order>) dao.findAll();
	}

	@GetMapping("/{id}")
	public Order getOrder(@PathVariable("id") int id) {
		return dao.findById(id).get();
	}

//	@GetMapping("/OrderCustIdId/{id}")
//	public Optional<List<Order>> getAllOrderCustId(@PathVariable("id") int id) {
//		return orderService.findOrdersByCustId(id);
//	}


	@PutMapping("")
	private String update(@RequestBody Order order) {
		 Order orderSave = dao.save(order);
		return "Updated product with Id { " +orderSave.getId() +" }";
	}

	@DeleteMapping("/{id}")
	public String deleteOrder(@PathVariable("id") int id) {
		dao.deleteById(id);
		return "Product deleted with Id { " +id + " } " ;
	}

	@PostMapping("/{orderId}")
	public String addInstallment(@PathVariable("orderId") int orderId) {
		orderService.createNewInstallment(orderId);
		return "Order installment successfully paid for Id { "+ orderId + " }";
	}
}
