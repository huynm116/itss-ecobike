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
import rentbike.views.ListBikeView;
import rentbike.views.ListDockView;
import rentbike.views.ListRentTicketView;
import rentbike.views.MenuView;
import rentbike.views.PayViewDialogView;
import rentbike.views.ReturnTicketView;
import rentbike.views.TokenDialogView;

public class ReturnTicketController {

    private final ReturnTicketView returnTicketView;
    private final PayViewDialogView dialog;
    private final RentTicket rentTicket;
    private final ReturnTicket returnTicket;
    private final DockDAO dockDAO;
    private final List<Dock> docks;
    private final ReturnTicketDAO returnTicketDAO;
    private final BikeDAO bikeDAO;
    
    public ReturnTicketController(ReturnTicketView returnTicketView, RentTicket rentTicket) {
        this.returnTicketView = returnTicketView;
        this.dialog = new PayViewDialogView(returnTicketView, true);
        this.rentTicket = rentTicket;
        this.dockDAO = new DockDAO();
        this.returnTicketDAO = new ReturnTicketDAO();
        this.bikeDAO = new BikeDAO();

        this.docks = dockDAO.findAll();

        returnTicket = new ReturnTicket();
        returnTicket.setId(new ULID().nextULID());
        returnTicket.setReturnDate(Millisecond.now());
        returnTicket.setRentTicket(rentTicket);
        returnTicket.setRentPrice(Util.calcMoney(rentTicket.getStartDate(), returnTicket.getReturnDate(), rentTicket.getBike().getRentingPrice()));

        returnTicketView.getjTextField1().setText(Util.milisToMinute(returnTicket.getReturnDate().asLong()-rentTicket.getStartDate().asLong())+"");
        returnTicketView.getjTextField5().setText(new SimpleDateFormat("HH:mm:ss dd/MM/yyyy").format(new Date(rentTicket.getStartDate().asLong())));
        
        returnTicketView.getjTextField3().setText(rentTicket.getBike().getName());
        returnTicketView.getjTextField4().setText(rentTicket.getDeposit() + "");

        returnTicketView.getjTextField2().setText(returnTicket.getRentPrice() + "");

        if (rentTicket.getIsReturn() == 1) {
            returnTicketView.getjButton1().setEnabled(false);
        }
        returnTicketView.addListener(new ReturnTicketListener());

        DefaultComboBoxModel model = (DefaultComboBoxModel) dialog.getjComboBox1().getModel();
        model.removeAllElements();

        for (Dock dock : docks) {
            model.addElement(dock.getName());
        }
        model.setSelectedItem(rentTicket.getBike().getDock().getName());
        dialog.addListener(new DialogListener());

    }

    class ReturnTicketListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == returnTicketView.getjButton2()) {
                ListRentTicketView listRentTicketView = new ListRentTicketView();
                ListRentTicketController listRentTicketController = new ListRentTicketController(listRentTicketView);
                listRentTicketView.setVisible(true);
                returnTicketView.dispose();
            }

            if (e.getSource() == returnTicketView.getjButton1()) {
                dialog.setVisible(true);
            }

        }

    }

    class DialogListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == dialog.getjButton1()) {
                if (!rentTicket.getUser().getSercurityToken().equals(dialog.getjTextField1().getText())) {
                    JOptionPane.showMessageDialog(returnTicketView, "Sercurity Token not correct");
                    dialog.getjTextField1().setText("");
                    dialog.dispose();
                    return;
                }
                
                if ((docks.get(dialog.getjComboBox1().getSelectedIndex()).getSlotMax()- bikeDAO.countBikeByDockId(docks.get(dialog.getjComboBox1().getSelectedIndex()).getId())) < 1) {
                    JOptionPane.showMessageDialog(returnTicketView, "Dock not enough slot");
                    dialog.getjTextField1().setText("");
                    dialog.dispose();
                    return;
                }

                int newBalance = rentTicket.getUser().getBalance() + rentTicket.getDeposit() - returnTicket.getRentPrice();
                if (newBalance < 0) {
                    JOptionPane.showMessageDialog(returnTicketView, "Balance in account not enough money for rent price");
                    dialog.getjTextField1().setText("");
                    dialog.dispose();
                    return;
                }

                rentTicket.getUser().setBalance(newBalance);

                rentTicket.getBike().setDock(docks.get(dialog.getjComboBox1().getSelectedIndex()));

                boolean isSuccess = returnTicketDAO.insert(returnTicket);
                if (isSuccess) {
                    JOptionPane.showMessageDialog(returnTicketView, "Trả xe thành công");

                    ListRentTicketView listRentTicketView = new ListRentTicketView();
                    ListRentTicketController listRentTicketController = new ListRentTicketController(listRentTicketView);
                    listRentTicketView.setVisible(true);
                    returnTicketView.dispose();

                    dialog.dispose();
                }
            }
        }

    }

}
