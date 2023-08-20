/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rentbike.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import rentbike.models.Dock;

public class DockDAO {
    private final Connection conn = MySQLConnection.getMySQLConnection();
    
    public List<Dock> findAll(){
        String sql = "SELECT * FROM dock";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            List<Dock> docks = new ArrayList<>();
            while (rs.next()) {
                Dock dock = new Dock();
                dock.setId(rs.getInt("id"));
                dock.setName(rs.getString("name"));
                dock.setAddress(rs.getString("address"));
                dock.setSlotMax(rs.getInt("slot_max"));
                
                docks.add(dock);
            }
            
            return docks;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    } 
}
