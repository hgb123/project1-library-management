/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectDB;

import Origin.ThuThu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Toan Ho
 */
public class ConnectThuThu extends Connect {
    private final String table = "thuthu";
    private Connection connection;
    
    public ConnectThuThu() {
        this.connection = this.getConnection();
    }
    
    public ResultSet login(String tenTaiKhoan, String matKhau) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String query = "select * from " + table + " where tenTaiKhoan = ? and matKhau = ?";
        
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, tenTaiKhoan);
            ps.setString(2, matKhau);
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        return rs;
    }
    
    public void showData(ResultSet rs) {
        try {
            while (rs.next()) {
                System.out.printf("%-3s %-20s %-10s\n"
                        ,rs.getInt(1),rs.getString(2),rs.getString(3));
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        System.out.println("");
    }
    
    public ResultSet getData() {
        ResultSet rs = null;
        String query = "select * from " + table;
        try {
            Statement st = connection.createStatement();
            rs = st.executeQuery(query);
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
        
        return rs;
    }
}
