package com.sky.pi.controller;

import com.sky.pi.view.Menu;
import com.sky.pi.view.PanelAlumnos;
import com.sky.pi.view.PanelCarreras;
import com.sky.pi.view.PanelCursados;
import com.sky.pi.view.PanelInscripciones;
import com.sky.pi.view.PanelMaterias;
import com.sky.pi.view.PanelProfesores;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;

/**
 *
 * @author SkylakeFrost
 */
public class ControladorMenuPrincipal implements ActionListener {

    private CardLayout cardLayout = new CardLayout();
    private Menu menu;
    //Paneles
    private PanelAlumnos panelAlumnos = new PanelAlumnos();
    private PanelCarreras panelCarreras = new PanelCarreras();
    private PanelInscripciones panelInscripciones = new PanelInscripciones();
    private PanelProfesores panelProfesores = new PanelProfesores();
    private PanelMaterias panelMaterias = new PanelMaterias();
    private PanelCursados panelCursados = new PanelCursados();
    //Controladores
    private ControladorAlumnos ca = new ControladorAlumnos(menu, panelAlumnos);
    private ControladorCarreras cc = new ControladorCarreras(menu, panelCarreras);
    private ControladorInscripciones ci = new ControladorInscripciones(menu, panelInscripciones);
    private ControladorProfesores cp = new ControladorProfesores(menu, panelProfesores);
    private ControladorMaterias cm = new ControladorMaterias(menu, panelMaterias);

    @SuppressWarnings("LeakingThisInConstructor")
    public ControladorMenuPrincipal(Menu menu) {
        this.cardLayout = (CardLayout) menu.getMainPanel().getLayout();
        this.menu = menu;

        this.menu.getBtnListaAlumnos().addActionListener(this);
        this.menu.getBtnListaCarreras().addActionListener(this);
        this.menu.getBtnCursados().addActionListener(this);
        this.menu.getBtnMaterias().addActionListener(this);
        this.menu.getBtnInscripciones().addActionListener(this);
        this.menu.getBtnListaProfesores().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menu.getBtnListaAlumnos()) {
            menu.getSelectAlumnos().setVisible(true);
            menu.getSelectCarrera().setVisible(false);
            menu.getSelectCursado().setVisible(false);
            menu.getSelectInsc().setVisible(false);
            menu.getSelectMat().setVisible(false);
            menu.getSelectProf().setVisible(false);
            menu.getLblMainTitle().setText("Lista De Alumnos");
            panelAlumno();
            ca.clearTable();
            ca.listarAlumnos(panelAlumnos.getTblAlumnos());

        }
        if (e.getSource() == menu.getBtnListaCarreras()) {
            menu.getSelectAlumnos().setVisible(false);
            menu.getSelectCarrera().setVisible(true);
            menu.getSelectCursado().setVisible(false);
            menu.getSelectInsc().setVisible(false);
            menu.getSelectMat().setVisible(false);
            menu.getSelectProf().setVisible(false);
            menu.getLblMainTitle().setText("Lista De Carreras");
            panelCarreras();
            cc.clearTable();
            cc.listarCarreras(panelCarreras.getTblCarreras());
        }
        if (e.getSource() == menu.getBtnInscripciones()) {
            menu.getSelectAlumnos().setVisible(false);
            menu.getSelectCarrera().setVisible(false);
            menu.getSelectCursado().setVisible(false);
            menu.getSelectInsc().setVisible(true);
            menu.getSelectMat().setVisible(false);
            menu.getSelectProf().setVisible(false);
            menu.getLblMainTitle().setText("Lista De Inscripciones");
            panelInscripciones();

            ci.clearTable();
            ci.listarInscripciones(panelInscripciones.getTblInscripciones());
        }
        if (e.getSource() == menu.getBtnListaProfesores()) {
            // panelAlumnos.dispatchEvent(e);
            menu.getSelectAlumnos().setVisible(false);
            menu.getSelectCarrera().setVisible(false);
            menu.getSelectCursado().setVisible(false);
            menu.getSelectInsc().setVisible(false);
            menu.getSelectMat().setVisible(false);
            menu.getSelectProf().setVisible(true);
            menu.getLblMainTitle().setText("Lista De Profesores");
            panelProfesores();
            cp.clearTable();
            cp.listarProfesores(panelProfesores.getTblProfesores());

        }
        if (e.getSource() == menu.getBtnMaterias()) {
            menu.getSelectAlumnos().setVisible(false);
            menu.getSelectCarrera().setVisible(false);
            menu.getSelectCursado().setVisible(false);
            menu.getSelectInsc().setVisible(false);
            menu.getSelectMat().setVisible(true);
            menu.getSelectProf().setVisible(false);
            menu.getLblMainTitle().setText("Lista De Materias");
            panelMaterias();
            cm.clearTable();
            cm.listarMaterias(panelMaterias.getTblMaterias());
        }
        if (e.getSource() == menu.getBtnCursados()) {
            menu.getSelectCursado().setVisible(true);
            menu.getSelectAlumnos().setVisible(false);
            menu.getSelectCarrera().setVisible(false);

            menu.getSelectInsc().setVisible(false);
            menu.getSelectMat().setVisible(false);
            menu.getSelectProf().setVisible(false);
            menu.getLblMainTitle().setText("Lista De Cursados");
            panelCursados();
        }
    }

    public void panelAlumno() {
        menu.getMainPanel().add(panelAlumnos, "panelAlumnos");
        cardLayout.show(menu.getMainPanel(), "panelAlumnos");
        SwingUtilities.updateComponentTreeUI(menu);
        menu.repaint();
        ca.listarAlumnos(panelAlumnos.getTblAlumnos());

    }

    public void panelCarreras() {
        menu.getMainPanel().add(panelCarreras, "panelCarreras");
        cardLayout.show(menu.getMainPanel(), "panelCarreras");
        SwingUtilities.updateComponentTreeUI(menu);
        menu.repaint();
        cc.listarCarreras(panelCarreras.getTblCarreras());
    }

    public void panelInscripciones() {
        menu.getMainPanel().add(panelInscripciones, "panelIns");
        cardLayout.show(menu.getMainPanel(), "panelIns");
        SwingUtilities.updateComponentTreeUI(menu);
        menu.repaint();
        ci.listarInscripciones(panelInscripciones.getTblInscripciones());
    }

    public void panelProfesores() {
        menu.getMainPanel().add(panelProfesores, "panelProf");
        cardLayout.show(menu.getMainPanel(), "panelProf");
        SwingUtilities.updateComponentTreeUI(menu);
        menu.repaint();
    }

    public void panelMaterias() {
        menu.getMainPanel().add(panelMaterias, "panelMat");
        cardLayout.show(menu.getMainPanel(), "panelMat");
        SwingUtilities.updateComponentTreeUI(menu);
        menu.repaint();
        cm.listarMaterias(panelMaterias.getTblMaterias());
    }

    public void panelCursados() {
        menu.getMainPanel().add(panelCursados, "panelCur");
        cardLayout.show(menu.getMainPanel(), "panelCur");
        SwingUtilities.updateComponentTreeUI(menu);
        menu.repaint();
    }
}
