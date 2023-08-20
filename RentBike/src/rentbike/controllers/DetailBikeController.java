/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rentbike.controllers;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import rentbike.daos.BikeDAO;
import rentbike.daos.RentTicketDAO;
import rentbike.daos.UserDAO;
import rentbike.models.Bike;
import rentbike.models.Millisecond;
import rentbike.models.RentTicket;
import rentbike.models.User;
import rentbike.utils.ULID;
import rentbike.views.DetailBikeView;
import rentbike.views.ListBikeView;
import rentbike.views.ListDockView;
import rentbike.views.MenuView;
import rentbike.views.TokenDialogView;

public class DetailBikeController {

    private final DetailBikeView detailBikeView;
    private Bike bike;
    private final BikeDAO bikeDAO;
    private final RentTicketDAO rentTicketDAO;
    private final UserDAO userDAO;
    private final TokenDialogView dialog;
    private final User user;

    public DetailBikeController(DetailBikeView detailBikeView, Bike bike) {
        this.detailBikeView = detailBikeView;
        this.bike = bike;
        this.bikeDAO = new BikeDAO();
        this.rentTicketDAO = new RentTicketDAO();
        this.userDAO = new UserDAO();
        this.dialog = new TokenDialogView(detailBikeView, true);
        user = userDAO.findByUsername("hungnv");

        int width = detailBikeView.getjLabel2().getWidth();
        int height = this.detailBikeView.getjLabel2().getHeight();
        Image image = new ImageIcon(bike.getImage()).getImage();
        Icon icon = new ImageIcon(image.getScaledInstance(width, height,
                image.SCALE_SMOOTH));
        detailBikeView.getjLabel2().setIcon(icon);

        detailBikeView.getjTextField1().setText(bike.getName());
        detailBikeView.getjTextField2().setText(bike.getType().name());
        detailBikeView.getjTextField3().setText(bike.getDock().getName());
        detailBikeView.getjTextField4().setText((bike.getValue() * bike.getPercentDeposit() / 100) + "");
        detailBikeView.getjTextField5().setText(bike.getRentingPrice() + "");

        detailBikeView.addListener(new DetailBikeListener());
        dialog.addListener(new DialogListener());

    }

    class DetailBikeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == detailBikeView.getjButton2()) {
                ListBikeView listBikeView = new ListBikeView();
                ListBikeController listBikeController = new ListBikeController(listBikeView, bike.getDock().getId());
                listBikeView.setVisible(true);

                detailBikeView.dispose();
            }

            if (e.getSource() == detailBikeView.getjButton1()) {
                dialog.setVisible(true);
            }

        }

    }
    
    class DialogListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == dialog.getjButton1()) {
                if(!user.getSercurityToken().equals(dialog.getjTextField1().getText())){
                    JOptionPane.showMessageDialog(detailBikeView, "Sercurity Token not correct");
                    dialog.getjTextField1().setText("");
                    dialog.dispose();
                    return;
                }
                
                int newBalance = user.getBalance() - bike.getValue() * bike.getPercentDeposit() / 100;
                if (newBalance< 0){
                    JOptionPane.showMessageDialog(detailBikeView, "Balance in account not enough money for deposit");
                    dialog.getjTextField1().setText("");
                    dialog.dispose();
                    return;
                }
                
                user.setBalance(newBalance);
                
                RentTicket rentTicket = new RentTicket();
                rentTicket.setBike(bike);
                rentTicket.setUser(user);
                rentTicket.setIsReturn(0);
                rentTicket.setStartDate(Millisecond.now());
                rentTicket.setDeposit(bike.getValue() * bike.getPercentDeposit() / 100);
                rentTicket.setId(new ULID().nextULID());

                boolean isSuccess = rentTicketDAO.insert(rentTicket);
                if (isSuccess) {
                    JOptionPane.showMessageDialog(detailBikeView, "Thuê xe thành công");

                    MenuView menuView = new MenuView();
                    MenuController menuController = new MenuController(menuView);
                    menuView.setVisible(true);
                    
                    dialog.dispose();
                    detailBikeView.dispose();
                }
            }
        }
        
    }

}
