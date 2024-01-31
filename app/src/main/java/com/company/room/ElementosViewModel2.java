package com.company.room;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ElementosViewModel2 extends AndroidViewModel {

    ElementosRepositorio2 elementosRepositorio2;

    MutableLiveData<List<Elemento2>> listElementosMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Elemento2> elementoSeleccionado = new MutableLiveData<>();

    public ElementosViewModel2(@NonNull Application application) {
        super(application);

        elementosRepositorio2 = new ElementosRepositorio2();

        listElementosMutableLiveData.setValue(elementosRepositorio2.obtener());
    }

    MutableLiveData<List<Elemento2>> obtener(){
        return listElementosMutableLiveData;
    }

    void insertar(Elemento2 elemento){
        elementosRepositorio2.insertar(elemento, new ElementosRepositorio2.Callback() {
            @Override
            public void cuandoFinalice(List<Elemento2> elementos) {
                listElementosMutableLiveData.setValue(elementos);
            }
        });
    }

    void eliminar(Elemento2 elemento){
        elementosRepositorio2.eliminar(elemento, new ElementosRepositorio2.Callback() {
            @Override
            public void cuandoFinalice(List<Elemento2> elementos) {
                listElementosMutableLiveData.setValue(elementos);
            }
        });
    }

    void actualizar(Elemento2 elemento, float valoracion){
        elementosRepositorio2.actualizar(elemento, valoracion, new ElementosRepositorio2.Callback() {
            @Override
            public void cuandoFinalice(List<Elemento2> elementos) {
                listElementosMutableLiveData.setValue(elementos);
            }
        });
    }

    void seleccionar(Elemento2 elemento){
        elementoSeleccionado.setValue(elemento);
    }

    MutableLiveData<Elemento2> seleccionado(){
        return elementoSeleccionado;
    }
}