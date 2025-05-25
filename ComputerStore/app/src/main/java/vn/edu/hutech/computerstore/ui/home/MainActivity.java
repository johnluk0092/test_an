package vn.edu.hutech.computerstore.ui.home;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.hutech.computerstore.R;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private NavController navController;

    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        final Toolbar toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        navController = Navigation.findNavController(this, R.id.home_nav_host_fragment);
        final BottomNavigationView bottomNavigationView = findViewById(R.id.home_bottom_navigation);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController);

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel.getBottomTabIdState().observe(this, bottomNavigationView::setSelectedItemId);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }
}