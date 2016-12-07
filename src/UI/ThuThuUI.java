/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Origin.*;
import ConnectDB.*;
import Utilities.Choice;
import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 *
 * @author Toan Ho
 */
public class ThuThuUI extends javax.swing.JFrame {

    /**
     * Creates new form ThuThuUI
     */
    private final String datePattern = "dd-MM-yyyy";
    
    private String presentChoice;
    ConnectSach connectSach = new ConnectSach();
    private Sach sach;
    private ArrayList<Sach> listSach;
    
    ConnectDocGia connectDocGia = new ConnectDocGia();
    private DocGia docGia;
    private ArrayList<DocGia> listDocGia;
    
    ConnectMuonTra connectMuonTra = new ConnectMuonTra();
    private MuonTra muonTra;
    private boolean isAddingMuonTra;
    
    public static int MUONTRA_CHOOSE_CHOICE;
   
    
    public ThuThuUI() {
        initComponents();
        if (DangNhap.thuThu != null) {
            welcomeMsg.setText("  Xin chào, " + DangNhap.thuThu.getTenThuThu() + "  ");
        }
        listDocGia = new ArrayList<DocGia>();
        listSach = new ArrayList<Sach>();
        
        loadAllSach();
        loadAllDocGia();
        loadAllMuonTra();
        
        clearSachInputFields(false);
        clearDocGiaInputFields(false);
        clearMuonTraInputFields(false);
        presentChoice = "";
        
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    private Object[][] getTableData(javax.swing.JTable table) {
        DefaultTableModel x = (DefaultTableModel) table.getModel();
        int row = x.getRowCount();
        int col = x.getColumnCount();
        Object[][] data = new Object[row+1][col];
        for (int c = 0; c < col; ++c) {
            data[0][c] = x.getColumnName(c);
        }
        for (int r = 1; r < row + 1; ++r) {
            for (int c = 0; c < col; ++c) {
                data[r][c] = x.getValueAt(r-1, c);
            }
        }
        return data; 
    }
    
    private void logOutThuThuSession() {
        this.dispose();
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            System.err.println(ex.toString()); 
        }
        DangNhap dangNhap = new DangNhap();
    }
    
    private void searchAndChoose() {
        TimVaChonUI timVaChon = new TimVaChonUI();
        timVaChon.setPreviousFrame(this);
        this.setEnabled(false);
    }
    
    public void onChooseDone(String ma) {
        if (MUONTRA_CHOOSE_CHOICE == 0) {
            tfMTMaDocGia.setText(ma);
            tfMTMaDocGia.requestFocus();
        } else {
            String currentExpectedSachList = tfMTMaSach.getText().trim();
            if (isAddingMuonTra) {
                if (currentExpectedSachList.equals("")) {
                tfMTMaSach.setText(ma);
                } else {
                    currentExpectedSachList += "," + ma;
                    tfMTMaSach.setText(currentExpectedSachList);
                }
            } else {
                tfMTMaSach.setText(ma);
            }
            
        }
    }
    
    /**
     * 
     * @section Sach
     * @functions searchSach(), addSach(), editSach(), loadAllSach()
     * @utilities clearSachInputFields()
     * @export exportSachSearchResult()
     */
    
