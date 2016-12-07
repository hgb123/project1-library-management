/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectDB;

/**
 *
 * @author Toan Ho
 */
public class TestConnect {
    public static void main(String[] args) {
//        ConnectDocGia connectDocGia = new ConnectDocGia();
//        connectDocGia.showData(connectDocGia.getData());
        
        ConnectSach connectSach = new ConnectSach();
        connectSach.showData(connectSach.getData());
        
//        ConnectThuThu connectTaiKhoan = new ConnectThuThu();
//        connectTaiKhoan.showData(connectTaiKhoan.getData());
    }
}
