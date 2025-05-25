package vn.edu.hutech.computerstore.ui.home.order.detail;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import lombok.Getter;
import vn.edu.hutech.computerstore.R;

@Getter
public class OrderListDetailItemViewHolder extends RecyclerView.ViewHolder {

    private final ImageView ivProductImage;
    private final TextView tvProductName;
    private final TextView tvPrice;
    private final TextView tvQuantity;

    public OrderListDetailItemViewHolder(@NonNull View itemView) {
        super(itemView);
        ivProductImage = itemView.findViewById(R.id.order_list_detail_item_iv_image);
        tvProductName = itemView.findViewById(R.id.order_list_detail_item_tv_product_name);
        tvPrice = itemView.findViewById(R.id.order_list_detail_item_tv_product_price);
        tvQuantity = itemView.findViewById(R.id.order_list_detail_item_tv_quantity);
    }

}
