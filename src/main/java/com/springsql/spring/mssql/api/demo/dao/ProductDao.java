package com.springsql.spring.mssql.api.demo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springsql.spring.mssql.api.demo.model.Product;

import java.util.Optional;

@Repository
public interface ProductDao extends CrudRepository<Product, Integer>{

    Optional<Product> findByName(String name);
}
