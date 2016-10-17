package com.supermarket.service;

import java.math.BigDecimal;

import com.supermarket.model.Discount;

public class ReportService {

	private static final String NO_DISCOUNT_MESSAGE="(No offers available)";
	BigDecimal subtotal;
	
	public ReportService(){		
	}
	
	public String getSubtotalMessage(int priceInPence){
		subtotal = BigDecimal.valueOf(priceInPence).divide(BigDecimal.valueOf(100));		
		return String.format("Subtotal: £%-3.2f", subtotal);
	}
	
	public String getOffersMessage(Discount discountProduct, int discountPriceInPence){
		if(discountPriceInPence == 0){
			return NO_DISCOUNT_MESSAGE;
		}else{
			return String.format("%s %d%% off: -%dp",discountProduct.getProduct().getName(), discountProduct.getDiscountPercent(), discountPriceInPence);		
		}
	}
	
	public String getTotalMessage(int discountPriceInPence){
		
		BigDecimal total = subtotal.subtract(BigDecimal.valueOf(discountPriceInPence).divide(BigDecimal.valueOf(100)));
		String totalString = String.format("Total: £%-3.2f\n",  total);
		
		return totalString;
	}
}
