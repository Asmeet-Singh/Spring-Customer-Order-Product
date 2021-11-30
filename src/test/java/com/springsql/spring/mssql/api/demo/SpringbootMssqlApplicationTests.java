package com.springsql.spring.mssql.api.demo;

import com.springsql.spring.mssql.api.demo.dao.CustomerDao;
import com.springsql.spring.mssql.api.demo.dao.OrderDao;
import com.springsql.spring.mssql.api.demo.dao.ProductDao;
import com.springsql.spring.mssql.api.demo.dto.CustomerRequest;
import com.springsql.spring.mssql.api.demo.dto.OrderRequest;
import com.springsql.spring.mssql.api.demo.dto.ProductRequest;
import com.springsql.spring.mssql.api.demo.model.Customer;
import com.springsql.spring.mssql.api.demo.model.Product;
import com.springsql.spring.mssql.api.demo.service.CustomerService;
import com.springsql.spring.mssql.api.demo.service.OrderService;
import com.springsql.spring.mssql.api.demo.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
class SpringbootMssqlApplicationTests extends SpringbootMssqlApplicationTestCases{

	@Test
	void contextLoads() {
	}

	@Autowired
	private ProductService pService;
	@Autowired
	private CustomerService cService;
	@Autowired
	private OrderService oService;

	@MockBean
	ProductDao pDao;
	@MockBean
	CustomerDao cDao;
	@MockBean
	OrderDao oDao;

	private static Validator validator;

	@BeforeAll
	public static void setup() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@Test
	public void testCreateProduct(){
		//Product product = new Product(1,"Laptop",10, 1000);
		ProductRequest pReq = new ProductRequest();
		pReq.setName("Mobile");
		pReq.setQuantity(10);
		pReq.setPrice(1000);

		Product product = new Product();
		product.setId(1);
		product.setName("Mobile");
		product.setQuantity(10);
		product.setPrice(1000);

		when(pDao.save(any())).thenReturn(product);
		Product createdProduct = pService.createProduct(pReq);
		Assertions.assertEquals(createdProduct.getPrice(), pReq.getPrice());
		Assertions.assertEquals(createdProduct.getQuantity(), pReq.getQuantity());
		Assertions.assertEquals(createdProduct.getName(), pReq.getName());

	}

		@Test
	public void testProductName(){
		ProductRequest pReq1 = new ProductRequest();
		pReq1.setName("ASMMM");
		pReq1.setQuantity(10);
		pReq1.setPrice(1000);
		Assertions.assertNotEquals("", pReq1.getName());
	}
	@Test
	public void testCheckSignShouldReturnNegative() {
		ProductRequest productRequest = new ProductRequest();
		productRequest.setPrice(100);
		productRequest.setQuantity(100);
		productRequest.setName("Hi");

		Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
		Assertions.assertTrue(violations.isEmpty());

		productRequest.setPrice(-10);
		violations = validator.validate(productRequest);
		Assertions.assertFalse(violations.isEmpty());

		productRequest.setPrice(100);
		productRequest.setQuantity(-1);
		violations = validator.validate(productRequest);
		Assertions.assertFalse(violations.isEmpty());

		productRequest.setName(null);
		productRequest.setQuantity(100);
		violations = validator.validate(productRequest);
		Assertions.assertFalse(violations.isEmpty());

		productRequest.setName("New Product");
		violations = validator.validate(productRequest);
		Assertions.assertTrue(violations.isEmpty());

		productRequest.setPrice(20000000);
		violations = validator.validate(productRequest);
		Assertions.assertFalse(violations.isEmpty());
	}
}
