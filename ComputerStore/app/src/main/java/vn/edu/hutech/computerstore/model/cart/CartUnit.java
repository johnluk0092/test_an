package vn.edu.hutech.computerstore.model.cart;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import vn.edu.hutech.computerstore.model.product.Product;

@Data
@NoArgsConstructor
@AllArgsConstructor
@IgnoreExtraProperties
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CartUnit {

    @EqualsAndHashCode.Include
    private String productId;

    private Product product;

    private int quantity;
    private boolean isSelected;

    public CartUnit(String productId) {
        this.productId = productId;
    }

    public CartUnit(String productId, int quantity, boolean isSelected) {
        this.productId = productId;
        this.quantity = quantity;
        this.isSelected = isSelected;
    }

    @Exclude
    public Product getProduct() {
        return product;
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void decreaseQuantity(int quantity) {
        this.quantity = Math.max(this.quantity - quantity, 0);
    }

}
