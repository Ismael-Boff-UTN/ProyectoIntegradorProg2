package com.sky.pi.controller;

import com.sky.pi.dao.AlumnoDAO;
import com.sky.pi.dao.CarreraDAO;
import com.sky.pi.dao.InscripcionDAO;
import com.sky.pi.model.Alumno;
import com.sky.pi.model.Carrera;
import com.sky.pi.model.Inscripcion;

import com.sky.pi.view.AgregarInscripcion;
import com.sky.pi.view.Menu;
import com.sky.pi.view.PanelInscripciones;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SkylakeFrost
 */
public class ControladorInscripciones implements ActionListener {

    Inscripcion inscripcion = new Inscripcion();
    InscripcionDAO inscripcionDAO = new InscripcionDAO();
    AgregarInscripcion agregarInscripcion = new AgregarInscripcion();
    PanelInscripciones panelInscripciones;
    private DefaultTableModel modelo;
    private Menu menu;

    //Para Llenar El Combo
    AlumnoDAO alumnoDAO = new AlumnoDAO();
    CarreraDAO carreraDAO = new CarreraDAO();

    @SuppressWarnings("LeakingThisInConstructor")
    public ControladorInscripciones(Menu menu, PanelInscripciones panelInscripciones) {
        this.panelInscripciones = panelInscripciones;
        this.menu = menu;

        this.panelInscripciones.getBtnNuevaInscripcion().addActionListener(this);
        this.panelInscripciones.getBtnEditar().addActionListener(this);
        this.panelInscripciones.getBtnEliminar().addActionListener(this);

        this.agregarInscripcion.getBtnInscribir().addActionListener(this);
        this.agregarInscripcion.getBtnCancelar().addActionListener(this);

    }

    public ControladorInscripciones() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelInscripciones.getBtnNuevaInscripcion()) {
            cargarComboBox();
            agregarInscripcion.setVisible(true);
        }
        if (e.getSource() == agregarInscripcion.getBtnCancelar()) {
            agregarInscripcion.dispose();
        }
        if (e.getSource() == agregarInscripcion.getBtnInscribir()) {
            agregar();
        }
        if (e.getSource() == panelInscripciones.getBtnEliminar()) {
            eliminar();
        }
        if (e.getSource() == panelInscripciones.getBtnEditar()) {
            //TODO
        }
    }

    public void agregar() {

        inscripcion = new Inscripcion(
                String.valueOf(agregarInscripcion.getCbxAlumnos().getSelectedItem()),
                Date.valueOf(convertirFecha(agregarInscripcion.getDateChooserCombo().getText())),
                String.valueOf(agregarInscripcion.getCbxCarreras().getSelectedItem())
        );

      
        if (inscripcionDAO.create(inscripcion) == true) {

            alumnoDAO.updateCarrera(new Alumno(
                    String.valueOf(agregarInscripcion.getCbxCarreras().getSelectedItem()),
                    idAlumno(String.valueOf(agregarInscripcion.getCbxAlumnos().getSelectedItem())))
            );
            clearTable();
            listarInscripciones(panelInscripciones.getTblInscripciones());
            JOptionPane.showMessageDialog(null, "Guardado Con Exito!");
            agregarInscripcion.dispose();

        } else {
            JOptionPane.showMessageDialog(null, "ERROR");
        }
    }

    public void eliminar() {
        int fila = panelInscripciones.getTblInscripciones().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debe Seleccione Una Fila!");
        } else {
            if (JOptionPane.showConfirmDialog(null, "Desea Elimnar?", "Seleccione Una Opc.", JOptionPane.YES_NO_OPTION) == 0) {
                int id = Integer.parseInt((String) panelInscripciones.getTblInscripciones().getValueAt(fila, 0).toString());
                inscripcionDAO.delete(id);
                clearTable();
                listarInscripciones(panelInscripciones.getTblInscripciones());
                JOptionPane.showMessageDialog(null, "Eliminado!");
            }

        }
    }

    public void cargarComboBox() {

        List<Alumno> listaAlumnos = alumnoDAO.read();
        agregarInscripcion.getCbxAlumnos().removeAllItems();
        for (int i = 0; i < listaAlumnos.size(); i++) {
            agregarInscripcion.getCbxAlumnos().addItem(listaAlumnos.get(i).getId() + " - " + listaAlumnos.get(i).getNombre() + " " + listaAlumnos.get(i).getApellido());
        }

        List<Carrera> listaCarreras = carreraDAO.read();
        agregarInscripcion.getCbxCarreras().removeAllItems();
        for (int i = 0; i < listaCarreras.size(); i++) {
            agregarInscripcion.getCbxCarreras().addItem(listaCarreras.get(i).getCodigoCarrera() + " - " + listaCarreras.get(i).getNombre());
        }

    }

    public String convertirFecha(String fechaDDMMYYYY) {

        String[] parts = fechaDDMMYYYY.split("-");
        String part1 = parts[0]; // Año
        String part2 = parts[1]; // Mes
        String part3 = parts[2]; //Dia

        String fechaYYYYMMDD = part3 + "-" + part2 + "-" + part1;

        return fechaYYYYMMDD;
    }

    public int idAlumno(String alumno) {
        String[] parts = alumno.split(" - ");
        String part1 = parts[0]; // ID

        return Integer.valueOf(part1);

    }

    public void listarInscripciones(JTable table) {
        modelo = (DefaultTableModel) panelInscripciones.getTblInscripciones().getModel();
        panelInscripciones.getTblInscripciones().setRowHeight(30);
        List<Inscripcion> lista = inscripcionDAO.read();
        Object[] fila = new Object[4];
        for (int i = 0; i < lista.size(); i++) {
            fila[0] = lista.get(i).getCodigoInscripcion();
            fila[1] = lista.get(i).getNombre();
            fila[2] = lista.get(i).getFecha();
            fila[3] = lista.get(i).getCodigoCarrera();

            modelo.addRow(fila);
        }
        panelInscripciones.getTblInscripciones().setModel(modelo);

    }

    public void clearTable() {
        for (int i = 0; i < panelInscripciones.getTblInscripciones().getRowCount(); i++) {
            modelo.removeRow(i);
            i -= 1;
        }
    }
}
