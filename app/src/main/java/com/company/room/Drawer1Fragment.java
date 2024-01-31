package com.company.room;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;


import com.company.room.databinding.FragmentRecyclerElementos2Binding;
import com.company.room.databinding.ViewholderElemento2Binding;


import java.util.List;


public class Drawer1Fragment extends Fragment {

    private FragmentRecyclerElementos2Binding binding;
    protected ElementosViewModel2 elementosViewModel2;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentRecyclerElementos2Binding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        elementosViewModel2 = new ViewModelProvider(requireActivity()).get(ElementosViewModel2.class);
        navController = Navigation.findNavController(view);

        binding.irANuevoElemento2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_drawer1Fragment_to_crearValFragment);
            }
        });

        ElementosAdapter2 elementosAdapter = new ElementosAdapter2();

        binding.recyclerView2.setAdapter(elementosAdapter);

        binding.recyclerView2.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.RIGHT  | ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int posicion = viewHolder.getAdapterPosition();
                Elemento2 elemento2 = elementosAdapter.obtenerElemento(posicion);
                elementosViewModel2.eliminar(elemento2);

            }
        }).attachToRecyclerView(binding.recyclerView2);

        obtenerElementos().observe(getViewLifecycleOwner(), new Observer<List<Elemento2>>() {
            @Override
            public void onChanged(List<Elemento2> elementos2) {
                elementosAdapter.establecerLista(elementos2);
            }
        });
    }

    LiveData<List<Elemento2>> obtenerElementos(){
        return elementosViewModel2.obtener();
    }

    class ElementosAdapter2 extends RecyclerView.Adapter<ElementoViewHolder> {

        List<Elemento2> elementos2;

        @NonNull
        @Override
        public ElementoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ElementoViewHolder(ViewholderElemento2Binding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ElementoViewHolder holder, int position) {

            Elemento2 elemento = elementos2.get(position);

            holder.binding.nombre2.setText(elemento.nombre);
            holder.binding.valoracion2.setRating(elemento.valoracion);

            holder.binding.valoracion2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if(fromUser) {
                        elementosViewModel2.actualizar(elemento, rating);
                    }
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    elementosViewModel2.seleccionar(elemento);
                    navController.navigate(R.id.action_drawer1Fragment_to_elementoValFragment);
                }
            });
        }

        @Override
        public int getItemCount() {
            return elementos2 != null ? elementos2.size() : 0;
        }

        public void establecerLista(List<Elemento2> elementos){
            this.elementos2 = elementos;
            notifyDataSetChanged();
        }

        public Elemento2 obtenerElemento(int posicion){
            return elementos2.get(posicion);
        }
    }

    static class ElementoViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderElemento2Binding binding;

        public ElementoViewHolder(ViewholderElemento2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}
