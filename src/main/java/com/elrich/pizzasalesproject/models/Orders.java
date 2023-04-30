package com.elrich.pizzasalesproject.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Orders{

	  @Id
	  @GeneratedValue (strategy = GenerationType.IDENTITY)
	  @Column(name = "id")
	  private long id;

	  @Column(name = "order_id")
	  private String orderId;

	  @Column(name = "order_date")
	  private Date orderDate;

	  @Column(name = "order_time")
	  private String time;
	
}
