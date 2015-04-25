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
public class Algoritmos {

    private String inicio;
    private String fin;
    private int[][] matriz;

    public Algoritmos(String inicio, String fin, int[][] matriz) {
        this.inicio = inicio;
        this.fin = fin;
        this.matriz = matriz;
    }

    private void imprimirMatriz(int[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public int[][] FC() {
        System.out.println("FC");
        System.out.println("Inicio: " + this.inicio + " Fin: " + this.fin);
        imprimirMatriz(this.matriz);
        return null;
    }

    public int[][] DFS() {
        System.out.println("DFS");
        System.out.println("Inicio: " + this.inicio + " Fin: " + this.fin);
        imprimirMatriz(this.matriz);
        return null;
    }

    public int[][] classic() {
        System.out.println("CLASSIC");
        System.out.println("Inicio: " + this.inicio + " Fin: " + this.fin);
        imprimirMatriz(this.matriz);
        return null;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public int[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(int[][] matriz) {
        this.matriz = matriz;
    }

}