    private void searchSach() {
        DefaultTableModel model = new DefaultTableModel();
        
        String maSach = tfMaSach.getText().trim();
        String tenSach = tfTenSach.getText().trim();
        String tacGia = tfTacGia.getText().trim();
        String nxb = tfNXB.getText().trim();
        String theLoai = tfTheLoai.getText().trim();
        String namXB = tfNamXB.getText().trim();
        
        ResultSet rs = connectSach.getQueryData(maSach, tenSach, tacGia, nxb, theLoai, namXB);
        try {
            // Load title for each column
            ResultSetMetaData rsMD = rs.getMetaData();
            int colNum = rsMD.getColumnCount();
            String[] arr = new String[colNum];
            for (int i=0; i<colNum; i++) {
                arr[i] = rsMD.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(arr);
            
            // Load data to table
            while (rs.next()) {
                for (int i=0; i<colNum; i++) {
                    arr[i] = rs.getString(i + 1);
                }
                model.addRow(arr);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        sachTable.setModel(model);
        
    }
    
    private void investigateSachTacGiaStat() {
        DefaultTableModel model = new DefaultTableModel();
        
        ResultSet rs = connectSach.getTacGiaStat();
        try {
            // Load title for each column
            ResultSetMetaData rsMD = rs.getMetaData();
            int colNum = rsMD.getColumnCount();
            String[] arr = new String[colNum];
            for (int i=0; i<colNum; i++) {
                arr[i] = rsMD.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(arr);
            
            // Load data to table
            while (rs.next()) {
                for (int i=0; i<colNum; i++) {
                    arr[i] = rs.getString(i + 1);
                }
                model.addRow(arr);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        sachTable.setModel(model);
    }
    
    private void investigateSachNXBStat() {
        DefaultTableModel model = new DefaultTableModel();
        
        ResultSet rs = connectSach.getNXBStat();
        try {
            // Load title for each column
            ResultSetMetaData rsMD = rs.getMetaData();
            int colNum = rsMD.getColumnCount();
            String[] arr = new String[colNum];
            for (int i=0; i<colNum; i++) {
                arr[i] = rsMD.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(arr);
            
            // Load data to table
            while (rs.next()) {
                for (int i=0; i<colNum; i++) {
                    arr[i] = rs.getString(i + 1);
                }
                model.addRow(arr);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        sachTable.setModel(model);
    }
    
    private void investigateSachTheLoaiStat() {
        DefaultTableModel model = new DefaultTableModel();
        
        ResultSet rs = connectSach.getTheLoaiStat();
        try {
            // Load title for each column
            ResultSetMetaData rsMD = rs.getMetaData();
            int colNum = rsMD.getColumnCount();
            String[] arr = new String[colNum];
            for (int i=0; i<colNum; i++) {
                arr[i] = rsMD.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(arr);
            
            // Load data to table
            while (rs.next()) {
                for (int i=0; i<colNum; i++) {
                    arr[i] = rs.getString(i + 1);
                }
                model.addRow(arr);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        sachTable.setModel(model);
    }
    
    private void investigateSachNamXBStat() {
        DefaultTableModel model = new DefaultTableModel();
        
        ResultSet rs = connectSach.getNamXBStat();
        try {
            // Load title for each column
            ResultSetMetaData rsMD = rs.getMetaData();
            int colNum = rsMD.getColumnCount();
            String[] arr = new String[colNum];
            for (int i=0; i<colNum; i++) {
                arr[i] = rsMD.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(arr);
            
            // Load data to table
            while (rs.next()) {
                for (int i=0; i<colNum; i++) {
                    arr[i] = rs.getString(i + 1);
                }
                model.addRow(arr);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        sachTable.setModel(model);
    }
    
    private void exportSachSearchResult() throws Exception {
        Object[][] data = getTableData(sachTable);
        JFileChooser fc = new JFileChooser();
        
        int choice = fc.showSaveDialog(null);
        if (choice == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String fileName = file.getAbsolutePath();
            
            WritableWorkbook wb = Workbook.createWorkbook(file);
            WritableSheet sheet = wb.createSheet("Tim kiem sach", 0);
            
            // Header
            WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 14);
            titleFont.setBoldStyle(WritableFont.BOLD);
            WritableCellFormat titleFormat = new WritableCellFormat(titleFont);
            sheet.addCell(new Label(1,0,"THƯ VIỆN XYZ", titleFormat));
            sheet.addCell(new Label(1,2,"KẾT QUẢ TÌM KIẾM SÁCH", titleFormat));
            // Content
            sheet.setColumnView(1, 30);
            sheet.setColumnView(2, 25);
            sheet.setColumnView(3, 15);
            sheet.setColumnView(4, 20);
            int r0 = 4;
            int c0 = 0;
            WritableCellFormat cellFormat = new WritableCellFormat();
            cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            for (int row = r0, i = 0; row < data.length + r0; ++row, ++i) {
                for (int col = c0, j = 0; col < data[0].length + c0; ++col, ++j) {
                    sheet.addCell(new Label(col, row, (String) data[i][j], cellFormat));
                }
            }
            // Footer
            WritableFont footerFont = new WritableFont(WritableFont.ARIAL, 12);
            footerFont.setBoldStyle(WritableFont.BOLD);
            WritableCellFormat footerFormat = new WritableCellFormat(footerFont);
            SimpleDateFormat sdfClock = new SimpleDateFormat("HH:mm");
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
            String tenThuThu = DangNhap.thuThu != null ? DangNhap.thuThu.getTenThuThu() : "XYZ";
            java.util.Date currentDay = new java.util.Date();
            sheet.addCell(new Label(3, data.length + r0 + 1,
                    "Hà Nội, " + sdfClock.format(currentDay) + " ngày " + sdfDate.format(currentDay), footerFormat));
            sheet.addCell(new Label(3, data.length + r0 + 3, "Người lập: " + tenThuThu, footerFormat));
            
            // Done and out
            wb.write();
            wb.close();
            
            JOptionPane.showMessageDialog(null, "Xuất thành công!", "Xuất kết quả tìm kiếm", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Sach_Search: File exported!");
        } else {
            System.out.println("Sach_Search: Nothing exported!");
        }
    }
    
    private void exportSachStatsResult() throws Exception {
        Object[][] data = getTableData(sachTable);
        JFileChooser fc = new JFileChooser();
        
        int choice = fc.showSaveDialog(null);
        if (choice == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String fileName = file.getAbsolutePath();
            
            WritableWorkbook wb = Workbook.createWorkbook(file);
            WritableSheet sheet = wb.createSheet("Thong ke sach", 0);
            
            // Header
            WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 14);
            titleFont.setBoldStyle(WritableFont.BOLD);
            WritableCellFormat titleFormat = new WritableCellFormat(titleFont);
            sheet.addCell(new Label(0,0,"THƯ VIỆN XYZ", titleFormat));
            sheet.addCell(new Label(0,2,"KẾT QUẢ THỐNG KÊ SÁCH", titleFormat));
            // Content
            sheet.setColumnView(0, 30);
            sheet.setColumnView(1, 15);
            int r0 = 4;
            int c0 = 0;
            WritableCellFormat cellFormat = new WritableCellFormat();
            cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            for (int row = r0, i = 0; row < data.length + r0; ++row, ++i) {
                for (int col = c0, j = 0; col < data[0].length + c0; ++col, ++j) {
                    sheet.addCell(new Label(col, row, (String) data[i][j], cellFormat));
                }
            }
            // Footer
            WritableFont footerFont = new WritableFont(WritableFont.ARIAL, 12);
            footerFont.setBoldStyle(WritableFont.BOLD);
            WritableCellFormat footerFormat = new WritableCellFormat(footerFont);
            SimpleDateFormat sdfClock = new SimpleDateFormat("HH:mm");
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
            String tenThuThu = DangNhap.thuThu != null ? DangNhap.thuThu.getTenThuThu() : "XYZ";
            java.util.Date currentDay = new java.util.Date();
            sheet.addCell(new Label(0, data.length + r0 + 1,
                    "Hà Nội, " + sdfClock.format(currentDay) + " ngày " + sdfDate.format(currentDay), footerFormat));
            sheet.addCell(new Label(0, data.length + r0 + 3, "Người lập: " + tenThuThu, footerFormat));
            
            // Done and out
            wb.write();
            wb.close();
            
            JOptionPane.showMessageDialog(null, "Xuất thành công!", "Xuất kết quả thống kê", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Sach_Stats: File exported!");
        } else {
            System.out.println("Sach_Stats: Nothing exported!");
        }
    }
    
    private void addSach() {
        String tenSach = tfTenSach.getText().trim();
        String tacGia = tfTacGia.getText().trim();
        String nxb = tfNXB.getText().trim();
        String theLoai = tfTheLoai.getText().trim();
        String namXB = tfNamXB.getText().trim();
        int soLuong = (Integer)spnSoLuongSach.getValue();
        
        connectSach.addData(new Sach(tenSach, tacGia, nxb, theLoai, namXB, soLuong));
        
        loadAllSach();
    }
    
    private void addSachFromFile() throws Exception {
        JFileChooser fc = new JFileChooser();
        int choice = fc.showOpenDialog(null);
        if (choice == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            String fileName = f.getAbsolutePath();
            System.out.println(fileName);
            Workbook wb = Workbook.getWorkbook(f);
            Sheet sh = wb.getSheet(0);
            int row = sh.getRows();
            int col = sh.getColumns();
            for (int i = 1; i < row; i++) {
                String tenSach = sh.getCell(0, i).getContents().trim();
                String tacGia = sh.getCell(1, i).getContents().trim();
                String nxb = sh.getCell(2, i).getContents().trim();
                String theLoai = sh.getCell(3, i).getContents().trim();
                String namXB = sh.getCell(4, i).getContents().trim();
                int soLuong = Integer.parseInt(sh.getCell(5, i).getContents().trim());

                connectSach.addData(new Sach(tenSach, tacGia, nxb, theLoai, namXB, soLuong)); 
            }
            
            JOptionPane.showMessageDialog(null, "Thêm thành công", "Thêm sách từ tệp", JOptionPane.INFORMATION_MESSAGE);
            loadAllSach();
        }
    }
     
    private void editSach() {
        int maSach = Integer.parseInt(tfMaSach.getText());
        String tenSach = tfTenSach.getText();
        String tacGia = tfTacGia.getText();
        String nxb = tfNXB.getText();
        String theLoai = tfTheLoai.getText();
        String namXB = tfNamXB.getText();
        int soLuong = (Integer) spnSoLuongSach.getValue();
        
        sach = new Sach(maSach, tenSach, tacGia, nxb, theLoai, namXB, soLuong);
        connectSach.updateByID(sach);
        
        loadAllSach();
    }
    
    private void loadAllSach() {
        DefaultTableModel model = new DefaultTableModel();
        listSach.clear();
        
        ResultSet rs = connectSach.getData();
        try {
            // Load title for each column
            ResultSetMetaData rsMD = rs.getMetaData();
            int colNum = rsMD.getColumnCount();
            String[] arr = new String[colNum];
            for (int i=0; i<colNum; i++) {
                arr[i] = rsMD.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(arr);
            
            // Load data to table
            while (rs.next()) {
                listSach.add(new Sach(
                        rs.getInt(1), rs.getString(2), 
                        rs.getString(3), rs.getString(4),
                        rs.getString(5),rs.getString(6),rs.getInt(7)));
                
                for (int i=0; i<colNum; i++) {
                    arr[i] = rs.getString(i + 1);
                }
                model.addRow(arr);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        sachTable.setModel(model);
    } 
    
    private void clearSachInputFields(boolean notInit) {
        sachInputFields.setBorder(BorderFactory.createTitledBorder(" "));
        tfMaSach.setEnabled(notInit);
        tfTenSach.setEnabled(notInit);
        tfTacGia.setEnabled(notInit);
        tfNXB.setEnabled(notInit);
        tfTheLoai.setEnabled(notInit);
        tfNamXB.setEnabled(notInit);
        spnSoLuongSach.setEnabled(notInit);
        
        sachBtnOK.setEnabled(notInit);
        sachBtnCancel.setEnabled(notInit);
        
        tfMaSach.setText("");
        tfNXB.setText("");
        tfNamXB.setText("");
        tfTacGia.setText("");
        tfTenSach.setText("");
        tfTheLoai.setText("");
        spnSoLuongSach.setValue(0);
    }
    
    /**
     * 
     * @section DocGia
     * @functions searchDocGia(), addDocGia(), editDocGia(), loadAllDocGia()
     * @utilities clearDocGiaInputFields()
     */
    
    private void searchDocGia() {
        DefaultTableModel model = new DefaultTableModel();
        
        String maDocGia = tfMaDocGia.getText().trim();
        String tenTaiKhoan = tfTenTaiKhoan.getText().trim();
        String matKhau = tfMatKhau.getText().trim();
        String hoTen = tfHoTen.getText().trim();
        String gioiTinh = tfGioiTinh.getText().trim();
        String ngaySinh = tfNgaySinh.getText().trim();
        String diaChi = tfDiaChi.getText().trim();
        String soDienThoai = tfSoDienThoai.getText().trim();
        String email = tfEmail.getText().trim();
        
        ResultSet rs = connectDocGia.getQueryData(
                maDocGia, tenTaiKhoan, matKhau,
                hoTen, gioiTinh, ngaySinh,
                diaChi, soDienThoai, email);
        try {
            // Load title for each column
            ResultSetMetaData rsMD = rs.getMetaData();
            int colNum = rsMD.getColumnCount();
            String[] arr = new String[colNum];
            for (int i=0; i<colNum; i++) {
                arr[i] = rsMD.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(arr);
            
            // Load data to table
            while (rs.next()) {
                for (int i=0; i<colNum; i++) {
                    arr[i] = rs.getString(i + 1);
                }
                model.addRow(arr);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        docGiaTable.setModel(model);
    }
    
    private void investigateDocGiaDiaChiStat() {
        DefaultTableModel model = new DefaultTableModel();
        
        ResultSet rs = connectDocGia.getDiaChiStat();
        try {
            // Load title for each column
            ResultSetMetaData rsMD = rs.getMetaData();
            int colNum = rsMD.getColumnCount();
            String[] arr = new String[colNum];
            for (int i=0; i<colNum; i++) {
                arr[i] = rsMD.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(arr);
            
            // Load data to table
            while (rs.next()) {
                for (int i=0; i<colNum; i++) {
                    arr[i] = rs.getString(i + 1);
                }
                model.addRow(arr);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        docGiaTable.setModel(model);
    }
    
    private void investigateDocGiaGioiTinhStat() {
        DefaultTableModel model = new DefaultTableModel();
        
        ResultSet rs = connectDocGia.getGioiTinhStat();
        try {
            // Load title for each column
            ResultSetMetaData rsMD = rs.getMetaData();
            int colNum = rsMD.getColumnCount();
            String[] arr = new String[colNum];
            for (int i=0; i<colNum; i++) {
                arr[i] = rsMD.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(arr);
            
            // Load data to table
            while (rs.next()) {
                for (int i=0; i<colNum; i++) {
                    arr[i] = rs.getString(i + 1);
                }
                model.addRow(arr);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        docGiaTable.setModel(model);
    }
    
    private void investigateDocGiaThangSinhStat() {
        DefaultTableModel model = new DefaultTableModel();
        
        ResultSet rs = connectDocGia.getThangSinhStat();
        try {
            // Load title for each column
            ResultSetMetaData rsMD = rs.getMetaData();
            int colNum = rsMD.getColumnCount();
            String[] arr = new String[colNum];
            for (int i=0; i<colNum; i++) {
                arr[i] = rsMD.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(arr);
            
            // Load data to table
            while (rs.next()) {
                for (int i=0; i<colNum; i++) {
                    arr[i] = rs.getString(i + 1);
                }
                model.addRow(arr);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        docGiaTable.setModel(model);
    }
    
    private void investigateDocGiaNamSinhStat() {
        DefaultTableModel model = new DefaultTableModel();
        
        ResultSet rs = connectDocGia.getNamSinhStat();
        try {
            // Load title for each column
            ResultSetMetaData rsMD = rs.getMetaData();
            int colNum = rsMD.getColumnCount();
            String[] arr = new String[colNum];
            for (int i=0; i<colNum; i++) {
                arr[i] = rsMD.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(arr);
            
            // Load data to table
            while (rs.next()) {
                for (int i=0; i<colNum; i++) {
                    arr[i] = rs.getString(i + 1);
                }
                model.addRow(arr);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        docGiaTable.setModel(model);
    }
    
    private void exportDocGiaSearchResult() throws Exception {
        Object[][] tempData = getTableData(docGiaTable);
        Object[][] data = new Object[tempData.length][tempData[0].length - 2];
        for (int i = 0; i < tempData.length; ++i) {
            for (int j = 0; j < tempData[0].length - 2; ++j) {
                data[i][j] = (j == 0)
                        ? tempData[i][j]
                        : tempData[i][j + 2];
            }
        }
        JFileChooser fc = new JFileChooser();
        
        int choice = fc.showSaveDialog(null);
        if (choice == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String fileName = file.getAbsolutePath();
            
            WritableWorkbook wb = Workbook.createWorkbook(file);
            WritableSheet sheet = wb.createSheet("Tim kiem doc gia", 0);
            
            // Header
            WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 14);
            titleFont.setBoldStyle(WritableFont.BOLD);
            WritableCellFormat titleFormat = new WritableCellFormat(titleFont);
            sheet.addCell(new Label(1,0,"THƯ VIỆN XYZ", titleFormat));
            sheet.addCell(new Label(1,2,"KẾT QUẢ TÌM KIẾM ĐỘC GIẢ", titleFormat));
            // Content
            sheet.setColumnView(0, 10);
            sheet.setColumnView(1, 25);
            sheet.setColumnView(2, 9);
            sheet.setColumnView(3, 11);
            sheet.setColumnView(4, 25);
            sheet.setColumnView(5, 14);
            sheet.setColumnView(6, 30);
            int r0 = 4;
            int c0 = 0;
            WritableCellFormat cellFormat = new WritableCellFormat();
            cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            for (int row = r0, i = 0; row < data.length + r0; ++row, ++i) {
                for (int col = c0, j = 0; col < data[0].length + c0; ++col, ++j) {
                    sheet.addCell(new Label(col, row, (String) data[i][j], cellFormat));
                }
            }
            // Footer
            WritableFont footerFont = new WritableFont(WritableFont.ARIAL, 12);
            footerFont.setBoldStyle(WritableFont.BOLD);
            WritableCellFormat footerFormat = new WritableCellFormat(footerFont);
            SimpleDateFormat sdfClock = new SimpleDateFormat("HH:mm");
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
            String tenThuThu = DangNhap.thuThu != null ? DangNhap.thuThu.getTenThuThu() : "XYZ";
            java.util.Date currentDay = new java.util.Date();
            sheet.addCell(new Label(5, data.length + r0 + 1,
                    "Hà Nội, " + sdfClock.format(currentDay) + " ngày " + sdfDate.format(currentDay), footerFormat));
            sheet.addCell(new Label(5, data.length + r0 + 3, "Người lập: " + tenThuThu, footerFormat));
            
            // Done and out
            wb.write();
            wb.close();
            
            JOptionPane.showMessageDialog(null, "Xuất thành công!", "Xuất kết quả tìm kiếm", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("DocGia_Search: File exported!");
        } else {
            System.out.println("DocGia_Search: Nothing exported!");
        }
    }
    
    private void exportDocGiaStatsResult() throws Exception {
        Object[][] data = getTableData(docGiaTable);
        JFileChooser fc = new JFileChooser();
        
        int choice = fc.showSaveDialog(null);
        if (choice == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String fileName = file.getAbsolutePath();
            
            WritableWorkbook wb = Workbook.createWorkbook(file);
            WritableSheet sheet = wb.createSheet("Thong ke doc gia", 0);
            
            // Header
            WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 14);
            titleFont.setBoldStyle(WritableFont.BOLD);
            WritableCellFormat titleFormat = new WritableCellFormat(titleFont);
            sheet.addCell(new Label(0,0,"THƯ VIỆN XYZ", titleFormat));
            sheet.addCell(new Label(0,2,"KẾT QUẢ THỐNG KÊ ĐỘC GIẢ", titleFormat));
            // Content
            sheet.setColumnView(0, 30);
            sheet.setColumnView(1, 15);
            int r0 = 4;
            int c0 = 0;
            WritableCellFormat cellFormat = new WritableCellFormat();
            cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            for (int row = r0, i = 0; row < data.length + r0; ++row, ++i) {
                for (int col = c0, j = 0; col < data[0].length + c0; ++col, ++j) {
                    sheet.addCell(new Label(col, row, (String) data[i][j], cellFormat));
                }
            }
            // Footer
            WritableFont footerFont = new WritableFont(WritableFont.ARIAL, 12);
            footerFont.setBoldStyle(WritableFont.BOLD);
            WritableCellFormat footerFormat = new WritableCellFormat(footerFont);
            SimpleDateFormat sdfClock = new SimpleDateFormat("HH:mm");
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
            String tenThuThu = DangNhap.thuThu != null ? DangNhap.thuThu.getTenThuThu() : "XYZ";
            java.util.Date currentDay = new java.util.Date();
            sheet.addCell(new Label(0, data.length + r0 + 1,
                    "Hà Nội, " + sdfClock.format(currentDay) + " ngày " + sdfDate.format(currentDay), footerFormat));
            sheet.addCell(new Label(0, data.length + r0 + 3, "Người lập: " + tenThuThu, footerFormat));
            
            // Done and out
            wb.write();
            wb.close();
            
            JOptionPane.showMessageDialog(null, "Xuất thành công!", "Xuất kết quả thống kê", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("DocGia_Stats: File exported!");
        } else {
            System.out.println("DocGia_Stats: Nothing exported!");
        }
    }
    
    private void addDocGia() {
        String tenTaiKhoan = tfTenTaiKhoan.getText().trim();
        String matKhau = tfMatKhau.getText().trim();
        String hoTen = tfHoTen.getText().trim();
        String gioiTinh = tfGioiTinh.getText().trim();
        String ngaySinh = tfNgaySinh.getText().trim();
        String diaChi = tfDiaChi.getText().trim();
        String soDienThoai = tfSoDienThoai.getText().trim();
        String email = tfEmail.getText().trim();
        
        connectDocGia.addData(new DocGia(tenTaiKhoan, matKhau, hoTen, gioiTinh, ngaySinh, diaChi, soDienThoai, email));
        
        loadAllDocGia();
    }
    
    private void addDocGiaFromFile() throws Exception {
        JFileChooser fc = new JFileChooser();
        int choice = fc.showOpenDialog(null);
        if (choice == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            String fileName = f.getAbsolutePath();
            System.out.println(fileName);
            Workbook wb = Workbook.getWorkbook(f);
            Sheet sh = wb.getSheet(0);
            int row = sh.getRows();
            int col = sh.getColumns();
            for (int i = 1; i < row; i++) {
                String tenTaiKhoan = sh.getCell(0, i).getContents().trim();
                String matKhau = sh.getCell(1, i).getContents().trim();
                String hoTen = sh.getCell(2, i).getContents().trim();
                String gioiTinh = sh.getCell(3, i).getContents().trim();
                String ngaySinh = sh.getCell(4, i).getContents().trim();
                String diaChi = sh.getCell(5, i).getContents().trim();
                String soDienThoai = sh.getCell(6, i).getContents().trim();
                String email = sh.getCell(7, i).getContents().trim();

                connectDocGia.addData(new DocGia(tenTaiKhoan, matKhau, hoTen, gioiTinh, ngaySinh, diaChi, soDienThoai, email)); 
            }
            
            JOptionPane.showMessageDialog(null, "Thêm thành công", "Thêm độc giả từ tệp", JOptionPane.INFORMATION_MESSAGE);
            loadAllDocGia();
        }
    }
    
    private void editDocGia() {
        int maDocGia = Integer.parseInt(tfMaDocGia.getText().trim());
        String tenTaiKhoan = tfTenTaiKhoan.getText().trim();
        String matKhau = tfMatKhau.getText().trim();
        String hoTen = tfHoTen.getText().trim();
        String gioiTinh = tfGioiTinh.getText().trim();
        String ngaySinh = tfNgaySinh.getText().trim();
        String diaChi = tfDiaChi.getText().trim();
        String soDienThoai = tfSoDienThoai.getText().trim();
        String email = tfEmail.getText().trim();
        
        docGia = new DocGia(maDocGia, tenTaiKhoan, matKhau, hoTen, gioiTinh, ngaySinh, diaChi, soDienThoai, email);
        connectDocGia.updateByID(docGia);
        
        loadAllDocGia();
    }
    
    private void loadAllDocGia() {
        DefaultTableModel model = new DefaultTableModel();
        listDocGia.clear();
        
        ResultSet rs = connectDocGia.getData();
        try {
            // Load title for each column
            ResultSetMetaData rsMD = rs.getMetaData();
            int colNum = rsMD.getColumnCount();
            String[] arr = new String[colNum];
            for (int i=0; i<colNum; i++) {
                arr[i] = rsMD.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(arr);
            
            // Load data to table
            while (rs.next()) {
                listDocGia.add(new DocGia(
                        rs.getInt(1),    rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getString(7), rs.getString(8), rs.getString(9)));
                
                for (int i=0; i<colNum; i++) {
                    arr[i] = rs.getString(i + 1);
                }
                model.addRow(arr);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        docGiaTable.setModel(model);
    }
    
    private void clearDocGiaInputFields(boolean notInit) {
        docGiaInputFields.setBorder(BorderFactory.createTitledBorder(" "));
        tfMaDocGia.setEnabled(notInit);
        tfTenTaiKhoan.setEnabled(notInit);
        tfMatKhau.setEnabled(notInit);
        tfHoTen.setEnabled(notInit);
        tfGioiTinh.setEnabled(notInit);
        tfNgaySinh.setEnabled(notInit);
        tfDiaChi.setEnabled(notInit);
        tfSoDienThoai.setEnabled(notInit);
        tfEmail.setEnabled(notInit);
        
        docGiaBtnOK.setEnabled(notInit);
        docGiaBtnCancel.setEnabled(notInit);
        
        tfMaDocGia.setText("");
        tfTenTaiKhoan.setText("");
        tfMatKhau.setText("");
        tfHoTen.setText("");
        tfGioiTinh.setText("");
        tfNgaySinh.setText("");
        tfDiaChi.setText("");
        tfSoDienThoai.setText("");
        tfEmail.setText("");
    }
    
    
    /**
     * 
     * @section MuonTra
     * @functions searchMuonTra(), addMuonTra(), editMuonTra(), loadAllMuonTra()
     * @utilities clearMuonTraInputFields()
     */
    
    private void searchMuonTra() {
        DefaultTableModel model = new DefaultTableModel();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date x = dcNgayMuon.getDate();
        Date y = dcHanTra.getDate();
        Date z = dcNgayTra.getDate();
        
        String maMuon = tfMTMaMuon.getText().trim();
        String maDocGia = tfMTMaDocGia.getText().trim();
        String maSach = tfMTMaSach.getText().trim();
        String ngayMuon = (x != null) ? sdf.format(x).trim() : "";
        String hanTra = (y != null) ? sdf.format(y).trim() : "";
        String ngayTra = (z != null) ? sdf.format(z).trim() : "";
        String tienDatCoc = tfMTTienCoc.getText().trim();
        String tienPhat = tfMTTienPhat.getText().trim();
        String ghiChu = tfMTGhiChu.getText().trim();
        
        ResultSet rs = connectMuonTra.getQueryData(
                maMuon, maDocGia, maSach,
                ngayMuon, hanTra, ngayTra,
                tienDatCoc, tienPhat, ghiChu);
        try {
            // Load title for each column
            ResultSetMetaData rsMD = rs.getMetaData();
            int colNum = rsMD.getColumnCount();
            String[] arr = new String[colNum];
            for (int i=0; i<colNum; i++) {
                arr[i] = rsMD.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(arr);
            
            // Load data to table
            while (rs.next()) {
                for (int i=0; i<colNum; i++) {
                    arr[i] = rs.getString(i + 1);
                }
                model.addRow(arr);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        muonTraTable.setModel(model);
    }
    
    private void investigateMuonTraMaSachStat() {
        DefaultTableModel model = new DefaultTableModel();
        
        ResultSet rs = connectMuonTra.getMaSachStat();
        try {
            // Load title for each column
            ResultSetMetaData rsMD = rs.getMetaData();
            int colNum = rsMD.getColumnCount();
            String[] arr = new String[colNum];
            for (int i=0; i<colNum; i++) {
                arr[i] = rsMD.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(arr);
            
            // Load data to table
            while (rs.next()) {
                for (int i=0; i<colNum; i++) {
                    arr[i] = rs.getString(i + 1);
                }
                model.addRow(arr);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        muonTraTable.setModel(model);
    }
    
    private void investigateMuonTraNgayMuonStat() {
        DefaultTableModel model = new DefaultTableModel();
        
        ResultSet rs = connectMuonTra.getNgayMuonStat();
        try {
            // Load title for each column
            ResultSetMetaData rsMD = rs.getMetaData();
            int colNum = rsMD.getColumnCount();
            String[] arr = new String[colNum];
            for (int i=0; i<colNum; i++) {
                arr[i] = rsMD.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(arr);
            
            // Load data to table
            while (rs.next()) {
                for (int i=0; i<colNum; i++) {
                    arr[i] = rs.getString(i + 1);
                }
                model.addRow(arr);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        muonTraTable.setModel(model);
    }
    
    private void investigateMuonTraHanTraStat() {
        DefaultTableModel model = new DefaultTableModel();
        
        ResultSet rs = connectMuonTra.getHanTraStat();
        try {
            // Load title for each column
            ResultSetMetaData rsMD = rs.getMetaData();
            int colNum = rsMD.getColumnCount();
            String[] arr = new String[colNum];
            for (int i=0; i<colNum; i++) {
                arr[i] = rsMD.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(arr);
            
            // Load data to table
            while (rs.next()) {
                for (int i=0; i<colNum; i++) {
                    arr[i] = rs.getString(i + 1);
                }
                model.addRow(arr);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        muonTraTable.setModel(model);
    }
    
    private void investigateMuonTraNgayTraStat() {
        DefaultTableModel model = new DefaultTableModel();
        
        ResultSet rs = connectMuonTra.getNgayTraStat();
        try {
            // Load title for each column
            ResultSetMetaData rsMD = rs.getMetaData();
            int colNum = rsMD.getColumnCount();
            String[] arr = new String[colNum];
            for (int i=0; i<colNum; i++) {
                arr[i] = rsMD.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(arr);
            
            // Load data to table
            while (rs.next()) {
                for (int i=0; i<colNum; i++) {
                    arr[i] = rs.getString(i + 1);
                }
                model.addRow(arr);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        muonTraTable.setModel(model);
    }
    
    private void investigateMuonTraTraMuonStat() {
        DefaultTableModel model = new DefaultTableModel();
        
        ResultSet rs = connectMuonTra.getTraMuonStat();
        try {
            // Load title for each column
            ResultSetMetaData rsMD = rs.getMetaData();
            int colNum = rsMD.getColumnCount();
            String[] arr = new String[colNum];
            for (int i=0; i<colNum; i++) {
                arr[i] = rsMD.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(arr);
            
            // Load data to table
            while (rs.next()) {
                for (int i=0; i<colNum; i++) {
                    arr[i] = rs.getString(i + 1);
                }
                model.addRow(arr);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        muonTraTable.setModel(model);
    }
    
    private void investigateMuonTraDocGiaMuonStat() {
        DefaultTableModel model = new DefaultTableModel();
        
        ResultSet rs = connectMuonTra.getDocGiaMuonStat();
        try {
            // Load title for each column
            ResultSetMetaData rsMD = rs.getMetaData();
            int colNum = rsMD.getColumnCount();
            String[] arr = new String[colNum];
            for (int i=0; i<colNum; i++) {
                arr[i] = rsMD.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(arr);
            
            // Load data to table
            while (rs.next()) {
                for (int i=0; i<colNum; i++) {
                    arr[i] = rs.getString(i + 1);
                }
                model.addRow(arr);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        muonTraTable.setModel(model);
    }
    
    private void investigateMuonTraDocGiaDangMuonStat() {
        DefaultTableModel model = new DefaultTableModel();
        
        ResultSet rs = connectMuonTra.getDocGiaDangMuonStat();
        try {
            // Load title for each column
            ResultSetMetaData rsMD = rs.getMetaData();
            int colNum = rsMD.getColumnCount();
            String[] arr = new String[colNum];
            for (int i=0; i<colNum; i++) {
                arr[i] = rsMD.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(arr);
            
            // Load data to table
            while (rs.next()) {
                for (int i=0; i<colNum; i++) {
                    arr[i] = rs.getString(i + 1);
                }
                model.addRow(arr);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        muonTraTable.setModel(model);
    }
    
    private void investigateMuonTraDocGiaDaTraStat() {
        DefaultTableModel model = new DefaultTableModel();
        
        ResultSet rs = connectMuonTra.getDocGiaDaTraStat();
        try {
            // Load title for each column
            ResultSetMetaData rsMD = rs.getMetaData();
            int colNum = rsMD.getColumnCount();
            String[] arr = new String[colNum];
            for (int i=0; i<colNum; i++) {
                arr[i] = rsMD.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(arr);
            
            // Load data to table
            while (rs.next()) {
                for (int i=0; i<colNum; i++) {
                    arr[i] = rs.getString(i + 1);
                }
                model.addRow(arr);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        muonTraTable.setModel(model);
    }
    
    private void investigateMuonTraDocGiaTraMuonStat() {
        DefaultTableModel model = new DefaultTableModel();
        
        ResultSet rs = connectMuonTra.getDocGiaTraMuonStat();
        try {
            // Load title for each column
            ResultSetMetaData rsMD = rs.getMetaData();
            int colNum = rsMD.getColumnCount();
            String[] arr = new String[colNum];
            for (int i=0; i<colNum; i++) {
                arr[i] = rsMD.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(arr);
            
            // Load data to table
            while (rs.next()) {
                for (int i=0; i<colNum; i++) {
                    arr[i] = rs.getString(i + 1);
                }
                model.addRow(arr);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        muonTraTable.setModel(model);
    }
    
    private void investigateMuonTraDocGiaThanhToanStat() {
        DefaultTableModel model = new DefaultTableModel();
        
        ResultSet rs = connectMuonTra.getDocGiaThanhToanStat();
        try {
            // Load title for each column
            ResultSetMetaData rsMD = rs.getMetaData();
            int colNum = rsMD.getColumnCount();
            String[] arr = new String[colNum];
            for (int i=0; i<colNum; i++) {
                arr[i] = rsMD.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(arr);
            
            // Load data to table
            while (rs.next()) {
                for (int i=0; i<colNum; i++) {
                    arr[i] = rs.getString(i + 1);
                }
                model.addRow(arr);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        muonTraTable.setModel(model);
    }
    
    private void exportMuonTraSearchResult() throws Exception {
        Object[][] data = getTableData(muonTraTable);
        JFileChooser fc = new JFileChooser();
        
        int choice = fc.showSaveDialog(null);
        if (choice == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String fileName = file.getAbsolutePath();
            
            WritableWorkbook wb = Workbook.createWorkbook(file);
            WritableSheet sheet = wb.createSheet("Tim kiem muon tra", 0);
            
            // Header
            WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 14);
            titleFont.setBoldStyle(WritableFont.BOLD);
            WritableCellFormat titleFormat = new WritableCellFormat(titleFont);
            sheet.addCell(new Label(1,0,"THƯ VIỆN XYZ", titleFormat));
            sheet.addCell(new Label(1,2,"KẾT QUẢ TÌM KIẾM MƯỢN TRẢ", titleFormat));
            // Content
            sheet.setColumnView(0, 10);
            sheet.setColumnView(1, 10);
            sheet.setColumnView(2, 20);
            sheet.setColumnView(3, 12);
            sheet.setColumnView(4, 12);
            sheet.setColumnView(5, 12);
            sheet.setColumnView(6, 10);
            sheet.setColumnView(7, 10);
            sheet.setColumnView(8, 20);
            int r0 = 4;
            int c0 = 0;
            WritableCellFormat cellFormat = new WritableCellFormat();
            cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            for (int row = r0, i = 0; row < data.length + r0; ++row, ++i) {
                for (int col = c0, j = 0; col < data[0].length + c0; ++col, ++j) {
                    sheet.addCell(new Label(col, row, (String) data[i][j], cellFormat));
                }
            }
            // Footer
            WritableFont footerFont = new WritableFont(WritableFont.ARIAL, 12);
            footerFont.setBoldStyle(WritableFont.BOLD);
            WritableCellFormat footerFormat = new WritableCellFormat(footerFont);
            SimpleDateFormat sdfClock = new SimpleDateFormat("HH:mm");
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
            String tenThuThu = DangNhap.thuThu != null ? DangNhap.thuThu.getTenThuThu() : "XYZ";
            java.util.Date currentDay = new java.util.Date();
            sheet.addCell(new Label(6, data.length + r0 + 1,
                    "Hà Nội, " + sdfClock.format(currentDay) + " ngày " + sdfDate.format(currentDay), footerFormat));
            sheet.addCell(new Label(6, data.length + r0 + 3, "Người lập: " + tenThuThu, footerFormat));
            
            // Done and out
            wb.write();
            wb.close();
            
            JOptionPane.showMessageDialog(null, "Xuất thành công!", "Xuất kết quả tìm kiếm", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("MuonTra_Search: File exported!");
        } else {
            System.out.println("MuonTra_Search: Nothing exported!");
        }
    }
    
    private void exportMuonTraStatsResult() throws Exception {
        Object[][] data = getTableData(muonTraTable);
        JFileChooser fc = new JFileChooser();
        
        int choice = fc.showSaveDialog(null);
        if (choice == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String fileName = file.getAbsolutePath();
            
            WritableWorkbook wb = Workbook.createWorkbook(file);
            WritableSheet sheet = wb.createSheet("Thong ke muon tra", 0);
            
            // Header
            WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 14);
            titleFont.setBoldStyle(WritableFont.BOLD);
            WritableCellFormat titleFormat = new WritableCellFormat(titleFont);
            sheet.addCell(new Label(0,0,"THƯ VIỆN XYZ", titleFormat));
            sheet.addCell(new Label(0,2,"KẾT QUẢ THỐNG KÊ MƯỢN TRẢ", titleFormat));
            // Content
            sheet.setColumnView(0, 25);
            sheet.setColumnView(1, 25);
            if (data[0].length > 2) {
                sheet.setColumnView(2, 25);
                sheet.setColumnView(3, 25);
            }
            int r0 = 4;
            int c0 = 0;
            WritableCellFormat cellFormat = new WritableCellFormat();
            cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            for (int row = r0, i = 0; row < data.length + r0; ++row, ++i) {
                for (int col = c0, j = 0; col < data[0].length + c0; ++col, ++j) {
                    sheet.addCell(new Label(col, row, (String) data[i][j], cellFormat));
                }
            }
            // Footer
            WritableFont footerFont = new WritableFont(WritableFont.ARIAL, 12);
            footerFont.setBoldStyle(WritableFont.BOLD);
            WritableCellFormat footerFormat = new WritableCellFormat(footerFont);
            SimpleDateFormat sdfClock = new SimpleDateFormat("HH:mm");
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
            String tenThuThu = DangNhap.thuThu != null ? DangNhap.thuThu.getTenThuThu() : "XYZ";
            java.util.Date currentDay = new java.util.Date();
            sheet.addCell(new Label(0, data.length + r0 + 1,
                    "Hà Nội, " + sdfClock.format(currentDay) + " ngày " + sdfDate.format(currentDay), footerFormat));
            sheet.addCell(new Label(0, data.length + r0 + 3, "Người lập: " + tenThuThu, footerFormat));
            
            // Done and out
            wb.write();
            wb.close();
            
            JOptionPane.showMessageDialog(null, "Xuất thành công!", "Xuất kết quả thống kê", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("MuonTra_Stats: File exported!");
        } else {
            System.out.println("MuonTra_Stats: Nothing exported!");
        }
    }
    
    private void exportOneMuonTra() throws Exception {
        JFileChooser fc = new JFileChooser();
        // Load data
        int selectedRow = muonTraTable.getSelectedRow();
        String maMuon = (String) muonTraTable.getValueAt(selectedRow, 0);
        String maDocGia = (String) muonTraTable.getValueAt(selectedRow, 1);
        String tenDocGia = "";
        for (DocGia i : listDocGia) {
            if (i.getMaDocGia() == Integer.parseInt(maDocGia)) {
                tenDocGia = i.getHoTen();
                break;
            }
        }
        String[] preMaSach = ((String) muonTraTable.getValueAt(selectedRow, 2)).trim().split(",");
        Object[][] data = new Object[preMaSach.length + 1][2];
        data[0][0] = "maSach"; data[0][1] = "tenSach";
        for (int i = 1; i < preMaSach.length + 1; i++) {
            data[i][0] = preMaSach[i - 1].trim();
            for (Sach j : listSach) {
                if (j.getMaSach() == Integer.parseInt((String) data[i][0])) {
                    data[i][1] = j.getTenSach();
                    break;
                }
            }
        }
        String ngayMuon = (String) muonTraTable.getValueAt(selectedRow, 3);
        String hanTra = (String) muonTraTable.getValueAt(selectedRow, 4);
        String ngayTra = (muonTraTable.getValueAt(selectedRow, 5) == null)
                ? ""
                : (String) muonTraTable.getValueAt(selectedRow, 5);
        String tienCoc = (String) muonTraTable.getValueAt(selectedRow, 6);
        String tienPhat = (String) muonTraTable.getValueAt(selectedRow, 7);
        String ghiChu = (String) muonTraTable.getValueAt(selectedRow, 8);
        
        int choice = fc.showSaveDialog(null);
        if (choice == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String fileName = file.getAbsolutePath();
            
            WritableWorkbook wb = Workbook.createWorkbook(file);
            WritableSheet sheet = wb.createSheet("Phieu muon", 0);
            
            // Header
            WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 14);
            titleFont.setBoldStyle(WritableFont.BOLD);
            WritableCellFormat titleFormat = new WritableCellFormat(titleFont);
            WritableFont fieldFont = new WritableFont(WritableFont.ARIAL, 10);
            fieldFont.setBoldStyle(WritableFont.BOLD);
            WritableCellFormat fieldFormat = new WritableCellFormat(fieldFont);
            sheet.addCell(new Label(0,0,"THƯ VIỆN XYZ", titleFormat));
            sheet.addCell(new Label(0,2,"PHIẾU MƯỢN", titleFormat));
            sheet.addCell(new Label(0,4,"Mã phiếu: " + maMuon, fieldFormat));
            sheet.addCell(new Label(0,5,"Mã độc giả: " + maDocGia, fieldFormat));
            sheet.addCell(new Label(0,6,"Tên độc giả: " + tenDocGia, fieldFormat));
            sheet.addCell(new Label(0,7,"Ngày mượn: " + ngayMuon, fieldFormat));
            sheet.addCell(new Label(0,8,"Hạn trả: " + hanTra, fieldFormat));
            sheet.addCell(new Label(0,9,"Ngày trả: " + ngayTra, fieldFormat));
            sheet.addCell(new Label(0,10,"Tiền đặt cọc: " + tienCoc, fieldFormat));
            sheet.addCell(new Label(0,11,"Tiền phạt: " + tienPhat, fieldFormat));
            sheet.addCell(new Label(0,12,"Ghi chú: " + ghiChu, fieldFormat));
            sheet.addCell(new Label(0,14,"Danh sách các sách mượn: ", fieldFormat));
            // Content
            sheet.setColumnView(0, 10);
            sheet.setColumnView(1, 35);
            int r0 = 16;
            int c0 = 0;
            WritableCellFormat cellFormat = new WritableCellFormat();
            cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            for (int row = r0, i = 0; row < data.length + r0; ++row, ++i) {
                for (int col = c0, j = 0; col < data[0].length + c0; ++col, ++j) {
                    sheet.addCell(new Label(col, row, (String) data[i][j], cellFormat));
                }
            }
            // Footer
            WritableFont footerFont = new WritableFont(WritableFont.ARIAL, 12);
            footerFont.setBoldStyle(WritableFont.BOLD);
            WritableCellFormat footerFormat = new WritableCellFormat(footerFont);
            SimpleDateFormat sdfClock = new SimpleDateFormat("HH:mm");
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
            String tenThuThu = DangNhap.thuThu != null ? DangNhap.thuThu.getTenThuThu() : "XYZ";
            java.util.Date currentDay = new java.util.Date();
            sheet.addCell(new Label(0, data.length + r0 + 1,
                    "Hà Nội, " + sdfClock.format(currentDay) + " ngày " + sdfDate.format(currentDay), footerFormat));
            sheet.addCell(new Label(0, data.length + r0 + 3, "Người lập: " + tenThuThu, footerFormat));
            
            // Done and out
            wb.write();
            wb.close();
            
            JOptionPane.showMessageDialog(null, "Xuất thành công!", "Xuất phiếu mượn trả", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("MuonTra_Bill: File exported!");
        } else {
            System.out.println("MuonTra_Bill: Nothing exported!");
        }
    }
    
    private void addMuonTra() {
        Date x = dcNgayMuon.getDate();
        Date y = dcHanTra.getDate();
        
        int maDocGia = Integer.parseInt(tfMTMaDocGia.getText().trim());
//        int maSach = Integer.parseInt(tfMTMaSach.getText().trim());
        ArrayList<Integer> maSach = new ArrayList<Integer>();
        String[] preMaSach = tfMTMaSach.getText().trim().split(",");
        for (String i : preMaSach) {
            maSach.add(Integer.valueOf(i));
        }
        java.sql.Date ngayMuon = new java.sql.Date(x.getTime());
        java.sql.Date hanTra = new java.sql.Date(y.getTime());
        String tienDatCoc = tfMTTienCoc.getText().trim();
        
        connectMuonTra.addData(new MuonTra(maDocGia, maSach, ngayMuon, hanTra, tienDatCoc));
        
        loadAllMuonTra();
    }
    
    private void editMuonTra() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date x = dcNgayMuon.getDate();
        Date y = dcHanTra.getDate();
        Date z = dcNgayTra.getDate();
        
        int maMuon = Integer.parseInt(tfMTMaMuon.getText().trim());
        int maDocGia = Integer.parseInt(tfMTMaDocGia.getText().trim());
//        int maSach = Integer.parseInt(tfMTMaSach.getText().trim());
        ArrayList<Integer> maSach = new ArrayList<Integer>();
        String[] preMaSach = tfMTMaSach.getText().trim().split(",");
        for (String i : preMaSach) {
            maSach.add(Integer.valueOf(i.trim()));
        }
        java.sql.Date ngayMuon = new java.sql.Date(x.getTime());
        java.sql.Date hanTra = new java.sql.Date(y.getTime());
        java.sql.Date ngayTra = (z != null) ? new java.sql.Date(z.getTime()) : null;
        String tienDatCoc = tfMTTienCoc.getText().trim();
        String tienPhat = tfMTTienPhat.getText().trim();
        if (ngayTra != null && ngayTra.getTime() > hanTra.getTime()) {
            long diff = ngayTra.getTime() - hanTra.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);
            // Tra muon: 500d / ngay / quyen
            tienPhat = Long.toString(diffDays * 500 * maSach.size());
        }
        String ghiChu = tfMTGhiChu.getText().trim();
        
        connectMuonTra.updateByID(new MuonTra(
                maMuon, maDocGia, maSach,
                ngayMuon, hanTra, ngayTra,
                tienDatCoc, tienPhat, ghiChu));
        
        loadAllMuonTra();
    }
    
    private void loadAllMuonTra() {
        DefaultTableModel model = new DefaultTableModel();
        
        ResultSet rs = connectMuonTra.getData();
        try {
            // Load title for each column
            ResultSetMetaData rsMD = rs.getMetaData();
            int colNum = rsMD.getColumnCount();
            String[] arr = new String[colNum];
            for (int i=0; i<colNum; i++) {
                arr[i] = rsMD.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(arr);
            
            // Load data to table
            while (rs.next()) {
                for (int i=0; i<colNum; i++) {
                    arr[i] = rs.getString(i + 1);
                }
                model.addRow(arr);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        
        muonTraTable.setModel(model);
    }
    
    private void clearMuonTraInputFields(boolean notInit) {
        muonTraInputFields.setBorder(BorderFactory.createTitledBorder(" "));
        tfMTMaMuon.setEnabled(notInit);
        tfMTMaDocGia.setEnabled(notInit);
        tfMTMaSach.setEnabled(notInit);
        dcNgayMuon.setEnabled(notInit);
        dcHanTra.setEnabled(notInit);
        dcNgayTra.setEnabled(notInit);
        tfMTTienCoc.setEnabled(notInit);
        tfMTTienPhat.setEnabled(notInit);
        tfMTGhiChu.setEnabled(notInit);
        
        muonTraBtnOK.setEnabled(notInit);
        muonTraBtnCancel.setEnabled(notInit);
        chooseDocGiaBtn.setEnabled(notInit);
        chooseSachBtn.setEnabled(notInit);
        
        tfMTMaMuon.setText("");
        tfMTMaDocGia.setText("");
        tfMTMaSach.setText("");
        dcNgayMuon.setDate(null);
        dcHanTra.setDate(null);
        dcNgayTra.setDate(null);
        tfMTTienCoc.setText("");
        tfMTTienPhat.setText("");
        tfMTGhiChu.setText("");
        
        if (!notInit) {
            isAddingMuonTra = notInit;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        change = new javax.swing.JPanel();
        greeting = new javax.swing.JPanel();
        welcomeMsg = new javax.swing.JLabel();
        btnThuThuLogOut = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        quanLySachPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        sachTable = new javax.swing.JTable();
        sachBtns = new javax.swing.JPanel();
        btnSearchSach = new javax.swing.JButton();
        btnGetStatSach = new javax.swing.JButton();
        btnAddSach = new javax.swing.JButton();
        btnAddSachFromFile = new javax.swing.JButton();
        btnEditSach = new javax.swing.JButton();
        btnDeleteSach = new javax.swing.JButton();
        sachInputFields = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tfMaSach = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tfTenSach = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tfTacGia = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tfNXB = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tfTheLoai = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        tfNamXB = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        spnSoLuongSach = new javax.swing.JSpinner();
        sachBtnOK = new javax.swing.JButton();
        sachBtnCancel = new javax.swing.JButton();
        quanLyDocGiaPanel = new javax.swing.JPanel();
        docGiaInputFields = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        tfMaDocGia = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        tfTenTaiKhoan = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        tfMatKhau = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        tfDiaChi = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        tfSoDienThoai = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        tfEmail = new javax.swing.JTextField();
        tfHoTen = new javax.swing.JTextField();
        tfGioiTinh = new javax.swing.JTextField();
        tfNgaySinh = new javax.swing.JTextField();
        docGiaInputFieldsBtn = new javax.swing.JPanel();
        docGiaBtnOK = new javax.swing.JButton();
        docGiaBtnCancel = new javax.swing.JButton();
        docGiaBtns = new javax.swing.JPanel();
        btnSearchDocGia = new javax.swing.JButton();
        btnGetStatDocGia = new javax.swing.JButton();
        btnAddDocGia = new javax.swing.JButton();
        btnAddDocGiaFromFile = new javax.swing.JButton();
        btnEditDocGia = new javax.swing.JButton();
        btnDeleteDocGia = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        docGiaTable = new javax.swing.JTable();
        quanLyMuonTraPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        muonTraTable = new javax.swing.JTable();
        muonTraBtns = new javax.swing.JPanel();
        btnSearchMuonTra = new javax.swing.JButton();
        btnGetStatMuonTra = new javax.swing.JButton();
        btnAddMuonTra = new javax.swing.JButton();
        btnUpdateMuonTra = new javax.swing.JButton();
        btnPrintOneMuonTra = new javax.swing.JButton();
        muonTraInputFields = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        tfMTMaMuon = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        tfMTMaDocGia = new javax.swing.JTextField();
        tfMTMaSach = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        dcNgayMuon = new com.toedter.calendar.JDateChooser();
        dcHanTra = new com.toedter.calendar.JDateChooser();
        dcNgayTra = new com.toedter.calendar.JDateChooser();
        tfMTTienCoc = new javax.swing.JTextField();
        tfMTTienPhat = new javax.swing.JTextField();
        tfMTGhiChu = new javax.swing.JTextField();
        muonTraBtnOK = new javax.swing.JButton();
        muonTraBtnCancel = new javax.swing.JButton();
        chooseDocGiaBtn = new javax.swing.JButton();
        chooseSachBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản lý thư viện XYZ");
        setIconImage((new javax.swing.ImageIcon(getClass().getResource("/Icon/library.png"))).getImage());
        setResizable(false);

        change.setBackground(new java.awt.Color(255, 255, 204));
        change.setLayout(null);

        greeting.setBackground(new java.awt.Color(255, 255, 204));
        greeting.setOpaque(false);

        welcomeMsg.setBackground(new java.awt.Color(249, 249, 219));
        welcomeMsg.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        welcomeMsg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Librarian/books.png"))); // NOI18N
        welcomeMsg.setText("  Xin chào, XYZ  ");
        welcomeMsg.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        welcomeMsg.setOpaque(true);

        btnThuThuLogOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Librarian/logout.png"))); // NOI18N
        btnThuThuLogOut.setText(" Đăng xuất");
        btnThuThuLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThuThuLogOutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout greetingLayout = new javax.swing.GroupLayout(greeting);
        greeting.setLayout(greetingLayout);
        greetingLayout.setHorizontalGroup(
            greetingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(greetingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(welcomeMsg)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 599, Short.MAX_VALUE)
                .addComponent(btnThuThuLogOut)
                .addGap(32, 32, 32))
        );
        greetingLayout.setVerticalGroup(
            greetingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(greetingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(greetingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(welcomeMsg)
                    .addComponent(btnThuThuLogOut))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        change.add(greeting);
        greeting.setBounds(10, 11, 946, 90);

        sachTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(sachTable);

        sachBtns.setLayout(new java.awt.GridLayout(1, 0));

        btnSearchSach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Librarian/search.png"))); // NOI18N
        btnSearchSach.setText(" Tìm kiếm");
        btnSearchSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchSachActionPerformed(evt);
            }
        });
        sachBtns.add(btnSearchSach);

        btnGetStatSach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Librarian/stats.png"))); // NOI18N
        btnGetStatSach.setText(" Thống kê");
        btnGetStatSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetStatSachActionPerformed(evt);
            }
        });
        sachBtns.add(btnGetStatSach);

        btnAddSach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Librarian/add.png"))); // NOI18N
        btnAddSach.setText(" Thêm");
        btnAddSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSachActionPerformed(evt);
            }
        });
        sachBtns.add(btnAddSach);

        btnAddSachFromFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Librarian/add-file.png"))); // NOI18N
        btnAddSachFromFile.setText(" Thêm từ tệp");
        btnAddSachFromFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSachFromFileActionPerformed(evt);
            }
        });
        sachBtns.add(btnAddSachFromFile);

        btnEditSach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Librarian/edit.png"))); // NOI18N
        btnEditSach.setText(" Sửa");
        btnEditSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditSachActionPerformed(evt);
            }
        });
        sachBtns.add(btnEditSach);

        btnDeleteSach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Librarian/delete.png"))); // NOI18N
        btnDeleteSach.setText(" Xoá");
        btnDeleteSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteSachActionPerformed(evt);
            }
        });
        sachBtns.add(btnDeleteSach);

        sachInputFields.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel2.setText("Mã sách");

        jLabel3.setText("Tên sách");

        jLabel4.setText("Tác giả");

        jLabel5.setText("Nhà xuất bản");

        jLabel6.setText("Thể loại");

        jLabel7.setText("Năm xuất bản");

        jLabel8.setText("Số lượng");

        sachBtnOK.setText("OK");
        sachBtnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sachBtnOKActionPerformed(evt);
            }
        });

        sachBtnCancel.setText("Cancel");
        sachBtnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sachBtnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout sachInputFieldsLayout = new javax.swing.GroupLayout(sachInputFields);
        sachInputFields.setLayout(sachInputFieldsLayout);
        sachInputFieldsLayout.setHorizontalGroup(
            sachInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sachInputFieldsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sachInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(31, 31, 31)
                .addGroup(sachInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfMaSach, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                    .addComponent(tfTenSach)
                    .addComponent(tfTacGia))
                .addGap(55, 55, 55)
                .addGroup(sachInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(25, 25, 25)
                .addGroup(sachInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sachInputFieldsLayout.createSequentialGroup()
                        .addComponent(tfNXB)
                        .addGap(59, 59, 59)
                        .addComponent(jLabel8))
                    .addComponent(tfTheLoai, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                    .addComponent(tfNamXB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(sachInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(spnSoLuongSach)
                    .addComponent(sachBtnOK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sachBtnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
                .addContainerGap(65, Short.MAX_VALUE))
        );

        sachInputFieldsLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {tfMaSach, tfNXB, tfNamXB, tfTheLoai});

        sachInputFieldsLayout.setVerticalGroup(
            sachInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sachInputFieldsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sachInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(tfNXB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(spnSoLuongSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(sachInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tfTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(tfTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sachBtnOK))
                .addGap(18, 18, 18)
                .addGroup(sachInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tfTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(tfNamXB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sachBtnCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        sachInputFieldsLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {spnSoLuongSach, tfNXB});

        javax.swing.GroupLayout quanLySachPanelLayout = new javax.swing.GroupLayout(quanLySachPanel);
        quanLySachPanel.setLayout(quanLySachPanelLayout);
        quanLySachPanelLayout.setHorizontalGroup(
            quanLySachPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(quanLySachPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(quanLySachPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(sachBtns, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sachInputFields, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        quanLySachPanelLayout.setVerticalGroup(
            quanLySachPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(quanLySachPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sachBtns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sachInputFields, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(63, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Quản lí sách", quanLySachPanel);

        docGiaInputFields.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel9.setText("Mã ĐG");

        jLabel10.setText("Tên tài khoản");

        jLabel11.setText("Mật khẩu");

        jLabel12.setText("Họ tên");

        jLabel13.setText("Giới tính");

        jLabel14.setText("Ngày sinh");

        jLabel15.setText("Địa chỉ");

        jLabel16.setText("SĐT");

        jLabel17.setText("Email");

        docGiaInputFieldsBtn.setLayout(new java.awt.GridLayout(1, 0));

        docGiaBtnOK.setText("OK");
        docGiaBtnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                docGiaBtnOKActionPerformed(evt);
            }
        });
        docGiaInputFieldsBtn.add(docGiaBtnOK);

        docGiaBtnCancel.setText("Cancel");
        docGiaBtnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                docGiaBtnCancelActionPerformed(evt);
            }
        });
        docGiaInputFieldsBtn.add(docGiaBtnCancel);

        javax.swing.GroupLayout docGiaInputFieldsLayout = new javax.swing.GroupLayout(docGiaInputFields);
        docGiaInputFields.setLayout(docGiaInputFieldsLayout);
        docGiaInputFieldsLayout.setHorizontalGroup(
            docGiaInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(docGiaInputFieldsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(docGiaInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addGap(31, 31, 31)
                .addGroup(docGiaInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(docGiaInputFieldsLayout.createSequentialGroup()
                        .addComponent(tfMaDocGia, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(jLabel12))
                    .addGroup(docGiaInputFieldsLayout.createSequentialGroup()
                        .addGroup(docGiaInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(tfTenTaiKhoan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(tfMatKhau))
                        .addGap(44, 44, 44)
                        .addGroup(docGiaInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14))))
                .addGap(36, 36, 36)
                .addGroup(docGiaInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addGroup(docGiaInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17))
                .addGap(42, 42, 42)
                .addGroup(docGiaInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfEmail)
                    .addComponent(tfSoDienThoai)
                    .addComponent(tfDiaChi)
                    .addComponent(docGiaInputFieldsBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        docGiaInputFieldsLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {tfDiaChi, tfEmail, tfGioiTinh, tfHoTen, tfMaDocGia, tfMatKhau, tfNgaySinh, tfSoDienThoai, tfTenTaiKhoan});

        docGiaInputFieldsLayout.setVerticalGroup(
            docGiaInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(docGiaInputFieldsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(docGiaInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(tfMaDocGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel15)
                    .addComponent(tfDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(docGiaInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(tfTenTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel16)
                    .addComponent(tfSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(docGiaInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(tfMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel17)
                    .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(docGiaInputFieldsBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        docGiaBtns.setLayout(new java.awt.GridLayout(1, 0));

        btnSearchDocGia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Librarian/search.png"))); // NOI18N
        btnSearchDocGia.setText(" Tìm kiếm");
        btnSearchDocGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchDocGiaActionPerformed(evt);
            }
        });
        docGiaBtns.add(btnSearchDocGia);

        btnGetStatDocGia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Librarian/stats.png"))); // NOI18N
        btnGetStatDocGia.setText(" Thống kê");
        btnGetStatDocGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetStatDocGiaActionPerformed(evt);
            }
        });
        docGiaBtns.add(btnGetStatDocGia);

        btnAddDocGia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Librarian/add.png"))); // NOI18N
        btnAddDocGia.setText(" Thêm");
        btnAddDocGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDocGiaActionPerformed(evt);
            }
        });
        docGiaBtns.add(btnAddDocGia);

        btnAddDocGiaFromFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Librarian/add-file.png"))); // NOI18N
        btnAddDocGiaFromFile.setText(" Thêm từ tệp");
        btnAddDocGiaFromFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDocGiaFromFileActionPerformed(evt);
            }
        });
        docGiaBtns.add(btnAddDocGiaFromFile);

        btnEditDocGia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Librarian/edit.png"))); // NOI18N
        btnEditDocGia.setText(" Sửa");
        btnEditDocGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditDocGiaActionPerformed(evt);
            }
        });
        docGiaBtns.add(btnEditDocGia);

        btnDeleteDocGia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Librarian/delete.png"))); // NOI18N
        btnDeleteDocGia.setText("Xoá");
        btnDeleteDocGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteDocGiaActionPerformed(evt);
            }
        });
        docGiaBtns.add(btnDeleteDocGia);

        docGiaTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(docGiaTable);

        javax.swing.GroupLayout quanLyDocGiaPanelLayout = new javax.swing.GroupLayout(quanLyDocGiaPanel);
        quanLyDocGiaPanel.setLayout(quanLyDocGiaPanelLayout);
        quanLyDocGiaPanelLayout.setHorizontalGroup(
            quanLyDocGiaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(quanLyDocGiaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(quanLyDocGiaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(docGiaBtns, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(docGiaInputFields, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        quanLyDocGiaPanelLayout.setVerticalGroup(
            quanLyDocGiaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(quanLyDocGiaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(docGiaBtns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(docGiaInputFields, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Quản lí độc giả", quanLyDocGiaPanel);

        muonTraTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(muonTraTable);

        muonTraBtns.setLayout(new java.awt.GridLayout(1, 0));

        btnSearchMuonTra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Librarian/search.png"))); // NOI18N
        btnSearchMuonTra.setText(" Tìm kiếm");
        btnSearchMuonTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchMuonTraActionPerformed(evt);
            }
        });
        muonTraBtns.add(btnSearchMuonTra);

        btnGetStatMuonTra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Librarian/stats.png"))); // NOI18N
        btnGetStatMuonTra.setText(" Thống kê");
        btnGetStatMuonTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetStatMuonTraActionPerformed(evt);
            }
        });
        muonTraBtns.add(btnGetStatMuonTra);

        btnAddMuonTra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Librarian/add.png"))); // NOI18N
        btnAddMuonTra.setText(" Thêm");
        btnAddMuonTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMuonTraActionPerformed(evt);
            }
        });
        muonTraBtns.add(btnAddMuonTra);

        btnUpdateMuonTra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Librarian/edit.png"))); // NOI18N
        btnUpdateMuonTra.setText(" Sửa thông tin");
        btnUpdateMuonTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateMuonTraActionPerformed(evt);
            }
        });
        muonTraBtns.add(btnUpdateMuonTra);

        btnPrintOneMuonTra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Librarian/printer.png"))); // NOI18N
        btnPrintOneMuonTra.setText(" In phiếu mượn");
        btnPrintOneMuonTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintOneMuonTraActionPerformed(evt);
            }
        });
        muonTraBtns.add(btnPrintOneMuonTra);

        muonTraInputFields.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel18.setText("Mã MT");

        jLabel19.setText("Mã ĐG");

        jLabel20.setText("Mã sách");

        tfMTMaDocGia.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfMTMaDocGiaFocusLost(evt);
            }
        });

        jLabel21.setText("Ngày mượn");

        jLabel22.setText("Hạn trả");

        jLabel23.setText("Ngày trả");

        jLabel24.setText("Tiền cọc");

        jLabel25.setText("Tiền phạt");

        jLabel26.setText("Ghi chú");

        muonTraBtnOK.setText("OK");
        muonTraBtnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                muonTraBtnOKActionPerformed(evt);
            }
        });

        muonTraBtnCancel.setText("Cancel");
        muonTraBtnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                muonTraBtnCancelActionPerformed(evt);
            }
        });

        chooseDocGiaBtn.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        chooseDocGiaBtn.setText("+");
        chooseDocGiaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseDocGiaBtnActionPerformed(evt);
            }
        });

        chooseSachBtn.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        chooseSachBtn.setText("+");
        chooseSachBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseSachBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout muonTraInputFieldsLayout = new javax.swing.GroupLayout(muonTraInputFields);
        muonTraInputFields.setLayout(muonTraInputFieldsLayout);
        muonTraInputFieldsLayout.setHorizontalGroup(
            muonTraInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(muonTraInputFieldsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(muonTraInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20))
                .addGap(39, 39, 39)
                .addGroup(muonTraInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfMTMaMuon, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                    .addGroup(muonTraInputFieldsLayout.createSequentialGroup()
                        .addGroup(muonTraInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfMTMaDocGia)
                            .addComponent(tfMTMaSach))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(muonTraInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(chooseSachBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(chooseDocGiaBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addGroup(muonTraInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jLabel21)
                    .addComponent(jLabel23))
                .addGap(34, 34, 34)
                .addGroup(muonTraInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dcHanTra, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dcNgayMuon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dcNgayTra, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(muonTraInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addComponent(jLabel24)
                    .addComponent(jLabel26))
                .addGap(25, 25, 25)
                .addGroup(muonTraInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfMTTienPhat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfMTTienCoc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfMTGhiChu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(muonTraInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(muonTraBtnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(muonTraBtnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37))
        );
        muonTraInputFieldsLayout.setVerticalGroup(
            muonTraInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(muonTraInputFieldsLayout.createSequentialGroup()
                .addGroup(muonTraInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(muonTraInputFieldsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(muonTraInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(muonTraInputFieldsLayout.createSequentialGroup()
                                .addGroup(muonTraInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(muonTraInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel18)
                                        .addComponent(tfMTMaMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel21)
                                        .addComponent(jLabel24)
                                        .addComponent(tfMTTienCoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(dcNgayMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(muonTraInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel19)
                                    .addComponent(tfMTMaDocGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel25)
                                    .addComponent(tfMTTienPhat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(chooseDocGiaBtn)))
                            .addComponent(dcHanTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(muonTraInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(muonTraInputFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel20)
                                .addComponent(tfMTMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel23)
                                .addComponent(jLabel26)
                                .addComponent(tfMTGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(chooseSachBtn))
                            .addComponent(dcNgayTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(muonTraInputFieldsLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(muonTraBtnOK)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(muonTraBtnCancel)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout quanLyMuonTraPanelLayout = new javax.swing.GroupLayout(quanLyMuonTraPanel);
        quanLyMuonTraPanel.setLayout(quanLyMuonTraPanelLayout);
        quanLyMuonTraPanelLayout.setHorizontalGroup(
            quanLyMuonTraPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(quanLyMuonTraPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(quanLyMuonTraPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 941, Short.MAX_VALUE)
                    .addComponent(muonTraBtns, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(muonTraInputFields, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        quanLyMuonTraPanelLayout.setVerticalGroup(
            quanLyMuonTraPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(quanLyMuonTraPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(muonTraBtns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(muonTraInputFields, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Quản lí mượn trả", quanLyMuonTraPanel);

        change.add(jTabbedPane1);
        jTabbedPane1.setBounds(0, 108, 966, 590);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Librarian/Background/libraries.jpg"))); // NOI18N
        change.add(jLabel1);
        jLabel1.setBounds(0, 0, 970, 130);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(change, javax.swing.GroupLayout.PREFERRED_SIZE, 964, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(change, javax.swing.GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void muonTraBtnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_muonTraBtnCancelActionPerformed
        // TODO add your handling code here:
        clearMuonTraInputFields(false);
    }//GEN-LAST:event_muonTraBtnCancelActionPerformed

    private void muonTraBtnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_muonTraBtnOKActionPerformed
        // TODO add your handling code here:
        int selected;
        switch (presentChoice) {
            case "#search":
                searchMuonTra();
                selected = JOptionPane.showConfirmDialog(null, "Xuất ra tệp?", "Xuất ra tệp", JOptionPane.YES_NO_OPTION);
                if (selected == JOptionPane.YES_OPTION) {
                    try {
                        exportMuonTraSearchResult();
                    } catch (Exception ex) {
                        System.err.println(ex.toString());
                    }
                } else {
                    System.out.println("MuonTra_Search: Nothing exported!");
                }
                break;
            case "#edit":
                editMuonTra();
                break;
            case "#add":
                addMuonTra();
                isAddingMuonTra = false;
                clearMuonTraInputFields(false);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_muonTraBtnOKActionPerformed

    private void tfMTMaDocGiaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfMTMaDocGiaFocusLost
        // TODO add your handling code here:
        String title;
        String tenDocGia = "";
        try {
            int maDocGia = Integer.parseInt(tfMTMaDocGia.getText().trim());
            for (DocGia dg : listDocGia) {
                if (dg.getMaDocGia() == maDocGia) {
                    tenDocGia = " - Độc giả: " + dg.getHoTen();
                    break;
                }
            }
        } catch (NumberFormatException ex) {
            tenDocGia = "";
        }

        switch (presentChoice) {
            case "#edit":
            title = "Sửa thông tin mượn trả ";
            muonTraInputFields.setBorder(BorderFactory.createTitledBorder(title + tenDocGia));
            break;
            case "#add":
            title = "Thêm phiếu mượn ";
            muonTraInputFields.setBorder(BorderFactory.createTitledBorder(title + tenDocGia));
            break;
        }
    }//GEN-LAST:event_tfMTMaDocGiaFocusLost

    private void btnUpdateMuonTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateMuonTraActionPerformed
        // TODO add your handling code here:
        clearMuonTraInputFields(false);
        int row = muonTraTable.getSelectedRow();
        if (row < 0) {
            // Display error
            JOptionPane.showMessageDialog(null, "Hãy chọn một hàng để sửa!", "Sửa thông tin mượn trả", JOptionPane.ERROR_MESSAGE);
            return ;
        } else {
            clearMuonTraInputFields(true);
            tfMTMaMuon.setEnabled(false);
            muonTraInputFields.setBorder(BorderFactory.createTitledBorder("Sửa thông tin mượn trả"));
            presentChoice = "#edit";

            try {
                String maMuon = (String) muonTraTable.getValueAt(row, 0);
                String maDocGia = (String) muonTraTable.getValueAt(row, 1);
                String maSach = (String) muonTraTable.getValueAt(row, 2);
                Date ngayMuon = new java.text.SimpleDateFormat(datePattern).parse((String) muonTraTable.getValueAt(row, 3));
                Date hanTra = new java.text.SimpleDateFormat(datePattern).parse((String) muonTraTable.getValueAt(row, 4));
                Date ngayTra = (muonTraTable.getValueAt(row, 5) == null) ?
                null :
                new java.text.SimpleDateFormat(datePattern).parse((String) muonTraTable.getValueAt(row, 5));
                String tienCoc = (String) muonTraTable.getValueAt(row, 6);
                String tienPhat = (String) muonTraTable.getValueAt(row, 7);
                String ghiChu = (String) muonTraTable.getValueAt(row, 8);

                tfMTMaMuon.setText(maMuon);
                tfMTMaDocGia.setText(maDocGia);
                tfMTMaSach.setText(maSach);
                dcNgayMuon.setDate(ngayMuon);
                dcHanTra.setDate(hanTra);
                dcNgayTra.setDate(ngayTra);
                tfMTTienCoc.setText(tienCoc);
                tfMTTienPhat.setText(tienPhat);
                tfMTGhiChu.setText(ghiChu);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnUpdateMuonTraActionPerformed

    private void btnAddMuonTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMuonTraActionPerformed
        // TODO add your handling code here:
        clearMuonTraInputFields(true);
        isAddingMuonTra = true;
        tfMTMaMuon.setEnabled(false);
        dcNgayTra.setEnabled(false);
        tfMTTienPhat.setEnabled(false);
        tfMTGhiChu.setEnabled(false);
        muonTraInputFields.setBorder(BorderFactory.createTitledBorder("Thêm phiếu mượn"));
        presentChoice = "#add";
    }//GEN-LAST:event_btnAddMuonTraActionPerformed

    private void btnGetStatMuonTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetStatMuonTraActionPerformed
        // TODO add your handling code here:
        Choice[] choices = {
            new Choice(1, "Mã sách"),
            new Choice(2, "Ngày mượn"),
            new Choice(3, "Hạn trả"),
            new Choice(4, "Ngày trả"),
            new Choice(5, "Trả muộn"),
            new Choice(6, "Độc giả (số sách mượn)"),
            new Choice(7, "Độc giả (số sách đang mượn)"),
            new Choice(8, "Độc giả (số sách đã trả)"),
            new Choice(9, "Độc giả (số sách trả muộn)"),
            new Choice(10, "Độc giả (số tiền thanh toán)")
        };
        Choice input = (Choice) JOptionPane.showInputDialog(null, "Thống kê theo",
            "Thống kê", JOptionPane.QUESTION_MESSAGE, null, choices ,choices[0]);
        if (input != null) {
            int selected;
            switch (input.getNo()) {
                case 1:
                    investigateMuonTraMaSachStat();
                    break;
                case 2:
                    investigateMuonTraNgayMuonStat();
                    break;
                case 3:
                    investigateMuonTraHanTraStat();
                    break;
                case 4:
                    investigateMuonTraNgayTraStat();
                    break;
                case 5:
                    investigateMuonTraTraMuonStat();
                    break;
                case 6:
                    investigateMuonTraDocGiaMuonStat();
                    break;
                case 7:
                    investigateMuonTraDocGiaDangMuonStat();
                    break;
                case 8:
                    investigateMuonTraDocGiaDaTraStat();
                    break;
                case 9:
                    investigateMuonTraDocGiaTraMuonStat();
                    break;
                case 10:
                    investigateMuonTraDocGiaThanhToanStat();
                    break;
            }
            selected = JOptionPane.showConfirmDialog(null, "Xuất ra tệp?", "Xuất ra tệp", JOptionPane.YES_NO_OPTION);
            if (selected == JOptionPane.YES_OPTION) {
                try {
                    exportMuonTraStatsResult();
                } catch (Exception ex) {
                    System.err.println(ex.toString());
                }
            } else {
                System.out.println("MuonTra_Stats: Nothing exported!");
            }
        } else {
            System.out.println("MuonTra_ThongKe: Aborted!");
        }
    }//GEN-LAST:event_btnGetStatMuonTraActionPerformed

    private void btnSearchMuonTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchMuonTraActionPerformed
        // TODO add your handling code here:
        clearMuonTraInputFields(true);
        tfMTTienCoc.setEnabled(false);
        tfMTTienPhat.setEnabled(false);
        muonTraInputFields.setBorder(BorderFactory.createTitledBorder("Tìm kiếm mượn trả"));
        presentChoice = "#search";
    }//GEN-LAST:event_btnSearchMuonTraActionPerformed

    private void btnDeleteDocGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteDocGiaActionPerformed
        // TODO add your handling code here:
        clearDocGiaInputFields(false);
        int row = docGiaTable.getSelectedRow();
        if (row < 0) {
            // Display error
            JOptionPane.showMessageDialog(null, "Hãy chọn một hàng để xoá!", "Xoá độc giả", JOptionPane.ERROR_MESSAGE);
            return ;
        }
        int selected = JOptionPane.showOptionDialog(null, "Bạn chắc chắn xoá chứ?", "Xoá",
            0, JOptionPane.YES_NO_OPTION, null, null, 1);
        if (selected == 0) {
            connectDocGia.deleteByID((String) docGiaTable.getValueAt(row, 0));
            loadAllDocGia();
        }

    }//GEN-LAST:event_btnDeleteDocGiaActionPerformed

    private void btnEditDocGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditDocGiaActionPerformed
        // TODO add your handling code here:
        clearDocGiaInputFields(false);
        int row = docGiaTable.getSelectedRow();
        if (row < 0) {
            // Display error
            JOptionPane.showMessageDialog(null, "Hãy chọn một hàng để sửa!", "Sửa thông tin độc giả", JOptionPane.ERROR_MESSAGE);
            return ;
        } else {
            clearDocGiaInputFields(true);
            tfMaDocGia.setEnabled(false);
            docGiaInputFields.setBorder(BorderFactory.createTitledBorder("Sửa thông tin độc giả"));
            presentChoice = "#edit";

            String maDocGia = (String) docGiaTable.getValueAt(row, 0);
            String tenTaiKhoan = (String) docGiaTable.getValueAt(row, 1);
            String matKhau = (String) docGiaTable.getValueAt(row, 2);
            String hoTen = (String) docGiaTable.getValueAt(row, 3);
            String gioiTinh = (String) docGiaTable.getValueAt(row, 4);
            String ngaySinh = (String) docGiaTable.getValueAt(row, 5);
            String diaChi = (String) docGiaTable.getValueAt(row, 6);
            String soDienThoai = (String) docGiaTable.getValueAt(row, 7);
            String email = (String) docGiaTable.getValueAt(row, 8);

            tfMaDocGia.setText(maDocGia);
            tfTenTaiKhoan.setText(tenTaiKhoan);
            tfMatKhau.setText(matKhau);
            tfHoTen.setText(hoTen);
            tfGioiTinh.setText(gioiTinh);
            tfNgaySinh.setText(ngaySinh);
            tfDiaChi.setText(diaChi);
            tfSoDienThoai.setText(soDienThoai);
            tfEmail.setText(email);

        }
    }//GEN-LAST:event_btnEditDocGiaActionPerformed

    private void btnAddDocGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDocGiaActionPerformed
        // TODO add your handling code here:
        clearDocGiaInputFields(true);
        tfMaDocGia.setEnabled(false);
        docGiaInputFields.setBorder(BorderFactory.createTitledBorder("Thêm độc giả"));
        presentChoice = "#add";

    }//GEN-LAST:event_btnAddDocGiaActionPerformed

    private void btnGetStatDocGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetStatDocGiaActionPerformed
        // TODO add your handling code here:
        Choice[] choices = {
            new Choice(1, "Địa chỉ"),
            new Choice(2, "Giới tính"),
            new Choice(3, "Tháng sinh"),
            new Choice(4, "Năm sinh")
        };
        Choice input = (Choice) JOptionPane.showInputDialog(null, "Thống kê theo",
            "Thống kê", JOptionPane.QUESTION_MESSAGE, null, choices ,choices[0]);
        if (input != null) {
            int selected;
            switch (input.getNo()) {
                case 1:
                    investigateDocGiaDiaChiStat();
                    break;
                case 2:
                    investigateDocGiaGioiTinhStat();
                    break;
                case 3:
                    investigateDocGiaThangSinhStat();
                    break;
                case 4:
                    investigateDocGiaNamSinhStat();
                    break;
            }
            selected = JOptionPane.showConfirmDialog(null, "Xuất ra tệp?", "Xuất ra tệp", JOptionPane.YES_NO_OPTION);
            if (selected == JOptionPane.YES_OPTION) {
                try {
                    exportDocGiaStatsResult();
                } catch (Exception ex) {
                    System.err.println(ex.toString());
                }
            } else {
                System.out.println("DocGia_Stats: Nothing exported!");
            }
        } else {
            System.out.println("DocGia_ThongKe: Aborted!");
        }
    }//GEN-LAST:event_btnGetStatDocGiaActionPerformed

    private void btnSearchDocGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchDocGiaActionPerformed
        // TODO add your handling code here:
        clearDocGiaInputFields(true);
        docGiaInputFields.setBorder(BorderFactory.createTitledBorder("Tìm kiếm độc giả"));
        presentChoice = "#search";
    }//GEN-LAST:event_btnSearchDocGiaActionPerformed

    private void docGiaBtnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_docGiaBtnCancelActionPerformed
        // TODO add your handling code here:
        clearDocGiaInputFields(false);
    }//GEN-LAST:event_docGiaBtnCancelActionPerformed

    private void docGiaBtnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_docGiaBtnOKActionPerformed
        // TODO add your handling code here:
        int selected;
        switch (presentChoice) {
            case "#search":
            searchDocGia();
            selected = JOptionPane.showConfirmDialog(null, "Xuất ra tệp?", "Xuất ra tệp", JOptionPane.YES_NO_OPTION);
            if (selected == JOptionPane.YES_OPTION) {
                try {
                    exportDocGiaSearchResult();
                } catch (Exception ex) {
                    System.err.println(ex.toString());
                }
            } else {
                System.out.println("DocGia_Search: Nothing exported!");
            }
            break;
            case "#edit":
            editDocGia();
            break;
            case "#add":
            addDocGia();
            clearDocGiaInputFields(false);
            break;
            default:
            break;
        }
    }//GEN-LAST:event_docGiaBtnOKActionPerformed

    private void sachBtnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sachBtnCancelActionPerformed
        // TODO add your handling code here:
        clearSachInputFields(false);
    }//GEN-LAST:event_sachBtnCancelActionPerformed

    private void sachBtnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sachBtnOKActionPerformed
        // TODO add your handling code here:
        int selected;
        switch (presentChoice) {
            case "#search":
                searchSach();
                selected = JOptionPane.showConfirmDialog(null, "Xuất ra tệp?", "Xuất ra tệp", JOptionPane.YES_NO_OPTION);
                if (selected == JOptionPane.YES_OPTION) {
                    try {
                        exportSachSearchResult();
                    } catch (Exception ex) {
                        System.err.println(ex.toString());
                    }
                } else {
                    System.out.println("Sach_Search: Nothing exported!");
                }
                break;
            case "#edit":
                selected = JOptionPane.showOptionDialog(null, "Bạn chắc chắn cập nhật thông tin chứ?", "Cập nhật thông tin cán bộ",
                        0, JOptionPane.YES_NO_OPTION, null, null, 1);
                if (selected == 0) {
                    editSach();
                }
                break;
            case "#add":
                addSach();
                clearSachInputFields(false);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_sachBtnOKActionPerformed

    private void btnDeleteSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteSachActionPerformed
        // TODO add your handling code here:
        clearSachInputFields(false);
        int row = sachTable.getSelectedRow();
        if (row < 0) {
            // Display error
            JOptionPane.showMessageDialog(null, "Hãy chọn một hàng để xoá!", "Xoá sách", JOptionPane.ERROR_MESSAGE);
            return ;
        }
        int selected = JOptionPane.showOptionDialog(null, "Bạn chắc chắn xoá chứ?", "Xoá",
            0, JOptionPane.YES_NO_OPTION, null, null, 1);
        if (selected == 0) {
            connectSach.deleteByID((String) sachTable.getValueAt(row, 0));
            loadAllSach();
        }
    }//GEN-LAST:event_btnDeleteSachActionPerformed

    private void btnEditSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditSachActionPerformed
        // TODO add your handling code here:
        clearSachInputFields(false);
        int row = sachTable.getSelectedRow();
        if (row < 0) {
            // Display error
            JOptionPane.showMessageDialog(null, "Hãy chọn một hàng để sửa!", "Sửa thông tin sách", JOptionPane.ERROR_MESSAGE);
            return ;
        } else {
            clearSachInputFields(true);
            tfMaSach.setEnabled(false);
            sachInputFields.setBorder(BorderFactory.createTitledBorder("Sửa thông tin sách"));
            presentChoice = "#edit";
            int maSach = Integer.parseInt((String) sachTable.getValueAt(row, 0));
            String tenSach = (String) sachTable.getValueAt(row, 1);
            String tacGia = (String) sachTable.getValueAt(row, 2);
            String nxb = (String) sachTable.getValueAt(row, 3);
            String theLoai = (String) sachTable.getValueAt(row, 4);
            String namXB = (String) sachTable.getValueAt(row, 5);
            int soLuong = Integer.parseInt((String) sachTable.getValueAt(row, 6));

            tfMaSach.setText(Integer.toString(maSach));
            tfTenSach.setText(tenSach);
            tfTacGia.setText(tacGia);
            tfNXB.setText(nxb);
            tfTheLoai.setText(theLoai);
            tfNamXB.setText(namXB);
            spnSoLuongSach.setValue(soLuong);
        }
    }//GEN-LAST:event_btnEditSachActionPerformed

    private void btnAddSachFromFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSachFromFileActionPerformed
        // TODO add your handling code here:
        try {
            addSachFromFile();
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }

        clearSachInputFields(false);
    }//GEN-LAST:event_btnAddSachFromFileActionPerformed

    private void btnAddSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSachActionPerformed
        // TODO add your handling code here:
        clearSachInputFields(true);
        tfMaSach.setEnabled(false);
        sachInputFields.setBorder(BorderFactory.createTitledBorder("Thêm sách"));
        presentChoice = "#add";

    }//GEN-LAST:event_btnAddSachActionPerformed

    private void btnGetStatSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetStatSachActionPerformed
        // TODO add your handling code here:
        Choice[] choices = {
            new Choice(1, "Tác giả"),
            new Choice(2, "Nhà xuất bản"),
            new Choice(3, "Thể loại sách"),
            new Choice(4, "Năm xuất bản"),
        };
        Choice input = (Choice) JOptionPane.showInputDialog(null, "Thống kê theo",
            "Thống kê", JOptionPane.QUESTION_MESSAGE, null, choices ,choices[0]);
        if (input != null) {
            int selected;
            switch (input.getNo()) {
                case 1:
                    investigateSachTacGiaStat();
                    break;
                case 2:
                    investigateSachNXBStat();
                    break;
                case 3:
                    investigateSachTheLoaiStat();
                    break;
                case 4:
                    investigateSachNamXBStat();
                    break;
            }
            selected = JOptionPane.showConfirmDialog(null, "Xuất ra tệp?", "Xuất ra tệp", JOptionPane.YES_NO_OPTION);
            if (selected == JOptionPane.YES_OPTION) {
                try {
                    exportSachStatsResult();
                } catch (Exception ex) {
                    System.err.println(ex.toString());
                }
            } else {
                System.out.println("Sach_Stats: Nothing exported!");
            }
        } else {
            System.out.println("Sach_ThongKe: Aborted!");
        }
    }//GEN-LAST:event_btnGetStatSachActionPerformed

    private void btnSearchSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchSachActionPerformed
        // TODO add your handling code here:
        clearSachInputFields(true);
        sachInputFields.setBorder(BorderFactory.createTitledBorder("Tìm kiếm sách"));
        presentChoice = "#search";
        spnSoLuongSach.setEnabled(false);
    }//GEN-LAST:event_btnSearchSachActionPerformed

    private void btnThuThuLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThuThuLogOutActionPerformed
        // TODO add your handling code here:
        if (DangNhap.thuThu != null) {
            int selected;
            selected = JOptionPane.showConfirmDialog(null, "Bạn có muốn đăng xuất?", "Thủ thư", JOptionPane.YES_NO_OPTION);
            if (selected == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "Xin chào bạn, hẹn gặp lại!", "Tạm biệt", JOptionPane.INFORMATION_MESSAGE);
                logOutThuThuSession();
            }
        } else {
            JOptionPane.showMessageDialog(null, "This is debug mode", "...", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnThuThuLogOutActionPerformed

    private void btnPrintOneMuonTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintOneMuonTraActionPerformed
        // TODO add your handling code here:
        clearMuonTraInputFields(false);
        int row = muonTraTable.getSelectedRow();
        if (row < 0) {
            // Display error
            JOptionPane.showMessageDialog(null, "Hãy chọn một lượt mượn trả!", "Xuất phiếu mượn", JOptionPane.ERROR_MESSAGE);
            return ;
        } else {
            if (muonTraTable.getColumnCount() != 9) {
                JOptionPane.showMessageDialog(null, "Hãy chọn lượt mượn trả hợp lệ!", "Xuất phiếu mượn", JOptionPane.ERROR_MESSAGE);
                return ;
            }
            int selected;
            selected = JOptionPane.showConfirmDialog(null, "Bạn muốn xuất ra tệp chứ?", "Xuất phiếu mượn", JOptionPane.YES_NO_OPTION);
            if (selected == JOptionPane.YES_OPTION) {
                try {
                    exportOneMuonTra();
                } catch (Exception ex) {
                    System.err.println(ex.toString());
                }
            } else {
                System.out.println("MuonTra_Bill: Nothing exported!");
            }
        }
    }//GEN-LAST:event_btnPrintOneMuonTraActionPerformed

    private void btnAddDocGiaFromFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDocGiaFromFileActionPerformed
        // TODO add your handling code here:
        try {
            addDocGiaFromFile();
        } catch (Exception ex) {
            System.err.println(ex.toString());
            ex.printStackTrace();
        }

        clearDocGiaInputFields(false);
    }//GEN-LAST:event_btnAddDocGiaFromFileActionPerformed

    private void chooseDocGiaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseDocGiaBtnActionPerformed
        // TODO add your handling code here:
        MUONTRA_CHOOSE_CHOICE = 0;
        searchAndChoose();
    }//GEN-LAST:event_chooseDocGiaBtnActionPerformed

    private void chooseSachBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseSachBtnActionPerformed
        // TODO add your handling code here:
        MUONTRA_CHOOSE_CHOICE = 1;
        searchAndChoose();
    }//GEN-LAST:event_chooseSachBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ThuThuUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThuThuUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThuThuUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThuThuUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ThuThuUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddDocGia;
    private javax.swing.JButton btnAddDocGiaFromFile;
    private javax.swing.JButton btnAddMuonTra;
    private javax.swing.JButton btnAddSach;
    private javax.swing.JButton btnAddSachFromFile;
    private javax.swing.JButton btnDeleteDocGia;
    private javax.swing.JButton btnDeleteSach;
    private javax.swing.JButton btnEditDocGia;
    private javax.swing.JButton btnEditSach;
    private javax.swing.JButton btnGetStatDocGia;
    private javax.swing.JButton btnGetStatMuonTra;
    private javax.swing.JButton btnGetStatSach;
    private javax.swing.JButton btnPrintOneMuonTra;
    private javax.swing.JButton btnSearchDocGia;
    private javax.swing.JButton btnSearchMuonTra;
    private javax.swing.JButton btnSearchSach;
    private javax.swing.JButton btnThuThuLogOut;
    private javax.swing.JButton btnUpdateMuonTra;
    private javax.swing.JPanel change;
    private javax.swing.JButton chooseDocGiaBtn;
    private javax.swing.JButton chooseSachBtn;
    private com.toedter.calendar.JDateChooser dcHanTra;
    private com.toedter.calendar.JDateChooser dcNgayMuon;
    private com.toedter.calendar.JDateChooser dcNgayTra;
    private javax.swing.JButton docGiaBtnCancel;
    private javax.swing.JButton docGiaBtnOK;
    private javax.swing.JPanel docGiaBtns;
    private javax.swing.JPanel docGiaInputFields;
    private javax.swing.JPanel docGiaInputFieldsBtn;
    private javax.swing.JTable docGiaTable;
    private javax.swing.JPanel greeting;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton muonTraBtnCancel;
    private javax.swing.JButton muonTraBtnOK;
    private javax.swing.JPanel muonTraBtns;
    private javax.swing.JPanel muonTraInputFields;
    private javax.swing.JTable muonTraTable;
    private javax.swing.JPanel quanLyDocGiaPanel;
    private javax.swing.JPanel quanLyMuonTraPanel;
    private javax.swing.JPanel quanLySachPanel;
    private javax.swing.JButton sachBtnCancel;
    private javax.swing.JButton sachBtnOK;
    private javax.swing.JPanel sachBtns;
    private javax.swing.JPanel sachInputFields;
    private javax.swing.JTable sachTable;
    private javax.swing.JSpinner spnSoLuongSach;
    private javax.swing.JTextField tfDiaChi;
    private javax.swing.JTextField tfEmail;
    private javax.swing.JTextField tfGioiTinh;
    private javax.swing.JTextField tfHoTen;
    private javax.swing.JTextField tfMTGhiChu;
    private javax.swing.JTextField tfMTMaDocGia;
    private javax.swing.JTextField tfMTMaMuon;
    private javax.swing.JTextField tfMTMaSach;
    private javax.swing.JTextField tfMTTienCoc;
    private javax.swing.JTextField tfMTTienPhat;
    private javax.swing.JTextField tfMaDocGia;
    private javax.swing.JTextField tfMaSach;
    private javax.swing.JTextField tfMatKhau;
    private javax.swing.JTextField tfNXB;
    private javax.swing.JTextField tfNamXB;
    private javax.swing.JTextField tfNgaySinh;
    private javax.swing.JTextField tfSoDienThoai;
    private javax.swing.JTextField tfTacGia;
    private javax.swing.JTextField tfTenSach;
    private javax.swing.JTextField tfTenTaiKhoan;
    private javax.swing.JTextField tfTheLoai;
    private javax.swing.JLabel welcomeMsg;
    // End of variables declaration//GEN-END:variables
}
