/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rentbike.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import rentbike.models.Dock;
import rentbike.models.Millisecond;
import rentbike.models.RentTicket;
import rentbike.models.ReturnTicket;

public class ReturnTicketDAO {

    private final Connection conn = MySQLConnection.getMySQLConnection();
    private final RentTicketDAO rentTicketDAO = new RentTicketDAO();

    public boolean insert(ReturnTicket returnTicket) {
        boolean isSuccess = true;
        String sql1 = "INSERT INTO return_ticket (id, return_date, rent_price, rent_ticket_id) VALUES (?,?,?,?)";
        String sql2 = "UPDATE bike SET is_rent = ?, dock_id = ? WHERE id = ?";
        String sql3 = "UPDATE user SET balance = ? WHERE username = ?";
        String sql4 = "UPDATE rent_ticket SET is_return = ? WHERE id = ?";
        try {
            conn.setAutoCommit(false);

            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setString(1, returnTicket.getId());
            ps1.setTimestamp(2, new Timestamp(returnTicket.getReturnDate().asLong()));
            ps1.setInt(3, returnTicket.getRentPrice());
            ps1.setString(4, returnTicket.getRentTicket().getId());

            ps1.executeUpdate();

            PreparedStatement ps2 = conn.prepareStatement(sql2);

            ps2.setInt(1, 0);
            ps2.setInt(2, returnTicket.getRentTicket().getBike().getDock().getId());
            ps2.setInt(3, returnTicket.getRentTicket().getBike().getId());

            ps2.executeUpdate();
            
            
            PreparedStatement ps3 = conn.prepareStatement(sql3);

            ps3.setInt(1, returnTicket.getRentTicket().getUser().getBalance());
            ps3.setString(2, returnTicket.getRentTicket().getUser().getUsername());

            ps3.executeUpdate();
            
            PreparedStatement ps4 = conn.prepareStatement(sql4);

            ps4.setInt(1, 1);
            ps4.setString(2, returnTicket.getRentTicket().getId());

            ps4.executeUpdate();
        } catch (SQLException e) {
            isSuccess = false;
            try {
                conn.rollback();
                isSuccess = false;
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                isSuccess = false;
                e.printStackTrace();
            }
        }
        return isSuccess;
    }

    public ReturnTicket findByRentTiketId(String rentTicketId){
        String sql = "SELECT * FROM return_ticket WHERE rent_ticket_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, rentTicketId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                ReturnTicket returnTicket = new ReturnTicket();
                returnTicket.setReturnDate(Millisecond.of(rs.getTimestamp("return_date").getTime()));
                returnTicket.setRentPrice(rs.getInt("rent_price"));
                returnTicket.setRentTicket(rentTicketDAO.findById(rs.getString("rent_ticket_id")));
                returnTicket.setId(rs.getString("id"));
                
                return returnTicket;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    } 
}
