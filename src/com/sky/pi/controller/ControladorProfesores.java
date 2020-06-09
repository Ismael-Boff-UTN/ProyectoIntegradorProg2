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
    private ProfesorDAO profesorDAO = new ProfesorDAO();
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
            editarProfesor.setVisible(true);
        }
        if (e.getSource() == agregarProfesor.getBtnGuardar()) {
            agregar();
        }
        if (e.getSource() == agregarProfesor.getBtnCancelar()) {
            agregarProfesor.dispose();
        }
        if (e.getSource() == editarProfesor.getBtnGuardar()) {

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

            System.out.println(profesor.getFechaNacimiento());
            if (profesorDAO.create(profesor) == true) {

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
                profesorDAO.delete(id);
                clearTable();
                listarProfesores(panelProfesores.getTblProfesores());
                JOptionPane.showMessageDialog(null, "Eliminado!");
            }

        }
    }

    public void listarProfesores(JTable table) {
        modelo = (DefaultTableModel) panelProfesores.getTblProfesores().getModel();
        panelProfesores.getTblProfesores().setRowHeight(30);
        List<Profesor> lista = profesorDAO.read();
        Object[] fila = new Object[7];
        for (int i = 0; i < lista.size(); i++) {

            fila[0] = lista.get(i).getId();
            fila[1] = lista.get(i).getDni();
            fila[2] = lista.get(i).getNombre();
            fila[3] = lista.get(i).getApellido();
            fila[4] = lista.get(i).getFechaNacimiento();
            fila[5] = lista.get(i).getDomicilio();
            fila[6] = lista.get(i).getTelefono();

            modelo.addRow(fila);

            for (int j = 0; j < lista.size(); j++) {
                System.out.println("Nombre : " + lista.get(i).getNombre());
            }
        }
        panelProfesores.getTblProfesores().setModel(modelo);

        for (int i = 0; i < lista.size(); i++) {
            System.out.println("nombre " + lista.get(i).getNombre());
        }
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
