package com.springsql.spring.mssql.api.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "rs_order")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name = "id")
	private int id;

	@Column(name = "status")
	private String status;

	@ManyToOne
	private Customer customer;

	@Column(name = "noOfInst", nullable = false)
	private Integer noOfInst;

	@Column(name = "completedNumberOfInstallment", nullable = false)
	private Integer completedNumberOfInstallment;

	@Column(name = "totalPrice")
	private Integer totalPrice;

	@Column(name = "outstandingBalance")
	private Integer outstandingBalance;

	@OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
	@JsonIgnore
	private List<OrderItem> orderItems;


	public void addItem(OrderItem item) {
		this.orderItems.add(item);
		item.setOrder(this);
	}
}
