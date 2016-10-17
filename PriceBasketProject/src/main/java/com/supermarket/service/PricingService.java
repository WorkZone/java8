package com.supermarket.service;

import java.util.List;

import com.supermarket.model.Basket;
import com.supermarket.model.Discount;
import com.supermarket.model.Offer;
import com.supermarket.model.Product;

public interface PricingService {

	 int performPricing(Basket basket);
	 
	 int calculateDiscount(Basket basket, List<Offer> offersList);
}
