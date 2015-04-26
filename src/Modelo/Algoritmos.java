/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Controladores.Ctrl_Principal;
import java.awt.Color;
import java.util.LinkedList;
import java.util.Stack;
import javax.swing.JButton;

/**
 *
 * @author Felix Diaz ®
 */
public class Algoritmos {

    private LinkedList visitados;
    private Cola frontera;
    private Terreno terreno;
    private boolean finalEncontrado;
    private Ctrl_Principal controlador;
    private final Color marcaBusqueda = Color.pink.brighter();
    private boolean diagonalConsiderada;

    public Algoritmos(Terreno terreno, Ctrl_Principal controlador, boolean conDiagonal) {
        this.terreno = terreno;
        this.visitados = new LinkedList();
        this.frontera = new Cola();
        this.finalEncontrado = false;
        this.controlador = controlador;
        this.diagonalConsiderada = conDiagonal;
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

        while (!frontera.estaVacia()) {
            actual = ((Nodos) frontera.dequeue());
            actual.setRecorrido(true);
            
            if(this.diagonalConsiderada){
                terreno.establecerVecinos8Direcciones(actual);
            }else{
                terreno.establecerVecinos4Direcciones(actual);
            }
            
            for (int i = 0; i < actual.getVecinos().dimensionCola(); i++) {
                vecinoActual = (Nodos) actual.getVecinos().get(i);
                if (vecinoActual.equals(terreno.getFin())) {
                    
                    Stack res = new Stack();
                    Nodos reco = vecinoActual;
                    while (reco != null) {
                        res.push(reco);
                        reco = reco.getAnterior();
                    }
                    return res;
                    
                } else {
                    actualizaTablero(vecinoActual);
                    frontera.enqueue(vecinoActual);
                }
            }
        }
        return null;
    }

    public int[][] classic() {
        return null;
    }

    private void actualizaTablero(Nodos actual) {
        int fila = actual.getFila();
        int columna = actual.getColumna();
        double costoTotal = actual.getCosto();
        controlador.actualizar(fila, columna, costoTotal, marcaBusqueda, false);

    }

}
