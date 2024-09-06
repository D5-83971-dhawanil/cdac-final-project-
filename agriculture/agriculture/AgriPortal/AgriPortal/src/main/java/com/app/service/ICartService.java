package com.app.service;

import java.util.List;

import com.app.dto.CartItemDto;

public interface ICartService {

	void addCartItem(long userId, long pid);

	List<com.app.dto.CartItemDto> getAllCartItemsInCart(long userId);

	void changeQuantity(long itemId, long userId, CartItemDto cartItemDto);

	void deleteItem(long itemId);

}
