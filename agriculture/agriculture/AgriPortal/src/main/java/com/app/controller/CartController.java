package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dao.ICartDao;
import com.app.dao.ICartItemDao;
import com.app.dao.IProductDao;
import com.app.dto.CartItemDto;
import com.app.pojos.Categories;
import com.app.pojos.Product;
import com.app.pojos.Role;
import com.app.pojos.SubCategories;
import com.app.service.ICartService;
import com.app.service.ICustomerService;

@RestController
@CrossOrigin(origins="*",allowedHeaders = "*")
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private ICartService cartItemService ;


	
	@PostMapping("/add/{userId}/{pid}")
	public ResponseEntity<?> addCarttItem(@PathVariable long userId,@PathVariable long pid){
		cartItemService.addCartItem(userId, pid);	
		return  ResponseEntity.ok("Product added to cart successfully!!!");
	}


	@GetMapping("/allItems/{userId}")
	public ResponseEntity<?> getAllCartItems(@PathVariable long userId) {
		List<CartItemDto> cartItems= cartItemService.getAllCartItemsInCart(userId);
		
		return ResponseEntity.ok(cartItems);

	}
	
	@PutMapping("/change/{itemId}/{userId}")
	public ResponseEntity<?> changeQuantity(@PathVariable long itemId,@PathVariable long userId,@RequestBody CartItemDto cartItemDto ){
		cartItemService.changeQuantity(itemId,userId,cartItemDto);
		return new ResponseEntity<>("success",  HttpStatus.OK);
	}
	
		@DeleteMapping("/{itemId}")
		public ResponseEntity<?> deleteCartItem(@PathVariable long itemId) {
			cartItemService.deleteItem(itemId);
			return  ResponseEntity.ok("Item Deleted Successfully!!!!");

	}

}
