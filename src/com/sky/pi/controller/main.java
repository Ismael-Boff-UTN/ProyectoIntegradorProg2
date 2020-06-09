package com.sky.pi.controller;

import com.sky.pi.view.Loading;
import com.sky.pi.view.Menu;

/**
 *
 * @author SkylakeFrost
 */
public class main {

    public static void main(String[] args) {

        long time_start, time_end;
        time_start = System.currentTimeMillis();
        Loading load = new Loading();

        load.setVisible(true);  //Se Abre El Splash De Carga y Se Inician Funciones...

        Menu menu = new Menu();
        ControladorMenuPrincipal cm = new ControladorMenuPrincipal(menu);
        cm.panelAlumno();// Por Defecto Inicia En El Panel De Alumnos
        menu.setLocationRelativeTo(null);
        menu.setResizable(false);
        menu.setTitle("Proyecto Final - Programacion II");
        menu.getSelectAlumnos().setVisible(true);
        menu.getLblMainTitle().setText("Lista De Alumnos");

        load.setVisible(false);//Se Cierra El Splash De Carga

        menu.setVisible(true);//Se Muestra El Menú Con Los Datos Cargados
        time_end = System.currentTimeMillis();

        System.out.println("Tiempo De Carga : " + (time_start - time_end));
    }
}
