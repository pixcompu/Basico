/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Controladores.Ctrl_Principal;
import java.awt.Color;
import java.util.Stack;

/**
 *
 * @author Felix Diaz ®
 */
public class Algoritmos {

    private Cola fronteraPrioridad;
    private Terreno terreno;
    private boolean finalEncontrado;
    private Ctrl_Principal controlador;
    private final Color marcaBusqueda = Color.PINK;
    private boolean diagonalConsiderada;

    public Algoritmos(Terreno terreno, Ctrl_Principal controlador, boolean conDiagonal) {
        this.terreno = terreno;
        this.fronteraPrioridad = new Cola();
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
    
    private double distanciaHeuristica(Nodos a, Nodos b){
        return (Math.abs(b.getFila() - a.getFila()) + Math.abs(b.getColumna() - a.getColumna()));
    }
   
        private double costoPotencial(Nodos actual, Nodos siguiente) {
        return actual.getCostoAcumulado() + siguiente.getCosto();
    }
    
    public Stack Depth_FS() {
        return null;
    }

    public Stack Breath_FS() {
        Nodos actual = null;
        Nodos vecinoActual = null;
        double costoAcumulado = 0.0;
        fronteraPrioridad.enqueue(terreno.getInicio());
        while (!fronteraPrioridad.estaVacia()) {
            actual = ((Nodos) fronteraPrioridad.dequeue());
            actual.setRecorrido(true);
            if(this.diagonalConsiderada){
                terreno.establecerVecinos8Direcciones(actual);
            }else{
                terreno.establecerVecinos4Direcciones(actual);
            }
            for (int i = 0; i < actual.getVecinos().dimensionCola(); i++) {
                vecinoActual = (Nodos) actual.getVecinos().get(i);
                vecinoActual.setCostoAcumulado(costoPotencial(actual, vecinoActual));
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
                    fronteraPrioridad.enqueue(vecinoActual);
                    fronteraPrioridad.imprimirCola();
                }
            }
        }
        return null;
    }

    public Stack StarA() {
        return null;
    }

    private void actualizaTablero(Nodos actual) {
        int fila = actual.getFila();
        int columna = actual.getColumna();
        controlador.actualizar(fila, columna, actual.getCostoAcumulado(), marcaBusqueda, false);

    }

}
