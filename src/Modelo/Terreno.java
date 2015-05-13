/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Felix Diaz ®
 */
public class Terreno {

    private Nodo inicio;
    private Nodo fin;
    private Nodo[][] grafo;
    private final int ancho;
    private final int alto;

    public Terreno(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
        this.grafo = new Nodo[ancho][alto];
    }

    public Nodo get(int fila, int columna) {
        if (fila < alto && fila >= 0 && columna < ancho && columna >= 0) {
            return grafo[fila][columna];
        } else {
            return null;
        }
    }

    public Nodo getInicio() {
        return inicio;
    }

    public void setInicio(Nodo inicio) {
        this.inicio = inicio;
    }

    public Nodo getFin() {
        return fin;
    }

    public void setFin(Nodo fin) {
        this.fin = fin;
    }

    public Nodo[][] getGrafo() {
        return grafo;
    }

    public void setGrafo(Nodo[][] grafo) {
        this.grafo = grafo;
    }

    public void estableceInicio(int fila, int columna) {
        this.inicio = get(fila, columna);
    }

    public void estableceFin(int fila, int columna) {
        this.fin = get(fila, columna);
    }
    
}
