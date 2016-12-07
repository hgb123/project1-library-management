/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectDB;

import Origin.MuonTra;
import java.sql.*;

/**
 *
 * @author Toan Ho
 */
public class ConnectMuonTra extends Connect {
    private final String table = "muontra";
    private final String table2 = "muontra_sach";
    private final String table3 = "docgia";
    private Connection connection;
    
    public ConnectMuonTra() {
        this.connection = this.getConnection();
    }
    
    public ResultSet getQueryData(
            String maMuon, String maDocGia, String maSach,
            String ngayMuon, String hanTra, String ngayTra,
            String tienDatCoc, String tienPhat, String ghiChu) {
        ResultSet rs = null;
        PreparedStatement ps = null;
//        String query = 
//                "select "
//                + "maMuon, maDocGia, maSach, "
//                + "date_format(ngayMuon,'%d-%m-%Y') as ngayMuon, "
//                + "date_format(hanTra,'%d-%m-%Y') as hanTra, "
//                + "date_format(ngayTra,'%d-%m-%Y') as ngayTra, "
//                + "tienDatCoc, tienPhat, ghiChu "
//                + "from " + table + " "
//                + "where "
//                + "maMuon like ? "
//                + "and maDocGia like ? "
//                + "and maSach like ? "
//                + "and ngayMuon like ? "
//                + "and hanTra like ? "
//                + "and (ngayTra like ? or ngayTra is null) "
//                + "and tienDatCoc like ? "
//                + "and tienPhat like ? "
//                + "and ghiChu like ? ";
        String query =
                "select * from "
                + "(select "
                + table +".maMuon, maDocGia, group_concat(maSach separator ',') as maSach, "
                + "date_format(ngayMuon,'%d-%m-%Y') as ngayMuon, "
                + "date_format(hanTra,'%d-%m-%Y') as hanTra, "
                + "date_format(ngayTra,'%d-%m-%Y') as ngayTra, "
                + "tienDatCoc, tienPhat, ghiChu "
                + "from " + table + ", " + table2 + " "
                + "where "
                + table + ".maMuon like ? and " + table + ".maMuon = " + table2 + ".maMuon "
                + "and maDocGia like ? "
                + "and ngayMuon like ? "
                + "and hanTra like ? "
                + "and (ngayTra like ? or ngayTra is null) "
                + "and tienDatCoc like ? "
                + "and tienPhat like ? "
                + "and ghiChu like ? group by " + table + ".maMuon) as temp "
                + "where maMuon in "
                + "(select maMuon from " + table2 + " where maSach like ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, "%" + maMuon + "%");
            ps.setString(2, "%" + maDocGia + "%");
            ps.setString(3, "%" + ngayMuon + "%");
            ps.setString(4, "%" + hanTra + "%");
            ps.setString(5, "%" + ngayTra + "%");
            ps.setString(6, "%" + tienDatCoc + "%");
            ps.setString(7, "%" + tienPhat + "%");
            ps.setString(8, "%" + ghiChu + "%");
            ps.setString(9, maSach.equals("") ? "%%" : maSach);
            rs = ps.executeQuery();
            System.out.println("MuonTra: Search done!");
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        return rs;
    }
    
    public ResultSet getMaSachStat() {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String query = 
                "select "
                + "maSach, count(*) soLuongMuon "
                + "from " + table2 + " "
                + "group by maSach "
                + "order by maSach";
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            System.out.println("MuonTra: Get stats success!");
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        return rs;
    }
    
    public ResultSet getNgayMuonStat() {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String query =
                "select "
                + "date_format(ngayMuon, '%m-%Y') ngayMuon, "
                + "count(distinct(" + table + ".maMuon)) luotMuon, "
                + "count(*) luongSachMuon "
                + "from " + table + ", " + table2 + " "
                + "where " + table + ".maMuon = " + table2 + ".maMuon "
                + "group by date_format(ngayMuon, '%m-%Y') "
                + "order by ngayMuon";
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            System.out.println("MuonTra: Get stats success!");
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        return rs;
    }
    
    public ResultSet getHanTraStat() {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String query =
                "select "
                + "date_format(hanTra, '%m-%Y') hanTra, "
                + "count(distinct(" + table + ".maMuon)) luotTra, "
                + "count(*) luongSachTra "
                + "from " + table + ", " + table2 + " "
                + "where " + table + ".maMuon = " + table2 + ".maMuon "
                + "group by date_format(hanTra, '%m-%Y') "
                + "order by hanTra";
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            System.out.println("MuonTra: Get stats success!");
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        return rs;
    }
    
