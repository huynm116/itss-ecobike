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

public class RentTicketDAO {

    private final Connection conn = MySQLConnection.getMySQLConnection();
    private final BikeDAO bikeDAO = new BikeDAO();
    private final UserDAO userDAO = new UserDAO();

    public boolean insert(RentTicket rentTicket) {
        boolean isSuccess = true;
        String sql1 = "INSERT INTO rent_ticket (id, start_date, user_name, bike_id, deposit, is_return) VALUES (?,?,?,?,?,?)";
        String sql2 = "UPDATE bike SET is_rent = ? WHERE id = ?";
        String sql3 = "UPDATE user SET balance = ? WHERE username = ?";
        try {
            conn.setAutoCommit(false);

            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setString(1, rentTicket.getId());
            ps1.setTimestamp(2, new Timestamp(rentTicket.getStartDate().asLong()));
            ps1.setString(3, rentTicket.getUser().getUsername());
            ps1.setInt(4, rentTicket.getBike().getId());
            ps1.setInt(5, rentTicket.getDeposit());
            ps1.setInt(6, rentTicket.getIsReturn());

            ps1.executeUpdate();

            PreparedStatement ps2 = conn.prepareStatement(sql2);

            ps2.setInt(1, 1);
            ps2.setInt(2, rentTicket.getBike().getId());

            ps2.executeUpdate();
            
            
            PreparedStatement ps3 = conn.prepareStatement(sql3);

            ps3.setInt(1, rentTicket.getUser().getBalance());
            ps3.setString(2, rentTicket.getUser().getUsername());

            ps3.executeUpdate();
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
    
    public List<RentTicket> findByUsername(String username){
        String sql = "SELECT * FROM rent_ticket";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            List<RentTicket> rentTickets = new ArrayList<>();
            while (rs.next()) {
                RentTicket rentTicket = new RentTicket();
                rentTicket.setStartDate(Millisecond.of(rs.getTimestamp("start_date").getTime()));
                rentTicket.setDeposit(rs.getInt("deposit"));
                rentTicket.setIsReturn(rs.getInt("is_return"));
                rentTicket.setUser(userDAO.findByUsername(username));
                rentTicket.setBike(bikeDAO.findById(rs.getInt("bike_id")));
                rentTicket.setId(rs.getString("id"));
                rentTickets.add(rentTicket);
            }
            
            return rentTickets;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    } 
    
    public RentTicket findById(String id){
        String sql = "SELECT * FROM rent_ticket WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                RentTicket rentTicket = new RentTicket();
                rentTicket.setStartDate(Millisecond.of(rs.getTimestamp("start_date").getTime()));
                rentTicket.setDeposit(rs.getInt("deposit"));
                rentTicket.setIsReturn(rs.getInt("is_return"));
                rentTicket.setUser(userDAO.findByUsername(rs.getString("user_name")));
                rentTicket.setBike(bikeDAO.findById(rs.getInt("bike_id")));
                rentTicket.setId(rs.getString("id"));
                
                return rentTicket;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    } 

}
