/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rentbike.controllers;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import rentbike.daos.BikeDAO;
import rentbike.daos.DockDAO;
import rentbike.daos.RentTicketDAO;
import rentbike.daos.ReturnTicketDAO;
import rentbike.daos.UserDAO;
import rentbike.models.Bike;
import rentbike.models.Dock;
import rentbike.models.Millisecond;
import rentbike.models.RentTicket;
import rentbike.models.ReturnTicket;
import rentbike.models.User;
import rentbike.utils.ULID;
import rentbike.utils.Util;
import rentbike.views.DetailBikeView;
import rentbike.views.DetailReturnTicketView;
import rentbike.views.ListBikeView;
import rentbike.views.ListDockView;
import rentbike.views.ListRentTicketView;
import rentbike.views.MenuView;
import rentbike.views.PayViewDialogView;
import rentbike.views.ReturnTicketView;
import rentbike.views.TokenDialogView;

public class DetailReturnTicketController {

    private final DetailReturnTicketView detailReturnTicketView;
    private final ReturnTicketDAO returnTicketDAO;

    public DetailReturnTicketController(DetailReturnTicketView detailReturnTicketView, RentTicket rentTicket) {
        this.detailReturnTicketView = detailReturnTicketView;
   
        this.returnTicketDAO = new ReturnTicketDAO();
        ReturnTicket returnTicket = returnTicketDAO.findByRentTiketId(rentTicket.getId());

       
        detailReturnTicketView.getjTextField1().setText(Util.milisToMinute(returnTicket.getReturnDate().asLong()-rentTicket.getStartDate().asLong())+"");
        detailReturnTicketView.getjTextField5().setText(new SimpleDateFormat("HH:mm:ss dd/MM/yyyy").format(new Date(returnTicket.getRentTicket().getStartDate().asLong())));
        
        detailReturnTicketView.getjTextField3().setText(returnTicket.getRentTicket().getBike().getName());
        detailReturnTicketView.getjTextField4().setText(returnTicket.getRentTicket().getDeposit() + "");

        detailReturnTicketView.getjTextField2().setText(returnTicket.getRentPrice() + "");
        detailReturnTicketView.getjTextField6().setText(returnTicket.getId());
        detailReturnTicketView.addListener(new ReturnTicketListener());

    }

    class ReturnTicketListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == detailReturnTicketView.getjButton2()) {
                ListRentTicketView listRentTicketView = new ListRentTicketView();
                ListRentTicketController listRentTicketController = new ListRentTicketController(listRentTicketView);
                listRentTicketView.setVisible(true);
                detailReturnTicketView.dispose();
            }

        }

    }

}
