package com.example.Spring_starter.service;

import com.example.Spring_starter.model.CartItem;
import com.example.Spring_starter.model.Order;
import com.example.Spring_starter.model.OrderItem;
import com.example.Spring_starter.model.User;
import com.example.Spring_starter.repository.CartItemRepository;
import com.example.Spring_starter.repository.OrderRepository;
import com.example.Spring_starter.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public OrderService(
            OrderRepository orderRepository,
            CartItemRepository cartItemRepository,
            UserRepository userRepository
    ) {
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Order placeOrder(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        List<CartItem> cartItems = cartItemRepository.findByUser_Id(userId);
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Cannot place an order with an empty cart.");
        }

        Order order = new Order();
        order.setUser(user);
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            order.addItem(orderItem);

            BigDecimal lineTotal = cartItem.getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            total = total.add(lineTotal);
        }

        order.setTotal(total);
        Order savedOrder = orderRepository.save(order);
        // Order persist hone ke baad cart rows clear hoti hain; dono kaam ek hi transaction mein hain.
        cartItemRepository.deleteAll(cartItems);
        return savedOrder;
    }
}
