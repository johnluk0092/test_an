package vn.edu.hutech.computerstore.ui.home.order.list;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import lombok.Getter;
import vn.edu.hutech.computerstore.R;

@Getter
public class OrderListViewItemHolder extends RecyclerView.ViewHolder {

    private final TextView tvOrderDateTime;
    private final TextView tvOrderStatus;
    private final TextView tvTotalItems;
    private final TextView tvTotalPrice;

    public OrderListViewItemHolder(@NonNull View itemView) {
        super(itemView);

        tvOrderDateTime = itemView.findViewById(R.id.order_list_tv_order_date);
        tvOrderStatus = itemView.findViewById(R.id.order_list_tv_order_status);
        tvTotalItems = itemView.findViewById(R.id.order_list_tv_total_items);
        tvTotalPrice = itemView.findViewById(R.id.order_list_tv_total_price);
    }

}
