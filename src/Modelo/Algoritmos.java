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
    private Ctrl_Principal controlador;
    private final Color marcaBusqueda = Color.CYAN.brighter();
    private boolean diagonalConsiderada;

    public Algoritmos(Terreno terreno, Ctrl_Principal controlador, boolean conDiagonal) {
        this.terreno = terreno;
        this.fronteraPrioridad = new Cola();
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

    private double distanciaHeuristica(Nodos a, Nodos b) {
        return (Math.abs(a.getFila() - b.getFila()) + Math.abs(a.getColumna() - b.getColumna()));
    }

    private double costoPotencial(Nodos actual, Nodos siguiente) {
        return actual.getCosto() + siguiente.getCosto();
    }

    public Stack Depth_FS() {
        Nodos actual = null;
        Nodos vecinoActual = null;
        fronteraPrioridad.enqueueP(terreno.getInicio());
        while (!fronteraPrioridad.estaVacia()) {
            actual = ((Nodos) fronteraPrioridad.dequeue());
            actual.setRecorrido(true);

            if (this.diagonalConsiderada) {
                terreno.establecerVecinos8Direcciones(actual);
            } else {
                terreno.establecerVecinos4Direcciones(actual);
            }
            for (int i = 0; i < actual.getVecinos().dimensionCola(); i++) {
                vecinoActual = (Nodos) actual.getVecinos().get(i);
                vecinoActual.setCostoAcumulado(costoPotencial(actual, vecinoActual));

                actualizaTablero(vecinoActual);
                fronteraPrioridad.enqueueP(vecinoActual);
            }
        }

        Stack res = new Stack();
        Nodos reco = terreno.getFin();
        Nodos reco2 = reco;
        while (reco != null) {
            res.push(reco);
            reco2 = reco;
            reco = reco.getAnterior();
        }
        if(reco2!=terreno.getInicio()){
            return null;
        }else{
            return res;
        }
    }

    public Stack Breath_FS() {
        Nodos actual = null;
        Nodos vecinoActual = null;
        fronteraPrioridad.enqueueP(terreno.getInicio());
        while (!fronteraPrioridad.estaVacia()) {
            actual = ((Nodos) fronteraPrioridad.dequeue());
            actual.setRecorrido(true);

            if (actual.equals(terreno.getFin())) {
                Stack res = new Stack();
                Nodos reco = actual;
                while (reco != null) {
                    res.push(reco);
                    reco = reco.getAnterior();
                }
                return res;
            }

            if (this.diagonalConsiderada) {
                terreno.establecerVecinos8Direcciones(actual);
            } else {
                terreno.establecerVecinos4Direcciones(actual);
            }
            for (int i = 0; i < actual.getVecinos().dimensionCola(); i++) {
                vecinoActual = (Nodos) actual.getVecinos().get(i);
                vecinoActual.setCostoAcumulado(costoPotencial(actual, vecinoActual));

                actualizaTablero(vecinoActual);
                fronteraPrioridad.enqueueP(vecinoActual);
            }
        }
        return null;
    }

    public Stack StarA() {
        Nodos actual = null;
        Nodos vecinoActual = null;

        fronteraPrioridad.enqueueP(terreno.getInicio());
        while (!fronteraPrioridad.estaVacia()) {
            actual = ((Nodos) fronteraPrioridad.dequeue());
            actual.setRecorrido(true);

            if (actual.equals(terreno.getFin())) {
                Stack res = new Stack();
                Nodos reco = actual;
                while (reco != null) {
                    res.push(reco);
                    reco = reco.getAnterior();
                }
                return res;
            }

            if (this.diagonalConsiderada) {
                terreno.establecerVecinos8Direcciones(actual);
            } else {
                terreno.establecerVecinos4Direcciones(actual);
            }

            for (int i = 0; i < actual.getVecinos().dimensionCola(); i++) {
                vecinoActual = (Nodos) actual.getVecinos().get(i);
                vecinoActual.setCostoAcumulado(costoPotencial(actual, vecinoActual) + distanciaHeuristica(terreno.getFin(), vecinoActual));
                actualizaTablero(vecinoActual);
                fronteraPrioridad.enqueueP(vecinoActual);
            }
        }
        return null;
    }

    private void actualizaTablero(Nodos actual) {
        int fila = actual.getFila();
        int columna = actual.getColumna();
        controlador.actualizar(fila, columna, actual.getCostoAcumulado(), marcaBusqueda, false);

    }

}
