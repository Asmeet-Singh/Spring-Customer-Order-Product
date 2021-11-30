package com.springsql.spring.mssql.api.demo.dto;


import com.springsql.spring.mssql.api.demo.model.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

	private Integer customerId;

	@Valid
	@Size(min = 1, max = 5, message = "Order items should be greater than 0 and less than 5")
	private List<OrderItemRequest> orderItems;

	@Min(value = 1, message = "number of installments should be greater than 0")
	@Max(value = 5, message = "number of installments should not be greater than 5")
	private Integer numOfInstallments;
}