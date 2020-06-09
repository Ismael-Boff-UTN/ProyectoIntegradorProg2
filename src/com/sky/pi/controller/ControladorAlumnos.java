package com.sky.pi.controller;

import com.sky.pi.dao.AlumnoDAO;
import com.sky.pi.model.Alumno;
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
    private AlumnoDAO alumnoDAO = new AlumnoDAO();

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

        this.agregarAlumno.getBtnAgregar().addActionListener(this);
        this.agregarAlumno.getBtnCancelar().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelAlumnos.getBtnNuevoAlumno()) {
            agregarAlumno.setVisible(true);
        }
        if (e.getSource() == agregarAlumno.getBtnAgregar()) {
            agregar();
        }
        if (e.getSource() == agregarAlumno.getBtnCancelar()) {
            agregarAlumno.dispose();
        }
        if (e.getSource() == panelAlumnos.getBtnEliminar()) {
            eliminar();
        }

    }

    public void listarAlumnos(JTable table) {
        modelo = (DefaultTableModel) panelAlumnos.getTblAlumnos().getModel();
        panelAlumnos.getTblAlumnos().setRowHeight(30);
        List<Alumno> lista = alumnoDAO.read();
        Object[] fila = new Object[8];
        for (int i = 0; i < lista.size(); i++) {
            fila[0] = lista.get(i).getId();
            fila[1] = lista.get(i).getDni();
            fila[2] = lista.get(i).getNombre();
            fila[3] = lista.get(i).getApellido();
            fila[4] = lista.get(i).getFechaNacimiento();
            fila[5] = lista.get(i).getDomicilio();
            fila[6] = lista.get(i).getTelefono();
            fila[7] = lista.get(i).getCodigoInscripcion();
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
        } else {

            alumno = new Alumno(
                    Integer.valueOf(agregarAlumno.getTxtDni().getText()),
                    agregarAlumno.getTxtNombre().getText(),
                    agregarAlumno.getTxtApellido().getText(),
                    Date.valueOf(convertirFecha(agregarAlumno.getDateChooser().getText())),
                    agregarAlumno.getTxtDomicilio().getText(),
                    Integer.valueOf(agregarAlumno.getTxtTelefono().getText()),
                    "No Inscripto A Carrera"
                    
            //Integer.valueOf(agregarAlumno.gett)
            );

            System.out.println(alumno.getFechaNacimiento());
            if (alumnoDAO.create(alumno) == true) {

                clearTable();
                listarAlumnos(panelAlumnos.getTblAlumnos());
                JOptionPane.showMessageDialog(null, "Guardado Con Exito!");
                agregarAlumno.dispose();

            } else {
                JOptionPane.showMessageDialog(null, "ERROR");
            }
        }
    }

    public void eliminar() {
        int fila = panelAlumnos.getTblAlumnos().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debe Seleccione Una Fila!");
        } else {
            if (JOptionPane.showConfirmDialog(null, "Desea Elimnar?", "Seleccione Una Opc.", JOptionPane.YES_NO_OPTION) == 0) {
                int id = Integer.parseInt((String) panelAlumnos.getTblAlumnos().getValueAt(fila, 0).toString());
                alumnoDAO.delete(id);
                clearTable();
                listarAlumnos(panelAlumnos.getTblAlumnos());
                JOptionPane.showMessageDialog(null, "Eliminado!");
            }

        }
    }

//    public void editar(){
//        int dni = Integer.valueOf(.getTxtID().getText());
//        String nombre = vistaEditarPersona.getTxtNombre().getText();
//        String apellido = vistaEditarPersona.getTxtApellido().getText();
//        int dni = Integer.valueOf(vistaEditarPersona.getTxtDNI().getText());
//        long cuit = Long.valueOf(vistaEditarPersona.getTxtCUIT().getText());
//        persona = new Persona(id, nombre, apellido, dni, cuit);
//        crud.update(persona);
//        if (crud.update(persona) == true) {
//            clearTable();
//            listInTable(vistaListaPersonas.getTblPersonas());
//            JOptionPane.showMessageDialog(null, "Editado Con Exito");
//            vistaEditarPersona.setVisible(false);
//
//        } else {
//            JOptionPane.showMessageDialog(null, "ha Ocurrido Un Error!");
//        }
//    }
    public String convertirFecha(String fechaDDMMYYYY) {

        String[] parts = fechaDDMMYYYY.split("-");
        String part1 = parts[0]; // Año
        String part2 = parts[1]; // Mes
        String part3 = parts[2]; //Dia

        String fechaYYYYMMDD = part3 + "-" + part2 + "-" + part1;

        return fechaYYYYMMDD;
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

}
