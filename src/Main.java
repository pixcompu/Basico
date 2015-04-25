
import Controladores.Ctrl_Principal;
import Ventanas.Principal;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Felix Diaz ®
 */
public class Main {
 
    public static void main(String[] args) {
        Principal ventana = new Principal();
        Ctrl_Principal controlador = new Ctrl_Principal(ventana);
    }
}
