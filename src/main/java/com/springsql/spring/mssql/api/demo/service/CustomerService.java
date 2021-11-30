package com.springsql.spring.mssql.api.demo.service;

import com.springsql.spring.mssql.api.demo.dao.CustomerDao;
import com.springsql.spring.mssql.api.demo.dto.CustomerRequest;
import com.springsql.spring.mssql.api.demo.dto.ProductRequest;
import com.springsql.spring.mssql.api.demo.exceptions.DoesNotExistException;
import com.springsql.spring.mssql.api.demo.model.Customer;
import com.springsql.spring.mssql.api.demo.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@Slf4j
//@Transactional(rollbackFor = Exception.class)
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    public Customer createCustomer(final CustomerRequest customerRequest) {
        Customer buildCustomer = Customer.builder().name(customerRequest.getName()).build();
        log.info("Creating a new customer: {}", customerRequest);
        Customer savedCustomer = customerDao.save(buildCustomer);
        log.info("Created a new customer: {}", savedCustomer);
        return savedCustomer;
    }

    public Customer findById(Integer customerId) {
        return customerDao.findById(customerId).orElseThrow(() -> new DoesNotExistException("Customer with id: " + customerId + " does not exist!"));
    }
    @PutMapping("/updateCustomers")
    public Customer update(Integer id, CustomerRequest customerRequest)
    {
        Optional<Customer> optionalProduct = customerDao.findById(id);
        optionalProduct.orElseThrow(() -> new IllegalArgumentException("Customer with name: " + id + " does not exist." +
                " Can't update the customer details."));

        Customer buildProduct = Customer.builder()
                .id(id)
                .name(customerRequest.getName())
                .build();
        Customer savedCustomer = customerDao.save(buildProduct);
        log.info("Updated Customer with name: {}", id);
        return savedCustomer;
    }

    @DeleteMapping("/deleteCustomers/{id}")
    public void deleteCustomer(@PathVariable("id") int id) {
        customerDao.deleteById(id);
    }
}
