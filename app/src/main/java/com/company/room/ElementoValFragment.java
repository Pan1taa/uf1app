package com.company.room;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.company.room.databinding.FragmentElementoValBinding;


public class ElementoValFragment extends Fragment {

    private FragmentElementoValBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentElementoValBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ElementosViewModel2 elementosViewModel2 = new ViewModelProvider(requireActivity()).get(ElementosViewModel2.class);

        elementosViewModel2.seleccionado().observe(getViewLifecycleOwner(), new Observer<Elemento2>() {
            @Override
            public void onChanged(Elemento2 elemento2) {
                binding.nombre2.setText(elemento2.nombre);
                binding.descripcion2.setText(elemento2.descripcion);

            }
        });
    }
}