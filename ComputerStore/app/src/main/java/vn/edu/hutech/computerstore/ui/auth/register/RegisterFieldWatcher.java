package vn.edu.hutech.computerstore.ui.auth.register;

import android.text.Editable;
import android.text.TextWatcher;

public class RegisterFieldWatcher implements TextWatcher {
    private Runnable validator;

    public RegisterFieldWatcher(Runnable validator) {
        this.validator = validator;
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    public void afterTextChanged(Editable s) {}

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        validator.run();
    }
}
