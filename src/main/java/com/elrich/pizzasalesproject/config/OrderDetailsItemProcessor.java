package com.elrich.pizzasalesproject.config;

import org.springframework.batch.item.ItemProcessor;

import com.elrich.pizzasalesproject.models.OrderDetails;

public class OrderDetailsItemProcessor implements ItemProcessor<OrderDetails, OrderDetails>{

	@Override
	public OrderDetails process(OrderDetails item) throws Exception {
		// TODO Auto-generated method stub
		return item;
	}

}
