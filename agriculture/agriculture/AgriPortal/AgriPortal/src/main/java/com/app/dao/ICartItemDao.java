package com.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pojos.Cart;
import com.app.pojos.CartItem;
import com.app.service.CartServiceImpl;

public interface ICartItemDao extends JpaRepository<CartItem, Long> {

	List<CartItem> findByProductId(long pid);

	boolean existsByProductId(long pid);

	List<CartItem> findByCart(Cart cart);

}
