package com.springsql.spring.mssql.api.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.springsql.spring.mssql.api.demo.dto.ProductRequest;
import com.springsql.spring.mssql.api.demo.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
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

import com.springsql.spring.mssql.api.demo.dao.ProductDao;
import com.springsql.spring.mssql.api.demo.model.Product;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/product")
@Slf4j
public class ProductController {

	@Autowired
	private ProductDao dao;

	@Autowired
	private ProductService productService;

	// POST /api/v1/product
	@PostMapping("")
	public String createProduct(@Valid @RequestBody ProductRequest productRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return bindingResult.getAllErrors()
					.stream()
					.map(objectError -> objectError.getDefaultMessage())
					.collect(Collectors.joining(", "));
		}
		log.info("Creating product: {}", productRequest);
		Product createdProduct = productService.createProduct(productRequest);
		log.info("Created product: {}", createdProduct);
		return "Product added having Id { " + createdProduct.getId() +" }";
	}

	// GET /api/v1/product/all
	@GetMapping("")
	public List<Product> getProduct(){
		return(List<Product>) dao.findAll();
	}
	
	@GetMapping("/{id}")
	public Product getProduct(@PathVariable("id") int id) {
		return dao.findById(id).get(); 
	}
	
	
	@PutMapping("/{id}")
	private String update(@PathVariable("id") int id, @Valid @RequestBody ProductRequest productRequest,
						  BindingResult bindingResult) {
		//dao.save(product);
		if (bindingResult.hasErrors()) {
			return bindingResult.getAllErrors()
					.stream()
					.map(ObjectError::getDefaultMessage)
					.collect(Collectors.joining(", "));
		}
		Product product = productService.updateProduct(id, productRequest);
		return "Updated product with Id { " +product.getId() +" }";
	}

	@DeleteMapping("/{id}")
	public String deleteProduct(@PathVariable("id") int id) {
		 dao.deleteById(id);
		 return "Product deleted with Id { " +id + " } " ;
	}
}
