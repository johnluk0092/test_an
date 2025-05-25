package vn.edu.hutech.computerstore.model.order;

import com.google.firebase.firestore.DocumentId;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Order {

    @DocumentId
    private String userId;

    private List<OrderDetail> orderDetails;

    public Order() {
        orderDetails = new ArrayList<>();
    }

}
