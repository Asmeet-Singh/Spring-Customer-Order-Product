package com.springsql.spring.mssql.api.demo.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
@Entity(name = "orderItem")
@Table(name = "orderItem")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "orderId", nullable = false)
	@ToString.Exclude
	private Order order;

	@ManyToOne
	@JoinColumn(name = "productId", nullable = false)
	private Product product;
	
	private Integer price;
	
	private Integer quantity;
}
