package com.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_exception.ResourceNotFoundException;
import com.app.dao.ICartDao;
import com.app.dao.ICartItemDao;
import com.app.dao.IProductDao;
import com.app.dto.CartItemDto;
import com.app.pojos.Cart;
import com.app.pojos.CartItem;
import com.app.pojos.Product;

@Service
@Transactional
public class CartServiceImpl implements ICartService {
    
    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    private ICartDao cartRepository;

    @Autowired
    private ICartItemDao cartItemRepository;

    @Autowired
    private IProductDao productDao;

    @Override
    public List<CartItemDto> getAllCartItemsInCart(long userId) {
        logger.info("Fetching all cart items for user with ID: {}", userId);
        Cart cart = cartRepository.findBycartOwnerId(userId);
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        List<CartItemDto> dtos = cartItems.stream()
                .map(c -> new CartItemDto(c.getId(), c.getQuantity(), c.getTotalPrice(), c.getProduct()))
                .collect(Collectors.toList());
        logger.info("Found {} items in cart for user with ID: {}", dtos.size(), userId);
        return dtos;
    }

    @Override
    public void changeQuantity(long itemId, long userId, CartItemDto cartItemDto) {
        logger.info("Changing quantity for cart item with ID: {} for user with ID: {}", itemId, userId);
        Cart cart = cartRepository.findBycartOwnerId(userId);
        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem with ID: " + itemId + " not found"));
        int oldQuantity = cartItem.getQuantity();
        double oldPrice = cartItem.getTotalPrice();
        cartItem.setQuantity(cartItemDto.getQuantity());
        cartItem.setTotalPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
        cart.setTotalItems(cart.getTotalItems() - oldQuantity + cartItem.getQuantity());
        cart.setTotalCartPrice(cart.getTotalCartPrice() - oldPrice + cartItem.getTotalPrice());
        logger.info("Updated cart item with ID: {}. New quantity: {}, New total price: {}", itemId, cartItem.getQuantity(), cartItem.getTotalPrice());
    }

    @Override
    public void deleteItem(long itemId) {
        logger.info("Deleting cart item with ID: {}", itemId);
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem with ID: " + itemId + " not found"));
        Cart cart = item.getCart();
        cart.setTotalCartPrice(cart.getTotalCartPrice() - item.getTotalPrice());
        cart.setTotalItems(cart.getTotalItems() - item.getQuantity());
        cartItemRepository.deleteById(itemId);
        logger.info("Deleted cart item with ID: {}. Updated cart total price: {}, total items: {}", itemId, cart.getTotalCartPrice(), cart.getTotalItems());
    }

    @Override
    public void addCartItem(long userId, long pid) {
        logger.info("Adding product with ID: {} to cart for user with ID: {}", pid, userId);
        Product product = productDao.findById(pid)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID: " + pid + " not found"));
        Cart cart = cartRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with ID: " + userId + " not found"));
        boolean itemExists = false;
        List<CartItem> list = cartItemRepository.findByProductId(pid);

        for (CartItem existingItem : list) {
            Cart itemCart = existingItem.getCart();
            if (itemCart.getId() == cart.getId()) {
                itemExists = true;
                existingItem.setQuantity(existingItem.getQuantity() + 1);
                existingItem.setTotalPrice(existingItem.getTotalPrice() + product.getPrice());
                cart.setTotalItems(cart.getTotalItems() + 1);
                cart.setTotalCartPrice(cart.getTotalCartPrice() + product.getPrice());
                logger.info("Updated existing cart item with ID: {}. New quantity: {}, New total price: {}", existingItem.getId(), existingItem.getQuantity(), existingItem.getTotalPrice());
                return;
            }
        }

        if (!itemExists) {
            CartItem newItem = new CartItem(1, product.getPrice());
            newItem.setCart(cart);
            newItem.setProduct(product);
            cartItemRepository.save(newItem);
            cart.setTotalItems(cart.getTotalItems() + 1);
            cart.setTotalCartPrice(cart.getTotalCartPrice() + product.getPrice());
            logger.info("Added new cart item with ID: {}. Quantity: 1, Price: {}", newItem.getId(), product.getPrice());
        }
    }
}
