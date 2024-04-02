package com.product.test.BoGoApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class BoGoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoGoApplication.class, args);
	}
	
	 @PostMapping("/applyDiscount")
	    public DiscountResponse applyDiscount(@RequestBody DiscountRequest request) {
	        List<Integer> productPrices = request.getProductPriceList();
	        List<Integer> discountedItems = new ArrayList<>();
	        List<Integer> payableItems = new ArrayList<>();

	        // Sort product prices in descending order
	        productPrices.sort((a, b) -> b - a);

	        int i = 0;
	        while (i < productPrices.size()) {
	            discountedItems.add(productPrices.get(i));
	            if (i + 1 < productPrices.size() && productPrices.get(i).equals(productPrices.get(i + 1))) {
	                // If next price is same, include it in discounted items
	                discountedItems.add(productPrices.get(i + 1));
	                i += 2;
	            } else {
	                i++;
	            }
	            if (i < productPrices.size()) {
	                payableItems.add(productPrices.get(i));
	            }
	            i++;
	        }

	        return new DiscountResponse(discountedItems, payableItems);
	    }

	    static class DiscountRequest {
	        private List<Integer> productPriceList;

	        public List<Integer> getProductPriceList() {
	            return productPriceList;
	        }

	        public void setProductPriceList(List<Integer> productPriceList) {
	            this.productPriceList = productPriceList;
	        }
	    }

	    static class DiscountResponse {
	        private List<Integer> discountedItems;
	        private List<Integer> payableItems;

	        public DiscountResponse(List<Integer> discountedItems, List<Integer> payableItems) {
	            this.discountedItems = discountedItems;
	            this.payableItems = payableItems;
	        }

	        public List<Integer> getDiscountedItems() {
	            return discountedItems;
	        }

	        public List<Integer> getPayableItems() {
	            return payableItems;
	        }
	    }
}
