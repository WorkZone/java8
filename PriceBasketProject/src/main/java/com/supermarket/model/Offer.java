package com.supermarket.model;

public class Offer {

	private Product product;
	private int quantity;	
	private Discount discount;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}	
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Discount getDiscount() {
		return discount;
	}
	public void setDiscount(Discount discount) {
		this.discount = discount;
	}
	
	public Offer(Product product, int quantity, Discount discount) {
		super();
		this.product = product;
		this.quantity = quantity;
		this.discount = discount;
	}
	@Override
	public String toString() {
		return "Offer [product=" + product + ", quantity=" + quantity + ", discount=" + discount + "]";
	}
		
}
