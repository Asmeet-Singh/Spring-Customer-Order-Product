package com.springsql.spring.mssql.api.demo;

import com.springsql.spring.mssql.api.demo.dao.CustomerDao;
import com.springsql.spring.mssql.api.demo.dao.OrderDao;
import com.springsql.spring.mssql.api.demo.dao.ProductDao;
import com.springsql.spring.mssql.api.demo.dto.OrderRequest;
import com.springsql.spring.mssql.api.demo.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class SpringbootMssqlApplicationTestsOrder extends SpringbootMssqlApplicationTestCases{

    @Autowired
    private OrderService oService;

    @MockBean
    OrderDao oDao;

    private static Validator validator;

    @BeforeAll
    public static void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void testCheckSignShouldReturnNegativeOrder() {
        OrderRequest orderRequest = new OrderRequest();
        Set<ConstraintViolation<OrderRequest>> violations = validator.validate(orderRequest);
        Assertions.assertTrue(violations.isEmpty());
        orderRequest.setNumOfInstallments(6);
        violations = validator.validate(orderRequest);
        Assertions.assertFalse(violations.isEmpty());

        orderRequest.setNumOfInstallments(-1);
        violations = validator.validate(orderRequest);
        Assertions.assertFalse(violations.isEmpty());

    }
}
