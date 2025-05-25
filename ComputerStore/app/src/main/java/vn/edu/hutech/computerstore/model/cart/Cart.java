package vn.edu.hutech.computerstore.model.cart;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Cart {
    @DocumentId
    private String userId;

    private List<CartUnit> cartUnits;

    private String shipAddress;

    public Cart() {
        cartUnits = new ArrayList<>();
    }

    public Cart(String userId) {
        this();
        this.userId = userId;
    }

    @Exclude
    public String getShipAddress() {
        return shipAddress;
    }

    @Exclude
    public long getTotalPrice() {
        return cartUnits.stream()
                        .mapToLong(p -> p.getProduct().getPrice() * Math.max(p.getQuantity(), 1))
                        .sum();
    }

}
