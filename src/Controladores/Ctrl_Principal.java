/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Algoritmos;
import Modelo.Nodos;
import Modelo.Terreno;
import Ventanas.Principal;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Felix Diaz ®
 */
public class Ctrl_Principal implements ActionListener {
    
    private final Dimension tamañoBoton = new Dimension(40, 20);
    private final Insets margen = new Insets(0,0,0,0);
    private final Font fuenteBtn = new Font("Tahoma", 1, 9);
    private Principal ventana;
    private JPanel cuadricula;
    private final JButton[][] tablero;
    private Terreno terreno;

    /**
     * Recibe la ventana y hace las configuraciones necesarias antes de
     * mostrarla establece el Layout del panel de la ventana, es Grid porque es
     * el mejor para tablas segun internet establece tamaño de tablero de
     * botones establece tamaño de matriz de enteros que sera la que manejaremos
     * en los algoritmos inicializa un inicio y un fin
     *
     * @param ventana
     */
    public Ctrl_Principal(Principal ventana) {
        this.ventana = ventana;
        this.cuadricula = ventana.pnl_Cuadricula;
        this.cuadricula.setLayout(new GridLayout(20, 20));
        this.tablero = new JButton[20][20];
        this.terreno = new Terreno(20,20);
        iniciar();
    }

