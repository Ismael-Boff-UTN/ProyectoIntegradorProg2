package com.sky.pi.controller;

import com.sky.pi.view.Loading;
import com.sky.pi.view.Menu;

/**
 *
 * @author SkylakeFrost
 */
public class main {

    public static void main(String[] args) throws InterruptedException {
        Menu menu = new Menu();
        Loading load = new Loading();
        Menu.setDefaultLookAndFeelDecorated(false);

        ControladorMenuPrincipal cm = new ControladorMenuPrincipal(menu);
        load.setVisible(true);
        Thread.sleep(3000);
        load.setVisible(false);
        
        menu.setVisible(true);
        menu.setLocationRelativeTo(null);
        menu.setTitle("Proyecto Final - Programacion II");
        menu.getLblAlumnos().setVisible(true);
        menu.getLblMainTitle().setText("Lista De Alumnos");
        cm.panelAlumno();// Por Defecto Inicia En El Panel De Alumnos

    }
}
