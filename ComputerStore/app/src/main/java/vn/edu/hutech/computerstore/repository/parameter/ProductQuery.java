package vn.edu.hutech.computerstore.repository.parameter;

import com.google.firebase.firestore.Query;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductQuery {

    @Builder.Default
    private String productName = "";

    @Builder.Default
    private String brandName = "";

    @Builder.Default
    private long priceFrom = 0;

    @Builder.Default
    private long priceTo = 999000000;

    @Builder.Default
    private OrderField orderField = OrderField.PRICE;

    @Builder.Default
    private Query.Direction orderDirection = Query.Direction.DESCENDING;

    public enum OrderField {
        PRICE("price"), PRODUCT_NAME("name");

        private String value;
        OrderField(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
