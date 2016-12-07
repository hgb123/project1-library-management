package Origin;


import java.sql.Date;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Toan Ho
 */
public class MuonTra {
    private int maMuon;
    private int maDocGia;
    private ArrayList<Integer> maSach;
    private Date ngayMuon;
    private Date hanTra;
    private Date ngayTra;
    private String tienDatCoc;
    private String tienPhat;
    private String ghiChu;
    
    
    public MuonTra() {
        
    }
    

    public MuonTra(int maDocGia, ArrayList<Integer> maSach, Date ngayMuon, Date hanTra, String tienDatCoc) {
        this.maDocGia = maDocGia;
        this.maSach = maSach;
        this.ngayMuon = ngayMuon;
        this.hanTra = hanTra;
        this.tienDatCoc = tienDatCoc;
    }

    public MuonTra(int maMuon, int maDocGia, ArrayList<Integer> maSach, Date ngayMuon, Date hanTra, Date ngayTra, String tienDatCoc, String tienPhat, String ghiChu) {
        this.maMuon = maMuon;
        this.maDocGia = maDocGia;
        this.maSach = maSach;
        this.ngayMuon = ngayMuon;
        this.hanTra = hanTra;
        this.ngayTra = ngayTra;
        this.tienDatCoc = tienDatCoc;
        this.tienPhat = tienPhat;
        this.ghiChu = ghiChu;
    }

    public int getMaMuon() {
        return maMuon;
    }

    public void setMaMuon(int maMuon) {
        this.maMuon = maMuon;
    }

    public int getMaDocGia() {
        return maDocGia;
    }

    public void setMaDocGia(int maDocGia) {
        this.maDocGia = maDocGia;
    }

    public ArrayList<Integer> getMaSach() {
        return maSach;
    }

    public void setMaSach(ArrayList<Integer> maSach) {
        this.maSach = maSach;
    }

    public Date getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(Date ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public Date getHanTra() {
        return hanTra;
    }

    public void setHanTra(Date hanTra) {
        this.hanTra = hanTra;
    }

    public Date getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(Date ngayTra) {
        this.ngayTra = ngayTra;
    }

    public String getTienDatCoc() {
        return tienDatCoc;
    }

    public void setTienDatCoc(String tienDatCoc) {
        this.tienDatCoc = tienDatCoc;
    }

    public String getTienPhat() {
        return tienPhat;
    }

    public void setTienPhat(String tienPhat) {
        this.tienPhat = tienPhat;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
    
    
}
