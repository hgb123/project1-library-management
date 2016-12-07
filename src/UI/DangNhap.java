/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import ConnectDB.*;
import Origin.DocGia;
import Origin.ThuThu;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Toan Ho
 */
public class DangNhap extends javax.swing.JFrame {

    /**
     * Creates new form DangNhap
     */
    
    private ResultSet rs; 
    ConnectThuThu connectThuThu = new ConnectThuThu();
    public static ThuThu thuThu; 
    ConnectDocGia connectDocGia = new ConnectDocGia();
    public static DocGia docGia;
    
    public DangNhap() {
        initComponents();
        
        rs = null;
        thuThu = null;
        docGia = null;
        
        setVisible(true);
        setLocationRelativeTo(null);
    }
    
    private void memberWorkingSession() throws SQLException {
        this.dispose();
        DocGiaUI docGiaUI = new DocGiaUI();
        
    }
    
    private void librarianWorkingSession() {
        this.dispose();
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            System.err.println(ex.toString()); 
        }
        ThuThuUI thuThuUI = new ThuThuUI();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chonQuyenRBtnGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        chonQuyenPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        rbtnThuThu = new javax.swing.JRadioButton();
        rbtnDocGia = new javax.swing.JRadioButton();
        dangNhapPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        tfTenDangNhap = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tfMatKhau = new javax.swing.JPasswordField();
        jPanel2 = new javax.swing.JPanel();
        btnLogin = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cổng thông tin điện tử thư viện XYZ");
        setIconImage((new javax.swing.ImageIcon(getClass().getResource("/Icon/library.png"))).getImage());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        chonQuyenPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Login/team.png"))); // NOI18N
        jLabel2.setText(" Bạn là:");

