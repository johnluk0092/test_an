package vn.edu.hutech.computerstore.ui.home.product.list;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import vn.edu.hutech.computerstore.model.product.Category;
import vn.edu.hutech.computerstore.model.product.Product;
import vn.edu.hutech.computerstore.repository.parameter.ProductQuery;
import vn.edu.hutech.computerstore.service.CartService;
import vn.edu.hutech.computerstore.service.ProductService;
import vn.edu.hutech.computerstore.ui.base.ActionResultState;
import vn.edu.hutech.computerstore.ui.base.BaseViewModel;
import vn.edu.hutech.computerstore.ui.base.SingleLiveEvent;

@HiltViewModel
public class ProductListViewModel extends BaseViewModel {
    private static final String LOG_TAG = ProductListViewModel.class.getSimpleName();

    private final MutableLiveData<List<Category>> categoriesState = new MutableLiveData<>();
    private final MutableLiveData<List<Product>> productsState = new MutableLiveData<>();
    private final SingleLiveEvent<ActionResultState> orderState = new SingleLiveEvent<>();

    private final ProductService productService;
    private final CartService cartService;


    @Inject
    public ProductListViewModel(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

    public LiveData<List<Product>> getProductListState() {
        return productsState;
    }

    public LiveData<List<Category>> getCategoriesState() {
        return categoriesState;
    }

    public void fetchProductsByCategoryPath(String categoryPath) {
        productService.fetchProductsByCategoryPath(categoryPath)
                      .addOnSuccessListener(productsState::setValue)
                      .addOnFailureListener(ex -> Log.e(LOG_TAG, "Failed to fetch product of " + categoryPath, ex));
    }

    public void fetchAllProducts() {
        productService.fetchProducts()
                      .addOnSuccessListener(productsState::setValue)
                      .addOnFailureListener(ex -> Log.e(LOG_TAG, "Failed to fetch all products", ex));
    }

    public void fetchCategories() {
        productService.fetchCategories()
                      .addOnSuccessListener(categoriesState::setValue)
                      .addOnFailureListener(ex -> Log.e(LOG_TAG, "Failed to fetch categories. ", ex));
    }

    public void fetchProductsByCriteria(ProductQuery productQuery) {
        productService.fetchProductsByCriteria(productQuery)
                      .addOnSuccessListener(productsState::setValue)
                      .addOnFailureListener(ex -> Log.e(LOG_TAG, "Failed to fetch products by criteria. ", ex));

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
