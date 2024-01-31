package com.company.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ElementosRepositorio {

    ElementosBaseDeDatos.ElementosDao elementosDao;
    Executor executor = Executors.newSingleThreadExecutor();

    ElementosRepositorio(Application application){
        elementosDao = ElementosBaseDeDatos.obtenerInstancia(application).obtenerElementosDao();
    }


    LiveData<List<Elemento>> obtener(){
        return elementosDao.obtener();
    }

    LiveData<List<Elemento>> masValorados() {
        return elementosDao.masValorados();
    }

    LiveData<List<Elemento>> buscar(String d) {
        return elementosDao.buscar(d);
    }


    void insertar(Elemento elemento){
        executor.execute(() -> elementosDao.insertar(elemento));
    }

    void eliminar(Elemento elemento) {
        executor.execute(() -> elementosDao.eliminar(elemento));
    }

    public void actualizar(Elemento elemento, float valoracion) {
        executor.execute(() -> {
            elemento.valoracion = valoracion;
            elementosDao.actualizar(elemento);
        });
    }
}