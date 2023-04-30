package com.elrich.pizzasalesproject.models;

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
public class Pizza {

	  @Id
	  @GeneratedValue (strategy = GenerationType.IDENTITY)
	  @Column(name = "id")
	  private long id;

	  @Column(name = "pizza_id")
	  private String pizzaId;

	  @Column(name = "pizza_type_id")
	  private String pizzaTypeId;

	  @Column(name = "size")
	  private String size;
	  
	  @Column(name = "price")
	  private double price;
	
}
