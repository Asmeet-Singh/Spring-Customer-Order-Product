package com.springsql.spring.mssql.api.demo.dao;

import com.springsql.spring.mssql.api.demo.model.Order;
import com.springsql.spring.mssql.api.demo.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemDao extends JpaRepository<OrderItem, Integer> {
}
