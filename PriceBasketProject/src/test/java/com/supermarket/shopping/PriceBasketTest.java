package com.supermarket.shopping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

import com.supermarket.model.Basket;
import com.supermarket.model.Discount;
import com.supermarket.model.Offer;
import com.supermarket.model.Product;
import com.supermarket.service.PricingServiceImpl;
import com.supermarket.service.ReportService;


public class PriceBasketTest {

	private static final int BREAD_DISCOUNT = 40;
	private static final int APPLES_DISCOUNT = 10;
	private static final int NO_DISCOUNT = 0;
	private static final int SOUP_PRICE = 65;
	private static final int BREAD_PRICE = 80;
	private static final int MILK_PRICE = 130;
	private static final int APPLES_PRICE = 100;
	private Product apples;
	private Product milk;
	private Product bread;
	private Product soup;
	List<Product> products ;
	
	private Discount applesDiscount;
	private Discount breadDiscount;
	
	private Basket shoppingBasket;		
		
	private Offer offer;
	private List<Offer> offersList;
	
	private PricingServiceImpl pricingService;
	private ReportService reportService;
	
	public Product getAProduct(String name){
	    apples = new Product("Apples", APPLES_PRICE,1);
		milk = new Product("Milk",MILK_PRICE,1);
		bread = new Product("Bread",BREAD_PRICE,1);
		soup = new Product("Soup",SOUP_PRICE,1);
		
		products =  new ArrayList<>();
		products.add(apples);
		products.add(milk);
		products.add(bread);
		products.add(soup);
		
		return products.stream().filter(p -> p.getName().equals(name)).findFirst().get();
	}
	
	public Basket createABasket(String[] args){
		
		List<Product> productList = new ArrayList<>();
		List<String> items = Arrays.asList(args);
		for(String item : items){
			productList.add(getAProduct(item));
		}
		Basket basket =  new Basket(productList);
		
		return basket;
	}
		
	public List<Offer> createOffers(){	
		offersList = new ArrayList<>();
		Product p = getAProduct("Apples");
		applesDiscount = new Discount(p, 10);
		
		offer = new Offer(p, 1, applesDiscount);
		offersList.add(offer);
		
		Product s = getAProduct("Soup");
		Product b = getAProduct("Bread");
		breadDiscount = new Discount(b, 50);		
		
		offer = new Offer(s, 2, breadDiscount);
		offersList.add(offer);
		
		return offersList;
	}
	
	@Test
	public void testCreateABasketOfApplesAndCalculatePrice(){
		String[] selection = {"Apples"}; 
		shoppingBasket = createABasket(selection);
		pricingService = new PricingServiceImpl();
		int priceInPence = pricingService.performPricing(shoppingBasket);
		Assert.assertEquals(apples.getPrice(), priceInPence);
	}
	
	
	@Test
	public void testCreateABasketOfDiscountedApplesAndFindDiscountPrice(){
		String[] selection = {"Apples"}; 
		shoppingBasket = createABasket(selection);
		
		offersList = createOffers();
		pricingService = new PricingServiceImpl();
		
		int discountPriceInPence = pricingService.calculateDiscount(shoppingBasket, offersList);
		Assert.assertEquals(APPLES_DISCOUNT, discountPriceInPence);
	}
	
	@Test
	public void testCreateABasketOf2DiscountedApplesAndFindDiscountPrice(){
		String[] selection = {"Apples","Apples"}; 
		shoppingBasket = createABasket(selection);
		
		offersList = createOffers();
		pricingService = new PricingServiceImpl();
		
		int discountPriceInPence = pricingService.calculateDiscount(shoppingBasket, offersList);
		Assert.assertEquals(APPLES_DISCOUNT*2, discountPriceInPence);
	}	
	
	@Test
	public void createABasketWith2SoupProductsAndCalculateThePrice(){
		String[] selection = {"Soup","Soup"}; 
		shoppingBasket = createABasket(selection);
		pricingService = new PricingServiceImpl();
		int priceInPence = pricingService.performPricing(shoppingBasket);
		Assert.assertEquals(soup.getPrice() * 2, priceInPence);
	}
	
	@Test
	public void noDiscountIfYouBuy2SoupWithoutBread(){
		String[] selection = {"Soup","Soup"}; 
		shoppingBasket = createABasket(selection);
		offersList = createOffers();
		pricingService = new PricingServiceImpl();
		int discountPriceInPence = pricingService.calculateDiscount(shoppingBasket, offersList);
		Assert.assertEquals(NO_DISCOUNT, discountPriceInPence);
	}
	
