package com.sky.pi.controller;

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
    private Validations val = new Validations();
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
        } else if (validarTextoCampos() == false) {
            JOptionPane.showMessageDialog(null, "Los Campos Nombre, Apellido Y Domicilio Pueden Tener Maximo 45 Caracteres!");
        } else if (val.verificarDni(agregarProfesor.getTxtDni().getText()) == false) {
            JOptionPane.showMessageDialog(null, "DNI Debe Contener 8 Digitos");
        } else if (val.verificarTelefono(agregarProfesor.getTxtTelefono().getText()) == false) {
            JOptionPane.showMessageDialog(null, "Telefono Puede Contener Un Maximo De 12 Caracteres Alfanumericos");
        } else if (val.dniContieneLetras(agregarProfesor.getTxtDni().getText()) == false) {
            JOptionPane.showMessageDialog(null, "DNI Solo Puede Contener Numeros!");
        } else if (profesor.profesorExist(Integer.valueOf(agregarProfesor.getTxtDni().getText())) == true) {
            JOptionPane.showMessageDialog(null, "El Profesor Con DNI : " + agregarProfesor.getTxtDni().getText() + " Ya Existe!");
        } else {
            //Se Crea El Profesor Luego De Las Validaciones
            profesor.setDni(Integer.valueOf(agregarProfesor.getTxtDni().getText()));
            profesor.setNombre(agregarProfesor.getTxtNombre().getText());
            profesor.setApellido(agregarProfesor.getTxtApellido().getText());
            profesor.setFechaNacimiento(Date.valueOf(agregarProfesor.getDateChooserCombo().getText()));
            profesor.setDomicilio(agregarProfesor.getTxtDomicilio().getText());
            profesor.setTelefono(agregarProfesor.getTxtTelefono().getText());

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

        if (validarCamposEdicion() == false) {
            JOptionPane.showMessageDialog(null, "Todos Los Campos Deben Estar Completos!");
        } else if (validarTextoCamposEdicion() == false) {
            JOptionPane.showMessageDialog(null, "Los Campos Nombre, Apellido Y Domicilio Pueden Tener Maximo 45 Caracteres!");
        } else if (val.verificarTelefono(editarProfesor.getTxtTelefono().getText()) == false) {
            JOptionPane.showMessageDialog(null, "Telefono Puede Contener Un Maximo De 12 Caracteres Alfanumericos");
        } else {
            //Se Crea El Profesor Luego De Las Validaciones
            profesor.setDni(Integer.valueOf(editarProfesor.getTxtDni().getText()));
            profesor.setNombre(editarProfesor.getTxtNombre().getText());
            profesor.setApellido(editarProfesor.getTxtApellido().getText());
            profesor.setFechaNacimiento(Date.valueOf(editarProfesor.getDateChooserCombo().getText()));
            profesor.setDomicilio(editarProfesor.getTxtDomicilio().getText());
            profesor.setTelefono(editarProfesor.getTxtTelefono().getText());

            if (profesor.updateProfesores(profesor) == true) {

                clearTable();
                listarProfesores(panelProfesores.getTblProfesores());
                JOptionPane.showMessageDialog(null, "Editado Con Exito!");
                agregarProfesor.dispose();

            } else {
                JOptionPane.showMessageDialog(null, "ERROR");
            }
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

    public boolean validarTextoCampos() {
        if (agregarProfesor.getTxtNombre().getText().length() > 45 || agregarProfesor.getTxtApellido().getText().length() > 45
                || agregarProfesor.getTxtDomicilio().getText().length() > 45) {
            return false;
        } else {
            return true;
        }
    }

    public boolean validarTextoCamposEdicion() {
        if (editarProfesor.getTxtNombre().getText().length() > 45 || editarProfesor.getTxtApellido().getText().length() > 45
                || editarProfesor.getTxtDomicilio().getText().length() > 45) {
            return false;
        } else {
            return true;
        }
    }

    public boolean validarCampos() {
        if (agregarProfesor.getTxtNombre().getText().isEmpty() || agregarProfesor.getTxtApellido().getText().isEmpty()
                || agregarProfesor.getTxtDomicilio().getText().isEmpty()
                || agregarProfesor.getTxtTelefono().getText().isEmpty() || agregarProfesor.getTxtDni().getText().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean validarCamposEdicion() {
        if (editarProfesor.getTxtNombre().getText().isEmpty() || editarProfesor.getTxtApellido().getText().isEmpty()
                || editarProfesor.getTxtDomicilio().getText().isEmpty()
                || editarProfesor.getTxtTelefono().getText().isEmpty() || editarProfesor.getTxtDni().getText().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