    public ResultSet getNgayTraStat() {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String query =
                "select "
                + "date_format(ngayTra, '%m-%Y') ngayTra, "
                + "count(distinct(" + table + ".maMuon)) luotTra, "
                + "count(*) luongSachTra "
                + "from " + table + ", " + table2 + " "
                + "where " + table + ".maMuon = " + table2 + ".maMuon "
                + "and ngayTra is not null "
                + "group by date_format(ngayTra, '%m-%Y') "
                + "order by ngayTra";
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            System.out.println("MuonTra: Get stats success!");
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        return rs;
    }
    
    public ResultSet getTraMuonStat() {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String query =
                "select "
                + "date_format(ngayTra, '%m-%Y') ngayTra, "
                + "count(distinct(" + table + ".maMuon)) luotTra, "
                + "count(*) luongSachTra "
                + "from " + table + ", " + table2 + " "
                + "where " + table + ".maMuon = " + table2 + ".maMuon "
                + "and ngayTra is not null "
                + "and ngayTra > hanTra "
                + "group by date_format(ngayTra, '%m-%Y') "
                + "order by ngayTra";
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            System.out.println("MuonTra: Get stats success!");
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        return rs;
    }
    
    public ResultSet getDocGiaMuonStat() {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String query = 
                "select "
                + table + ".maDocGia, hoTen, "
                + "count(distinct(" + table + ".maMuon)) luotMuon, "
                + "count(*) soSachMuon "
                + "from " + table + ", " + table2 + ", " + table3 + " "
                + "where " + table + ".maMuon = " + table2 + ".maMuon "
                + "and " + table + ".maDocGia = " + table3 + ".maDocGia "
                + "group by " + table + ".maDocGia "
                + "order by " + table + ".maDocGia";
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            System.out.println("MuonTra: Get stats success!");
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        return rs;
    }
    
    public ResultSet getDocGiaDangMuonStat() {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String query = 
                "select "
                + table + ".maDocGia, hoTen, count(*) sachDangMuon "
                + "from " + table + ", " + table2 + ", " + table3 + " "
                + "where " + table + ".maMuon = " + table2 + ".maMuon "
                + "and " + table + ".maDocGia = " + table3 + ".maDocGia "
                + "and ngayTra is null "
                + "group by " + table + ".maDocGia "
                + "order by " + table + ".maDocGia";
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            System.out.println("MuonTra: Get stats success!");
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        return rs;
    }
    
    public ResultSet getDocGiaDaTraStat() {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String query = 
                "select "
                + table + ".maDocGia, hoTen, count(*) sachDaTra "
                + "from " + table + ", " + table2 + ", " + table3 + " "
                + "where " + table + ".maMuon = " + table2 + ".maMuon "
                + "and " + table + ".maDocGia = " + table3 + ".maDocGia "
                + "and ngayTra is not null "
                + "group by " + table + ".maDocGia "
                + "order by " + table + ".maDocGia";
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            System.out.println("MuonTra: Get stats success!");
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        return rs;
    }
    
    public ResultSet getDocGiaTraMuonStat() {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String query = 
                "select "
                + table + ".maDocGia, hoTen, "
                + "count(distinct(" + table + ".maMuon)) luotTraMuon, "
                + "count(*) sachTraMuon "
                + "from " + table + ", " + table2 + ", " + table3 + " "
                + "where " + table + ".maMuon = " + table2 + ".maMuon "
                + "and " + table + ".maDocGia = " + table3 + ".maDocGia "
                + "and ngayTra is not null "
                + "and ngayTra > hanTra "
                + "group by " + table + ".maDocGia "
                + "order by " + table + ".maDocGia";
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            System.out.println("MuonTra: Get stats success!");
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        return rs;
    }
    
    public ResultSet getDocGiaThanhToanStat() {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String query = 
                "select "
                + table + ".maDocGia, hoTen, sum(tienDatCoc) tienDatCoc, sum(tienPhat) tienPhat "
                + "from " + table + ", " + table3 + " "
                + "where " + table + ".maDocGia = " + table3 + ".maDocGia "
                + "group by " + table + ".maDocGia "
                + "order by " + table + ".maDocGia";
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            System.out.println("MuonTra: Get stats success!");
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        return rs;
    }
    
