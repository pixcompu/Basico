/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Algoritmos;
import Ventanas.Principal;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Felix Diaz ®
 */
public class Ctrl_Principal implements ActionListener {

    private Principal ventana;
    private JPanel cuadricula;
    private final JButton[][] tablero;
    private final int[][] tableroAlgoritmo;
    private int[][] tableroResuesta;
    private JButton inicio;
    private JButton fin;

    public Ctrl_Principal(Principal ventana) {
        this.ventana = ventana;
        this.cuadricula = ventana.pnl_Cuadricula;
        this.cuadricula.setLayout(new GridLayout(20, 20));
        this.tablero = new JButton[20][20];
        this.tableroAlgoritmo = new int[20][20];
        this.inicio = null;
        this.fin = null;
        iniciar();
    }

    private void iniciaTablero() {

        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                tablero[i][j] = new JButton(" ");
                tablero[i][j].setActionCommand("" + i + "," + j + "");
                tablero[i][j].setToolTipText("Posicion: (" + i + "," + j + ")");
                tablero[i][j].setBackground(Color.WHITE);
                tablero[i][j].addActionListener(this);
                tableroAlgoritmo[i][j] = 0;
                cuadricula.add(tablero[i][j]);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "Iniciar":
                if (estaConfigurado()) {
                    iniciarAlgoritmo(this.ventana.list_Metodos.getSelectedIndex());
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un inicio y un fin", "Error", 2);
                }
                break;
            case "Reset":
                reset();
                break;
            default:
                if (this.ventana.radio_inicio.isSelected()) {
                    marcarInicio(e.getActionCommand());
                } else {
                    if (this.ventana.radio_fin.isSelected()) {
                        marcarFin(e.getActionCommand());
                    } else {
                        marcarObstaculo(e.getActionCommand(), this.ventana.lista_dificultad.getSelectedIndex());
                    }
                }
        }
    }

    private void iniciar() {
        this.ventana.setLocationRelativeTo(null);
        this.ventana.setTitle("Ordinario");
        this.ventana.grp_Radios.add(this.ventana.radio_fin);
        this.ventana.grp_Radios.add(this.ventana.radio_inicio);
        this.ventana.grp_Radios.add(this.ventana.radio_obstaculo);
        this.ventana.radio_inicio.setSelected(true);
        this.ventana.btn_realizar.addActionListener(this);
        this.ventana.btn_limpiar.addActionListener(this);
        iniciaTablero();
        this.ventana.pack();
        this.ventana.setVisible(true);
    }

    private void reset() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                this.tablero[i][j].setEnabled(true);
                this.tablero[i][j].setBackground(Color.WHITE);
                this.tableroAlgoritmo[i][j] = 0;
            }
        }
        this.ventana.radio_inicio.setSelected(true);
        this.ventana.radio_inicio.setEnabled(true);
        this.ventana.radio_fin.setEnabled(true);
        this.inicio = null;
        this.fin = null;
    }

    private boolean estaConfigurado() {
        return ((this.inicio != null) && (this.fin != null));
    }

    private void iniciarAlgoritmo(int indice) {
        Algoritmos algoritmos = new Algoritmos(this.inicio.getActionCommand(),this.fin.getActionCommand(),this.tableroAlgoritmo);
        switch (indice) {
            case 0:
                this.tableroResuesta = algoritmos.classic();
                break;
            case 1:
                this.tableroResuesta = algoritmos.DFS();
                break;
            case 2:
                this.tableroResuesta = algoritmos.FC();
                break;
            default:
                throw new AssertionError();
        }
        mostrarSolucion();
    }

    private void marcarInicio(String actionCommand) {
        int fila = Integer.parseInt(actionCommand.split(",")[0]);
        int columna = Integer.parseInt(actionCommand.split(",")[1]);
        tablero[fila][columna].setBackground(Color.GREEN);
        this.ventana.radio_inicio.setEnabled(false);
        this.ventana.radio_obstaculo.setSelected(true);
        tablero[fila][columna].setEnabled(false);
        this.inicio = tablero[fila][columna];
        this.tableroAlgoritmo[fila][columna] = 1;
    }

    private void marcarFin(String actionCommand) {
        int fila = Integer.parseInt(actionCommand.split(",")[0]);
        int columna = Integer.parseInt(actionCommand.split(",")[1]);
        tablero[fila][columna].setBackground(Color.RED);
        this.ventana.radio_fin.setEnabled(false);
        this.ventana.radio_obstaculo.setSelected(true);
        tablero[fila][columna].setEnabled(false);
        this.fin = tablero[fila][columna];
        this.tableroAlgoritmo[fila][columna] = 2;
    }

    private void marcarObstaculo(String actionCommand, int selectedIndex) {
        int fila = Integer.parseInt(actionCommand.split(",")[0]);
        int columna = Integer.parseInt(actionCommand.split(",")[1]);
        switch (selectedIndex) {
            case 0:
                tablero[fila][columna].setBackground(Color.BLACK);
                tablero[fila][columna].setEnabled(false);
                this.tableroAlgoritmo[fila][columna] = 3;
                break;
            case 1:
                tablero[fila][columna].setBackground(Color.GRAY.darker());
                tablero[fila][columna].setEnabled(false);
                this.tableroAlgoritmo[fila][columna] = 4;
                break;
            case 2:
                tablero[fila][columna].setBackground(Color.GRAY.brighter());
                tablero[fila][columna].setEnabled(false);
                this.tableroAlgoritmo[fila][columna] = 5;
                break;
            case 3:
                tablero[fila][columna].setBackground(Color.ORANGE);
                tablero[fila][columna].setEnabled(false);
                this.tableroAlgoritmo[fila][columna] = 6;
                break;
            case 4:
                tablero[fila][columna].setBackground(Color.YELLOW);
                tablero[fila][columna].setEnabled(false);
                this.tableroAlgoritmo[fila][columna] = 7;
                break;
            default:
                throw new AssertionError();
        }
    }

    private void mostrarSolucion() {
        try{
            int i = this.tableroResuesta.length;
        }catch(NullPointerException e){
            System.out.println("Aqui se mostraria la solucion");
        }
    
    }

}
