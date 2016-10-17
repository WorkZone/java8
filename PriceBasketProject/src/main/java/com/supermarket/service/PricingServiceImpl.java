	package com.supermarket.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import com.supermarket.model.Basket;
import com.supermarket.model.Discount;
import com.supermarket.model.Offer;
import com.supermarket.model.Product;

public class PricingServiceImpl implements PricingService {

	/**
	 * This method will calculate total using standard prices. For simplicity all calculations are done using integer
	 * though BigDecimal can be used
	 */
	@Override
	public int performPricing(Basket basket) {			
		return basket.getItems().entrySet().stream()
		.map(e->e.getKey().getPrice()*e.getValue())
		.reduce(0, Integer::sum);		
	}

	/**
	 * This method will calculate total discount prices. For simplicity all calculations are done using integer
	 * though BigDecimal can be used
	 */
	@Override
	public int calculateDiscount(Basket basket, List<Offer> offersList) {
				
		int discountTotal =0;
		Product mainOfferingProduct = null;
		Product discountProduct = null;
		Product cartProduct = null;
		
		//get customer basket items
		HashMap<Product, Integer> cart =  basket.getItems();
		//iterate and check for discounts
		for(Entry<Product, Integer> item: cart.entrySet()){	
			cartProduct = item.getKey();
			int quantityBought = item.getValue();
			
			for(Offer offer: offersList){
				mainOfferingProduct = offer.getProduct();
				int qualifiedQuantity = offer.getQuantity();
				discountProduct = offer.getDiscount().getProduct();
				
				if(discountProduct.equals(cartProduct)){ //if my purchased produced in a discount product
					
					if(cart.containsKey(mainOfferingProduct)){ //check main offering product is in my cart
						int mainOfferingProductsQuantity = cart.get(mainOfferingProduct);
						if(mainOfferingProductsQuantity >= qualifiedQuantity ){ //if my cart quantity is >= minimum discount quantity
							int multiDiscount = mainOfferingProductsQuantity/qualifiedQuantity;
							
							discountTotal += (offer.getDiscount().getDiscountPercent() * (cartProduct.getPrice()* Integer.min(multiDiscount, quantityBought)))/100;
						}
					}						
				}
			}
		}		
		return discountTotal;
	}
}

