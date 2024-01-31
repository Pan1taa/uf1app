package com.company.room;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.company.room.databinding.FragmentRecyclerElementosBinding;
import com.company.room.databinding.ViewholderElementoBinding;

import java.util.List;


public class RecyclerElementosFragment extends Fragment {

    private FragmentRecyclerElementosBinding binding;
    ElementosViewModel elementosViewModel;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentRecyclerElementosBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        elementosViewModel = new ViewModelProvider(requireActivity()).get(ElementosViewModel.class);
        navController = Navigation.findNavController(view);

        binding.irANuevoElemento.setOnClickListener(v -> navController.navigate(R.id.action_nuevoElementoFragment));

        ElementosAdapter elementosAdapter;
        elementosAdapter = new ElementosAdapter();

        binding.recyclerView.setAdapter(elementosAdapter);

        binding.recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

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
                Elemento elemento = elementosAdapter.obtenerElemento(posicion);
                elementosViewModel.eliminar(elemento);

            }
        }).attachToRecyclerView(binding.recyclerView);

        obtenerElementos().observe(getViewLifecycleOwner(), elementosAdapter::establecerLista);
    }

    LiveData<List<Elemento>> obtenerElementos(){
        return elementosViewModel.obtener();
    }

    class ElementosAdapter extends RecyclerView.Adapter<ElementoViewHolder> {

        List<Elemento> elementos;

        @NonNull
        @Override
        public ElementoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ElementoViewHolder(ViewholderElementoBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ElementoViewHolder holder, int position) {

            Elemento elemento = elementos.get(position);

            holder.binding.textView1.setText(elemento.nombre);


            holder.itemView.setOnClickListener(v -> {
                elementosViewModel.seleccionar(elemento);
                navController.navigate(R.id.action_mostrarElementoFragment);
            });
        }

        @Override
        public int getItemCount() {
            return elementos != null ? elementos.size() : 0;
        }

        @SuppressLint("NotifyDataSetChanged")
        public void establecerLista(List<Elemento> elementos){
            this.elementos = elementos;
            notifyDataSetChanged();
        }

        public Elemento obtenerElemento(int posicion){
            return elementos.get(posicion);
        }
    }

    static class ElementoViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderElementoBinding binding;

        public ElementoViewHolder(ViewholderElementoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}