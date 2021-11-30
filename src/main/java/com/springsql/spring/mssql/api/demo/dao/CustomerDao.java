package com.springsql.spring.mssql.api.demo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springsql.spring.mssql.api.demo.model.Customer;

@Repository
public interface CustomerDao extends CrudRepository<Customer, Integer>{

}
