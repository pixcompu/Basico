/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.LinkedList;
import java.util.Stack;

/**
 *
 * @author Felix Diaz ®
 */
public class Algoritmos {

    private LinkedList visitados;
    private Cola frontera;
    private Terreno terreno;
    private boolean finalEncontrado;

    public Algoritmos(Terreno terreno) {
        this.terreno = terreno;
        this.visitados = new LinkedList();
        this.frontera = new Cola();
        this.finalEncontrado = false;
    }

    private void imprimirMatriz(Nodos[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                System.out.print(mat[i][j].getCosto() + " ");
            }
            System.out.println("");
        }
    }

    public int[][] FC() {
        return null;
    }

    public Stack DFS() {
        Nodos actual = null;
        Nodos vecinoActual = null;
        frontera.enqueue(terreno.getInicio());
        while (!frontera.estaVacia() && !this.finalEncontrado) {
            actual = ((Nodos) frontera.dequeue());
            actual.setRecorrido(true);
            terreno.establecerVecinos4Direeciones(actual);
            for (int i = 0; i < actual.getVecinos().dimensionCola() && !this.finalEncontrado; i++) {
                vecinoActual = (Nodos) actual.getVecinos().get(i);
                if (vecinoActual.equals(terreno.getFin())) {
                    this.finalEncontrado = true;
                } else {
                    frontera.enqueue(actual.getVecinos().get(i));
                }
            }
        }
        Stack res = new Stack();
        Nodos reco = vecinoActual;
        while (reco != null) {
            res.push(reco);
            reco = reco.getAnterior();
        }
        System.out.println(res.size());
        return res;
    }

    public int[][] classic() {
        return null;
    }

}
