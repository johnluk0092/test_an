package vn.edu.hutech.computerstore.model.order;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDetail implements Serializable {
    private String orderItemId;
    private String orderDateTime;
    private List<OrderUnit> orderUnits;
    private String address;
    private boolean isPayed;
    private String payDateTime;
    private OrderStatus orderStatus;

    public OrderDetail(String orderItemId, String orderDateTime, List<OrderUnit> orderUnits, String address) {
        this.orderItemId = orderItemId;
        this.orderDateTime = orderDateTime;
        this.orderUnits = orderUnits;
        this.isPayed = false;
        this.address = address;
        this.orderStatus = OrderStatus.CUSTOMER_CONFIRMED;
    }

    @Exclude
    public long getTotalPrice() {
        return orderUnits.stream()
                         .mapToLong(o -> o.getQuantity() * o.getPrice())
                         .sum();
    }

}
