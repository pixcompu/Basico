/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 *
 * @author Felix Diaz ®
 */
public class Cola {

    protected final LinkedList list;
    private Comparator cmp = new Comparator<Nodos>() {
        @Override
        public int compare(Nodos o1, Nodos o2) {
            if (o1.getCostoAcumulado() > o2.getCostoAcumulado()) {
                return 1;
            } else if (o1.getCostoAcumulado() < o2.getCostoAcumulado()) {
                return -1;
            } else {
                return 0;
            }
        }
    };

    public Cola() {
        this.list = new LinkedList();
    }

    public int dimensionCola() {
        return this.list.size();
    }

    public void imprimirCola() {
        int dim = dimensionCola();
        for (int i = 0; i < dim; i++) {
            System.out.print(((Nodos) get(i)).getCostoAcumulado() + ((i < dim - 1) ? " -> " : "\n"));
        }
    }

    public void enqueue(Object a) {
        this.list.addLast(a);
    }

    public void enqueueP(Object a) {
        this.list.addLast(a);
        Collections.sort(list, cmp);
    }

    public Object dequeue() {
        return this.list.removeFirst();
    }

    public boolean eliminar(Object o) {
        return this.list.remove(o);
    }

    public Object eliminar(int i) {
        return this.list.remove(i);
    }

    public Object get(int i) {
        return this.list.get(i);
    }

    public Object peek() {
        return this.list.peekFirst();
    }

    public boolean estaVacia() {
        return this.list.isEmpty();
    }
}
