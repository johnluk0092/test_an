package vn.edu.hutech.computerstore.ui.auth.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.hutech.computerstore.R;
import vn.edu.hutech.computerstore.databinding.FragmentLoginBinding;
import vn.edu.hutech.computerstore.ui.base.BaseFragment;
import vn.edu.hutech.computerstore.ui.home.MainActivity;

@AndroidEntryPoint
public class LoginFragment extends BaseFragment {

    private LoginViewModel loginViewModel;
    private FragmentLoginBinding binding;
    private boolean isEditEmail = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        final EditText etEmail = binding.loginEtEmail;
        final EditText etPassword = binding.loginEtPassword;
        final Button btnLogin = binding.loginBtnSignIn;
        final Button btnRegister = binding.loginBtnRegister;
        final ProgressBar pbLoading = binding.loginPbLoading;

        loginViewModel.getRememberUserEmail().ifPresent(email -> {
            if (!isEditEmail) {
                etEmail.setText(email);
            }
        });

        loginViewModel.getLoginFormState().observe(getViewLifecycleOwner(), loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            btnLogin.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getUsernameError() != null) {
                etEmail.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                etPassword.setError(getString(loginFormState.getPasswordError()));
            }
        });

        loginViewModel.getLoginResultState().observe(getViewLifecycleOwner(), loginResult -> {
            if (loginResult == null) {
                return;
            }
            pbLoading.setVisibility(View.GONE);
            if (loginResult.getError() != null) {
                if (getContext() != null && getContext().getApplicationContext() != null) {
                    Toast.makeText(
                            getContext().getApplicationContext(),
                            loginResult.getError(),
                            Toast.LENGTH_LONG).show();
                }
            }

            if (loginResult.getUser() != null) {
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
            }
        });

        final TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isEditEmail = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(etEmail.getText().toString(),
                                                etPassword.getText().toString());
            }
        };

        etEmail.addTextChangedListener(afterTextChangedListener);
        etPassword.addTextChangedListener(afterTextChangedListener);
        etPassword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                btnLogin.callOnClick();
            }
            return false;
        });

        btnLogin.setOnClickListener(v -> {
            pbLoading.setVisibility(View.VISIBLE);
            loginViewModel.login(etEmail.getText().toString(), etPassword.getText().toString());
        });

        btnRegister.setOnClickListener(v -> Navigation.findNavController(getView()).navigate(R.id.nav_auth_register));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}