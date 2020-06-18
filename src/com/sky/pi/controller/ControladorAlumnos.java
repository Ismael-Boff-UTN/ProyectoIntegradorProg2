package com.sky.pi.controller;

import com.sky.pi.model.Alumno;
import com.sky.pi.model.Inscripcion;
import com.sky.pi.view.AgregarAlumno;
import com.sky.pi.view.EditarAlumno;
import com.sky.pi.view.Menu;
import com.sky.pi.view.PanelAlumnos;
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
public class ControladorAlumnos implements ActionListener {

    private Alumno alumno = new Alumno();
    private Validations val = new Validations();
    private Inscripcion inscripcion = new Inscripcion();
    private AgregarAlumno agregarAlumno = new AgregarAlumno();
    private EditarAlumno editarAlumno = new EditarAlumno();
    private PanelAlumnos panelAlumnos;
    private DefaultTableModel modelo;
    private Menu menu;

    @SuppressWarnings("LeakingThisInConstructor")
    public ControladorAlumnos(Menu menu, PanelAlumnos panelAlumnos) {
        this.panelAlumnos = panelAlumnos;
        this.menu = menu;

        this.panelAlumnos.getBtnNuevoAlumno().addActionListener(this);
        this.panelAlumnos.getBtnEliminar().addActionListener(this);
        this.panelAlumnos.getBtnEditar().addActionListener(this);

        this.agregarAlumno.getBtnAgregar().addActionListener(this);
        this.agregarAlumno.getBtnCancelar().addActionListener(this);

        this.editarAlumno.getBtnEditar().addActionListener(this);
        this.editarAlumno.getBtnCancelar().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == panelAlumnos.getBtnNuevoAlumno()) {
            agregarAlumno.setVisible(true);
        } else if (e.getSource() == panelAlumnos.getBtnEliminar()) {
            eliminar();
        } else if (e.getSource() == panelAlumnos.getBtnEditar()) {
            cargarVistaEditar();

        } else if (e.getSource() == agregarAlumno.getBtnAgregar()) {

            agregar();
        } else if (e.getSource() == agregarAlumno.getBtnCancelar()) {
            agregarAlumno.dispose();
        } else if (e.getSource() == editarAlumno.getBtnEditar()) {
            editar();
        } else if (e.getSource() == editarAlumno.getBtnCancelar()) {
            editarAlumno.dispose();
        }

    }

    public void listarAlumnos(JTable table) {
        modelo = (DefaultTableModel) panelAlumnos.getTblAlumnos().getModel();
        panelAlumnos.getTblAlumnos().setRowHeight(30);
        List<Alumno> lista = alumno.readlumnos();
        Object[] fila = new Object[7];
        for (int i = 0; i < lista.size(); i++) {
            fila[0] = lista.get(i).getDni();
            fila[1] = lista.get(i).getNombre();
            fila[2] = lista.get(i).getApellido();
            fila[3] = lista.get(i).getFechaNacimiento();
            fila[4] = lista.get(i).getDomicilio();
            fila[5] = lista.get(i).getTelefono();
            fila[6] = lista.get(i).getCodigoInscripcion();
            modelo.addRow(fila);
        }
        panelAlumnos.getTblAlumnos().setModel(modelo);

    }

    public void clearTable() {
        for (int i = 0; i < panelAlumnos.getTblAlumnos().getRowCount(); i++) {
            modelo.removeRow(i);
            i -= 1;
        }
    }

    public void agregar() {

        if (validarCampos() == false) {
            JOptionPane.showMessageDialog(null, "Todos Los Campos Deben Estar Completos!");
        } else if (validarTextoCampos() == false) {
            JOptionPane.showMessageDialog(null, "Los Campos Nombre, Apellido Y Domicilio Pueden Tener Maximo 45 Caracteres!");
        } else if (val.verificarDni(agregarAlumno.getTxtDni().getText()) == false) {
            JOptionPane.showMessageDialog(null, "DNI Debe Contener 8 Digitos");
        } else if (val.verificarTelefono(agregarAlumno.getTxtTelefono().getText()) == false) {
            JOptionPane.showMessageDialog(null, "Telefono Puede Contener Un Maximo De 12 Caracteres Alfanumericos");
        } else if (val.dniContieneLetras(agregarAlumno.getTxtDni().getText()) == false) {
            JOptionPane.showMessageDialog(null, "DNI Solo Puede Contener Numeros!");
        } else if (alumno.alumnoExist(Integer.valueOf(agregarAlumno.getTxtDni().getText())) == true) {
            JOptionPane.showMessageDialog(null, "El Alumno Con DNI : " + agregarAlumno.getTxtDni().getText() + " Ya Existe!");
        } else {
            //Se Crea El Alumno Una Vez Que Se Validaron Los Datos Ingresados Por La Vista
            alumno.setDni(Integer.valueOf(agregarAlumno.getTxtDni().getText()));
            alumno.setNombre(agregarAlumno.getTxtNombre().getText());
            alumno.setApellido(agregarAlumno.getTxtApellido().getText());
            alumno.setFechaNacimiento(Date.valueOf(agregarAlumno.getDateChooser().getText()));
            alumno.setDomicilio(agregarAlumno.getTxtDomicilio().getText());
            alumno.setTelefono(agregarAlumno.getTxtTelefono().getText());

            if (alumno.createAlumno(alumno) == true) {

                clearTable();
                listarAlumnos(panelAlumnos.getTblAlumnos());
                JOptionPane.showMessageDialog(null, "Guardado Con Exito!");

                agregarAlumno.dispose();

            } else {
                JOptionPane.showMessageDialog(null, "ERROR, Revisar Consola...");

            }
        }
    }

    public void editar() {

        if (validarCamposEdicion() == false) {
            JOptionPane.showMessageDialog(null, "Todos Los Campos Deben Estar Completos!");

        } else if (validarTextoCamposEditar() == false) {
            JOptionPane.showMessageDialog(null, "Los Campos Nombre, Apellido Y Domicilio Pueden Tener Maximo 45 Caracteres!");
        } else if (val.verificarTelefono(editarAlumno.getTxtTelefono().getText()) == false) {
            JOptionPane.showMessageDialog(null, "Telefono Puede Contener Un Maximo De 12 Caracteres Alfanumericos");
        } else {
            //Se Actualiza El Alumno Una Vez Que Se Validaron Los Datos Ingresados Por La Vista
            alumno.setDni(Integer.valueOf(editarAlumno.getTxtDni().getText()));
            alumno.setNombre(editarAlumno.getTxtNombre().getText());
            alumno.setApellido(editarAlumno.getTxtApellido().getText());
            alumno.setFechaNacimiento(Date.valueOf(editarAlumno.getDateChooser().getText()));
            alumno.setDomicilio(editarAlumno.getTxtDomicilio().getText());
            alumno.setTelefono(editarAlumno.getTxtTelefono().getText());

            if (alumno.updateAlumno(alumno) == true) {

                clearTable();
                listarAlumnos(panelAlumnos.getTblAlumnos());
                JOptionPane.showMessageDialog(null, "Editado Con Exito");
                editarAlumno.dispose();

            } else {
                JOptionPane.showMessageDialog(null, "ERROR, Revisar Consola...");

            }
        }

    }

    public void eliminar() {
        int fila = panelAlumnos.getTblAlumnos().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debe Seleccione Una Fila!");
        } else {
            if (JOptionPane.showConfirmDialog(null, "Esto Tambien Eliminará La Inscripcion Del Alumno!", "Desea Eliminar?", JOptionPane.YES_NO_OPTION) == 0) {
                int id = Integer.parseInt((String) panelAlumnos.getTblAlumnos().getValueAt(fila, 0).toString());
                int inscCod = Integer.valueOf(panelAlumnos.getTblAlumnos().getValueAt(fila, 6).toString());
                if (Integer.valueOf(panelAlumnos.getTblAlumnos().getValueAt(fila, 6).toString()) > 0) {
                    inscripcion.deleteInscripcion(inscCod);
                }

                alumno.deleteAlumno(id);
                clearTable();
                listarAlumnos(panelAlumnos.getTblAlumnos());
                JOptionPane.showMessageDialog(null, "Eliminado!");
            }

        }
    }

    public void cargarVistaEditar() {

        int fila = panelAlumnos.getTblAlumnos().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debe Seleccione Una Fila!");
        } else {
            editarAlumno.getTxtDni().setText(panelAlumnos.getTblAlumnos().getValueAt(fila, 0).toString());
            editarAlumno.getTxtNombre().setText(panelAlumnos.getTblAlumnos().getValueAt(fila, 1).toString());
            editarAlumno.getTxtApellido().setText(panelAlumnos.getTblAlumnos().getValueAt(fila, 2).toString());

            editarAlumno.getDateChooser().setText(panelAlumnos.getTblAlumnos().getValueAt(fila, 3).toString());

            editarAlumno.getTxtDomicilio().setText(panelAlumnos.getTblAlumnos().getValueAt(fila, 4).toString());
            editarAlumno.getTxtTelefono().setText(panelAlumnos.getTblAlumnos().getValueAt(fila, 5).toString());
            editarAlumno.setVisible(true);
        }

    }

    public boolean validarCampos() {
        if (agregarAlumno.getTxtNombre().getText().isEmpty() || agregarAlumno.getTxtApellido().getText().isEmpty()
                || agregarAlumno.getTxtDomicilio().getText().isEmpty()
                || agregarAlumno.getTxtTelefono().getText().isEmpty() || agregarAlumno.getTxtDni().getText().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean validarCamposEdicion() {
        if (editarAlumno.getTxtNombre().getText().isEmpty() || editarAlumno.getTxtApellido().getText().isEmpty()
                || editarAlumno.getTxtDomicilio().getText().isEmpty()
                || editarAlumno.getTxtTelefono().getText().isEmpty() || editarAlumno.getTxtDni().getText().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean validarTextoCampos() {
        if (agregarAlumno.getTxtNombre().getText().length() > 45 || agregarAlumno.getTxtApellido().getText().length() > 45
                || agregarAlumno.getTxtDomicilio().getText().length() > 45) {
            return false;

        } else {
            return true;
        }
    }

    public boolean validarTextoCamposEditar() {
        if (editarAlumno.getTxtNombre().getText().length() > 45 || editarAlumno.getTxtApellido().getText().length() > 45
                || editarAlumno.getTxtDomicilio().getText().length() > 45) {
            return false;

        } else {
            return true;
        }
    }
}
