package com.proyect.trigal.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.proyect.trigal.R;
import com.proyect.trigal.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    private FragmentDashboardBinding binding;
    private DashboardViewModel dashboardViewModel;



    private GoogleMap mMAP;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        // Inicializar el mapa de Google
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMAP = googleMap;
        mMAP.setOnMapClickListener(this);
        mMAP.setOnMapLongClickListener(this);

        // Añadir un marcador por defecto al mapa
        LatLng trigal = new LatLng(-12.1277782,-76.9893604);
        mMAP.addMarker(new MarkerOptions().position(trigal).title("Trigal Coworking"));
        mMAP.moveCamera(CameraUpdateFactory.newLatLng(trigal));
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        // Acciones al hacer clic en el mapa

    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        // Acciones al mantener presionado en el mapa

    }
}
