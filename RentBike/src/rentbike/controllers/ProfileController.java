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
import rentbike.utils.Constant;
import rentbike.utils.ULID;
import rentbike.utils.Util;
import rentbike.views.DetailBikeView;
import rentbike.views.DetailReturnTicketView;
import rentbike.views.ListBikeView;
import rentbike.views.ListDockView;
import rentbike.views.ListRentTicketView;
import rentbike.views.MenuView;
import rentbike.views.PayViewDialogView;
import rentbike.views.ProfileView;
import rentbike.views.ReturnTicketView;
import rentbike.views.TokenDialogView;

public class ProfileController {

    private final ProfileView profileView;
    private final UserDAO userDAO;

    public ProfileController(ProfileView profileView) {
        this.profileView = profileView;
        this.userDAO = new UserDAO();
   
        User user = userDAO.findByUsername(Constant.userName);

       
        profileView.getjTextField1().setText(user.getCardNumber());
        profileView.getjTextField5().setText(user.getCardHolderName());
        
        profileView.getjTextField3().setText(user.getSercurityToken());
        profileView.getjTextField4().setText(user.getBalance()+"");

        profileView.getjTextField2().setText(user.getExperiationDate());
        profileView.getjTextField6().setText(user.getUsername());
        profileView.getjTextField7().setText(user.getMobile());
        profileView.getjTextField8().setText(user.getEmail());
        profileView.getjTextField9().setText(user.getAddress());
        
        profileView.addListener(new ReturnTicketListener());

    }

    class ReturnTicketListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == profileView.getjButton2()) {
                MenuView menuView = new MenuView();
                MenuController menuController = new MenuController(menuView);
                menuView.setVisible(true);
                profileView.dispose();
            }

        }

    }

}
