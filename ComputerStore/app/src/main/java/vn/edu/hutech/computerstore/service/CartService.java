package vn.edu.hutech.computerstore.service;

import com.google.android.gms.tasks.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

import vn.edu.hutech.computerstore.model.cart.Cart;
import vn.edu.hutech.computerstore.model.cart.CartUnit;
import vn.edu.hutech.computerstore.repository.remote.CartRepository;

@Singleton
public class CartService extends BaseService {

    private final CartRepository cartRepository;

    @Inject
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Task<Void> add(String userId, String productId, int quantity) {
        return cartRepository.findByUserId(userId).onSuccessTask(task -> {
            final int idx = task.getCartUnits().indexOf(new CartUnit(productId));

            final CartUnit cartUnit;
            if (idx != -1) {
                cartUnit = task.getCartUnits().get(idx);
                cartUnit.increaseQuantity(1);
            } else {
                cartUnit = new CartUnit(productId, quantity, true);
            }

            return cartRepository.save(userId, cartUnit);
        });
    }

    public Task<Cart> fetchCarts(String userId) {
        return cartRepository.findByUserId(userId);
    }

    public Task<Void> removeProduct(String userId, String productId) {
        return cartRepository.remove(userId, productId);
    }

    public Task<Void> update(String userId, CartUnit cartUnit) {
        return cartRepository.save(userId, cartUnit);
    }

}
