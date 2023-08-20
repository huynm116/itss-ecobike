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
import rentbike.models.Bike;
import rentbike.models.Dock;
import rentbike.models.RentTicket;

public class BikeDAO {
    private final Connection conn = MySQLConnection.getMySQLConnection();
    
    public long countBikeByDockId(int dockId){
        String sql = "select count(bike.id) from bike where dock_id = ? and is_rent = 0";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, dockId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getLong("count(bike.id)");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    } 
    
    public List<Bike> findByDockId(int dockId){
        String sql = "SELECT * FROM bike INNER JOIN dock ON dock.id = dock_id WHERE dock_id = ? AND is_rent = 0";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, dockId);
            ResultSet rs = ps.executeQuery();
            
            List<Bike> bikes = new ArrayList<>();
            while (rs.next()) {
                Bike bike = new Bike();
                bike.setId(rs.getInt("id"));
                bike.setName(rs.getString("name"));
                bike.setValue(rs.getInt("value"));
                bike.setIsRent(rs.getInt("is_rent"));
                bike.setType(Bike.Type.valueOf(rs.getString("type")));
                
                Dock dock = new Dock();
                dock.setId(rs.getInt("id"));
                dock.setName(rs.getString("dock.name"));
                dock.setAddress(rs.getString("address"));
                bike.setDock(dock);
                
                bike.setRentingPrice(rs.getInt("renting_price"));
                bike.setPercentDeposit(rs.getInt("percent_deposit"));
                bike.setImage(rs.getString("image"));
                
                bikes.add(bike);
            }
            
            return bikes;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    } 
    
    public Bike findById(int id){
        String sql = "SELECT * FROM bike INNER JOIN dock ON dock.id = dock_id WHERE bike.id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
          
            if (rs.next()) {
                Bike bike = new Bike();
                bike.setId(rs.getInt("id"));
                bike.setName(rs.getString("name"));
                bike.setValue(rs.getInt("value"));
                bike.setIsRent(rs.getInt("is_rent"));
                bike.setType(Bike.Type.valueOf(rs.getString("type")));
                
                Dock dock = new Dock();
                dock.setId(rs.getInt("id"));
                dock.setName(rs.getString("dock.name"));
                dock.setAddress(rs.getString("address"));
                bike.setDock(dock);
                
                bike.setRentingPrice(rs.getInt("renting_price"));
                bike.setPercentDeposit(rs.getInt("percent_deposit"));
                bike.setImage(rs.getString("image"));
                
                return bike;
            }
            

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    } 
    
    public boolean updateIsRent(int bikeId, int isRent) {
        boolean isSuccess = true;
        String sql = "UPDATE bike SET is_rent = ? WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
  
            ps.setInt(1, isRent);
            ps.setInt(2, bikeId);

            ps.executeUpdate();
        } catch (SQLException e) {
            isSuccess = false;
            e.printStackTrace();
        }
        
        return isSuccess;
    }
    
}
