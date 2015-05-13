/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Controladores.Ctrl_Principal;
import java.util.Comparator;
import java.util.Stack;

/**
 *
 * @author Felix Diaz ®
 */
public class Algoritmos {

    private final Cola fronteraPrioridad;
    private Terreno terreno;
    private boolean diagonalConsiderada;
    private final Stack pilaDFS = new Stack();

    private static final Comparator cmpCostoAcumulado = new Comparator<Nodo>() {
        @Override
        public int compare(Nodo o1, Nodo o2) {
            if (o1.getCostoAcumulado() > o2.getCostoAcumulado()) {
                return 1;
            } else if (o1.getCostoAcumulado() < o2.getCostoAcumulado()) {
                return -1;
            } else {
                return 0;
            }
        }
    };

    private static final Comparator cmpCostoHeuristico = new Comparator<Nodo>() {
        @Override
        public int compare(Nodo o1, Nodo o2) {
            if (o1.getDistanciaHeuristica() > o2.getDistanciaHeuristica()) {
                return 1;
            } else if (o1.getDistanciaHeuristica() < o2.getDistanciaHeuristica()) {
                return -1;
            } else {
                return 0;
            }
        }
    };

    private static final Comparator cmpCosto = new Comparator<Nodo>() {
        @Override
        public int compare(Nodo o1, Nodo o2) {
            if (o1.getCosto() > o2.getCosto()) {
                return 1;
            } else if (o1.getCosto() < o2.getCosto()) {
                return -1;
            } else {
                return 0;
            }
        }
    };

