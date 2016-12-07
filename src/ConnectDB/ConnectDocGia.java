/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectDB;

import Origin.DocGia;
import java.sql.*;

/**
 *
 * @author Toan Ho
 */
public class ConnectDocGia extends Connect {
    private final String table = "docgia";
    private Connection connection;
    
    public ConnectDocGia() {
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
                System.out.printf("%-3d %-20s %-4s %-10s %-20s %-10s %-10s\n"
                        ,rs.getInt(1),rs.getString(2)
                        ,rs.getString(3),rs.getString(4),rs.getString(5)
                        ,rs.getString(6),rs.getString(7));
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        System.out.println("");
    }
    
    public ResultSet getQueryData(
            String maDocGia, String tenTaiKhoan, String matKhau, 
            String hoTen, String gioiTinh, String ngaySinh,
            String diaChi, String soDienThoai, String email) {
        ResultSet rs = null;
        String query = 
                "select * from " + table +
                " where "
                + "maDocGia like '%" + maDocGia + "%' "
                + "and tenTaiKhoan like '%" + tenTaiKhoan + "%' "
                + "and matKhau like '%" + matKhau + "%' "
                + "and hoTen like '%" + hoTen + "%' "
                + "and gioiTinh like '%" + gioiTinh + "%' "
                + "and ngaySinh like '%" + ngaySinh + "%' "
                + "and diaChi like '%" + diaChi + "%' "
                + "and soDienThoai like '%" + soDienThoai + "%' "
                + "and email like '%" + email + "%' ";
        
        try {
            Statement st = connection.createStatement();
            rs = st.executeQuery(query);
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
        
        return rs; 
    }
    
    public ResultSet getDiaChiStat() {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String query = 
                "select "
                + "diaChi, count(*) soLuong "
                + "from " + table + " "
                + "group by diaChi "
                + "order by diaChi";
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            System.out.println("DocGia: Get stats success!");
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        return rs;
    }
    
    public ResultSet getGioiTinhStat() {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String query = 
                "select "
                + "gioiTinh, count(*) soLuong "
                + "from " + table + " "
                + "group by gioiTinh "
                + "order by gioiTinh";
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            System.out.println("DocGia: Get stats success!");
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        return rs;
    }
    
    public ResultSet getThangSinhStat() {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String query = 
                "select "
                + "substring_index(substring_index(ngaySinh, '-', -2), '-', 1) thangSinh, count(*) soLuong "
                + "from " + table + " "
                + "group by thangSinh "
                + "order by thangSinh";
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            System.out.println("DocGia: Get stats success!");
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        return rs;
    }
    
    public ResultSet getNamSinhStat() {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String query = 
                "select "
                + "substring_index(ngaySinh, '-', -1) namSinh, count(*) soLuong "
                + "from " + table + " "
                + "group by namSinh "
                + "order by namSinh";
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            System.out.println("DocGia: Get stats success!");
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        return rs;
    }
    
    public void addData(DocGia docGia) {
        String query = 
                "insert into " + table 
                + "(tenTaiKhoan,matKhau,"
                + "hoTen,gioiTinh,ngaySinh,"
                + "diaChi,soDienThoai,email)"
                + " values(?,?,?,?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, docGia.getTenTaiKhoan());
            ps.setString(2, docGia.getMatKhau());
            ps.setString(3, docGia.getHoTen());
            ps.setString(4, docGia.getGioiTinh());
            ps.setString(5, docGia.getNgaySinh());
            ps.setString(6, docGia.getDiaChi());
            ps.setString(7, docGia.getSoDienThoai());
            ps.setString(8, docGia.getEmail());
            if (ps.executeUpdate() > 0) {
                System.out.println("DocGia: Add success!");
            } else {
                System.err.println("DocGia: Add error!");
            }
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
        
    }
    
    public void updateByID(DocGia docGia) {
        String query = "update " + table + " "
                + "set "
                + "maDocGia = ?, "
                + "tenTaiKhoan = ?, "
                + "matKhau = ?, "
                + "hoTen = ?, "
                + "gioiTinh = ?, "
                + "ngaySinh = ?, "
                + "diaChi = ?, "
                + "soDienThoai = ?, "
                + "email = ? "
                + "where maDocGia = ?";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, docGia.getMaDocGia());
            ps.setString(2, docGia.getTenTaiKhoan());
            ps.setString(3, docGia.getMatKhau());
            ps.setString(4, docGia.getHoTen());
            ps.setString(5, docGia.getGioiTinh());
            ps.setString(6, docGia.getNgaySinh());
            ps.setString(7, docGia.getDiaChi());
            ps.setString(8, docGia.getSoDienThoai());
            ps.setString(9, docGia.getEmail());
            ps.setInt(10, docGia.getMaDocGia());
            if (ps.executeUpdate() > 0) {
                System.out.println("DocGia: Edit success!");
            } else {
                System.err.println("DocGia: Edit error!");
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    public void deleteByID(String maDocGia) {
        String query = "delete from " + table 
                + " where maDocGia = " + maDocGia;
        try {
            Statement st = connection.createStatement();
            if (st.executeUpdate(query) > 0) {
                st.executeUpdate("alter table " + table + " auto_increment = 1");
                System.out.println("DocGia: Delete success!");
            } else {
                System.err.println("DocGia: Delete error!");
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
