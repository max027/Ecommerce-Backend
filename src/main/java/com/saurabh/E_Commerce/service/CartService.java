package com.saurabh.E_Commerce.service;

import com.saurabh.E_Commerce.dto.CartDtos.CartDto;
import com.saurabh.E_Commerce.dto.CartDtos.CartItemsDto;
import com.saurabh.E_Commerce.dto.CartDtos.CartRequestDto;
import com.saurabh.E_Commerce.exception.ApiError;
import com.saurabh.E_Commerce.models.Cart;
import com.saurabh.E_Commerce.models.CartItems;
import com.saurabh.E_Commerce.models.Products;
import com.saurabh.E_Commerce.models.Users;
import com.saurabh.E_Commerce.repository.CartItemsRepository;
import com.saurabh.E_Commerce.repository.CartRepository;
import com.saurabh.E_Commerce.repository.ProductsRepository;
import com.saurabh.E_Commerce.security.AuthUtils;
import com.saurabh.E_Commerce.utils.DataMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final ProductsRepository productsRepository;
    private final AuthUtils authUtils;
    private final CartItemsRepository cartItemsRepository;
    private Users fetchUser(){
        return authUtils.getCurrentUser();
    }
    private Products fetchProducts(long id){
        return productsRepository.findById(id).orElseThrow(
                ()->new ApiError("Product not found id:"+id, HttpStatus.NOT_FOUND.value())
        );
    }

    public CartDto getUserCart() {
        Users users=fetchUser();
        Cart cart=users.getCart();
        List<CartItems> items=cart.getItems();

        CartDto cartDto=new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setUsersId(users.getUserId());

        List<CartItemsDto>list=new ArrayList<>();
        double totalAmount=0.0d;

        for (CartItems cartItems:items){
            CartItemsDto dto= DataMapper.convertToCartItemsDto(cartItems);
            double quantity=Double.parseDouble(String.valueOf(cartItems.getQuantity()));
            totalAmount+=quantity*cartItems.getPrice();
            list.add(dto);
        }

        cartDto.setItems(list);
        cartDto.setTotalAmount(totalAmount);

        return cartDto;

    }
    private Cart checkCart(){
        Users users=fetchUser();
        return cartRepository
                .findByUsersUserId(users.getUserId())
                .orElseGet(()->{
                    Cart cart1=new Cart();
                    cart1.setUsers(users);
                    return cart1;
                });
    }

    public void addToCart(@Valid CartRequestDto request) {
        Cart cart=checkCart();

        Products products=fetchProducts(request.getProductId());
        Optional<CartItems>existingItem=cart.getItems().stream().filter(i->i.getProducts().getName().equals(products.getName())).findFirst();
        if (existingItem.isPresent()){
           CartItems cartItems=existingItem.get();
            cartItems.setQuantity(request.getQuantity());
        }else{
            CartItems items=new CartItems();
            items.setCart(cart);
            items.setPrice(products.getPrice());
            items.setProducts(products);
            items.setQuantity(request.getQuantity());

            cart.setItems(List.of(items));
        }

        cartRepository.save(cart);
    }

    public void updateCart(int quantity,long id) {
        Cart cart=checkCart();
        CartItems items=cartItemsRepository.findById(id).orElseThrow(()->new ApiError("no such items id:"+id,HttpStatus.NOT_FOUND.value()));
        if (cart.getId()!=items.getCart().getId()){
            throw new ApiError("No such item in cart id:"+id,HttpStatus.FORBIDDEN.value());
        }
        items.setId(id);
        items.setQuantity(quantity);

        cartItemsRepository.save(items);
    }

    public void removeFromCart(long id) {
        Cart cart=checkCart();
        CartItems items=cartItemsRepository.findById(id).orElseThrow(()->new ApiError("no such items id:"+id,HttpStatus.NOT_FOUND.value()));
        if (cart.getId()!=items.getCart().getId()){
            throw new ApiError("No such item in cart id:"+id,HttpStatus.FORBIDDEN.value());
        }
        cartItemsRepository.delete(items);
    }

    public void clearCart() {
        Cart cart=checkCart();
        cart.getItems().clear();
    }
}
