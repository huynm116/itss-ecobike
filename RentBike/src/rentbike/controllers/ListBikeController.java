/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rentbike.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import rentbike.daos.BikeDAO;
import rentbike.models.Bike;
import rentbike.models.Dock;
import rentbike.views.DetailBikeView;
import rentbike.views.ListBikeView;
import rentbike.views.ListDockView;

public class ListBikeController {
    private final ListBikeView listBikeView;
    private final BikeDAO bikeDAO;

    public ListBikeController(ListBikeView listBikeView, int dockId) {
        this.listBikeView = listBikeView;
        this.bikeDAO = new BikeDAO();
        
        List<Bike> bikes = bikeDAO.findByDockId(dockId);
        
        DefaultTableModel model = (DefaultTableModel) listBikeView.getjTable1().getModel();
        model.setRowCount(0);

        int index = 1;
        for (Bike bike : bikes) {
            model.addRow(bike.toObject(index));
            index++;
        }


        listBikeView.getjTable1().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int r = listBikeView.getjTable1().getSelectedRow();
                
                DetailBikeView detailBikeView = new DetailBikeView();
                DetailBikeController detailBikeController = new DetailBikeController(detailBikeView, bikes.get(r));
                detailBikeView.setVisible(true);
                
                listBikeView.dispose();
            }

        });
        
        listBikeView.addListener(new ListBikeListener());
       
        
    }
    
    class ListBikeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == listBikeView.getjButton1()) {
                ListDockView listDockView = new ListDockView();
                ListDockController listDockController = new ListDockController(listDockView);
                listDockView.setVisible(true);
                
                listBikeView.dispose();
            }

       
        }

    }
    
    
    
}
