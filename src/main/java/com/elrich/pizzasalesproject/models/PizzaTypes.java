package com.elrich.pizzasalesproject.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PizzaTypes {

	  @Id
	  @GeneratedValue (strategy = GenerationType.IDENTITY)
	  @Column(name = "id")
	  private long id;

	  @OneToOne(cascade = CascadeType.ALL)
	  @JoinColumn(name = "pizza_type_id", referencedColumnName = "pizza_type_id")
	  private Pizza pizzaTypeId;

	  @Column(name = "name")
	  private String name;
	  
	  @Column(name = "category")
	  private String category;
	  
	  @Column(name = "ingredients")
	  private String ingredients;
	
}