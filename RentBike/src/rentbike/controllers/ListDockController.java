/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rentbike.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import rentbike.daos.BikeDAO;
import rentbike.daos.DockDAO;
import rentbike.models.Dock;
import rentbike.views.ListBikeView;
import rentbike.views.ListDockView;
import rentbike.views.MenuView;

public class ListDockController {
    private final ListDockView listDockView;
    private final DockDAO dockDAO;
    private final BikeDAO bikeDAO;

    public ListDockController(ListDockView listDockView) {
        this.listDockView = listDockView;
        this.dockDAO = new DockDAO();
        this.bikeDAO = new BikeDAO();
        
        List<Dock> docks = dockDAO.findAll();
        
        DefaultTableModel model = (DefaultTableModel) listDockView.getjTable1().getModel();
        model.setRowCount(0);

        int index = 1;
        for (Dock dock : docks) {
            model.addRow(dock.toObject(index, bikeDAO.countBikeByDockId(dock.getId())));
            index++;
        }


        listDockView.getjTable1().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int r = listDockView.getjTable1().getSelectedRow();
                ListBikeView listBikeView = new ListBikeView();
                ListBikeController listBikeController = new ListBikeController(listBikeView, docks.get(r).getId());
                listBikeView.setVisible(true);
                
                listDockView.dispose();
                        
            }

        });
        
        listDockView.addListener(new ListDockListener());
        
    }
    
    class ListDockListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == listDockView.getjButton1()) {
                MenuView menuView = new MenuView();
                MenuController menuController = new MenuController(menuView);
                menuView.setVisible(true);
                
                listDockView.dispose();
            }

       
        }

    }
    
}
