package vn.edu.hutech.computerstore.ui.home.cart;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import lombok.Getter;
import vn.edu.hutech.computerstore.R;

@Getter
public class CartListItemViewHolder extends RecyclerView.ViewHolder {

    private final ImageView ivProductImage;
    private final TextView tvProductName;
    private final TextView tvPrice;
    private final ImageButton btnMinus;
    private final TextView tvQuantity;
    private final ImageButton btnAdd;
    private final ImageButton btnRemove;

    public CartListItemViewHolder(@NonNull View itemView) {
        super(itemView);

        ivProductImage = itemView.findViewById(R.id.cart_list_item_iv_image);
        tvProductName = itemView.findViewById(R.id.cart_list_item_tv_product_name);
        tvPrice = itemView.findViewById(R.id.cart_list_item_tv_product_price);
        btnMinus = itemView.findViewById(R.id.cart_list_item_btn_minus);
        tvQuantity = itemView.findViewById(R.id.cart_list_item_tv_quantity);
        btnAdd = itemView.findViewById(R.id.cart_list_item_btn_add);
        btnRemove = itemView.findViewById(R.id.cart_list_item_ib_remove);
    }

}