    public Algoritmos() {
        this.fronteraPrioridad = new Cola();
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

    private int distanciaHeuristica(Nodo a, Nodo b) {
        int dist;
        int dx = a.getColumna() - b.getColumna();
        int dy = a.getFila() - b.getFila();
        if (this.diagonalConsiderada) {
            //Distancia Euclideana si hay diagonales
            dist = (int) ((double) 1000 * Math.sqrt(dx * dx + dy * dy));
        } else {
            //Distancia Manhatan si no hay diagonales
            dist = Math.abs(dx) + Math.abs(dy);
        }
        return dist;
    }

    public Stack Depth_FS(Nodo origen, Nodo objetivo) {
        Nodo actual = null;
        int fila, columna;
        pilaDFS.push(origen);
        while (!pilaDFS.isEmpty()) {
            actual = ((Nodo) pilaDFS.pop());

            if (actual.equals(objetivo)) {
                Stack res = new Stack();
                Nodo reco = actual;
                while (reco != null) {
                    res.push(reco);
                    reco = reco.getAnterior();
                }
                return res;
            }

            actual.setRecorrido(true);
            fila = actual.getFila();
            columna = actual.getColumna();
            if (this.diagonalConsiderada) {
                evaluarVecinoDFS(terreno.get(fila + 1, columna - 1), actual);
                evaluarVecinoDFS(terreno.get(fila + 1, columna), actual);
                evaluarVecinoDFS(terreno.get(fila + 1, columna + 1), actual);
                evaluarVecinoDFS(terreno.get(fila, columna + 1), actual);
                evaluarVecinoDFS(terreno.get(fila - 1, columna + 1), actual);
                evaluarVecinoDFS(terreno.get(fila - 1, columna), actual);
                evaluarVecinoDFS(terreno.get(fila - 1, columna - 1), actual);
                evaluarVecinoDFS(terreno.get(fila, columna - 1), actual);
            } else {
                evaluarVecinoDFS(terreno.get(fila + 1, columna), actual);
                evaluarVecinoDFS(terreno.get(fila, columna + 1), actual);
                evaluarVecinoDFS(terreno.get(fila - 1, columna), actual);
                evaluarVecinoDFS(terreno.get(fila, columna - 1), actual);
            }
        }
        Stack res = new Stack();
        Nodo reco = terreno.getFin();
        Nodo reco2 = reco;
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

    public Stack Breath_FS(Nodo origen, Nodo objetivo) {
        Nodo actual = null;
        int fila, columna;
        fronteraPrioridad.enqueue(origen);
        while (!fronteraPrioridad.estaVacia()) {
            actual = ((Nodo) fronteraPrioridad.dequeue());

            if (actual.equals(objetivo)) {
                Stack res = new Stack();
                Nodo reco = actual;
                while (reco != null) {
                    res.push(reco);
                    reco = reco.getAnterior();
                }
                return res;
            }

            actual.setRecorrido(true);

            fila = actual.getFila();
            columna = actual.getColumna();
            if (this.diagonalConsiderada) {
                evaluarVecinoBFS(terreno.get(fila + 1, columna - 1), actual);
                evaluarVecinoBFS(terreno.get(fila + 1, columna), actual);
                evaluarVecinoBFS(terreno.get(fila + 1, columna + 1), actual);
                evaluarVecinoBFS(terreno.get(fila, columna + 1), actual);
                evaluarVecinoBFS(terreno.get(fila - 1, columna + 1), actual);
                evaluarVecinoBFS(terreno.get(fila - 1, columna), actual);
                evaluarVecinoBFS(terreno.get(fila - 1, columna - 1), actual);
                evaluarVecinoBFS(terreno.get(fila, columna - 1), actual);
            } else {
                evaluarVecinoBFS(terreno.get(fila + 1, columna), actual);
                evaluarVecinoBFS(terreno.get(fila, columna + 1), actual);
                evaluarVecinoBFS(terreno.get(fila - 1, columna), actual);
                evaluarVecinoBFS(terreno.get(fila, columna - 1), actual);
            }
        }
        return null;
    }

    public Stack StarA(Nodo origen, Nodo objetivo) {
        Nodo actual = null;
        origen.setDistanciaHeuristica(distanciaHeuristica(origen, objetivo));
        fronteraPrioridad.enqueue(origen);
        int fila, columna;
        while (!fronteraPrioridad.estaVacia()) {
            actual = ((Nodo) fronteraPrioridad.dequeue());

            if (actual.equals(objetivo)) {
                Stack res = new Stack();
                Nodo reco = actual;
                while (reco != null) {
                    res.push(reco);
                    reco = reco.getAnterior();
                }
                return res;
            }

            actual.setRecorrido(true);

            fila = actual.getFila();
            columna = actual.getColumna();
            if (this.diagonalConsiderada) {
                evaluarVecinoAStar(terreno.get(fila + 1, columna - 1), actual);
                evaluarVecinoAStar(terreno.get(fila + 1, columna), actual);
                evaluarVecinoAStar(terreno.get(fila + 1, columna + 1), actual);
                evaluarVecinoAStar(terreno.get(fila, columna + 1), actual);
                evaluarVecinoAStar(terreno.get(fila - 1, columna + 1), actual);
                evaluarVecinoAStar(terreno.get(fila - 1, columna), actual);
                evaluarVecinoAStar(terreno.get(fila - 1, columna - 1), actual);
                evaluarVecinoAStar(terreno.get(fila, columna - 1), actual);
            } else {
                evaluarVecinoAStar(terreno.get(fila + 1, columna), actual);
                evaluarVecinoAStar(terreno.get(fila, columna + 1), actual);
                evaluarVecinoAStar(terreno.get(fila - 1, columna), actual);
                evaluarVecinoAStar(terreno.get(fila, columna - 1), actual);
            }
        }
        return null;
    }

    private boolean evaluarVecinoDFS(Nodo vecino, Nodo actual) {
        if (vecinoAceptable(vecino)) {
             double nuevoCosto = actual.getCostoAcumulado() + vecino.getCosto();
            if (!pilaDFS.contains(vecino) || (nuevoCosto <= vecino.getCostoAcumulado())) {
                vecino.setCostoAcumulado(nuevoCosto);
                vecino.setAnterior(actual);
                if (pilaDFS.contains(vecino)) {
                    pilaDFS.remove(vecino);
                }
                pilaDFS.push(vecino);
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean evaluarVecinoAStar(Nodo vecino, Nodo actual) {
        if (vecinoAceptable(vecino)) {
            double nuevoCosto = actual.getCostoAcumulado() + vecino.getCosto();
            if (!fronteraPrioridad.contains(vecino) || (nuevoCosto <= vecino.getCostoAcumulado())) {
                vecino.setCostoAcumulado(nuevoCosto);
                vecino.setDistanciaHeuristica(vecino.getCostoAcumulado() + distanciaHeuristica(vecino, terreno.getFin()));
                vecino.setAnterior(actual);
                if (fronteraPrioridad.contains(vecino)) {
                    fronteraPrioridad.eliminar(vecino);
                }
                fronteraPrioridad.enqueue(vecino, cmpCostoHeuristico);
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean evaluarVecinoBFS(Nodo vecino, Nodo actual) {
        if (vecinoAceptable(vecino)) {
            double nuevoCosto = actual.getCostoAcumulado() + vecino.getCosto();
            if (!fronteraPrioridad.contains(vecino) || (nuevoCosto <= vecino.getCostoAcumulado())) {
                vecino.setCostoAcumulado(nuevoCosto);
                vecino.setAnterior(actual);
                if (fronteraPrioridad.contains(vecino)) {
                    fronteraPrioridad.eliminar(vecino);
                }
                fronteraPrioridad.enqueue(vecino, cmpCostoAcumulado);
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean vecinoAceptable(Nodo vecino) {
        return ((vecino != null) && (!vecino.isRecorrido()) && (vecino.getTipoNodo() != Ctrl_Principal.IMPOSIBLE)
                && !vecino.equals(terreno.getInicio()));
    }

    public void resetCola() {
        this.fronteraPrioridad.hardReset();
    }

}