    /**
     * Configura cada boton del tablero le agrega texto le agrega informacion de
     * la posicion en forma de actioncommand le pone un color de fondo inicia
     * las cuadriculas del tablero de algoritmos en 0 añade al panel el boton
     * recien creado
     */
    private void iniciaTablero() {

        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                tablero[i][j] = new JButton();
                tablero[i][j].setActionCommand("" + i + "," + j + "");
                tablero[i][j].setToolTipText("Posicion: (" + i + "," + j + ")");
                tablero[i][j].setBackground(Color.WHITE);
                tablero[i][j].setFont(fuenteBtn);
                tablero[i][j].setMargin(margen);
                tablero[i][j].setPreferredSize(tamañoBoton);
                tablero[i][j].addActionListener(this);
                terreno.getGrafo()[i][j] = new Nodos(i,j,1.0);
                cuadricula.add(tablero[i][j]);
            }
        }
    }

    /**
     * Metodo para atender las peticiones de la ventana Java lo pide poner al
     * implementar ActionComand Caso iniciar: verifica que se haya puesto un
     * inicio y un final, si es asi inicia el algoritmo seleccionado Caso Reset:
     * resetea todas las opciones a su valor inicial Caso Default: verifica si
     * esta seleccionado inicio asigna inicio si esta seleccionado final asigna
     * el final cualquier otro caso asigna obstaculo, con la dificultad
     * seleccionada
     *
     * @param e
     */
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

    /**
     * Configuracion Inicial la primera linea para que salga la ventana en el
     * centro la segunda le da un titulo la tercera, cuarta y quinta enlaza los
     * botones de inicio, fin y obstaculo para que no se puedan seleccionar
     * simultaneamente inicia el selector en inicio añade el boton de realizar y
     * limpiar un listener, para que cuando se clicke entre al metodo
     * actionPerformed de arriba realiza la inicializacion de cada boton la
     * siguiente pack hace que el tamaño de la ventana se ajuste para poder ver
     * todos los botones la siguiente lanza la ventana en pantalla
     */
    private void iniciar() {
        this.ventana.setTitle("Ordinario");
        this.ventana.grp_Radios.add(this.ventana.radio_fin);
        this.ventana.grp_Radios.add(this.ventana.radio_inicio);
        this.ventana.grp_Radios.add(this.ventana.radio_obstaculo);
        this.ventana.radio_inicio.setSelected(true);
        this.ventana.btn_realizar.addActionListener(this);
        this.ventana.btn_limpiar.addActionListener(this);
        this.ventana.setResizable(false);
        iniciaTablero();
        this.ventana.pack();
        this.ventana.setLocationRelativeTo(null);
        this.ventana.setVisible(true);
    }

    /**
     * Establece los valores iniciales de los botones
     */
    private void reset() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                this.tablero[i][j].setEnabled(true);
                this.tablero[i][j].setBackground(Color.WHITE);
                this.terreno.getGrafo()[i][j].setCosto(1.0);
                this.tablero[i][j].setText("    ");
            }
        }
        this.ventana.radio_inicio.setSelected(true);
        this.ventana.radio_inicio.setEnabled(true);
        this.ventana.radio_fin.setEnabled(true);
        this.terreno.setInicio(null);
        this.terreno.setFin(null);
        this.ventana.pack();
    }

    /**
     * Nos dice si el valor de inicio y final existen
     *
     * @return
     */
    private boolean estaConfigurado() {
        return ((this.terreno.getInicio() != null) && (this.terreno.getFin() != null));
    }

    /**
     * Inicia algoritmo Basado en la seleccion de la lista de algoritmos,
     * realiza uno y lo muestra en pantalla
     *
     * @param indice
     */
    private void iniciarAlgoritmo(int indice) {
        Algoritmos algoritmos = new Algoritmos(this.terreno);
        switch (indice) {
            case 0:
                Stack res = algoritmos.DFS();
                System.out.println("camino DFS");
                while(!res.isEmpty())System.out.println(res.pop());          ;
                break;
            case 1:
                
                break;
            case 2:
                
                break;
            default:
                throw new AssertionError();
        }
    }

    /**
     * Marca inicio primero obtiene la informacion de actioncomand que dice la
     * posicion cambia el color a verde desactiva el selector de inicio la
     * bolita, para que ya no se pueda usar selecciona por defecto el boton
     * bolita de obstaculo establece el valor de inicio como ese boton marca el
     * tablero de enteros con 1
     *
     * @param actionCommand
     */
    private void marcarInicio(String actionCommand) {
        int fila = Integer.parseInt(actionCommand.split(",")[0]);
        int columna = Integer.parseInt(actionCommand.split(",")[1]);
        tablero[fila][columna].setBackground(Color.GREEN);
        this.ventana.radio_inicio.setEnabled(false);
        this.ventana.radio_obstaculo.setSelected(true);
        tablero[fila][columna].setEnabled(false);
        this.terreno.estableceInicio(fila,columna);
    }

    /**
     * Marca Final primero obtiene la informacion de actioncomand que dice la
     * posicion cambia el color a rojo desactiva el selector de fin la bolita,
     * para que ya no se pueda usar selecciona por defecto el boton bolita de
     * obstaculo establece el valor de fin como ese boton marca el tablero de
     * enteros con 2
     *
     * @param actionCommand
     */
    private void marcarFin(String actionCommand) {
        int fila = Integer.parseInt(actionCommand.split(",")[0]);
        int columna = Integer.parseInt(actionCommand.split(",")[1]);
        tablero[fila][columna].setBackground(Color.RED);
        this.ventana.radio_fin.setEnabled(false);
        this.ventana.radio_obstaculo.setSelected(true);
        tablero[fila][columna].setEnabled(false);
        this.terreno.estableceFin(fila,columna);
    }

    /**
     * Marca Obstaculo primero obtiene la informacion de actioncomand que dice
     * la posicion cambia el color al correspondiente y la matriz de enteros a
     * un valor identificable 3 - IMPOSIBLE 4 - VERY TOUGH 5 - TOUGH 6 - NORMAL
     * 7 - EASY
     *
     * @param actionCommand
     * @param selectedIndex
     */
    private void marcarObstaculo(String actionCommand, int selectedIndex) {
        int fila = Integer.parseInt(actionCommand.split(",")[0]);
        int columna = Integer.parseInt(actionCommand.split(",")[1]);
        tablero[fila][columna].setEnabled(false);
        switch (selectedIndex) {
            case 0:
                tablero[fila][columna].setBackground(Color.BLACK);
                this.terreno.getGrafo()[fila][columna].setCosto(0.0);
                this.terreno.getGrafo()[fila][columna].setObstaculo(true);
                break;
            case 1:
                tablero[fila][columna].setBackground(Color.GRAY.darker());
                this.terreno.getGrafo()[fila][columna].setCosto(10.0);
                break;
            case 2:
                tablero[fila][columna].setBackground(Color.GRAY.brighter());
                this.terreno.getGrafo()[fila][columna].setCosto(5.0);
                break;
            case 3:
                tablero[fila][columna].setBackground(Color.WHITE);
                this.terreno.getGrafo()[fila][columna].setCosto(1.0);
                break;
            case 4:
                tablero[fila][columna].setBackground(Color.ORANGE);
                this.terreno.getGrafo()[fila][columna].setCosto(0.3);
                break;
            default:
                throw new AssertionError();
        }
    }

}
