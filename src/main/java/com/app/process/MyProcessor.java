package com.app.process;

import org.springframework.batch.item.ItemProcessor;

import com.app.model.Product;

public class MyProcessor implements ItemProcessor<Product,Product>{

	@Override
	public Product process(Product p) throws Exception {
		// TODO Auto-generated method stub
		double cost=p.getCost();
		p.setDisc(cost*3/100.0);
		p.setGst(cost*12/100.0);
		return p;
	}

}
