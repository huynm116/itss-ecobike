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
import rentbike.models.User;

public class UserDAO {
    private final Connection conn = MySQLConnection.getMySQLConnection();
    
    public User findByUsername(String username){
        String sql = "SELECT * FROM user WHERE username=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            
            
            if (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setCardHolderName(rs.getString("card_holder_name"));
                user.setCardNumber(rs.getString("card_number"));
                user.setExperiationDate(rs.getString("experiation_date"));
                user.setSercurityToken(rs.getString("sercurity_token"));
                user.setBalance(rs.getInt("balance"));
                user.setPassword(rs.getString("password"));
                user.setMobile(rs.getString("mobile"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                
                return user;
                
            }
            
        
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    } 
}
