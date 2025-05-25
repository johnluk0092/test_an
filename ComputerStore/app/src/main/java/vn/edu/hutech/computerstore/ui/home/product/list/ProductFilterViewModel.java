package vn.edu.hutech.computerstore.ui.home.product.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import vn.edu.hutech.computerstore.repository.parameter.ProductQuery;
import vn.edu.hutech.computerstore.ui.base.BaseViewModel;

@HiltViewModel
public class ProductFilterViewModel extends BaseViewModel {

    private final MutableLiveData<ProductQuery> productQueryFilterState = new MutableLiveData<>();


    @Inject
    public ProductFilterViewModel() {
        productQueryFilterState.setValue(ProductQuery.builder().build());
    }

    public LiveData<ProductQuery> getProductQueryFilter() {
        return productQueryFilterState;
    }

    public void setProductQueryFilterState(ProductQuery productQuery) {
        productQueryFilterState.setValue(productQuery);
    }

}
