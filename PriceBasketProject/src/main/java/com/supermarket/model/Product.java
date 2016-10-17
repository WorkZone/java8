package com.supermarket.model;

public class Product {

	private String name;	
	private int price; 
	private int unit;

	public String getName() {
		return name;
	}
	
	public int getPrice() {
		return price;
	}
	
	public int getUnit() {
		return unit;
	}	

	public Product(String name, int price, int unit) {
		super();
		this.name = name;
		this.price = price;
		this.unit = unit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + price;
		result = prime * result + unit;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price != other.price)
			return false;
		if (unit != other.unit)
			return false;
		return true;
	}
	
	
}
