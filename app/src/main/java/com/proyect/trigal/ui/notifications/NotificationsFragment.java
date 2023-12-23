package com.proyect.trigal.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.proyect.trigal.ActLogin;
import com.proyect.trigal.ActSalaBlanca;
import com.proyect.trigal.ActSalaCapacitacion;
import com.proyect.trigal.ActSalaMarron;
import com.proyect.trigal.databinding.FragmentNotificationsBinding;
import com.proyect.trigal.R;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ImageView clothingImage1 = root.findViewById(R.id.clothingImage1);
        ImageView clothingImage2 = root.findViewById(R.id.clothingImage2);
        ImageView clothingImage3 = root.findViewById(R.id.clothingImage3);


        clothingImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ActSalaCapacitacion.class);
                startActivity(intent);
            }
        });

        clothingImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),  ActSalaBlanca.class);
                startActivity(intent);
            }
        });

        clothingImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActSalaMarron.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
