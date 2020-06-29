/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HuyTuan
 */
public class MyDatabase {

    private static Connection con = null;

    public MyDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String sURL = "jdbc:mysql://localhost:3306/ezrentbike?zeroDateTimeBehavior=convertToNull";
            if (con == null) {
                con = DriverManager.getConnection(sURL, "root", "anhyeuem");
                System.out.println("Ket noi database thanh cong");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("err: " + ex.getMessage());
        }
    }

    public ResultSet query(String sql) {
        Statement st = null;
        ResultSet res = null;
        try {
            st = con.createStatement();
            res = st.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(MyDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    public int execStatment(String sql) {
        Statement st = null;
        try {
            Statement statement = con.createStatement();
            int rowCount = statement.executeUpdate(sql);
            return rowCount;
        } catch (SQLException ex) {
            Logger.getLogger(MyDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
