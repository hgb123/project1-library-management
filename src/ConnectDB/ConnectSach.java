/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectDB;

import Origin.Sach;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Toan Ho
 */
public class ConnectSach extends Connect {
    private final String table = "sach";
    private Connection connection;
    
    public ConnectSach() {
        this.connection = this.getConnection();
    }
    
    public void showData(ResultSet rs) {
        try {
            while (rs.next()) {
                System.out.printf("%-3d %-50s %-25s %-10s %-20s %-10s %-5d\n"
                        ,rs.getInt(1),rs.getString(2)
                        ,rs.getString(3),rs.getString(4),rs.getString(5)
                        ,rs.getString(6),rs.getInt(7));
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        System.out.println("");
    }
    
    public ResultSet getQueryData(String maSach, String tenSach, String tacGia, String nxb, String theLoai, String namXB) {
        ResultSet rs = null;
        String query = 
                "select * from " + table +
                " where "
                + "maSach like '%" + maSach + "%' "
                + "and tenSach like '%" + tenSach + "%' "
                + "and tacGia like '%" + tacGia + "%' "
                + "and nxb like '%" + nxb + "%' "
                + "and theLoai like '%" + theLoai + "%' "
                + "and namXB like '%" + namXB + "%' ";
        
        try {
            Statement st = connection.createStatement();
            rs = st.executeQuery(query);
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
        
        return rs; 
    }
    
    public ResultSet getTacGiaStat() {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String query = 
                "select "
                + "tacGia, count(*) as soLuong "
                + "from " + table + " "
                + "group by tacGia "
                + "order by tacGia ";
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            System.out.println("Sach: Get stats success");
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        return rs;
    }
    
    public ResultSet getNXBStat() {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String query = 
                "select "
                + "nxb, count(*) as soLuong "
                + "from " + table + " "
                + "group by nxb "
                + "order by nxb ";
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            System.out.println("Sach: Get stats success");
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        return rs;
    }
    
    public ResultSet getTheLoaiStat() {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String query = 
                "select "
                + "theLoai, count(*) as soLuong "
                + "from " + table + " "
                + "group by theLoai "
                + "order by theLoai ";
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            System.out.println("Sach: Get stats success");
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        return rs;
    }
    
    public ResultSet getNamXBStat() {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String query = 
                "select "
                + "namXB, count(*) as soLuong "
                + "from " + table + " "
                + "group by namXB "
                + "order by namXB ";
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            System.out.println("Sach: Get stats success");
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        return rs;
    }
    
    public void addData(Sach sach) {
        String query = 
                "insert into " + table + "(tenSach,tacGia,nxb,theLoai,namXB,soLuong)"
                + " values(" 
                + "'" + sach.getTenSach() + "'," 
                + "'" + sach.getTacGia() + "'," 
                + "'" + sach.getNxb() + "'," 
                + "'" + sach.getTheLoai() + "'," 
                + "'" + sach.getNamXB() + "'," 
                + sach.getSoLuong() + ")";
        try {
            Statement st = connection.createStatement();
            if (st.executeUpdate(query) > 0) {
                System.out.println("Sach: Add success!");
            } else {
                System.err.println("Sach: Add error!");
            }
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
        
    }
    
    public void deleteByID(String maSach) {
        String query = "delete from " + table 
                + " where maSach = " + maSach;
        try {
            Statement st = connection.createStatement();
            if (st.executeUpdate(query) > 0) {
                st.executeUpdate("alter table " + table + " auto_increment = 1");
                System.out.println("Sach: Delete success!");
            } else {
                System.err.println("Sach: Delete error!");
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    public void updateByID(Sach sach) {
        String query = "update " + table + " "
                + "set maSach = ?, "
                + "tenSach = ?, "
                + "tacGia = ?, "
                + "nxb = ?, "
                + "theLoai = ?, "
                + "namXB = ?, "
                + "soLuong = ? "
                + "where maSach = ?";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, sach.getMaSach());
            ps.setString(2, sach.getTenSach());
            ps.setString(3, sach.getTacGia());
            ps.setString(4, sach.getNxb());
            ps.setString(5, sach.getTheLoai());
            ps.setString(6, sach.getNamXB());
            ps.setInt(7, sach.getSoLuong());
            ps.setInt(8, sach.getMaSach());
            if (ps.executeUpdate() > 0) {
                System.out.println("Sach: Edit success!");
            } else {
                System.err.println("Sach: Edit error!");
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
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
