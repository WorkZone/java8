package com.supermarket.model;

public class Discount {

	private Product product;	
	private int discountPercent ;
	
	public Product getProduct() {
		return product;
	}
	
	public int getDiscountPercent() {
		return discountPercent;
	}
	
	public Discount(Product product, int discountPercent) {
		super();
		this.product = product;		
		this.discountPercent = discountPercent;
	}
	
	@Override
	public String toString() {
		return "Discount [product=" + product + ", discountPercent=" + discountPercent + "]";
	}
	
}
