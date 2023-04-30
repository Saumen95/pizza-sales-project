package com.elrich.pizzasalesproject.config;

import org.springframework.batch.item.ItemProcessor;

import com.elrich.pizzasalesproject.models.PizzaTypes;

public class PizzaTypeItemProcessor implements ItemProcessor<PizzaTypes, PizzaTypes>{

	@Override
	public PizzaTypes process(PizzaTypes item) throws Exception {
		// TODO Auto-generated method stub
		return item;
	}

}
