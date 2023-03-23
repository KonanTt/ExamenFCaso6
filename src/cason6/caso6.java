/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cason6;

import Controlador.ControladorSocio;
import Vista.VistaSocio;

/**
 *
 * @author Asus
 */
public class caso6 {
  public static void main(String[] args) {
//pruebaGIT

        VistaSocio vista = new VistaSocio();

        ControladorSocio control = new ControladorSocio(vista);
        control.iniciarControl();
    }
    
}


