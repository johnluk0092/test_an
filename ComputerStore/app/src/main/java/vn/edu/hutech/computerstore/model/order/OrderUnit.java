package vn.edu.hutech.computerstore.model.order;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderUnit implements Serializable {
    private String productId;
    private String productImagePath;
    private String productName;
    private int quantity;
    private long price;
}
