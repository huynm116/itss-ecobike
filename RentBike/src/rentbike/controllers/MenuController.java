/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rentbike.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import rentbike.views.ListDockView;
import rentbike.views.ListRentTicketView;
import rentbike.views.MenuView;
import rentbike.views.ProfileView;

public class MenuController {
    private final MenuView menuView;

    public MenuController(MenuView menuView) {
        this.menuView = menuView;
        menuView.addListener(new MenuListener());
    }
    
    class MenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == menuView.getjButton1()) {
                ListDockView listDockView = new ListDockView();
                ListDockController listDockController = new ListDockController(listDockView);
                listDockView.setVisible(true);
                menuView.dispose();
            }

            if (e.getSource() == menuView.getjButton2()) {
                ListRentTicketView listRentTicketView = new ListRentTicketView();
                ListRentTicketController listRentTicketController = new ListRentTicketController(listRentTicketView);
                listRentTicketView.setVisible(true);
                menuView.dispose();
            }
            
            if (e.getSource() == menuView.getjButton3()) {
                ProfileView profileView = new ProfileView();
                ProfileController profileController = new ProfileController(profileView);
                profileView.setVisible(true);
                menuView.dispose();
            }
       
        }

    }
    
}