    public void addData(MuonTra muonTra) {
        String query =
                "insert into " + table
                + "(maDocGia,"
                + "ngayMuon,hanTra,tienDatCoc) "
                + "values(?,?,?,?)";
        String query2 = 
                "insert into " + table2 + " "
                + "values (?,?)";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // First step
            ps = connection.prepareStatement(query);
            ps.setInt(1, muonTra.getMaDocGia());
            ps.setDate(2, muonTra.getNgayMuon());
            ps.setDate(3, muonTra.getHanTra());
            ps.setString(4, muonTra.getTienDatCoc());
            if (ps.executeUpdate() > 0) {
                System.out.println("MuonTra: Add success!");
            } else {
                System.err.println("MuonTra: Add error!");
            }
            
            // Final step
            Statement st = connection.createStatement();
            rs = st.executeQuery("select last_insert_id()");
            while (rs.next()) {
                muonTra.setMaMuon(rs.getInt(1));
            }
            ps = connection.prepareStatement(query2);
            for (int count = 0; count < muonTra.getMaSach().size(); ++count) {
                ps = connection.prepareStatement(query2);
                ps.setInt(1, muonTra.getMaMuon());
                ps.setInt(2, muonTra.getMaSach().get(count));
                if (ps.executeUpdate() > 0) {
                    System.out.println("MuonTra_Sach: Add success!" + "(" + (count + 1) + ")");
                } else {
                    System.err.println("MuonTra_Sach: Add error!" + "(" + (count + 1) + ")");
                }
            }
            
            
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    public void updateByID(MuonTra muonTra) {
        String preQuery = 
                "delete from " + table2 + " "
                + "where maMuon = ?";
        String query = 
                "update " + table + " "
                + "set "
                + "maMuon = ? , "
                + "maDocGia = ? , "
                + "ngayMuon = ? , "
                + "hanTra = ? , "
                + "ngayTra = ? , "
                + "tienDatCoc = ? , "
                + "tienPhat = ? , "
                + "ghiChu = ? "
                + "where maMuon = ?";
        String query2 = 
                "insert into " + table2 + " "
                + "values (?,?)";
        PreparedStatement ps = null;
        try {
            // Pre step
            ps = connection.prepareStatement(preQuery);
            ps.setInt(1, muonTra.getMaMuon());
            if (ps.executeUpdate() > 0) {
                System.out.println("MuonTra: Pre-update success!");
            } else {
                System.err.println("MuonTra: Pre-update error!");
            }
            
            // First step
            ps = connection.prepareStatement(query);
            ps.setInt(1, muonTra.getMaMuon());
            ps.setInt(2, muonTra.getMaDocGia());
            ps.setDate(3, muonTra.getNgayMuon());
            ps.setDate(4, muonTra.getHanTra());
            ps.setDate(5, muonTra.getNgayTra());
            ps.setString(6, muonTra.getTienDatCoc());
            ps.setString(7, muonTra.getTienPhat());
            ps.setString(8, muonTra.getGhiChu());
            ps.setInt(9, muonTra.getMaMuon());
            if (ps.executeUpdate() > 0) {
                System.out.println("MuonTra: Update success!");
            } else {
                System.err.println("MuonTra: Update error!");
            }
            
            // Final step
            ps = connection.prepareStatement(query2);
            for (int count = 0; count < muonTra.getMaSach().size(); ++count) {
                ps = connection.prepareStatement(query2);
                ps.setInt(1, muonTra.getMaMuon());
                ps.setInt(2, muonTra.getMaSach().get(count));
                if (ps.executeUpdate() > 0) {
                    System.out.println("MuonTra_Sach: Update success!" + "(" + (count + 1) + ")");
                } else {
                    System.err.println("MuonTra_Sach: Update error!" + "(" + (count + 1) + ")");
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    public ResultSet getData() {
//        select muontra.maMuon, maDocGia, group_concat(maSach separator ',') as maSach,
//                ngayMuon, hanTra, ngayTra, tienDatCoc, tienPhat, ghiChu  
//                        from muontra, muontra_sach 
//                                where muontra.maMuon = muontra_sach.maMuon group by muontra.maMuon;
        ResultSet rs = null;
        String query = "select "
                + table + ".maMuon, maDocGia, group_concat(maSach separator ',') as maSach, "
                + "date_format(ngayMuon,'%d-%m-%Y') as ngayMuon, "
                + "date_format(hanTra,'%d-%m-%Y') as hanTra, "
                + "date_format(ngayTra,'%d-%m-%Y') as ngayTra, "
                + "tienDatCoc, tienPhat, ghiChu "
                + "from " + table + ", " + table2 + " "
                + "where " + table + ".maMuon = " + table2 + ".maMuon "
                + "group by " + table + ".maMuon" ;
        try {
            Statement st = connection.createStatement();
            rs = st.executeQuery(query);
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
        
        return rs;
    }
    
}
