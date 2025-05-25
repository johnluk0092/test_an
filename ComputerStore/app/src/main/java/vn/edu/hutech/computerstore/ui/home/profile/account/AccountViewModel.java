package vn.edu.hutech.computerstore.ui.home.profile.account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import vn.edu.hutech.computerstore.model.user.User;
import vn.edu.hutech.computerstore.ui.base.BaseViewModel;
import vn.edu.hutech.computerstore.ui.base.LoginAccountManager;

@HiltViewModel
public class AccountViewModel extends BaseViewModel {
    private MutableLiveData<User> userState = new MutableLiveData<>();

    @Inject
    public AccountViewModel() {
        userState.setValue(LoginAccountManager.getInstance().getUser());
    }

    public LiveData<User> getUserState() {
        return userState;
    }

}
