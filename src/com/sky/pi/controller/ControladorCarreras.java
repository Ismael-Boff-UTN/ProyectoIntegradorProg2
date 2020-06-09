package com.sky.pi.controller;

import com.sky.pi.dao.CarreraDAO;
import com.sky.pi.model.Carrera;
import com.sky.pi.view.AgregarCarrera;
import com.sky.pi.view.EditarCarrera;
import com.sky.pi.view.Menu;
import com.sky.pi.view.PanelCarreras;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SkylakeFrost
 */
public class ControladorCarreras implements ActionListener {
    
    private Carrera carrera = new Carrera();
    private CarreraDAO carreraDAO = new CarreraDAO();
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
    }
    
    public ControladorCarreras() {
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelCarreras.getBtnNuevo()) {
            agregarCarrera.setVisible(true);
        }
        
    }
    
    public void listarCarreras(JTable table) {
        modelo = (DefaultTableModel) panelCarreras.getTblCarreras().getModel();
        panelCarreras.getTblCarreras().setRowHeight(30);
        List<Carrera> lista = carreraDAO.read();
        Object[] fila = new Object[3];
        for (int i = 0; i < lista.size(); i++) {
            fila[0] = lista.get(i).getCodigoCarrera();
            fila[1] = lista.get(i).getNombre();
            fila[2] = lista.get(i).getDuracion();
            
            modelo.addRow(fila);
        }
        panelCarreras.getTblCarreras().setModel(modelo);
        
        for (int i = 0; i < lista.size(); i++) {
            System.out.println("nombre " + lista.get(i).getNombre());
        }
    }
    
    public void clearTable() {
        for (int i = 0; i < panelCarreras.getTblCarreras().getRowCount(); i++) {
            modelo.removeRow(i);
            i -= 1;
        }
    }
    
}
