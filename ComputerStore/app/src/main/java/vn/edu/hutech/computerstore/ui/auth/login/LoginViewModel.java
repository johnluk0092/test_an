package vn.edu.hutech.computerstore.ui.auth.login;

import android.util.Log;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import vn.edu.hutech.computerstore.R;
import vn.edu.hutech.computerstore.model.user.UserSetting;
import vn.edu.hutech.computerstore.service.UserService;
import vn.edu.hutech.computerstore.ui.base.BaseViewModel;

@HiltViewModel
public class LoginViewModel extends BaseViewModel {

    private static final String LOG_TAG = LoginViewModel.class.getSimpleName();


    private final MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private final MutableLiveData<LoginResultState> loginResultState = new MutableLiveData<>();

    private final UserService userService;

    @Inject
    public LoginViewModel(UserService userService) {
        this.userService = userService;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResultState> getLoginResultState() {
        return loginResultState;
    }

    public void login(String email, String password) {
        userService.login(email, password)
                   .addOnSuccessListener(user -> loginResultState.setValue(new LoginResultState(user)))
                   .addOnFailureListener(ex -> {
                       Log.d(LOG_TAG, "Failed to login " + email, ex);
                       loginResultState.setValue(new LoginResultState(R.string.login_failed));
                   });
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.login_invalid_email, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.login_invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    public Optional<String> getRememberUserEmail() {
        final List<UserSetting> userSettings = userService.findAllRememberAccount();
        if (userSettings.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(userSettings.get(0).getEmail());
    }

    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(username).matches();
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

}