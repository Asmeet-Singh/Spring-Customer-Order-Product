package com.springsql.spring.mssql.api.demo.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class OrderItemRequest {
    @Min(value = 1, message = "Enter a valid product id")
    private Integer productId;

    @Min(value = 1, message = "Quantity should be greater than 1")
    private Integer quantity;
}
