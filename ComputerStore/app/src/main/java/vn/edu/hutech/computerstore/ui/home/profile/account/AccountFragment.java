package vn.edu.hutech.computerstore.ui.home.profile.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.hutech.computerstore.R;
import vn.edu.hutech.computerstore.databinding.FragmentAccountBinding;

@AndroidEntryPoint
public class AccountFragment extends Fragment {

    private AccountViewModel viewModel;

    private FragmentAccountBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(AccountViewModel.class);

        viewModel.getUserState().observe(getViewLifecycleOwner(), user -> {
            binding.accountEtEmail.setText(user.getUserId());
            binding.accountEtName.setText(user.getName());
            binding.accountEtBirthday.setText(user.getBirthday());
            binding.accountEtPhone.setText(user.getPhoneNumber());
        });
    }
}