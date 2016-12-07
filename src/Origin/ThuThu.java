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
public class ThuThu {
    private int maThuThu;
    private String tenTaiKhoan;
    private String matKhau;
    private String tenThuThu;
    
    public ThuThu() {
        
    }

    public ThuThu(int maThuThu, String tenTaiKhoan, String matKhau, String tenThuThu) {
        this.maThuThu = maThuThu;
        this.tenTaiKhoan = tenTaiKhoan;
        this.matKhau = matKhau;
        this.tenThuThu = tenThuThu;
    }

    public int getMaThuThu() {
        return maThuThu;
    }

    public void setMaThuThu(int maThuThu) {
        this.maThuThu = maThuThu;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getTenThuThu() {
        return tenThuThu;
    }

    public void setTenThuThu(String tenThuThu) {
        this.tenThuThu = tenThuThu;
    }

    
    
    
}
