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

    public void enqueue(Object a, Comparator cmp) {
        this.list.add(a);
        Collections.sort(this.list, cmp);
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

    public void hardReset() {
        this.list.clear();
    }
}
