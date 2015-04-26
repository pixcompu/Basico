/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Controladores.Ctrl_Principal;
import java.awt.Color;
import java.util.Comparator;
import java.util.Stack;

/**
 *
 * @author Felix Diaz ®
 */
public class Algoritmos {

    private final Cola fronteraPrioridad;
    private Terreno terreno;
    private final Ctrl_Principal controlador;
    private final Color marcaBusqueda = Color.CYAN.brighter();
    private boolean diagonalConsiderada;
    private static final Comparator cmpCostoAcumulado = new Comparator<Nodos>() {
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
    private static final Comparator cmpCosto = new Comparator<Nodos>() {
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

    public Algoritmos(Ctrl_Principal controlador) {
        this.fronteraPrioridad = new Cola();
        this.controlador = controlador;
    }

    public Terreno getTerreno() {
        return terreno;
    }

    public void setTerreno(Terreno terreno) {
        this.terreno = terreno;
    }

    public boolean isDiagonalConsiderada() {
        return diagonalConsiderada;
    }

    public void setDiagonalConsiderada(boolean diagonalConsiderada) {
        this.diagonalConsiderada = diagonalConsiderada;
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
        int fila, columna;
        fronteraPrioridad.enqueue(terreno.getInicio());
        while (!fronteraPrioridad.estaVacia()) {
            actual = ((Nodos) fronteraPrioridad.dequeue());
            actual.setRecorrido(true);

            fila = actual.getFila();
            columna = actual.getColumna();
            if (this.diagonalConsiderada) {
                evaluarVecinoDFS(terreno.get(fila + 1, columna - 1), actual, cmpCostoAcumulado);
                evaluarVecinoDFS(terreno.get(fila + 1, columna), actual, cmpCostoAcumulado);
                evaluarVecinoDFS(terreno.get(fila + 1, columna + 1), actual, cmpCostoAcumulado);
                evaluarVecinoDFS(terreno.get(fila, columna + 1), actual, cmpCostoAcumulado);
                evaluarVecinoDFS(terreno.get(fila - 1, columna + 1), actual, cmpCostoAcumulado);
                evaluarVecinoDFS(terreno.get(fila - 1, columna), actual, cmpCostoAcumulado);
                evaluarVecinoDFS(terreno.get(fila - 1, columna - 1), actual, cmpCostoAcumulado);
                evaluarVecinoDFS(terreno.get(fila, columna - 1), actual, cmpCostoAcumulado);
            } else {
                evaluarVecinoDFS(terreno.get(fila + 1, columna), actual, cmpCostoAcumulado);
                evaluarVecinoDFS(terreno.get(fila, columna + 1), actual, cmpCostoAcumulado);
                evaluarVecinoDFS(terreno.get(fila - 1, columna), actual, cmpCostoAcumulado);
                evaluarVecinoDFS(terreno.get(fila, columna - 1), actual, cmpCostoAcumulado);
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
        if (reco2 != terreno.getInicio()) {
            return null;
        } else {
            return res;
        }
    }

    public Stack Breath_FS() {
        Nodos actual = null;
        Nodos vecinoActual = null;
        int fila, columna;
        fronteraPrioridad.enqueue(terreno.getInicio());
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
            fila = actual.getFila();
            columna = actual.getColumna();
            if (this.diagonalConsiderada) {
                evaluarVecinoBFS(terreno.get(fila + 1, columna - 1), actual, cmpCostoAcumulado);
                evaluarVecinoBFS(terreno.get(fila + 1, columna), actual, cmpCostoAcumulado);
                evaluarVecinoBFS(terreno.get(fila + 1, columna + 1), actual, cmpCostoAcumulado);
                evaluarVecinoBFS(terreno.get(fila, columna + 1), actual, cmpCostoAcumulado);
                evaluarVecinoBFS(terreno.get(fila - 1, columna + 1), actual, cmpCostoAcumulado);
                evaluarVecinoBFS(terreno.get(fila - 1, columna), actual, cmpCostoAcumulado);
                evaluarVecinoBFS(terreno.get(fila - 1, columna - 1), actual, cmpCostoAcumulado);
                evaluarVecinoBFS(terreno.get(fila, columna - 1), actual, cmpCostoAcumulado);
            } else {
                evaluarVecinoBFS(terreno.get(fila + 1, columna), actual, cmpCostoAcumulado);
                evaluarVecinoBFS(terreno.get(fila, columna + 1), actual, cmpCostoAcumulado);
                evaluarVecinoBFS(terreno.get(fila - 1, columna), actual, cmpCostoAcumulado);
                evaluarVecinoBFS(terreno.get(fila, columna - 1), actual, cmpCostoAcumulado);
            }
        }
        return null;
    }

    public Stack StarA() {
        Nodos actual = null;
        Nodos vecinoActual = null;
        fronteraPrioridad.enqueue(terreno.getInicio());
        int fila, columna;
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
            fila = actual.getFila();
            columna = actual.getColumna();
            if (this.diagonalConsiderada) {
                evaluarVecinoAStar(terreno.get(fila + 1, columna - 1), actual, cmpCostoAcumulado);
                evaluarVecinoAStar(terreno.get(fila + 1, columna), actual, cmpCostoAcumulado);
                evaluarVecinoAStar(terreno.get(fila + 1, columna + 1), actual, cmpCostoAcumulado);
                evaluarVecinoAStar(terreno.get(fila, columna + 1), actual, cmpCostoAcumulado);
                evaluarVecinoAStar(terreno.get(fila - 1, columna + 1), actual, cmpCostoAcumulado);
                evaluarVecinoAStar(terreno.get(fila - 1, columna), actual, cmpCostoAcumulado);
                evaluarVecinoAStar(terreno.get(fila - 1, columna - 1), actual, cmpCostoAcumulado);
                evaluarVecinoAStar(terreno.get(fila, columna - 1), actual, cmpCostoAcumulado);
            } else {
                evaluarVecinoAStar(terreno.get(fila + 1, columna), actual, cmpCostoAcumulado);
                evaluarVecinoAStar(terreno.get(fila, columna + 1), actual, cmpCostoAcumulado);
                evaluarVecinoAStar(terreno.get(fila - 1, columna), actual, cmpCostoAcumulado);
                evaluarVecinoAStar(terreno.get(fila, columna - 1), actual, cmpCostoAcumulado);
            }
        }
        return null;
    }

    private void evaluarVecinoDFS(Nodos vecino, Nodos actual, Comparator cmp) {
        if (vecinoAceptable(vecino)) {
            fronteraPrioridad.enqueue(vecino, cmp);
            vecino.setAnterior(actual);
            actualizaTablero(vecino);
        }
    }

    private void evaluarVecinoAStar(Nodos vecino, Nodos actual, Comparator cmp) {
        if (vecinoAceptable(vecino)) {
            fronteraPrioridad.enqueue(vecino, cmp);
            vecino.setAnterior(actual);
            actualizaTablero(vecino);
        }
    }

    private void evaluarVecinoBFS(Nodos vecino, Nodos actual, Comparator cmp) {
        if (vecinoAceptable(vecino)) {
            fronteraPrioridad.enqueue(vecino, cmp);
            vecino.setAnterior(actual);
            actualizaTablero(vecino);
        }
    }

    private boolean vecinoAceptable(Nodos vecino) {
        return ((vecino != null) && (!vecino.isRecorrido()) && !(vecino.isObstaculo()));
    }

    private void actualizaTablero(Nodos actual) {
        int fila = actual.getFila();
        int columna = actual.getColumna();
        controlador.actualizar(fila, columna, actual.getCostoAcumulado(), marcaBusqueda, false);
    }

    public void resetCola() {
        this.fronteraPrioridad.hardReset();
    }

}
