package com.sky.pi.controller;

import com.sky.pi.model.Carrera;
import com.sky.pi.view.AgregarCarrera;
import com.sky.pi.view.EditarCarrera;
import com.sky.pi.view.Menu;
import com.sky.pi.view.PanelCarreras;
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
public class ControladorCarreras implements ActionListener {

    private Carrera carrera = new Carrera();

    private AgregarCarrera agregarCarrera = new AgregarCarrera();
    private EditarCarrera editarCarrera = new EditarCarrera();
    private PanelCarreras panelCarreras;
    private DefaultTableModel modelo;
    private Menu menu;

    @SuppressWarnings("LeakingThisInConstructor")
    public ControladorCarreras(Menu menu, PanelCarreras panelCarreras) {
        this.panelCarreras = panelCarreras;
        this.menu = menu;

        this.panelCarreras.getBtnNuevo().addActionListener(this);
        this.panelCarreras.getBtnEliminar().addActionListener(this);
        this.panelCarreras.getBtnEditar().addActionListener(this);

        this.agregarCarrera.getBtnAgregar().addActionListener(this);
        this.agregarCarrera.getBtnCancelar().addActionListener(this);

        this.editarCarrera.getBtnGuardarCambios().addActionListener(this);
        this.editarCarrera.getBtnCancelar().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelCarreras.getBtnNuevo()) {
            agregarCarrera.setVisible(true);
        } else if (e.getSource() == panelCarreras.getBtnEditar()) {
            cargarVistaEditar();

        } else if (e.getSource() == panelCarreras.getBtnEliminar()) {
            eliminar();
        } else if (e.getSource() == agregarCarrera.getBtnAgregar()) {
            agregar();
        } else if (e.getSource() == agregarCarrera.getBtnCancelar()) {
            agregarCarrera.dispose();
        } else if (e.getSource() == editarCarrera.getBtnGuardarCambios()) {
            editar();
        } else if (e.getSource() == editarCarrera.getBtnCancelar()) {
            editarCarrera.dispose();
        }

    }

    public void agregar() {
        if (revisarCampos() == false) {
            JOptionPane.showMessageDialog(null, "Campos Vacios");
        } else if (carrera.carreraExist(Integer.valueOf(agregarCarrera.getTxtCodCarrera().getText()))) {
            JOptionPane.showMessageDialog(null, "Carrera Con El Codigo " + agregarCarrera.getTxtCodCarrera().getText() + ", Ya Existe!");
        } else {

            carrera.setCodigoCarrera(Integer.valueOf(agregarCarrera.getTxtCodCarrera().getText()));
            carrera.setNombre(agregarCarrera.getTxtNombre().getText());
            carrera.setDuracion(agregarCarrera.getTxtDuracion().getText());

            if (carrera.createCarrera(carrera) == true) {
                clearTable();
                listarCarreras(panelCarreras.getTblCarreras());
                JOptionPane.showMessageDialog(null, "Guardado Con Exito!");
                agregarCarrera.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "ERROR");
            }
        }

    }

    public void editar() {

        if (revisarCamposEditar() == false) {
            JOptionPane.showMessageDialog(null, "Campos Vacios");
        } else {

            carrera.setCodigoCarrera(Integer.valueOf(editarCarrera.getTxtCarCod().getText()));
            carrera.setNombre(editarCarrera.getTxtNombre().getText());
            carrera.setDuracion(editarCarrera.getTxtDuracion().getText());

            if (carrera.updateCarrera(carrera) == true) {
                clearTable();
                listarCarreras(panelCarreras.getTblCarreras());
                JOptionPane.showMessageDialog(null, "Editado Con Exito!");
                editarCarrera.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "ERROR");
            }
        }
    }

    public void eliminar() {
        int fila = panelCarreras.getTblCarreras().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debe Seleccione Una Fila!");
        } else {
            if (JOptionPane.showConfirmDialog(null, "Desea Elimnar?", "Seleccione Una Opc.", JOptionPane.YES_NO_OPTION) == 0) {
                int id = Integer.parseInt((String) panelCarreras.getTblCarreras().getValueAt(fila, 0).toString());
                carrera.deleteCarrera(id);
                clearTable();
                listarCarreras(panelCarreras.getTblCarreras());
                JOptionPane.showMessageDialog(null, "Eliminado!");
            }

        }
    }

    public void cargarVistaEditar() {
        int fila = panelCarreras.getTblCarreras().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debe Seleccione Una Fila!");
        } else {
            editarCarrera.getTxtCarCod().setText(panelCarreras.getTblCarreras().getValueAt(fila, 0).toString());
            editarCarrera.getTxtNombre().setText(panelCarreras.getTblCarreras().getValueAt(fila, 1).toString());
            editarCarrera.getTxtDuracion().setText(panelCarreras.getTblCarreras().getValueAt(fila, 2).toString());

            editarCarrera.setVisible(true);
        }
    }

    public void listarCarreras(JTable table) {
        modelo = (DefaultTableModel) panelCarreras.getTblCarreras().getModel();
        panelCarreras.getTblCarreras().setRowHeight(30);
        List<Carrera> lista = carrera.readCarrera();
        Object[] fila = new Object[3];
        for (int i = 0; i < lista.size(); i++) {
            fila[0] = lista.get(i).getCodigoCarrera();
            fila[1] = lista.get(i).getNombre();
            fila[2] = lista.get(i).getDuracion();

            modelo.addRow(fila);
        }
        panelCarreras.getTblCarreras().setModel(modelo);

    }

    public void clearTable() {
        for (int i = 0; i < panelCarreras.getTblCarreras().getRowCount(); i++) {
            modelo.removeRow(i);
            i -= 1;
        }
    }

    public boolean revisarCampos() {
        if (agregarCarrera.getTxtNombre().getText().isEmpty()
                || agregarCarrera.getTxtDuracion().getText().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean revisarCamposEditar() {
        if (editarCarrera.getTxtNombre().getText().isEmpty()
                || editarCarrera.getTxtDuracion().getText().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

}
