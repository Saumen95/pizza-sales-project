package com.elrich.pizzasalesproject.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails{

	  @Id
	  @GeneratedValue (strategy = GenerationType.IDENTITY)
	  @Column(name = "order_details_id")
	  private long id;

	  @OneToOne(cascade = CascadeType.ALL)
	  @JoinColumn(name = "order_id", referencedColumnName = "order_id")
	  private Orders orderId;
	  
	  @OneToMany(fetch = FetchType.LAZY)
	  @JoinColumn(name = "pizza_id", nullable = false)
	  @OnDelete(action = OnDeleteAction.CASCADE)
	  private Pizza pizzaId;

	  @Column(name = "quantity")
	  private int quantity;
	
}
