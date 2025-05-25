package vn.edu.hutech.computerstore.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import vn.edu.hutech.computerstore.ui.base.BaseViewModel;

public class MainActivityViewModel extends BaseViewModel {

    private MutableLiveData<Integer> bottomTabIdState = new MutableLiveData<>();

    public MainActivityViewModel() {
    }

    public LiveData<Integer> getBottomTabIdState() {
        return bottomTabIdState;
    }

    public void setBottomTabIdState(int resId) {
        bottomTabIdState.setValue(resId);
    }

}
