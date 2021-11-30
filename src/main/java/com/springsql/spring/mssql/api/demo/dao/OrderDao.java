package com.springsql.spring.mssql.api.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springsql.spring.mssql.api.demo.model.Order;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDao extends JpaRepository<Order, Integer>{

    Optional<List<Order>> findByCustomer_Id(int customerId);
}
