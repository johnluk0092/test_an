package vn.edu.hutech.computerstore.ui.home.order.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.hutech.computerstore.R;
import vn.edu.hutech.computerstore.model.order.OrderDetail;
import vn.edu.hutech.computerstore.utils.ProductNumberUtils;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListViewItemHolder> {

    private final Context context;
    private List<OrderDetail> orderDetails;
    private View.OnClickListener onClickItemListener;

    public OrderListAdapter(Context context) {
        this.context = context;
        orderDetails = new ArrayList<>();
    }

    public void setOnClickItemListener(View.OnClickListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public void setOrderItems(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @NonNull
    @Override
    public OrderListViewItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderListViewItemHolder(LayoutInflater.from(context)
                                                         .inflate(R.layout.layout_order_list_item,
                                                                  parent,
                                                                  false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListViewItemHolder holder, int position) {
        final OrderDetail orderDetail = orderDetails.get(position);
        holder.itemView.setTag(orderDetail);
        holder.getTvOrderDateTime().setText(orderDetail.getOrderDateTime());
        holder.getTvOrderStatus().setText(context.getText(orderDetail.getOrderStatus().getResId()));
        holder.getTvTotalItems().setText(ProductNumberUtils.toNumberWithThousandSplit(orderDetail.getOrderUnits().size()));
        holder.getTvTotalPrice().setText(ProductNumberUtils.toCurrencyVND(orderDetail.getTotalPrice()));
        holder.itemView.setOnClickListener(onClickItemListener);
    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }

}
