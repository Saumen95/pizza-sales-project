package com.elrich.pizzasalesproject.config;

import org.springframework.batch.item.ItemProcessor;
import com.elrich.pizzasalesproject.models.Pizza;


public class PizzaItemProcessor implements ItemProcessor<Pizza, Pizza>{

	@Override
	public Pizza process(Pizza item) throws Exception {
		// TODO Auto-generated method stub
		return item;
	}

}