	@Test
	public void getDiscountIfYouBuy2SoupAndBread(){
		String[] selection = {"Soup","Bread","Soup"}; 
		shoppingBasket = createABasket(selection);
		
		offersList = createOffers();
		pricingService = new PricingServiceImpl();
		int discountPriceInPence = pricingService.calculateDiscount(shoppingBasket, offersList);
		Assert.assertEquals(BREAD_DISCOUNT, discountPriceInPence);
	}
	
	@Test
	public void getDiscountIfYouBuy2SoupAnd2Bread(){
		String[] selection = {"Soup","Bread","Soup","Bread"}; 
		shoppingBasket = createABasket(selection);
		
		offersList = createOffers();
		pricingService = new PricingServiceImpl();
		int discountPriceInPence = pricingService.calculateDiscount(shoppingBasket, offersList);
		Assert.assertEquals(BREAD_DISCOUNT, discountPriceInPence);
	}
	
	@Test
	public void getDiscountIfYouBuy3SoupAnd2Bread(){
		String[] selection = {"Soup","Bread","Soup","Bread","Soup"}; 
		shoppingBasket = createABasket(selection);
		
		offersList = createOffers();
		pricingService = new PricingServiceImpl();
		int discountPriceInPence = pricingService.calculateDiscount(shoppingBasket, offersList);
		Assert.assertEquals(BREAD_DISCOUNT, discountPriceInPence);
	}

	@Test
	public void getSingleBreadDiscountEvenIfYouBuy4SoupAndBread(){
		String[] selection = {"Soup","Bread","Soup","Soup","Soup"}; 
		shoppingBasket = createABasket(selection);
		
		offersList = createOffers();
		pricingService = new PricingServiceImpl();
		int discountPriceInPence = pricingService.calculateDiscount(shoppingBasket, offersList);
		Assert.assertEquals(BREAD_DISCOUNT, discountPriceInPence);
	}
	
	@Test
	public void get2BreadDiscountsIfYouBuy4SoupAnd2Bread(){
		String[] selection = {"Soup","Bread","Bread","Soup","Soup","Soup"}; 
		shoppingBasket = createABasket(selection);
		
		offersList = createOffers();
		pricingService = new PricingServiceImpl();
		int discountPriceInPence = pricingService.calculateDiscount(shoppingBasket, offersList);
		Assert.assertEquals(BREAD_DISCOUNT*2, discountPriceInPence);
	}
	
	@Test(expected=NoSuchElementException.class)
	public void fail_testCreateABasketWithUnknownProduct() throws Exception{
		
		String[] selection = {"Beetroot"}; 
		shoppingBasket = createABasket(selection);		
	}
	
	@Test
	public void inputTest1(){
		String[] selection = {"Apples","Milk","Bread"}; 
		shoppingBasket = createABasket(selection);
		pricingService = new PricingServiceImpl();
		reportService = new ReportService();
		int priceInPence = pricingService.performPricing(shoppingBasket);
		Assert.assertEquals(310, priceInPence);
		System.out.println(reportService.getSubtotalMessage(priceInPence));
		offersList = createOffers();
		int discountPriceInPence = pricingService.calculateDiscount(shoppingBasket, offersList);
		Assert.assertEquals(applesDiscount.getDiscountPercent(), discountPriceInPence);				
		System.out.println(reportService.getOffersMessage(applesDiscount, discountPriceInPence));
		
		System.out.println(reportService.getTotalMessage(discountPriceInPence));
	}
	
	@Test
	public void inputTest2(){
		String[] selection = {"Milk"}; 
		shoppingBasket = createABasket(selection);
		pricingService = new PricingServiceImpl();
		reportService = new ReportService();
		int priceInPence = pricingService.performPricing(shoppingBasket);
		Assert.assertEquals(MILK_PRICE, priceInPence);
		
		System.out.println(reportService.getSubtotalMessage(priceInPence));
		offersList = createOffers();
		int discountPriceInPence = pricingService.calculateDiscount(shoppingBasket, offersList);
		Assert.assertEquals(NO_DISCOUNT, discountPriceInPence);
		System.out.println(reportService.getOffersMessage(applesDiscount, discountPriceInPence));
		System.out.println(reportService.getTotalMessage(discountPriceInPence));		
	}
}
