package vn.edu.hutech.computerstore.ui.home.product.list;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import lombok.Getter;
import vn.edu.hutech.computerstore.R;

@Getter
public class ProductListViewHolder extends RecyclerView.ViewHolder {

    private final ImageView ivProductImage;
    private final TextView tvProductName;
    private final TextView tvPrice;
    private final ImageButton ibAddCart;

    public ProductListViewHolder(@NonNull View itemView) {
        super(itemView);

        ivProductImage = itemView.findViewById(R.id.home_product_iv_image);
        tvProductName = itemView.findViewById(R.id.home_product_tv_name);
        tvPrice = itemView.findViewById(R.id.home_product_tv_price);
        ibAddCart = itemView.findViewById(R.id.home_product_ib_add_cart);
    }

}
