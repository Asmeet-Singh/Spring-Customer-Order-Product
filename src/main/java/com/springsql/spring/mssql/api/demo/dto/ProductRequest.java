package com.springsql.spring.mssql.api.demo.dto;

import com.springsql.spring.mssql.api.demo.model.Order;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
@Data
public class ProductRequest {

	@NotBlank(message = "Product name cannot be blank")
	private String name;

	@Min(value = 1, message = "Quantity should be greater than 0")
	@Max(value = 10000, message = "Quantity should be lesser than 10000")
	private Integer quantity;

	@Min(value = 1, message = "Price should be greater than 0")
	@Max(value = 10000000, message = "Price should be lesser than 10000000")
	private Integer price;

}
