package vn.edu.hutech.computerstore.ui.home.product.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.tabs.TabLayout;

import java.util.Locale;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.hutech.computerstore.R;
import vn.edu.hutech.computerstore.databinding.FragmentProductListBinding;
import vn.edu.hutech.computerstore.model.product.Category;
import vn.edu.hutech.computerstore.model.product.Product;
import vn.edu.hutech.computerstore.ui.base.LoginAccountManager;
import vn.edu.hutech.computerstore.ui.home.product.detail.ProductDetailViewModel;

@AndroidEntryPoint
public class ProductListFragment extends Fragment {

    private ProductListAdapter productListAdapter;

    private ProductListViewModel listViewModel;
    private ProductDetailViewModel detailViewModel;
    private ProductFilterViewModel filterViewModel;

    private FragmentProductListBinding binding;
    private ProductFilterDialog productFilterDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productFilterDialog = new ProductFilterDialog();
        productFilterDialog.setOnClickApply(productQuery -> {
            productQuery.setProductName(binding.homeEtSearch.getText().toString());
            listViewModel.fetchProductsByCriteria(productQuery);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void observeStates() {
        listViewModel.getProductListState().observe(getViewLifecycleOwner(), products -> {
            if (Objects.nonNull(products)) {
                productListAdapter.setProducts(products);
            }
        });

        listViewModel.getCategoriesState().observe(getViewLifecycleOwner(), categories -> {
            binding.homeTlCategory.removeAllTabs();

            final TabLayout.Tab allTab = binding.homeTlCategory.newTab();
            allTab.setText(getString(R.string.home_product_list_category_all_name));
            allTab.setTag(Category.ALL);
            binding.homeTlCategory.addTab(allTab);

            categories.forEach(category -> {
                final TabLayout.Tab tab = binding.homeTlCategory.newTab();
                tab.setText(category.getName());
                tab.setTag(category);
                binding.homeTlCategory.addTab(tab);
            });
        });

        detailViewModel.getOrderState().observe(getViewLifecycleOwner(), orderState -> {
            Toast.makeText(getContext(),
                           orderState.isSuccess() ? R.string.product_detail_add_to_cart_success : R.string.product_detail_add_to_cart_failure,
                           Toast.LENGTH_SHORT)
                 .show();
        });
    }

    private void searchProduct(String searchText) {
        binding.homeTlCategory.getTabAt(0).select();
        filterViewModel.getProductQueryFilter().getValue().setProductName(searchText.toLowerCase(Locale.US));
        listViewModel.fetchProductsByCriteria(filterViewModel.getProductQueryFilter().getValue());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listViewModel = new ViewModelProvider(this).get(ProductListViewModel.class);
        detailViewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);
        filterViewModel = new ViewModelProvider(getActivity()).get(ProductFilterViewModel.class);
        observeStates();

        productListAdapter = new ProductListAdapter(getContext());
        productListAdapter.setOnItemClickListener(v -> {
            final Product product = (Product) v.getTag();
            final Bundle productBundle = new Bundle();
            productBundle.putSerializable("product", product);
            Navigation.findNavController(getView())
                      .navigate(R.id.action_nav_home_product_list_nav_home_product_detail, productBundle);
        });

        productListAdapter.setOnCartClickListener(v -> {
            final Product product = (Product) v.getTag();
            detailViewModel.addToCart(LoginAccountManager.getInstance().getUserId(),
                                      product.getId(),
                                      1);
        });

        binding.homeRvProduct.setAdapter(productListAdapter);

        binding.homeEtSearch.setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchProduct(binding.homeEtSearch.getText().toString());
                return true;
            }
            return false;
        });

        binding.homeTlCategory.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                final Category category = (Category) tab.getTag();
                if (Objects.isNull(category)) {
                    return;
                }

                if (Category.ALL.equals(category)) {
                    listViewModel.fetchAllProducts();
                } else {
                    listViewModel.fetchProductsByCategoryPath(category.getPath());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                onTabSelected(tab);
            }
        });

        binding.homeIbFilter.setOnClickListener(v -> {
            productFilterDialog.show(getChildFragmentManager(), "filterDialog");
        });

        listViewModel.fetchCategories();
    }

}