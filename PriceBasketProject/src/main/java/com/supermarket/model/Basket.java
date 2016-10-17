package com.supermarket.model;

import java.util.HashMap;
import java.util.List;

public class Basket {
	
	HashMap<Product, Integer> items;
	
	public void setItems(HashMap<Product, Integer> items) {
		this.items = items;
	}

	public HashMap<Product, Integer> getItems() {
		return items;
	}
	public Basket(List<Product> products){
		
		items = new HashMap<Product,Integer>();
	    for(Product p: products) {
	       Integer i = items.get(p);
	       if (i ==  null) {
	           i = 0;
	       }
	       items.put(p, i + 1);
	    }
	}

	@Override
	public String toString() {
		return "Basket [items=" + items + "]";
	}	
	
}
