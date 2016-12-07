package Origin;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Toan Ho
 */
public class Sach {
    private int maSach;
    private String tenSach;
    private String tacGia;
    private String nxb;
    private String theLoai;
    private String namXB;
    private int soLuong;
    
    public Sach() {
        
    }

    public Sach(String tenSach, String tacGia, String nxb, String theLoai, String namXB, int soLuong) {
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.nxb = nxb;
        this.theLoai = theLoai;
        this.namXB = namXB;
        this.soLuong = soLuong;
    }

    public Sach(int maSach, String tenSach, String tacGia, String nxb, String theLoai, String namXB) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.nxb = nxb;
        this.theLoai = theLoai;
        this.namXB = namXB;
    }

    public Sach(int maSach, String tenSach, String tacGia, String nxb, String theLoai, String namXB, int soLuong) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.nxb = nxb;
        this.theLoai = theLoai;
        this.namXB = namXB;
        this.soLuong = soLuong;
    }
    
    

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getNxb() {
        return nxb;
    }

    public void setNxb(String nxb) {
        this.nxb = nxb;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public String getNamXB() {
        return namXB;
    }

    public void setNamXB(String namXB) {
        this.namXB = namXB;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
    
    
}
