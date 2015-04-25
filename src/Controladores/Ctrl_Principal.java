/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controladores;

import Ventanas.Principal;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Felix Diaz ®
 */
public class Ctrl_Principal implements ActionListener{
    
    private Principal ventana;
    private JPanel cuadricula;
    private final JButton[][] tablero;
    

    public Ctrl_Principal(Principal ventana) {
        this.ventana = ventana;
        this.cuadricula = ventana.pnl_Cuadricula;
        this.cuadricula.setLayout(new GridLayout(20,20));
        this.tablero = new JButton[20][20];
        iniciar();
    }

    private void iniciaTablero() {

        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                tablero[i][j] = new JButton(" ");
                tablero[i][j].setActionCommand(""+i+","+j+"");
                tablero[i][j].setToolTipText("Posicion: ("+i+","+j+")");
                tablero[i][j].addActionListener(this);
                cuadricula.add(tablero[i][j]);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
    }

    private void iniciar() {
        this.ventana.setLocationRelativeTo(null);
        this.ventana.setTitle("Ordinario");
        iniciaTablero();
        this.ventana.pack();
        this.ventana.setVisible(true);
    }
    
    
}
