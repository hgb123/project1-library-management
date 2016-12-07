/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import ConnectDB.ConnectDocGia;
import ConnectDB.ConnectSach;
import Origin.DocGia;
import Origin.Sach;
import java.awt.CardLayout;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Toan Ho
 */
public class DocGiaUI extends javax.swing.JFrame {

    /**
     * Creates new form DocGiaUI
     */
    
    // Connect to database
    ConnectSach connectSach = new ConnectSach();
    ConnectDocGia connectDocGia = new ConnectDocGia();
    private DocGia docGia;
    
    public DocGiaUI() {
        initComponents();
        
        // Load data from database
//        loadSach();
        docGia = DangNhap.docGia;
        btnFindBook.setEnabled(false);
        welcomeMsg.setText("Xin chào, " + (docGia != null ? docGia.getHoTen() : "anonymous" ));
        
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    /**
     * @section sach
     * 
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
    
    private void loadSach() {
        DefaultTableModel model = new DefaultTableModel();
        
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
    
    private void clearSachInputFields() {
        tfMaSach.setText("");
        tfTacGia.setText("");
        tfNXB.setText("");
        tfNamXB.setText("");
        tfTheLoai.setText("");
        tfTenSach.setText("");
    }

    /**
     * @section docgia
     */
    
    private void loadDocGiaLatestInfo(boolean beingEdited) {
        if (docGia != null) {
            tfDocGiaHoTen.setText(docGia.getHoTen());
            for (int i = 0; i < cbDocGiaGioiTinh.getItemCount(); ++i) {
                if (cbDocGiaGioiTinh.getItemAt(i).equalsIgnoreCase(docGia.getGioiTinh())) {
                    cbDocGiaGioiTinh.setSelectedIndex(i);
                }
            }
            tfDocGiaNgaySinh.setText(docGia.getNgaySinh());
            tfDocGiaDiaChi.setText(docGia.getDiaChi());
            tfDocGiaSDT.setText(docGia.getSoDienThoai());
            tfDocGiaEmail.setText(docGia.getEmail());
            
            tfDocGiaHoTen.setEnabled(beingEdited);
            cbDocGiaGioiTinh.setEnabled(beingEdited);
            tfDocGiaNgaySinh.setEnabled(beingEdited);
            tfDocGiaDiaChi.setEnabled(beingEdited);
            tfDocGiaSDT.setEnabled(beingEdited);
            tfDocGiaEmail.setEnabled(beingEdited);
            
            btnEditInfo.setEnabled(!beingEdited);
            btnSaveChanges.setEnabled(beingEdited);
            btnCancelEdit.setEnabled(beingEdited);
            
        }
    }
    
    private void logOutDocGiaSession() {
        this.dispose();
        DangNhap dangNhap = new DangNhap();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        choicePanel = new javax.swing.JPanel();
        icon = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        welcomeMsg = new javax.swing.JLabel();
        btnFindBook = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnLogOut = new javax.swing.JButton();
        btnChangeInfo = new javax.swing.JButton();
        workPanel = new javax.swing.JPanel();
        searchBook = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        sachTable = new javax.swing.JTable();
        searchComp = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tfMaSach = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tfTenSach = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tfTacGia = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tfNXB = new javax.swing.JTextField();
        tfTheLoai = new javax.swing.JTextField();
        tfNamXB = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnClearField = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        profileSetting = new javax.swing.JPanel();
        profilePicture = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        tfDocGiaHoTen = new javax.swing.JTextField();
        cbDocGiaGioiTinh = new javax.swing.JComboBox<>();
        tfDocGiaNgaySinh = new javax.swing.JTextField();
        tfDocGiaDiaChi = new javax.swing.JTextField();
        tfDocGiaSDT = new javax.swing.JTextField();
        tfDocGiaEmail = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        btnEditInfo = new javax.swing.JButton();
        btnSaveChanges = new javax.swing.JButton();
        btnCancelEdit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tra cứu thông tin cho bạn đọc");
        setIconImage((new javax.swing.ImageIcon(getClass().getResource("/Icon/library.png"))).getImage());

        choicePanel.setBackground(new java.awt.Color(153, 204, 255));
        choicePanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Reader/reading.png"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));

        welcomeMsg.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        welcomeMsg.setText(" Xin chào, XYZ");

        btnFindBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Reader/search.png"))); // NOI18N
        btnFindBook.setText(" Tìm sách");
        btnFindBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindBookActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnFindBook)
                    .addComponent(welcomeMsg))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(welcomeMsg)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnFindBook)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));

        btnLogOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Reader/logout.png"))); // NOI18N
        btnLogOut.setText(" Đăng xuất");
        btnLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogOutActionPerformed(evt);
            }
        });

        btnChangeInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Reader/settings.png"))); // NOI18N
        btnChangeInfo.setText(" Tuỳ chỉnh");
        btnChangeInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeInfoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnChangeInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLogOut))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnChangeInfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLogOut)
                .addContainerGap())
        );

        javax.swing.GroupLayout choicePanelLayout = new javax.swing.GroupLayout(choicePanel);
        choicePanel.setLayout(choicePanelLayout);
        choicePanelLayout.setHorizontalGroup(
            choicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(choicePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(icon)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 548, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        choicePanelLayout.setVerticalGroup(
            choicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(choicePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(choicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(icon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(choicePanel, java.awt.BorderLayout.PAGE_START);

        workPanel.setLayout(new java.awt.CardLayout());

        searchBook.setBackground(new java.awt.Color(255, 255, 255));

        sachTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        sachTable.setEnabled(false);
        jScrollPane1.setViewportView(sachTable);

        searchComp.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Reader/bookcode.png"))); // NOI18N
        jLabel1.setText(" Mã sách");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Reader/bookname.png"))); // NOI18N
        jLabel2.setText(" Tên sách");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Reader/author.png"))); // NOI18N
        jLabel3.setText(" Tác giả");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Reader/publisher.png"))); // NOI18N
        jLabel4.setText(" Nhà xuất bản");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Reader/bookstype.png"))); // NOI18N
        jLabel5.setText(" Thể loại");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Reader/calendar.png"))); // NOI18N
        jLabel6.setText(" Năm xuất bản");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        btnClearField.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Reader/eraser.png"))); // NOI18N
        btnClearField.setText(" Xoá");
        btnClearField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearFieldActionPerformed(evt);
            }
        });

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Reader/search-engine.png"))); // NOI18N
        btnSearch.setText(" Tìm");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnClearField, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btnClearField)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSearch)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout searchCompLayout = new javax.swing.GroupLayout(searchComp);
        searchComp.setLayout(searchCompLayout);
        searchCompLayout.setHorizontalGroup(
            searchCompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchCompLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchCompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(searchCompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(searchCompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(29, 29, 29)
                .addGroup(searchCompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfNXB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfNamXB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        searchCompLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {tfMaSach, tfNXB, tfNamXB, tfTheLoai});

        searchCompLayout.setVerticalGroup(
            searchCompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchCompLayout.createSequentialGroup()
                .addGroup(searchCompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(searchCompLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, searchCompLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(searchCompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(tfMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(tfNXB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(searchCompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(tfTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(tfTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(searchCompLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(tfTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(tfNamXB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout searchBookLayout = new javax.swing.GroupLayout(searchBook);
        searchBook.setLayout(searchBookLayout);
        searchBookLayout.setHorizontalGroup(
            searchBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchBookLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchComp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        searchBookLayout.setVerticalGroup(
            searchBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, searchBookLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchComp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                .addContainerGap())
        );

        workPanel.add(searchBook, "searchBook");

        profileSetting.setBackground(new java.awt.Color(253, 253, 215));

        profilePicture.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        profilePicture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Reader/boy.png"))); // NOI18N

        jPanel4.setOpaque(false);

        jLabel8.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Reader/info.png"))); // NOI18N
        jLabel8.setText("       THÔNG TIN CÁ NHÂN");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Reader/edit_info/user-name.png"))); // NOI18N
        jLabel9.setText(" Họ tên");

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Reader/edit_info/unisex.png"))); // NOI18N
        jLabel10.setText(" Giới tính");

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Reader/calendar.png"))); // NOI18N
        jLabel11.setText(" Ngày sinh");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Reader/edit_info/house.png"))); // NOI18N
        jLabel12.setText(" Địa chỉ");

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Reader/edit_info/phone-set.png"))); // NOI18N
        jLabel13.setText(" Số điện thoại");

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Reader/edit_info/email.png"))); // NOI18N
        jLabel14.setText(" Email");

        cbDocGiaGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nu" }));
        cbDocGiaGioiTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbDocGiaGioiTinhActionPerformed(evt);
            }
        });

        jPanel5.setOpaque(false);
        jPanel5.setLayout(new java.awt.GridLayout(1, 0));

        btnEditInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Reader/editinfo.png"))); // NOI18N
        btnEditInfo.setText(" Sửa");
        btnEditInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditInfoActionPerformed(evt);
            }
        });
        jPanel5.add(btnEditInfo);

        btnSaveChanges.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Reader/savechanges.png"))); // NOI18N
        btnSaveChanges.setText(" Lưu");
        btnSaveChanges.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveChangesActionPerformed(evt);
            }
        });
        jPanel5.add(btnSaveChanges);

        btnCancelEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Reader/cancel.png"))); // NOI18N
        btnCancelEdit.setText(" Bỏ qua");
        btnCancelEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelEditActionPerformed(evt);
            }
        });
        jPanel5.add(btnCancelEdit);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
                .addGap(44, 44, 44)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tfDocGiaEmail)
                    .addComponent(tfDocGiaSDT)
                    .addComponent(tfDocGiaDiaChi)
                    .addComponent(tfDocGiaNgaySinh)
                    .addComponent(cbDocGiaGioiTinh, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tfDocGiaHoTen))
                .addContainerGap(75, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(tfDocGiaHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cbDocGiaGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(tfDocGiaNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(tfDocGiaDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(tfDocGiaSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(tfDocGiaEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout profileSettingLayout = new javax.swing.GroupLayout(profileSetting);
        profileSetting.setLayout(profileSettingLayout);
        profileSettingLayout.setHorizontalGroup(
            profileSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profileSettingLayout.createSequentialGroup()
                .addComponent(profilePicture, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        profileSettingLayout.setVerticalGroup(
            profileSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(profilePicture, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        workPanel.add(profileSetting, "profileSetting");

        getContentPane().add(workPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnClearFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearFieldActionPerformed
        // TODO add your handling code here:
        clearSachInputFields();
    }//GEN-LAST:event_btnClearFieldActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        searchSach();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnFindBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindBookActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) workPanel.getLayout();
        card.show(workPanel, "searchBook");
        btnFindBook.setEnabled(false);
        btnChangeInfo.setEnabled(true);
    }//GEN-LAST:event_btnFindBookActionPerformed

    private void btnChangeInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeInfoActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) workPanel.getLayout();
        card.show(workPanel, "profileSetting");
        btnChangeInfo.setEnabled(false);
        btnFindBook.setEnabled(true);
        
        btnEditInfo.requestFocus();
        loadDocGiaLatestInfo(false);
        
    }//GEN-LAST:event_btnChangeInfoActionPerformed

    private void cbDocGiaGioiTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbDocGiaGioiTinhActionPerformed
        // TODO add your handling code here:
        switch (cbDocGiaGioiTinh.getSelectedIndex()) {
            case 0:
                profilePicture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Reader/boy.png")));
                break;
            case 1:
                profilePicture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Reader/girl.png")));
                break;
        }
    }//GEN-LAST:event_cbDocGiaGioiTinhActionPerformed

    private void btnLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogOutActionPerformed
        // TODO add your handling code here:
        int selected;
        selected = JOptionPane.showConfirmDialog(null, "Bạn có muốn đăng xuất?", "Độc giả", JOptionPane.YES_NO_OPTION);
        if (selected == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Xin chào bạn, hẹn gặp lại!", "Tạm biệt", JOptionPane.INFORMATION_MESSAGE);
            logOutDocGiaSession();
        }
    }//GEN-LAST:event_btnLogOutActionPerformed

    private void btnCancelEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelEditActionPerformed
        // TODO add your handling code here:
        loadDocGiaLatestInfo(false);
        btnEditInfo.requestFocus();
    }//GEN-LAST:event_btnCancelEditActionPerformed

    private void btnSaveChangesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveChangesActionPerformed
        // TODO add your handling code here:
        String hoTen = tfDocGiaHoTen.getText().trim();
        String gioiTinh = (String) cbDocGiaGioiTinh.getSelectedItem();
        String ngaySinh = tfDocGiaNgaySinh.getText().trim();
        String diaChi = tfDocGiaDiaChi.getText().trim();
        String soDienThoai = tfDocGiaSDT.getText().trim();
        String email = tfDocGiaEmail.getText().trim();

        if (
            hoTen.equals(docGia.getHoTen()) &&
            gioiTinh.equals(docGia.getGioiTinh()) &&
            ngaySinh.equals(docGia.getNgaySinh()) &&
            diaChi.equals(docGia.getDiaChi()) &&
            soDienThoai.equals(docGia.getSoDienThoai()) &&
            email.equals(docGia.getEmail())) {
            JOptionPane.showMessageDialog(null, "Bạn chưa thay đổi gì", "Sửa thông tin độc giả", JOptionPane.WARNING_MESSAGE);
        } else {
            docGia.setHoTen(hoTen);
            docGia.setGioiTinh(gioiTinh);
            docGia.setNgaySinh(ngaySinh);
            docGia.setDiaChi(diaChi);
            docGia.setSoDienThoai(soDienThoai);
            docGia.setEmail(email);
            connectDocGia.updateByID(docGia);
            JOptionPane.showMessageDialog(null, "Sửa thành công!", "Sửa thông tin độc giả", JOptionPane.INFORMATION_MESSAGE);
            welcomeMsg.setText("Xin chào, " + docGia.getHoTen());
            loadDocGiaLatestInfo(false);
            btnEditInfo.requestFocus();
        }
    }//GEN-LAST:event_btnSaveChangesActionPerformed

    private void btnEditInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditInfoActionPerformed
        // TODO add your handling code here:
        loadDocGiaLatestInfo(true);
        btnSaveChanges.requestFocus();
    }//GEN-LAST:event_btnEditInfoActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DocGiaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DocGiaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DocGiaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DocGiaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DocGiaUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelEdit;
    private javax.swing.JButton btnChangeInfo;
    private javax.swing.JButton btnClearField;
    private javax.swing.JButton btnEditInfo;
    private javax.swing.JButton btnFindBook;
    private javax.swing.JButton btnLogOut;
    private javax.swing.JButton btnSaveChanges;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cbDocGiaGioiTinh;
    private javax.swing.JPanel choicePanel;
    private javax.swing.JLabel icon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel profilePicture;
    private javax.swing.JPanel profileSetting;
    private javax.swing.JTable sachTable;
    private javax.swing.JPanel searchBook;
    private javax.swing.JPanel searchComp;
    private javax.swing.JTextField tfDocGiaDiaChi;
    private javax.swing.JTextField tfDocGiaEmail;
    private javax.swing.JTextField tfDocGiaHoTen;
    private javax.swing.JTextField tfDocGiaNgaySinh;
    private javax.swing.JTextField tfDocGiaSDT;
    private javax.swing.JTextField tfMaSach;
    private javax.swing.JTextField tfNXB;
    private javax.swing.JTextField tfNamXB;
    private javax.swing.JTextField tfTacGia;
    private javax.swing.JTextField tfTenSach;
    private javax.swing.JTextField tfTheLoai;
    private javax.swing.JLabel welcomeMsg;
    private javax.swing.JPanel workPanel;
    // End of variables declaration//GEN-END:variables
}
