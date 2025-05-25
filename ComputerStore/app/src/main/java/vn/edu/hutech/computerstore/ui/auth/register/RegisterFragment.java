package vn.edu.hutech.computerstore.ui.auth.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.hutech.computerstore.R;
import vn.edu.hutech.computerstore.databinding.FragmentRegisterBinding;
import vn.edu.hutech.computerstore.model.user.User;
import vn.edu.hutech.computerstore.ui.home.MainActivity;

@AndroidEntryPoint
public class RegisterFragment extends Fragment {

    private static final int MIN_YEAR_OLD_MEMBER = 16;

    private RegisterViewModel viewModel;
    private FragmentRegisterBinding binding;

    final DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.CANADA);

    public RegisterFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private boolean validateName() {
        return viewModel.validateName(binding.registerEtName.getText().toString());
    }

    private boolean validatePhone() {
        return viewModel.validatePhone(binding.registerEtPhone.getText().toString());
    }

    private boolean validateBirthday() {
        return viewModel.validateBirthday(binding.registerEtBirthday.getText().toString());
    }

    private boolean validateEmail() {
        return viewModel.validateEmail(binding.registerEtEmail.getText().toString());
    }

    private boolean validatePassword() {
        return viewModel.validatePassword(binding.registerEtPassword.getText().toString(),
                                   binding.registerEtPassword2.getText().toString());
    }

    private boolean isRegisterFieldsValid() {
        return validateName() && validatePhone() && validateBirthday() && validateEmail() && validatePassword();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        final Calendar birthdayMaxYear = Calendar.getInstance();
        birthdayMaxYear.add(Calendar.YEAR, MIN_YEAR_OLD_MEMBER * -1);
        binding.registerEtBirthday.setText(dateFormatter.format(birthdayMaxYear.getTime()));

        binding.registerBtnRegister.setOnClickListener(v -> {
            if (isRegisterFieldsValid()) {
                binding.registerPbLoading.setVisibility(View.VISIBLE);
                final User user = new User(binding.registerEtEmail.getText().toString(), binding.registerEtName.getText().toString(), binding.registerEtBirthday.getText().toString(), binding.registerEtPhone.getText().toString());
                viewModel.register(user, binding.registerEtPassword.getText().toString());
            }
        });

        binding.registerTvLogin.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_nav_auth_register_to_nav_auth_login);
        });

        viewModel.getRegisterResultState().observe(getViewLifecycleOwner(), registerState -> {
            binding.registerPbLoading.setVisibility(View.GONE);
            if (Objects.nonNull(registerState.getUser())) {
                startActivity(new Intent(getContext(), MainActivity.class));
                return;
            }

            if (Objects.nonNull(getContext()) && Objects.nonNull(getContext().getApplicationContext())) {
                Toast.makeText(getContext().getApplicationContext(), registerState.getError(), Toast.LENGTH_LONG).show();
            }
        });

        binding.registerEtName.addTextChangedListener(new RegisterFieldWatcher(this::validateName));
        binding.registerEtPhone.addTextChangedListener(new RegisterFieldWatcher(this::validatePhone));
        binding.registerEtEmail.addTextChangedListener(new RegisterFieldWatcher(this::validateEmail));
        binding.registerEtPassword.addTextChangedListener(new RegisterFieldWatcher(this::validatePassword));
        binding.registerEtPassword2.addTextChangedListener(new RegisterFieldWatcher(this::validatePassword));

        binding.registerEtBirthday.setFocusable(false);
        binding.registerEtBirthday.setOnClickListener(v -> {
            final DateValidatorPointBackward dateValidator = DateValidatorPointBackward.before(birthdayMaxYear.getTimeInMillis());
            final MaterialDatePicker datePicker =
                    MaterialDatePicker.Builder.datePicker()
                                              .setTitleText(R.string.register_birthday_picker_title)
                                              .setCalendarConstraints(new CalendarConstraints.Builder()
                                                                              .setValidator(dateValidator)
                                                                              .build())
                                              .setSelection(birthdayMaxYear.getTimeInMillis())
                                              .build();
            datePicker.addOnPositiveButtonClickListener(o -> {
                binding.registerEtBirthday.setText(dateFormatter.format(new Date((long) o)));
                validateBirthday();
            });

            datePicker.addOnNegativeButtonClickListener(o -> {
                validateBirthday();
            });

            datePicker.show(getChildFragmentManager(), "birthday");
        });

        viewModel.getFormState().observe(getViewLifecycleOwner(), formState -> {
            if (formState.getErrorName() != -1) {
                binding.registerTilName.setError(getString(formState.getErrorName()));
            } else {
                binding.registerTilName.setErrorEnabled(false);
            }

            if (formState.getErrorPhone() != -1) {
                binding.registerTilPhone.setError(getString(formState.getErrorPhone()));
            } else {
                binding.registerTilPhone.setErrorEnabled(false);
            }

            if (formState.getErrorBirthday() != -1) {
                binding.registerTilBirthday.setError(getString(formState.getErrorBirthday()));
            } else {
                binding.registerTilBirthday.setErrorEnabled(false);
            }

            if (formState.getErrorEmail() != -1) {
                binding.registerTilEmail.setError(getString(formState.getErrorEmail()));
            } else {
                binding.registerTilEmail.setErrorEnabled(false);
            }

            if (formState.getErrorPassword() != -1) {
                binding.registerTilPassword.setError(getString(formState.getErrorPassword()));
            } else {
                binding.registerTilPassword.setErrorEnabled(false);
            }

        });
    }
}