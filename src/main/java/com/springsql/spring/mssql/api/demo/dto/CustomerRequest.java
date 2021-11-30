package com.springsql.spring.mssql.api.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CustomerRequest {

    //private int id;
    @NotBlank(message = "Customer name should not be blank!")
    private String name;


}
