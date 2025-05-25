package vn.edu.hutech.computerstore.ui.home.product.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.firestore.Query;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.hutech.computerstore.databinding.DialogProductFilterBinding;
import vn.edu.hutech.computerstore.repository.parameter.ProductQuery;

@AndroidEntryPoint
public class ProductFilterDialog extends DialogFragment {

    private DialogProductFilterBinding binding;

    private OnClickApplyProductFilter onClickApply;

    private ProductFilterViewModel filterViewModel;

    public void setOnClickApply(OnClickApplyProductFilter onClickApply) {
        this.onClickApply = onClickApply;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogProductFilterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        filterViewModel = new ViewModelProvider(getActivity()).get(ProductFilterViewModel.class);

        binding.productListFilterBtnApply.setOnClickListener(v -> {
            final ProductQuery productQuery = filterViewModel.getProductQueryFilter().getValue();
            productQuery.setBrandName(binding.productListFilterDialogEtBrand.getText().toString());
            productQuery.setPriceFrom(Long.parseLong(binding.productListFilterDialogEtPriceFrom.getText().toString()));
            productQuery.setPriceTo(Long.parseLong(binding.productListFilterDialogEtPriceTo.getText().toString()));
            productQuery.setOrderField(binding.productListFilterDialogRgSortByPrice.isChecked() ? ProductQuery.OrderField.PRICE : ProductQuery.OrderField.PRODUCT_NAME);
            productQuery.setOrderDirection(binding.productListFilterDialogRgSortOrderAsc.isChecked() ? Query.Direction.ASCENDING : Query.Direction.DESCENDING);

            filterViewModel.setProductQueryFilterState(productQuery);
            onClickApply.onClick(productQuery);
            dismiss();
        });

        binding.productListFilterBtnCancel.setOnClickListener(v -> {
            dismiss();
        });
    }

    public interface OnClickApplyProductFilter {
        void onClick(ProductQuery productQuery);
    }

}
