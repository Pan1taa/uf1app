package com.company.room;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.room.databinding.FragmentCrearValBinding;

public class CrearValFragment extends Fragment {

    private FragmentCrearValBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentCrearValBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ElementosViewModel2 elementosViewModel2 = new ViewModelProvider(requireActivity()).get(ElementosViewModel2.class);
        NavController navController = Navigation.findNavController(view);

        binding.crear22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = binding.nombre22.getText().toString();
                String descripción = binding.descripcion22.getText().toString();

                elementosViewModel2.insertar(new Elemento2(nombre, descripción));

                navController.popBackStack();
            }
        });
    }
}