package com.proyect.trigal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.proyect.trigal.databinding.ActivityMainBinding;
import com.proyect.trigal.ui.notifications.NotificationsFragment;

import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    Intent intent;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent = getIntent();
        key= intent.getStringExtra("key");
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if (navDestination.getId() == R.id.navigation_notifications) {
                    NotificationsFragment fragment = new NotificationsFragment();
                    Bundle fragmentBundle = new Bundle();
                    fragmentBundle.putString("key", key);
                    fragment.setArguments(fragmentBundle);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_host_fragment_activity_main, fragment)
                            .commit();
                }
            }
        });
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        String zonaHoraria = "America/Lima";
        TimeZone.setDefault(TimeZone.getTimeZone(zonaHoraria));

    }

    public void reservar(View view){
        Intent intent=new Intent(MainActivity.this, ActReservarSalaUsu.class);
        intent.putExtra("key", key);
        startActivity(intent);
    }

}