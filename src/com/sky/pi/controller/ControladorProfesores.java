package com.sky.pi.controller;

import com.sky.pi.dao.ProfesorDAO;
import com.sky.pi.model.Alumno;
import com.sky.pi.model.Profesor;
import com.sky.pi.view.AgregarProfesor;
import com.sky.pi.view.EditarProfesor;
import com.sky.pi.view.Menu;
import com.sky.pi.view.PanelProfesores;
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
public class ControladorProfesores implements ActionListener {

    private Profesor profesor = new Profesor();
    //private ProfesorDAO profesorDAO = new ProfesorDAO();
    private AgregarProfesor agregarProfesor = new AgregarProfesor();
    private EditarProfesor editarProfesor = new EditarProfesor();
    private PanelProfesores panelProfesores;
    private DefaultTableModel modelo;
    private Menu menu;

    @SuppressWarnings("LeakingThisInConstructor")
    public ControladorProfesores(Menu menu, PanelProfesores panelProfesores) {
        this.panelProfesores = panelProfesores;
        this.menu = menu;

        this.panelProfesores.getBtnNuevo().addActionListener(this);
        this.panelProfesores.getBtnEliminar().addActionListener(this);
        this.panelProfesores.getBtnEditar().addActionListener(this);

        this.agregarProfesor.getBtnGuardar().addActionListener(this);
        this.agregarProfesor.getBtnCancelar().addActionListener(this);

        this.editarProfesor.getBtnGuardar().addActionListener(this);
        this.editarProfesor.getBtnCancelar().addActionListener(this);

    }

    public ControladorProfesores() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelProfesores.getBtnNuevo()) {
            agregarProfesor.setVisible(true);
        }
        if (e.getSource() == panelProfesores.getBtnEliminar()) {
            eliminar();
        }
        if (e.getSource() == panelProfesores.getBtnEditar()) {
            cargarVistaEditar();
            editarProfesor.setVisible(true);
        }
        if (e.getSource() == agregarProfesor.getBtnGuardar()) {
            agregar();
        }
        if (e.getSource() == agregarProfesor.getBtnCancelar()) {
            agregarProfesor.dispose();
        }
        if (e.getSource() == editarProfesor.getBtnGuardar()) {
            editar();

        }
        if (e.getSource() == editarProfesor.getBtnCancelar()) {
            editarProfesor.dispose();
        }

    }

    public void agregar() {
        if (validarCampos() == false) {
            JOptionPane.showMessageDialog(null, "Todos Los Campos Deben Estar Completos!");
        } else {

            profesor = new Profesor(
                    Integer.valueOf(agregarProfesor.getTxtDni().getText()),
                    agregarProfesor.getTxtNombre().getText(),
                    agregarProfesor.getTxtApellido().getText(),
                    Date.valueOf(convertirFecha(agregarProfesor.getDateChooserCombo().getText())),
                    agregarProfesor.getTxtDomicilio().getText(),
                    Integer.valueOf(agregarProfesor.getTxtTelefono().getText())
            );

            if (profesor.createProfesor(profesor) == true) {

                clearTable();
                listarProfesores(panelProfesores.getTblProfesores());
                JOptionPane.showMessageDialog(null, "Guardado Con Exito!");
                agregarProfesor.dispose();

            } else {
                JOptionPane.showMessageDialog(null, "ERROR");
            }
        }
    }

    public void eliminar() {
        int fila = panelProfesores.getTblProfesores().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debe Seleccione Una Fila!");
        } else {
            if (JOptionPane.showConfirmDialog(null, "Desea Elimnar?", "Seleccione Una Opc.", JOptionPane.YES_NO_OPTION) == 0) {
                int id = Integer.parseInt((String) panelProfesores.getTblProfesores().getValueAt(fila, 0).toString());
                profesor.deleteProfesor(id);
                clearTable();
                listarProfesores(panelProfesores.getTblProfesores());
                JOptionPane.showMessageDialog(null, "Eliminado!");
            }

        }
    }

    public void editar() {

        int dni = Integer.valueOf(editarProfesor.getTxtDni().getText());
        String nombre = editarProfesor.getTxtNombre().getText();
        String apellido = editarProfesor.getTxtApellido().getText();
        Date fechaNacimiento = Date.valueOf(convertirFecha(editarProfesor.getDateChooserCombo().getText()));
        String domicilio = editarProfesor.getTxtDomicilio().getText();
        int telefono = Integer.valueOf(editarProfesor.getTxtTelefono().getText());

        profesor = new Profesor(dni, nombre, apellido, fechaNacimiento, domicilio, telefono);

        if (profesor.updateProfesores(profesor) == true) {
            clearTable();
            listarProfesores(panelProfesores.getTblProfesores());
            JOptionPane.showMessageDialog(null, "Editado Con Exito");
            editarProfesor.setVisible(false);

        } else {
            JOptionPane.showMessageDialog(null, "ha Ocurrido Un Error!");
        }
    }

    public void cargarVistaEditar() {

        int fila = panelProfesores.getTblProfesores().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debe Seleccione Una Fila!");
        } else {
            editarProfesor.getTxtDni().setText(panelProfesores.getTblProfesores().getValueAt(fila, 0).toString());
            editarProfesor.getTxtNombre().setText(panelProfesores.getTblProfesores().getValueAt(fila, 1).toString());
            editarProfesor.getTxtApellido().setText(panelProfesores.getTblProfesores().getValueAt(fila, 2).toString());
            editarProfesor.getDateChooserCombo().setText(panelProfesores.getTblProfesores().getValueAt(fila, 3).toString());
            editarProfesor.getTxtDomicilio().setText(panelProfesores.getTblProfesores().getValueAt(fila, 4).toString());
            editarProfesor.getTxtTelefono().setText(panelProfesores.getTblProfesores().getValueAt(fila, 5).toString());
            editarProfesor.setVisible(true);
        }

    }

    public void listarProfesores(JTable table) {
        modelo = (DefaultTableModel) panelProfesores.getTblProfesores().getModel();
        panelProfesores.getTblProfesores().setRowHeight(30);
        List<Profesor> lista = profesor.readProfesores();
        Object[] fila = new Object[6];
        for (int i = 0; i < lista.size(); i++) {

            fila[0] = lista.get(i).getDni();
            fila[1] = lista.get(i).getNombre();
            fila[2] = lista.get(i).getApellido();
            fila[3] = lista.get(i).getFechaNacimiento();
            fila[4] = lista.get(i).getDomicilio();
            fila[5] = lista.get(i).getTelefono();

            modelo.addRow(fila);

        }
        panelProfesores.getTblProfesores().setModel(modelo);

    }

    public void clearTable() {
        for (int i = 0; i < panelProfesores.getTblProfesores().getRowCount(); i++) {
            modelo.removeRow(i);
            i -= 1;
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

    public boolean validarCampos() {
        if (agregarProfesor.getTxtNombre().getText().isEmpty() || agregarProfesor.getTxtNombre().getText().isEmpty()
                || agregarProfesor.getTxtDomicilio().getText().isEmpty()
                || agregarProfesor.getTxtTelefono().getText().isEmpty() || agregarProfesor.getTxtDni().getText().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
