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
import rentbike.daos.RentTicketDAO;
import rentbike.models.Dock;
import rentbike.models.RentTicket;
import rentbike.utils.Constant;
import rentbike.views.DetailReturnTicketView;
import rentbike.views.ListBikeView;
import rentbike.views.ListDockView;
import rentbike.views.ListRentTicketView;
import rentbike.views.MenuView;
import rentbike.views.ReturnTicketView;

public class ListRentTicketController {

    private final ListRentTicketView listRentTicketView;
    private final RentTicketDAO rentTicketDAO;

    public ListRentTicketController(ListRentTicketView listRentTicketView) {
        this.listRentTicketView = listRentTicketView;
        rentTicketDAO = new RentTicketDAO();

        List<RentTicket> rentTickets = rentTicketDAO.findByUsername(Constant.userName);

        DefaultTableModel model = (DefaultTableModel) listRentTicketView.getjTable1().getModel();
        model.setRowCount(0);

        for (RentTicket rentTicket : rentTickets) {
            model.addRow(rentTicket.toObject());
        }

        listRentTicketView.getjTable1().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int r = listRentTicketView.getjTable1().getSelectedRow();

                RentTicket rentTicket = rentTickets.get(r);

                if (rentTicket.getIsReturn() == 0) {
                    ReturnTicketView returnTicketView = new ReturnTicketView();
                    ReturnTicketController returnTicketController = new ReturnTicketController(returnTicketView, rentTicket);
                    returnTicketView.setVisible(true);
                    listRentTicketView.dispose();
                }
                else{
                    DetailReturnTicketView detailReturnTicketView = new DetailReturnTicketView();
                    DetailReturnTicketController detailReturnTicketController = new DetailReturnTicketController(detailReturnTicketView, rentTicket);
                    detailReturnTicketView.setVisible(true);
                    listRentTicketView.dispose();
                }

            }

        });

        listRentTicketView.addListener(new ListRentTicketListener());

    }

    class ListRentTicketListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == listRentTicketView.getjButton1()) {
                MenuView menuView = new MenuView();
                MenuController menuController = new MenuController(menuView);
                menuView.setVisible(true);

                listRentTicketView.dispose();
            }

        }

    }

}
