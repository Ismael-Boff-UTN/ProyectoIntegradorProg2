package com.sky.pi.controller;

import com.sky.pi.model.Cursado;
import com.sky.pi.view.AgregarCursado;
import com.sky.pi.view.EditarCursado;
import com.sky.pi.view.Menu;
import com.sky.pi.view.PanelCursados;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SkylakeFrost
 */
public class ControladorCursados implements ActionListener {

    private Cursado cursado = new Cursado();
    private AgregarCursado agregarCursado = new AgregarCursado();
    private EditarCursado editarCursado = new EditarCursado();
    private PanelCursados panelCursados;
    private DefaultTableModel modelo;
    private Menu menu;

    @SuppressWarnings("LeakingThisInConstructor")
    public ControladorCursados(Menu menu, PanelCursados panelCursados) {

        this.menu = menu;
        this.panelCursados = panelCursados;

        this.agregarCursado.getBtnAgregar().addActionListener(this);
        this.agregarCursado.getBtnCancelar().addActionListener(this);

        this.editarCursado.getBtnAgregar().addActionListener(this);
        this.editarCursado.getBtnCancelar().addActionListener(this);
    }

    public ControladorCursados() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelCursados.getBtnNuevo()) {
            agregarCursado.setVisible(true);
        } else if (e.getSource() == panelCursados.getBtnEditar()) {
            cargarVistaEditar();
        } else if (e.getSource() == panelCursados.getBtnEliminar()) {
            eliminar();
        } else if (e.getSource() == agregarCursado.getBtnAgregar()) {
            agregar();
        } else if (e.getSource() == agregarCursado.getBtnCancelar()) {
            agregarCursado.dispose();
        } else if (e.getSource() == editarCursado.getBtnAgregar()) {
            editar();
        } else if (e.getSource() == editarCursado.getBtnCancelar()) {
            editarCursado.dispose();
        }
    }

    public void agregar() {

    }

    public void eliminar() {

    }

    public void editar() {

    }

    public void cargarVistaEditar() {

    }

}
