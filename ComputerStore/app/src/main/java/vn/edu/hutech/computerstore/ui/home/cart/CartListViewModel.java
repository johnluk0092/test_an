package vn.edu.hutech.computerstore.ui.home.cart;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import vn.edu.hutech.computerstore.model.cart.Cart;
import vn.edu.hutech.computerstore.model.cart.CartUnit;
import vn.edu.hutech.computerstore.service.CartService;
import vn.edu.hutech.computerstore.service.OrderService;
import vn.edu.hutech.computerstore.ui.base.ActionResultState;
import vn.edu.hutech.computerstore.ui.base.BaseViewModel;
import vn.edu.hutech.computerstore.ui.base.SingleLiveEvent;

@HiltViewModel
public class CartListViewModel extends BaseViewModel {
    private static final String LOG_TAG = CartListViewModel.class.getSimpleName();

    private final MutableLiveData<Cart> cartState = new MutableLiveData<>();

    private final SingleLiveEvent<ActionResultState> checkoutState = new SingleLiveEvent<>();

    private final CartService cartService;
    private final OrderService orderService;

    @Inject
    public CartListViewModel(CartService cartService, OrderService orderService) {
        this.cartService = cartService;
        this.orderService = orderService;
    }

    public LiveData<Cart> getCartState() {
        return cartState;
    }

    public MutableLiveData<ActionResultState> getCheckoutState() {
        return checkoutState;
    }

    public void fetchCart(String userId) {
        cartService.fetchCarts(userId)
                   .addOnSuccessListener(cartState::setValue)
                   .addOnFailureListener(ex -> Log.d(LOG_TAG, "Failed to fetch cart from " + userId, ex));
    }

    public void removeProduct(String userId, String productId) {
        cartService.removeProduct(userId, productId)
                   .addOnSuccessListener(t -> fetchCart(userId))
                   .addOnFailureListener(ex -> Log.e(LOG_TAG, "Failed to remove product " + productId, ex));
    }

    public void updateCart(String userId, CartUnit cartUnit) {
        cartService.update(userId, cartUnit)
                   .addOnSuccessListener(t -> fetchCart(userId))
                   .addOnFailureListener(ex -> Log.e(LOG_TAG, "Failed to update cart " + cartUnit, ex));
    }

    public void order(String userId, Cart cart) {
        orderService.add(userId, cart)
                    .addOnSuccessListener(t -> checkoutState.setValue(ActionResultState.success()))
                    .addOnFailureListener(ex -> {
                        Log.d(LOG_TAG, "Failed to checkout " + userId + ";" + cart, ex);
                        checkoutState.setValue(ActionResultState.error());
                    });
    }

}
