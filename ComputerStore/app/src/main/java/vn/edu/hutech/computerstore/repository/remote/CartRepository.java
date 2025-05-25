package vn.edu.hutech.computerstore.repository.remote;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import vn.edu.hutech.computerstore.model.cart.Cart;
import vn.edu.hutech.computerstore.model.cart.CartUnit;
import vn.edu.hutech.computerstore.model.product.Product;

@Singleton
public class CartRepository {
    private static final String LOG_TAG = CartRepository.class.getSimpleName();
    private static final String COLLECTION_CART = "cart";

    private FirebaseFirestore db;
    private ProductRepository productRepository;

    @Inject
    public CartRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
        db = FirebaseFirestore.getInstance();
    }

    public Task<Void> save(String userId, CartUnit cartUnit) {
        Log.d(LOG_TAG, "save " + userId + "; " + cartUnit);
        return db.collection(COLLECTION_CART).document(userId).get().onSuccessTask(cartResult -> {
            final Cart cart = Optional.ofNullable(cartResult.toObject(Cart.class)).orElse(new Cart(userId));
            final int cartUnitIndex = cart.getCartUnits().indexOf(cartUnit);
            if (cartUnitIndex != -1) {
                if (cartUnit.getQuantity() <= 0) {
                    cart.getCartUnits().remove(cartUnit);
                } else {
                    cart.getCartUnits().get(cartUnitIndex).setQuantity(cartUnit.getQuantity());
                }
            } else {
                cart.getCartUnits().add(cartUnit);
            }
            return db.collection(COLLECTION_CART).document(userId).set(cart);
        });
    }

    public Task<Cart> findByUserId(String userId) {
        return db.collection(COLLECTION_CART).document(userId).get().continueWithTask(cartResult -> {
            final Cart cart = Optional.ofNullable(cartResult.getResult().toObject(Cart.class)).orElse(new Cart(userId));

            final List<String> productIds = cart.getCartUnits()
                                                .stream()
                                                .map(CartUnit::getProductId)
                                                .collect(Collectors.toList());
            if (productIds.isEmpty()) {
                return db.collection(COLLECTION_CART)
                         .document(userId)
                         .set(cart)
                         .continueWith(t -> cart);
            }

            return productRepository.findByProductIdIn(productIds).continueWith(products -> {
                if (!products.isSuccessful()) {
                    return null;
                }
                final Map<String, Product> productMap = products.getResult()
                                                                .stream()
                                                                .collect(Collectors.toMap(Product::getId, Function.identity()));
                cart.getCartUnits()
                    .forEach(cartUnit -> cartUnit.setProduct(productMap.get(cartUnit.getProductId())));
                return cart;
            });
        });
    }

    public Task<Void> remove(String userId, String productId) {
        return db.collection(COLLECTION_CART).document(userId).get().onSuccessTask(cartResult -> {
            final Cart cart = cartResult.toObject(Cart.class);
            cart.getCartUnits().remove(new CartUnit(productId));
            return db.collection(COLLECTION_CART).document(userId).set(cart);
        });
    }

    public Task<Void> remove(String userId) {
        return db.collection(COLLECTION_CART).document(userId).delete();
    }

}