        chonQuyenRBtnGroup.add(rbtnThuThu);
        rbtnThuThu.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        rbtnThuThu.setText("Thủ thư");
        rbtnThuThu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnThuThuActionPerformed(evt);
            }
        });

        chonQuyenRBtnGroup.add(rbtnDocGia);
        rbtnDocGia.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        rbtnDocGia.setText("Độc giả");
        rbtnDocGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnDocGiaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout chonQuyenPanelLayout = new javax.swing.GroupLayout(chonQuyenPanel);
        chonQuyenPanel.setLayout(chonQuyenPanelLayout);
        chonQuyenPanelLayout.setHorizontalGroup(
            chonQuyenPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chonQuyenPanelLayout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(jLabel2)
                .addGap(95, 95, 95)
                .addComponent(rbtnThuThu)
                .addGap(52, 52, 52)
                .addComponent(rbtnDocGia)
                .addContainerGap(176, Short.MAX_VALUE))
        );
        chonQuyenPanelLayout.setVerticalGroup(
            chonQuyenPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chonQuyenPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(chonQuyenPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(rbtnThuThu)
                    .addComponent(rbtnDocGia))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dangNhapPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Login/users.png"))); // NOI18N
        jLabel3.setText(" Tên đăng nhập:");

        tfTenDangNhap.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        tfTenDangNhap.setEnabled(false);

        jLabel4.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Login/pass.png"))); // NOI18N
        jLabel4.setText(" Mật khẩu:");

        tfMatKhau.setEnabled(false);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        btnLogin.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        btnLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Login/login2.png"))); // NOI18N
        btnLogin.setText("  Đăng nhập");
        btnLogin.setEnabled(false);
        btnLogin.setMaximumSize(new java.awt.Dimension(117, 33));
        btnLogin.setMinimumSize(new java.awt.Dimension(117, 33));
        btnLogin.setPreferredSize(new java.awt.Dimension(117, 33));
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        btnExit.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        btnExit.setText("Thoát");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
            .addComponent(btnExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout dangNhapPanelLayout = new javax.swing.GroupLayout(dangNhapPanel);
        dangNhapPanel.setLayout(dangNhapPanelLayout);
        dangNhapPanelLayout.setHorizontalGroup(
            dangNhapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dangNhapPanelLayout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(dangNhapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(dangNhapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tfMatKhau)
                    .addComponent(tfTenDangNhap))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dangNhapPanelLayout.setVerticalGroup(
            dangNhapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dangNhapPanelLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(dangNhapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfTenDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(dangNhapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tfMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        dangNhapPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {tfMatKhau, tfTenDangNhap});

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 20)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Login/books.png"))); // NOI18N
        jLabel1.setText("  CỔNG THÔNG TIN THƯ VIỆN XYZ ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chonQuyenPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dangNhapPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(chonQuyenPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dangNhapPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rbtnThuThuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnThuThuActionPerformed
        // TODO add your handling code here:
        System.out.println("Switched to Librarians");
        tfTenDangNhap.setText("");
        tfMatKhau.setText("");
        tfTenDangNhap.setEnabled(true);
        tfMatKhau.setEnabled(true);
        
        btnLogin.setEnabled(true);
    }//GEN-LAST:event_rbtnThuThuActionPerformed

    private void rbtnDocGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnDocGiaActionPerformed
        // TODO add your handling code here:
        System.out.println("Switched to Members");
        tfTenDangNhap.setText("");
        tfMatKhau.setText("");
        tfTenDangNhap.setEnabled(true);
        tfMatKhau.setEnabled(true);
        
        btnLogin.setEnabled(true);
    }//GEN-LAST:event_rbtnDocGiaActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // TODO add your handling code here:
        if (tfMatKhau.getPassword().length == 0 || tfTenDangNhap.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Hãy điền đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
            System.err.println("Invalid input!");
        } else {
            try {
                String tenTaiKhoan = new String(tfTenDangNhap.getText().trim());
                String matKhau = new String(tfMatKhau.getPassword());
                System.out.println("Valid input!");
                if (rbtnDocGia.isSelected()) {
                    // For member
                    
                    System.out.println("Logging in for Member with username: "
                            + tenTaiKhoan + " and password: " + matKhau + " ..");
                    rs = connectDocGia.login(tenTaiKhoan, matKhau);
                    if (rs.isBeforeFirst()) {
                        System.out.println("Successfully login!");
                        JOptionPane.showMessageDialog(null, "Đăng nhập thành công!", "Độc giả", JOptionPane.INFORMATION_MESSAGE);
                        while (rs.next()) {
                            docGia = new DocGia(
                                    rs.getInt(1), rs.getString(2), rs.getString(3),
                                    rs.getString(4), rs.getString(5), rs.getString(6), 
                                    rs.getString(7), rs.getString(8), rs.getString(9));
                        }
                        memberWorkingSession();
                    } else {
                        JOptionPane.showMessageDialog(null, "Tài khoản không tồn tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        System.err.println("Account not exist!");
                    }
                } else {
                    // For librarian
                    
                    System.out.println("Logging in for Librarian with username: " 
                            + tenTaiKhoan + " and password: " + matKhau + " ..");
                    rs = connectThuThu.login(tenTaiKhoan, matKhau);
                    if (rs.isBeforeFirst()) {
                        System.out.println("Successfully login!");
                        JOptionPane.showMessageDialog(null, "Đăng nhập thành công!", "Thủ thư", JOptionPane.INFORMATION_MESSAGE);
                        while (rs.next()) {
                            thuThu = new ThuThu(
                                    rs.getInt(1), rs.getString(2),
                                    rs.getString(3), rs.getString(4));
                        }
                        librarianWorkingSession();
                    } else {
                        JOptionPane.showMessageDialog(null, "Tài khoản không tồn tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        System.err.println("Account not exist!");
                    }
                }    
            } catch (SQLException ex) {
                System.err.println(ex.toString());
            }
            
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btnExitActionPerformed

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
            java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DangNhap().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnLogin;
    private javax.swing.JPanel chonQuyenPanel;
    private javax.swing.ButtonGroup chonQuyenRBtnGroup;
    private javax.swing.JPanel dangNhapPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton rbtnDocGia;
    private javax.swing.JRadioButton rbtnThuThu;
    private javax.swing.JPasswordField tfMatKhau;
    private javax.swing.JTextField tfTenDangNhap;
    // End of variables declaration//GEN-END:variables
}