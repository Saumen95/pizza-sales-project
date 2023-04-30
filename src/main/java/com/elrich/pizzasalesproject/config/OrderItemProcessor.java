package com.elrich.pizzasalesproject.config;

import org.springframework.batch.item.ItemProcessor;

import com.elrich.pizzasalesproject.models.Orders;

public class OrderItemProcessor implements ItemProcessor<Orders, Orders>{

	@Override
	public Orders process(Orders item) throws Exception {
		// TODO Auto-generated method stub
		return item;
	}

}
