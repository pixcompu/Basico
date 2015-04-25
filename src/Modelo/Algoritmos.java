/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.LinkedList;

/**
 *
 * @author Felix Diaz ®
 */
public class Algoritmos {


    private LinkedList visitados;
    private Cola frontera;
    private Terreno terreno;

    public Algoritmos(Terreno terreno) {
        this.terreno = terreno;
        this.visitados = new LinkedList();
        this.frontera = new Cola();
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

    public int[][] DFS() {
        frontera.enqueue(terreno.getInicio());
        while (!frontera.estaVacia()) {

        }
        return null;
    }

    public int[][] classic() {
        return null;
    }

}
