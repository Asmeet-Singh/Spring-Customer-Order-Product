package com.springsql.spring.mssql.api.demo;

import com.springsql.spring.mssql.api.demo.dao.CustomerDao;
import com.springsql.spring.mssql.api.demo.dao.ProductDao;
import com.springsql.spring.mssql.api.demo.dto.CustomerRequest;
import com.springsql.spring.mssql.api.demo.model.Customer;
import com.springsql.spring.mssql.api.demo.service.CustomerService;
import com.springsql.spring.mssql.api.demo.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SpringbootMssqlApplicationTestsCustomer extends SpringbootMssqlApplicationTestCases{

    @Autowired
    private CustomerService cService;

    @MockBean
    CustomerDao cDao;

    @Test
    public void testCreateCustomer() {

        CustomerRequest cReq = new CustomerRequest();
        cReq.setName("Shivam");

        Customer customer = new Customer();
        customer.setName("Shivam");

        when(cDao.save(any())).thenReturn(customer);
        Customer createdCustomer = cService.createCustomer(cReq);
        Assertions.assertEquals(createdCustomer.getName(), cReq.getName());
    }
}
