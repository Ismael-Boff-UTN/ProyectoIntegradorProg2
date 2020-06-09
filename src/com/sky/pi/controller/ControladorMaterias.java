package com.sky.pi.controller;

import com.sky.pi.dao.MateriaDAO;
import com.sky.pi.dao.ProfesorDAO;
import com.sky.pi.model.Materia;
import com.sky.pi.model.Profesor;
import com.sky.pi.view.AgregarMateria;
import com.sky.pi.view.EditarMateria;
import com.sky.pi.view.Menu;
import com.sky.pi.view.PanelMaterias;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SkylakeFrost
 */
public class ControladorMaterias implements ActionListener {

    private Materia materia = new Materia();
    private MateriaDAO materiaDAO = new MateriaDAO();
    private AgregarMateria agregarMateria = new AgregarMateria();
    private EditarMateria editarMateria = new EditarMateria();
    private PanelMaterias panelMaterias;
    private DefaultTableModel modelo;
    private Menu menu;

    ProfesorDAO profesorDAO = new ProfesorDAO();

    @SuppressWarnings("LeakingThisInConstructor")
    public ControladorMaterias(Menu menu, PanelMaterias panelMaterias) {
        this.panelMaterias = panelMaterias;
        this.menu = menu;

        this.panelMaterias.getBtnNuevo().addActionListener(this);
        this.panelMaterias.getBtnEliminar().addActionListener(this);
        this.panelMaterias.getBtnEditar().addActionListener(this);

        this.agregarMateria.getBtnAgregar().addActionListener(this);
        this.agregarMateria.getBtnCancelar().addActionListener(this);

        this.editarMateria.getBtnAgregar().addActionListener(this);
        this.editarMateria.getBtnCancelar().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelMaterias.getBtnNuevo()) {
            agregarMateria.setVisible(true);
            cargarComboBoxProfesores();
        }
        if (e.getSource() == panelMaterias.getBtnEliminar()) {
            //TODO
        }
        if (e.getSource() == panelMaterias.getBtnEditar()) {
            //TODO
        }
        if (e.getSource() == agregarMateria.getBtnAgregar()) {
            agregar();
        }
        if (e.getSource() == agregarMateria.getBtnCancelar()) {
            agregarMateria.dispose();
        }
    }

    public void agregar() {

        materia = new Materia(
                agregarMateria.getTxtNombre().getText(),
                Integer.valueOf(agregarMateria.getTxtDniProfesor().getText()),
                String.valueOf(agregarMateria.getCbcProfesores().getSelectedItem()));

        if (materiaDAO.create(materia) == true) {

            clearTable();
            listarMaterias(panelMaterias.getTblMaterias());
            JOptionPane.showMessageDialog(null, "Guardado Con Exito!");
            agregarMateria.dispose();

        } else {
            JOptionPane.showMessageDialog(null, "ERROR");
        }
    }

    public void cargarComboBoxProfesores() {
        List<Profesor> listaProfesores = profesorDAO.read();

        agregarMateria.getCbcProfesores().removeAllItems();
        for (int i = 0; i < listaProfesores.size(); i++) {
            agregarMateria.getCbcProfesores().addItem(listaProfesores.get(i).getNombre() + " " + listaProfesores.get(i).getApellido());

        }
    }

    public void listarMaterias(JTable table) {
        modelo = (DefaultTableModel) panelMaterias.getTblMaterias().getModel();
        panelMaterias.getTblMaterias().setRowHeight(30);
        List<Materia> lista = materiaDAO.read();
        Object[] fila = new Object[4];
        for (int i = 0; i < lista.size(); i++) {
            fila[0] = lista.get(i).getIdMateria();
            fila[1] = lista.get(i).getNombreMateria();
            fila[2] = lista.get(i).getDniProfesor();
            fila[3] = lista.get(i).getNombreProfesor();

            modelo.addRow(fila);
        }
        panelMaterias.getTblMaterias().setModel(modelo);

    }

    public void clearTable() {
        for (int i = 0; i < panelMaterias.getTblMaterias().getRowCount(); i++) {
            modelo.removeRow(i);
            i -= 1;
        }
    }

}
