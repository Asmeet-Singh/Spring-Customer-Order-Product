package com.springsql.spring.mssql.api.demo.service;

import com.springsql.spring.mssql.api.demo.dao.OrderDao;
import com.springsql.spring.mssql.api.demo.dto.OrderItemRequest;
import com.springsql.spring.mssql.api.demo.dto.OrderRequest;
import com.springsql.spring.mssql.api.demo.enums.OrderStatus;
import com.springsql.spring.mssql.api.demo.model.Customer;
import com.springsql.spring.mssql.api.demo.model.Order;
import com.springsql.spring.mssql.api.demo.model.OrderItem;
import com.springsql.spring.mssql.api.demo.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
//@Transactional(rollbackFor = Exception.class)
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    public Order createOrder(final OrderRequest orderRequest) {
        List<OrderItemRequest> orderItemRequests = orderRequest.getOrderItems();

        Optional<Integer> quantitySum = orderItemRequests.stream().map(OrderItemRequest::getQuantity).reduce(Integer::sum);
        if (quantitySum.get() > 5) {
            throw new IllegalArgumentException("Only 5 products can be bought in a single order");
        }
        Integer totalPrice = orderItemRequests.stream()
                .map(orderItemRequest -> productService.calculatePrice(orderItemRequest.getProductId(),
                        orderItemRequest.getQuantity()))
                .reduce(Integer::sum)
                .get();

        List<OrderItem> orderItems = orderItemRequests.stream().map(orderItemRequest -> {
            Product product = productService.findByProductId(orderItemRequest.getProductId());
            if (product.getQuantity() < orderItemRequest.getQuantity()) {
                throw new IllegalStateException("Product quantity left is less than the quantity asked");
            }
            return OrderItem.builder()
                    .price(product.getPrice())
                    .product(product)
                    .quantity(orderItemRequest.getQuantity())
                    .build();
        }).collect(Collectors.toList());
        Customer customer = customerService.findById(orderRequest.getCustomerId());

        Order order = Order.builder()
                .customer(customer)
                .orderItems(new ArrayList<>())
                .noOfInst(orderRequest.getNumOfInstallments())
                .totalPrice(totalPrice)
                .outstandingBalance(totalPrice - totalPrice / orderRequest.getNumOfInstallments())
                .status(orderRequest.getNumOfInstallments() == 1 ? OrderStatus.CLOSED.name() : OrderStatus.OPEN.name())
                .completedNumberOfInstallment(1)
                .build();
//        Order order1 = new Order();
//        order1.setStatus(orderRequest.getNumOfInstallments() == 1 ? OrderStatus.CLOSED.name() : OrderStatus.OPEN.name());

        orderItems.forEach(item -> order.addItem(item));
        Order save = orderDao.save(order);
        log.info("Order created successfully: {}", save);


        return save;
    }

    public Order createNewInstallment(int orderId) {
        Order order = findByOrderId(orderId);
        if (order.getCompletedNumberOfInstallment().equals(order.getNoOfInst())) {
            throw new IllegalStateException("All the installments are already paid");
        }
        order.setCompletedNumberOfInstallment(order.getCompletedNumberOfInstallment() + 1);
        order.setOutstandingBalance(order.getOutstandingBalance() - order.getTotalPrice() / order.getNoOfInst());
        if (order.getCompletedNumberOfInstallment().equals(order.getNoOfInst())) {
            order.setOutstandingBalance(0);
            order.setStatus(OrderStatus.CLOSED.name());
        }
        return orderDao.save(order);
    }

    public Order findByOrderId(int orderId) {
        return orderDao.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order with id " + orderId + " does not exists."));
    }


    public List<Order> findOrdersByCustId(int customerId){
        return orderDao.findByCustomer_Id(customerId).orElseThrow(() -> new IllegalArgumentException("Order with id " + customerId + " does not exists."));
    }
}
