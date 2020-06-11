package com.sky.pi.controller;

import com.sky.pi.model.Alumno;
import com.sky.pi.model.Carrera;
import com.sky.pi.model.Inscripcion;
import com.sky.pi.view.AgregarInscripcion;
import com.sky.pi.view.Menu;
import com.sky.pi.view.PanelInscripciones;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SkylakeFrost
 */
public class ControladorInscripciones implements ActionListener, ItemListener {

    private Inscripcion inscripcion = new Inscripcion();

    //Alumno y Carrera Se Instancian Para Llenar Los Combobox Del Formulario
    private Alumno alumno = new Alumno();
    private Carrera carrera = new Carrera();

    private AgregarInscripcion agregarInscripcion = new AgregarInscripcion();
    private PanelInscripciones panelInscripciones;
    private DefaultTableModel modelo;
    private Menu menu;

    @SuppressWarnings("LeakingThisInConstructor")
    public ControladorInscripciones(Menu menu, PanelInscripciones panelInscripciones) {
        this.panelInscripciones = panelInscripciones;
        this.menu = menu;
        //Cargo Los Combobox
        //this.cargarComboBox();
        //Listeners Para Cuando Se Selecciona Un Item En Los ComboBox
        this.agregarInscripcion.getCbxCarreras().addItemListener(this);
        this.agregarInscripcion.getCbxAlumnos().addItemListener(this);

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

    //Funcion De Boton Agregar
    public void agregar() {
        Alumno alu = alumno.findAlumno(splitearAlumno(agregarInscripcion.getCbxAlumnos().getSelectedItem().toString()));
        int inscripCod = alu.getCodigoInscripcion();
        if (inscripCod > 0) {
            JOptionPane.showMessageDialog(null, "Este Alumno Ya Posee Una Inscricpion A Carrera");
        } else {
            if (revisarCampos() == true) {
                inscripcion = new Inscripcion(
                        Integer.valueOf(agregarInscripcion.getTxtCodigoInscripcion().getText()),
                        String.valueOf(agregarInscripcion.getCbxAlumnos().getSelectedItem()),
                        Date.valueOf(convertirFecha(agregarInscripcion.getDateChooserCombo().getText())),
                        splitearCarrera(agregarInscripcion.getCbxCarreras().getSelectedItem().toString())
                );

                if (inscripcion.createInscripcion(inscripcion) == true) {

                    alumno.updateCarreraAlumno(new Alumno(
                            Integer.valueOf(agregarInscripcion.getTxtCodigoInscripcion().getText()),
                            splitearAlumno(agregarInscripcion.getCbxAlumnos().getSelectedItem().toString()))
                    );
                    clearTable();
                    listarInscripciones(panelInscripciones.getTblInscripciones());
                    JOptionPane.showMessageDialog(null, "Guardado Con Exito!");
                    agregarInscripcion.dispose();

                } else {
                    JOptionPane.showMessageDialog(null, "ERROR");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Campos Vacios!");
            }
        }

    }

    //Funcion Del Boton Eliminar
    public void eliminar() {
        int fila = panelInscripciones.getTblInscripciones().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debe Seleccione Una Fila!");
        } else {
            if (JOptionPane.showConfirmDialog(null, "Desea Elimnar?", "Seleccione Una Opc.", JOptionPane.YES_NO_OPTION) == 0) {
                int id = Integer.parseInt((String) panelInscripciones.getTblInscripciones().getValueAt(fila, 0).toString());
                inscripcion.deleteInscripcion(id);
                clearTable();
                listarInscripciones(panelInscripciones.getTblInscripciones());
                JOptionPane.showMessageDialog(null, "Eliminado!");
            }

        }
    }

    //Carga De ComboBoxes
    public void cargarComboBox() {

        List<Alumno> listaAlumnos = alumno.readlumnos();
        agregarInscripcion.getCbxAlumnos().removeAllItems();
        agregarInscripcion.getCbxAlumnos().addItem("Seleccionar Alumno");
        for (int i = 0; i < listaAlumnos.size(); i++) {
            agregarInscripcion.getCbxAlumnos().addItem(String.valueOf(listaAlumnos.get(i).getDni()) + " - " + listaAlumnos.get(i).getNombre()
                    + " " + listaAlumnos.get(i).getApellido());
        }

        List<Carrera> listaCarreras = carrera.readCarrera();
        agregarInscripcion.getCbxCarreras().removeAllItems();
        agregarInscripcion.getCbxCarreras().addItem("Seleccionar Carrera");

        for (int i = 0; i < listaCarreras.size(); i++) {
            agregarInscripcion.getCbxCarreras().addItem(String.valueOf(listaCarreras.get(i).getCodigoCarrera() + " - " + listaCarreras.get(i).getNombre()));
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

    public int splitearCarrera(String carreraIDNombre) {
        String[] parts = carreraIDNombre.split(" - ");
        String part1 = parts[0]; // Cod Carrera

        return Integer.valueOf(part1);
    }

    public int splitearAlumno(String alumnoDniNombre) {
        String[] parts = alumnoDniNombre.split(" - ");
        String part1 = parts[0]; // DNI

        return Integer.valueOf(part1);
    }

    //Se Listan Las Incripciones En La JTable
    public void listarInscripciones(JTable table) {
        modelo = (DefaultTableModel) panelInscripciones.getTblInscripciones().getModel();
        panelInscripciones.getTblInscripciones().setRowHeight(30);
        List<Inscripcion> lista = inscripcion.readInscripciones();
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

    //Para Limpiar La Tabla
    public void clearTable() {
        for (int i = 0; i < panelInscripciones.getTblInscripciones().getRowCount(); i++) {
            modelo.removeRow(i);
            i -= 1;
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getItemSelectable().equals(agregarInscripcion.getCbxCarreras()) && !agregarInscripcion.getCbxCarreras().getSelectedItem().equals("Seleccionar Carrera")) {

            //Se Obtiene El Codigo De La Carrera
            int idCarrera = splitearCarrera(agregarInscripcion.getCbxCarreras().getSelectedItem().toString());
            //Usando Ese Codigo Busco La Carrera En La Base De Datos
            Carrera car = carrera.findCarrera(idCarrera);
            //Seteo El Nombre De La Carrera En Cuestion Al JTextField
            agregarInscripcion.getTxtNombreCarrera().setText(car.getDuracion());

        } else if (e.getItemSelectable().equals(agregarInscripcion.getCbxAlumnos()) && !agregarInscripcion.getCbxAlumnos().getSelectedItem().equals("Seleccionar Alumno")) {
            //Lo Mismo Se Realiza Para Los Alumnos
            int dniAlumno = splitearAlumno(agregarInscripcion.getCbxAlumnos().getSelectedItem().toString());
            Alumno al = alumno.findAlumno(dniAlumno);
            agregarInscripcion.getTxtNombreAlumno().setText(String.valueOf(al.getTelefono()));

        }
    }

    public boolean revisarCampos() {
        if (agregarInscripcion.getTxtCodigoInscripcion().getText().isEmpty()
                || agregarInscripcion.getCbxAlumnos().getSelectedItem().equals("Seleccionar Alumno")
                || agregarInscripcion.getCbxAlumnos().getSelectedItem() == null
                || agregarInscripcion.getCbxCarreras().getSelectedItem().equals("Seleccionar Carrera")
                || agregarInscripcion.getCbxAlumnos().getSelectedItem() == null) {
            return false;
        } else {
            return true;
        }
    }

}
