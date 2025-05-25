package vn.edu.hutech.computerstore.ui.home.profile;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.hutech.computerstore.R;
import vn.edu.hutech.computerstore.databinding.FragmentProfileBinding;
import vn.edu.hutech.computerstore.ui.auth.AuthActivity;

@AndroidEntryPoint
public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    private ProfileViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        binding.profileTvAccount.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_nav_home_profile_to_nav_home_profile_account);
        });

        binding.profileTvTermOfService.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_nav_home_profile_to_nav_home_profile_term_service);
        });

        binding.profileTvSignOut.setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.profile_sign_out_title)
                    .setMessage(R.string.profile_sign_out_content)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(R.string.all_yes, ((dialog, which) -> {
                        startActivity(new Intent(getActivity(), AuthActivity.class));
                        getActivity().finish();
                    }))
                    .setNegativeButton(R.string.all_no, null)
                    .show();
        });
    }
}