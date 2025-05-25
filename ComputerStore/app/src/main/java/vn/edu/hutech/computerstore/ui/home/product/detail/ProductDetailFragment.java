package vn.edu.hutech.computerstore.ui.home.product.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.hutech.computerstore.R;
import vn.edu.hutech.computerstore.databinding.FragmentProductDetailBinding;
import vn.edu.hutech.computerstore.model.product.Product;
import vn.edu.hutech.computerstore.ui.base.LoginAccountManager;
import vn.edu.hutech.computerstore.utils.DrawableUtils;
import vn.edu.hutech.computerstore.utils.ProductNumberUtils;

@AndroidEntryPoint
public class ProductDetailFragment extends Fragment {

    private FragmentProductDetailBinding binding;

    private ProductDetailViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);

        viewModel.getProductState().observe(getViewLifecycleOwner(), product -> {
            Glide.with(view)
                 .load(product.getImagePath())
                 .placeholder(DrawableUtils.getCircularProgress(getContext()))
                 .into(binding.productDetailIvImage);

            binding.productDetailTvTitle.setText(product.getName());
            binding.productDetailTvPrice.setText(ProductNumberUtils.toCurrencyVND(product.getPrice()));
            binding.productDetailTvBrand.setText(product.getBrand());
            binding.productDetailTvWarranty.setText(product.getWarranty());
            binding.productDetailTvDescription.setText(product.getDescription());
        });

        viewModel.getOrderState().observe(getViewLifecycleOwner(), orderState -> {
            Toast.makeText(getContext(),
                           orderState.isSuccess() ? R.string.product_detail_add_to_cart_success : R.string.product_detail_add_to_cart_failure,
                           Toast.LENGTH_SHORT)
                 .show();
        });

        binding.productDetailBtnAddToCart.setOnClickListener(v -> {
            viewModel.addToCart(LoginAccountManager.getInstance().getUser().getUserId(),
                                viewModel.getProductState().getValue().getId(),
                                1);
        });


        viewModel.setProductState((Product) getArguments().get("product"));
    }
}