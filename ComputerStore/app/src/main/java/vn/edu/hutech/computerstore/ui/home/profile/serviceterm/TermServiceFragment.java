package vn.edu.hutech.computerstore.ui.home.profile.serviceterm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.edu.hutech.computerstore.R;
import vn.edu.hutech.computerstore.databinding.FragmentTermServiceBinding;

public class TermServiceFragment extends Fragment {

    private static final String URL_TERM_OF_SERVICE = "https://policies.google.com/terms?hl=en-VN&fg=1";

    private FragmentTermServiceBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTermServiceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.profileTermServiceWebView.loadUrl(URL_TERM_OF_SERVICE);
    }

}