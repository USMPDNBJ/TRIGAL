package com.proyect.trigal.ui.notifications;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.proyect.trigal.ActReservarSalaUsu;
import com.proyect.trigal.MainActivity;
import com.proyect.trigal.R;
import com.proyect.trigal.Reservas;
import com.proyect.trigal.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private ListView listaNotifi;
    private Button btnAgreg;
    private String key;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Bundle bundle = getArguments();
        listaNotifi=root.findViewById(R.id.lstListaRes);
        btnAgreg=root.findViewById(R.id.btnOrdenar);

        if (bundle != null) {
            key = bundle.getString("key");
            listarReservas(key);
        }
            return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void listarReservas(String key){
        FirebaseDatabase db= FirebaseDatabase.getInstance();
        DatabaseReference dbref=db.getReference(Reservas.class.getSimpleName());
        ArrayList<Reservas> lisrec= new ArrayList<Reservas>();
        ArrayAdapter<Reservas> adapRes= new ArrayAdapter<Reservas>(this.getContext(), android.R.layout.simple_list_item_1,lisrec);
        listaNotifi.setAdapter(adapRes);
        dbref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Reservas reserv=snapshot.getValue(Reservas.class);
                if (key.equals(reserv.getUsuario())){
                    lisrec.add(reserv);
                    adapRes.notifyDataSetChanged();
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                adapRes.notifyDataSetChanged();
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}
