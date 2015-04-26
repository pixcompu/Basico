/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Felix Diaz ®
 */
public class ColaPrioridad extends Cola {

    private double prioridad;
    private Comparator cmp = new Comparator<Nodos>() {
        @Override
        public int compare(Nodos o1, Nodos o2) {
            if (o1.getCosto() > o2.getCosto()) {
                return 1;
            } else if (o1.getCosto() < o2.getCosto()) {
                return -1;
            } else {
                return 0;
            }
        }
    };

    public ColaPrioridad() {
        super();
    }

    @Override
    public void imprimirCola() {
        int dim = dimensionCola();
        for (int i = 0; i < dim; i++) {
            System.out.print(((Nodos) get(i)).getCosto() + ((i < dim - 1) ? " -> " : "\n"));
        }
    }

    @Override
    public void enqueue(Object a) {
        super.enqueue(a);
        Collections.sort(super.list, cmp);
    }

}
