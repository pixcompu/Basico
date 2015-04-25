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
    private Nodos inicio;
    private Nodos fin;
    private Nodos[][] grafo;
    private int ancho;
    private int alto;

    public Terreno(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
        this.grafo = new Nodos[ancho][alto];
    }

   public Nodos get(int fila, int columna){
       if((Math.abs(fila-alto-1)<alto) && (Math.abs(columna-ancho-1)<ancho)){
           return grafo[fila][columna];
       }else{
           return null;
       }
   }
    
    public Nodos getInicio() {
        return inicio;
    }

    public void setInicio(Nodos inicio) {
        this.inicio = inicio;
    }

    public Nodos getFin() {
        return fin;
    }

    public void setFin(Nodos fin) {
        this.fin = fin;
    }

    public Nodos[][] getGrafo() {
        return grafo;
    }

    public void setGrafo(Nodos[][] grafo) {
        this.grafo = grafo;
    }

    public void estableceInicio(int fila, int columna) {
        this.inicio = get(fila, columna);
        if (this.inicio != null) {
            this.inicio.setAnterior(null);
        }else{
            assert inicio == null;
        }
    }
    
    public void estableceFin(int fila, int columna) {
        this.fin = get(fila,columna);
    }
    
  public Cola getVecinos(int fila, int columna){
      Nodos reco;
      reco = get(fila, columna);
      Cola aux = new Cola();
      if(reco != null){
          aux.enqueue(get(fila+1,columna));
          aux.enqueue(get(fila,columna+1));
          aux.enqueue(get(fila-1,columna));
          aux.enqueue(get(fila,columna-1));
          reco.setVecinos(aux);
      }
      return aux;
  }
    
    
    
}
