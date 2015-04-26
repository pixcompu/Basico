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
public class Nodos {

    private int fila;
    private int columna;
    private double costo;
    private boolean obstaculo;
    private boolean recorrido;
    private int tipoCamino;
    private ColaPrioridad vecinos;
    private Nodos anterior;
    private double costoAcumulado;

    public Nodos(int fila, int columna, double costo) {
        this.fila = fila;
        this.columna = columna;
        this.costo = costo;
        this.obstaculo = false;
        this.recorrido = false;
        this.tipoCamino = 3;
        this.vecinos = null;
        this.anterior = null;
        this.costoAcumulado = 0.0;
    }

    public double getCostoAcumulado() {
        return costoAcumulado;
    }

    public void setCostoAcumulado(double costoAcumulado) {
        this.costoAcumulado = costoAcumulado;
    }
    
    public int getTipoCamino() {
        return tipoCamino;
    }

    public void setTipoCamino(int tipoCamino) {
        this.tipoCamino = tipoCamino;
    }

    public boolean isRecorrido() {
        return recorrido;
    }

    public void setRecorrido(boolean recorrido) {
        this.recorrido = recorrido;
    }

    public boolean isObstaculo() {
        return obstaculo;
    }

    public void setObstaculo(boolean obstaculo) {
        this.obstaculo = obstaculo;
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

    public ColaPrioridad getVecinos() {
        return vecinos;
    }

    public void setVecinos(ColaPrioridad vecinos) {
        this.vecinos = vecinos;
    }

    public Nodos getAnterior() {
        return anterior;
    }

    public void setAnterior(Nodos anterior) {
        this.anterior = anterior;
    }

    @Override
    public String toString() {
        return "(" + getFila() + "," + getColumna() + ")";
    }


}
