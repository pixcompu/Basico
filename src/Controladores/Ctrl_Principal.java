/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Algoritmos;
import Modelo.Nodo;
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

    private final Dimension tamañoBoton = new Dimension(45, 25);
    private final Insets margen = new Insets(0, 0, 0, 0);
    private final Font fuenteBtn = new Font("Tahoma", 1, 9);
    private final Principal ventana;
    private final JPanel cuadricula;
    private final Terreno terreno;
    private final JButton[][] tablero;
    private boolean opcionDiagonal;
    private boolean algoritmoIniciado;
    private Algoritmos algoritmos;
    public static final int INICIO = 0;
    public static final int FACIL = 1;
    public static final int NORMAL = 2;
    public static final int DIFICIL = 3;
    public static final int EXTREMO = 4;
    public static final int IMPOSIBLE = 5;
    public static final int FIN = 6;
    private final Color COLOR_INICIO = Color.GREEN;
    private final Color COLOR_FIN = Color.RED;
    private final Color COLOR_FACIL = Color.ORANGE;
    private final Color COLOR_NORMAL = Color.WHITE;
    private final Color COLOR_DIFICIL = Color.GRAY.brighter();
    private final Color COLOR_EXTREMO = Color.GRAY.darker();
    private final Color COLOR_IMPOSIBLE = Color.BLACK;
    private final Color COLOR_RECORRIDO = Color.CYAN.brighter();
    private final Color COLOR_CAMINO = Color.YELLOW;

    public void pintaBusqueda(Nodo[][] tableroNodo) {
        Nodo actual;
        int dim = tableroNodo.length;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                actual = tableroNodo[i][j];
                if (actual.isRecorrido() && !actual.equals(this.terreno.getInicio()) && !actual.equals(this.terreno.getFin())) {
                    this.tablero[i][j].setText(String.valueOf(actual.getCostoAcumulado()));
                    this.tablero[i][j].setBackground(COLOR_RECORRIDO);
                }
            }
        }
    }

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
        this.terreno = new Terreno(20, 20);
        this.tablero = new JButton[20][20];
        this.algoritmoIniciado = false;
        iniciar();
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
        this.opcionDiagonal = false;
        this.ventana.tgl_diagonal.addActionListener(this);
        iniciaTablero();
        algoritmos = new Algoritmos();
        this.ventana.pack();
        this.ventana.setLocationRelativeTo(null);
        this.ventana.setVisible(true);
    }

    /**
     * Configura cada boton del tablero le agrega texto le agrega informacion de
     * la posicion en forma de actioncommand le pone un color de fondo inicia
     * las cuadriculas del tablero de algoritmos en 0 añade al panel el boton
     * recien creado
     */
    private void iniciaTablero() {
        for (int i = 0; i < terreno.getGrafo().length; i++) {
            for (int j = 0; j < terreno.getGrafo().length; j++) {
                this.tablero[i][j] = new JButton("   ");
                this.tablero[i][j].setActionCommand("" + i + "," + j + "");
                this.tablero[i][j].setToolTipText("Posicion: (" + i + "," + j + ")");
                this.tablero[i][j].setBackground(COLOR_NORMAL);
                this.tablero[i][j].setFont(fuenteBtn);
                this.tablero[i][j].setMargin(margen);
                this.tablero[i][j].setPreferredSize(tamañoBoton);
                this.tablero[i][j].addActionListener(this);
                terreno.getGrafo()[i][j] = new Nodo(i, j, 1.0);
                terreno.getGrafo()[i][j].setTipoNodo(NORMAL);
                cuadricula.add(this.tablero[i][j]);
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
                reset(false);
                break;
            case "diagonal":
                this.opcionDiagonal = this.ventana.tgl_diagonal.isSelected();
                this.ventana.tgl_diagonal.setText((this.opcionDiagonal ? "Con " : "Sin ") + "Diagonales");
                if (this.algoritmoIniciado) {
                    reset(true);
                    iniciarAlgoritmo(this.ventana.list_Metodos.getSelectedIndex());
                }
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
                if (this.algoritmoIniciado) {
                    reset(true);
                    iniciarAlgoritmo(this.ventana.list_Metodos.getSelectedIndex());
                }
        }
    }

    private Color getColor(int tipoNodo) {
        switch (tipoNodo) {
            case NORMAL:
                return COLOR_NORMAL;
            case DIFICIL:
                return COLOR_DIFICIL;
            case EXTREMO:
                return COLOR_EXTREMO;
            case IMPOSIBLE:
                return COLOR_IMPOSIBLE;
            case FACIL:
                return COLOR_FACIL;
            case INICIO:
                return COLOR_INICIO;
            case FIN:
                return COLOR_FIN;
            default:
                throw new AssertionError();
        }
    }

    /**
     * Establece los valores iniciales de los botones
     */
    private void reset(boolean enEjecucion) {
        Nodo[][] grafo = this.terreno.getGrafo();
        int dim = grafo.length;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                grafo[i][j].resetNodo();
                if (!enEjecucion) {
                    this.tablero[i][j].setEnabled(true);
                    this.tablero[i][j].setBackground(COLOR_NORMAL);
                    grafo[i][j].setCosto(1.0);
                    grafo[i][j].setTipoNodo(NORMAL);
                } else {
                    this.tablero[i][j].setBackground(getColor(grafo[i][j].getTipoNodo()));
                }
                this.tablero[i][j].setText("    ");
            }
        }
        if (!enEjecucion) {
            this.terreno.setInicio(null);
            this.terreno.setFin(null);
            this.algoritmoIniciado = false;
        }

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
        this.algoritmoIniciado = true;
        this.algoritmos.setDiagonalConsiderada(this.opcionDiagonal);
        this.algoritmos.setTerreno(this.terreno);
        this.algoritmos.resetCola();
        Stack res;
        switch (indice) {
            case 0:
                res = algoritmos.Depth_FS(terreno.getInicio(), terreno.getFin());
                break;
            case 1:
                res = algoritmos.Breath_FS(terreno.getInicio(), terreno.getFin());
                break;
            case 2:
                res = algoritmos.StarA(terreno.getInicio(), terreno.getFin());
                break;
            default:
                throw new AssertionError();
        }
        pintaBusqueda(this.terreno.getGrafo());
        if (res != null) {
            mostrarCamino(res);
        } else {
            mostrarMensaje("No se encontraron caminos");
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
        if (this.terreno.getInicio() != null) {
            int filaAnterior = this.terreno.getInicio().getFila();
            int columnaAnterior = this.terreno.getInicio().getColumna();
            this.tablero[filaAnterior][columnaAnterior].setBackground(COLOR_NORMAL);
            this.tablero[filaAnterior][columnaAnterior].setEnabled(true);
            this.terreno.getInicio().setCosto(1.0);
            this.terreno.getInicio().setTipoNodo(NORMAL);
        }
        int fila = Integer.parseInt(actionCommand.split(",")[0]);
        int columna = Integer.parseInt(actionCommand.split(",")[1]);
        this.tablero[fila][columna].setBackground(COLOR_INICIO);
        this.tablero[fila][columna].setEnabled(false);
        this.terreno.estableceInicio(fila, columna);
        this.terreno.getInicio().setCosto(Integer.MIN_VALUE);
        this.terreno.getInicio().setTipoNodo(INICIO);
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
        if (this.terreno.getFin() != null) {
            int filaAnterior = this.terreno.getFin().getFila();
            int columnaAnterior = this.terreno.getFin().getColumna();
            this.tablero[filaAnterior][columnaAnterior].setBackground(COLOR_NORMAL);
            this.tablero[filaAnterior][columnaAnterior].setEnabled(true);
            this.terreno.getFin().setCosto(1.0);
            this.terreno.getFin().setTipoNodo(NORMAL);
        }
        int fila = Integer.parseInt(actionCommand.split(",")[0]);
        int columna = Integer.parseInt(actionCommand.split(",")[1]);
        this.tablero[fila][columna].setBackground(COLOR_FIN);
        this.tablero[fila][columna].setEnabled(false);
        this.terreno.estableceFin(fila, columna);
        this.terreno.getFin().setCosto(Integer.MIN_VALUE);
        this.terreno.getFin().setTipoNodo(FIN);
    }

    private void marcaCasillaObstaculo(int fila, int columna, Color c, double costo) {
        this.tablero[fila][columna].setBackground(c);
        this.terreno.getGrafo()[fila][columna].setCosto(costo);
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
        switch (selectedIndex) {
            case 0:
                this.terreno.getGrafo()[fila][columna].setTipoNodo(IMPOSIBLE);
                marcaCasillaObstaculo(fila, columna, COLOR_IMPOSIBLE, Integer.MAX_VALUE);
                break;
            case 1:
                this.terreno.getGrafo()[fila][columna].setTipoNodo(EXTREMO);
                marcaCasillaObstaculo(fila, columna, COLOR_EXTREMO, 10.0);
                break;
            case 2:
                this.terreno.getGrafo()[fila][columna].setTipoNodo(DIFICIL);
                marcaCasillaObstaculo(fila, columna, COLOR_DIFICIL, 5.0);
                break;
            case 3:
                this.terreno.getGrafo()[fila][columna].setTipoNodo(NORMAL);
                marcaCasillaObstaculo(fila, columna, COLOR_NORMAL, 1.0);
                break;
            case 4:
                this.terreno.getGrafo()[fila][columna].setTipoNodo(FACIL);
                marcaCasillaObstaculo(fila, columna, COLOR_FACIL, 0.3);
                break;
            default:
                throw new AssertionError();
        }
    }

    private void mostrarCamino(Stack res) {
        Nodo reco;
        int fila, columna;
        double costo = 0;
        while (!res.isEmpty()) {
            reco = ((Nodo) res.pop());
            if (!reco.equals(terreno.getInicio()) && !reco.equals(terreno.getFin())) {
                fila = reco.getFila();
                columna = reco.getColumna();
                costo += reco.getCosto();
                tablero[fila][columna].setBackground(COLOR_CAMINO);
                tablero[fila][columna].setText(String.valueOf(costo));
            }
        }
    }

    private void mostrarMensaje(String msj) {
        JOptionPane.showMessageDialog(this.ventana, msj, "Alerta", 1);
    }

}
