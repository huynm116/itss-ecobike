/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rentbike.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLConnection {

    private static final String hostName = "localhost";
    private static final String dbName = "rent_bike";
    private static final String userName = "root";
    private static final String password = "123456";

    public static Connection getMySQLConnection() {
        return getMySQLConnection(hostName, dbName, userName, password);
    }

    public static Connection getMySQLConnection(String hostName, String dbName,
            String userName, String password) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName + "?autoReconnect=true&useUnicode=true&characterEncoding=utf-8&useSSL=false";

            Connection conn = DriverManager.getConnection(connectionURL, userName,
                    password);
            return conn;
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }

        return null;

    }
    
    public static void main(String[] args) {
        System.out.println(getMySQLConnection());
    }
}
