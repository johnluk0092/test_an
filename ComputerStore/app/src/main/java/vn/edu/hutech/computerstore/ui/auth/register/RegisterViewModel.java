package vn.edu.hutech.computerstore.ui.auth.register;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import vn.edu.hutech.computerstore.R;
import vn.edu.hutech.computerstore.model.user.User;
import vn.edu.hutech.computerstore.service.UserService;
import vn.edu.hutech.computerstore.ui.base.BaseViewModel;

@HiltViewModel
public class RegisterViewModel extends BaseViewModel {

    private static final String LOG_TAG = RegisterViewModel.class.getSimpleName();

    private final UserService userService;

    private final MutableLiveData<RegisterState.Result> registerResultState = new MutableLiveData<>();
    private final MutableLiveData<RegisterState.Form> formState = new MutableLiveData<>();

    public LiveData<RegisterState.Result> getRegisterResultState() {
        return registerResultState;
    }

    public LiveData<RegisterState.Form> getFormState() {
        return formState;
    }

    @Inject
    public RegisterViewModel(UserService userService) {
        formState.setValue(new RegisterState.Form());
        this.userService = userService;
    }

    public void register(User user, String password) {
        userService.register(user, password)
                .addOnSuccessListener(u -> {
                    registerResultState.setValue(RegisterState.Result.success(user));
                })
                .addOnFailureListener(ex -> {
                    Log.e(LOG_TAG, "Failed to register user " + user, ex);
                    if (ex instanceof FirebaseAuthUserCollisionException) {
                        registerResultState.setValue(RegisterState.Result.fail(R.string.register_failed_email_existed));
                    } else {
                        registerResultState.setValue(RegisterState.Result.fail(R.string.register_failed));
                    }
                });
    }

    public boolean validateName(String name) {
        final boolean isNameValid = !TextUtils.isEmpty(name) && name.length() > 2;
        final RegisterState.Form form = this.formState.getValue().clone();
        form.setErrorName(isNameValid ? -1 : R.string.register_invalid_name);
        this.formState.setValue(form);
        return isNameValid;
    }

    public boolean validatePhone(String phone) {
        final boolean isPhoneValid = !TextUtils.isEmpty(phone) &&
                phone.length() > 9 && phone.length() < 13;
                Patterns.PHONE.matcher(phone).matches();
        final RegisterState.Form form = this.formState.getValue().clone();
        form.setErrorPhone(isPhoneValid ? -1 : R.string.register_invalid_phone);
        formState.setValue(form);
        return isPhoneValid;
    }

    public boolean validateBirthday(String birthday) {
        final boolean isBirthdayValid = !TextUtils.isEmpty(birthday);
        final RegisterState.Form form = this.formState.getValue().clone();
        form.setErrorBirthday(isBirthdayValid ? -1 : R.string.register_invalid_birthday);
        formState.setValue(form);
        return isBirthdayValid;
    }

    public boolean validateEmail(String email) {
        final boolean isEmailValid = !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
        final RegisterState.Form form = this.formState.getValue().clone();
        form.setErrorEmail(isEmailValid ? -1 : R.string.register_invalid_email);
        formState.setValue(form);
        return isEmailValid;
    }

    public boolean validatePassword(String password, String password2) {
        final boolean isPasswordValid = password.length() > 5 && password.equals(password2);
        final RegisterState.Form form = formState.getValue().clone();
        form.setErrorPassword(isPasswordValid ? -1 : R.string.register_invalid_password);
        formState.setValue(form);
        return isPasswordValid;
    }

}
