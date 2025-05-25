package vn.edu.hutech.computerstore.ui.home.product.detail;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import vn.edu.hutech.computerstore.model.product.Product;
import vn.edu.hutech.computerstore.service.CartService;
import vn.edu.hutech.computerstore.ui.base.ActionResultState;
import vn.edu.hutech.computerstore.ui.base.BaseViewModel;
import vn.edu.hutech.computerstore.ui.base.SingleLiveEvent;

@HiltViewModel
public class ProductDetailViewModel extends BaseViewModel {

    private static final String LOG_TAG = ProductDetailViewModel.class.getSimpleName();

    private final MutableLiveData<Product> productState = new MutableLiveData<>();

    private final SingleLiveEvent<ActionResultState> orderState = new SingleLiveEvent<>();


    private final CartService cartService;

    @Inject
    public ProductDetailViewModel(CartService cartService) {
        this.cartService = cartService;
    }

    public LiveData<Product> getProductState() {
        return productState;
    }

    public void setProductState(Product product) {
        productState.setValue(product);
    }

    public LiveData<ActionResultState> getOrderState() {
        return orderState;
    }

    public void addToCart(String userId, String productId, int quantity) {
        cartService.add(userId, productId, quantity)
                   .addOnSuccessListener(r -> orderState.setValue(ActionResultState.success()))
                   .addOnFailureListener(ex -> {
                       Log.e(LOG_TAG, "Failed to product to cart " + productId);
                       orderState.setValue(ActionResultState.error());
                   });
    }

}
