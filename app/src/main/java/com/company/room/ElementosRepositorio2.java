package com.company.room;
import java.util.ArrayList;
import java.util.List;

public class ElementosRepositorio2 {

    List<Elemento2> elementos = new ArrayList<>();

    interface Callback {
        void cuandoFinalice(List<Elemento2> elementos);
    }

    ElementosRepositorio2(){
        elementos.add(new Elemento2("Ivan bermejo", "Es el hombre que siempre ayuda y carrilea en mayor parte. El fifa no es su juego, pero lo intenta"));
        elementos.add(new Elemento2("Sara Essakkal", "En veces le dan mareos, pero es porque se esta volviendo a acostumbrar a la carne."));
        elementos.add(new Elemento2("Said El Morao", "Si se le mete una palabra en la cabeza, te la va a repetir 3000 veces si es necesario hast que te rias."));
        elementos.add(new Elemento2("DGD", "De natura intranquila, poeta y motorista, carrilea de cojones."));
        elementos.add(new Elemento2("Yassin (Tonaxe)","Un chaval misterioso, no hay por donde cogerlo."));
        elementos.add(new Elemento2("Jose Anton","Le molesta todo en general."));
    }

    List<Elemento2> obtener() {
        return elementos;
    }

    void insertar(Elemento2 elemento, Callback callback){
        elementos.add(elemento);
        callback.cuandoFinalice(elementos);
    }

    void eliminar(Elemento2 elemento, Callback callback) {
        elementos.remove(elemento);
        callback.cuandoFinalice(elementos);
    }

    void actualizar(Elemento2 elemento, float valoracion, Callback callback) {
        elemento.valoracion = valoracion;
        callback.cuandoFinalice(elementos);
    }
}