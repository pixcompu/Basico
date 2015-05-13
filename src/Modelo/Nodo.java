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
public class Nodo{

    private int fila;
    private int columna;
    private double costo;
    private double costoAcumulado;
    private double distanciaHeuristica;
    private boolean recorrido;
    private Nodo anterior;
    private int tipoNodo;

    public Nodo(int fila, int columna, double costo) {
        this.fila = fila;
        this.columna = columna;
        this.costo = costo;
        this.recorrido = false;
        this.anterior = null;
        this.costoAcumulado = 0.0;
        this.distanciaHeuristica = 0;
    }

    public void resetNodo(){
        this.anterior = null;
        this.costoAcumulado = 0.0;
        this.distanciaHeuristica = 0.0;
        this.recorrido = false;
    }
    
    public double getDistanciaHeuristica() {
        return distanciaHeuristica;
    }

    public void setDistanciaHeuristica(double distanciaHeuristica) {
        this.distanciaHeuristica = distanciaHeuristica;
    }
    
    public double getCostoAcumulado() {
        return costoAcumulado;
    }

    public void setCostoAcumulado(double costoAcumulado) {
        this.costoAcumulado = costoAcumulado;
    }
    
    public int getTipoNodo() {
        return tipoNodo;
    }

    public void setTipoNodo(int tipoNodo) {
        this.tipoNodo = tipoNodo;
    }
    
    public boolean isRecorrido() {
        return recorrido;
    }

    public void setRecorrido(boolean recorrido) {
        this.recorrido = recorrido;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public Nodo getAnterior() {
        return anterior;
    }

    public void setAnterior(Nodo anterior) {
        this.anterior = anterior;
    }

    @Override
    public String toString() {
        return "(" + getFila() + "," + getColumna() + ")";
    }

}
