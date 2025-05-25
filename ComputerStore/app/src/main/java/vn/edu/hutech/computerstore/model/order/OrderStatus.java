package vn.edu.hutech.computerstore.model.order;

import lombok.AllArgsConstructor;
import vn.edu.hutech.computerstore.R;

@AllArgsConstructor
public enum OrderStatus {

    CUSTOMER_CONFIRMED(R.string.order_list_order_status_custom_confirm),
    SELLER_CONFIRMED(R.string.order_list_order_status_seller_confirm),
    DELIVERING(R.string.order_list_order_status_delivering),
    DONE(R.string.order_list_order_status_done);

    private int resId;

    public int getResId() {
        return resId;
    }

}
