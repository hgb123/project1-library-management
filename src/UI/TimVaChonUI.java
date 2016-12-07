/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import ConnectDB.ConnectDocGia;
import ConnectDB.ConnectSach;
import Origin.DocGia;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Toan Ho
 */
public class TimVaChonUI extends javax.swing.JFrame {

    /**
     * Creates new form TestFiltering
     */
    
    private ConnectDocGia connectDocGia = new ConnectDocGia();
    private String maDocGia;
    private ConnectSach connectSach = new ConnectSach();
    private String maSach;
    
    private String dataName;
    private TableRowSorter<TableModel> sorter;
    private JFrame previousFrame;
    
    public TimVaChonUI() {
        initComponents();
        
        if (ThuThuUI.MUONTRA_CHOOSE_CHOICE == 0) {
            loadAllDocGia();
        } else {
            loadAllSach();
        }
        
        filterTf.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = filterTf.getText().trim();
                
                if (text.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    try {
                        sorter.setRowFilter(RowFilter.regexFilter(text,1));
                    } catch (PatternSyntaxException psex) {
                        System.err.println("Bad regex pattern");
                    }
                }
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyReleased(KeyEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                onExit();
            }
        });
    }
    
    private void onExit() {
        this.getPreviousFrame().setEnabled(true);
        this.dispose();
    }

    public JFrame getPreviousFrame() {
        return previousFrame;
    }

    public void setPreviousFrame(JFrame previousFrame) {
        this.previousFrame = previousFrame;
    }
    
    private void loadAllDocGia() {
        dataName = "độc giả";
        DefaultTableModel model = new DefaultTableModel();
        
        ResultSet rs = connectDocGia.getData();
        try {
            // Load title for each column
            ResultSetMetaData rsMD = rs.getMetaData();
            String[] arr = new String[2];
            arr[0] = rsMD.getColumnName(1);
            arr[1] = rsMD.getColumnName(4);
            
            model.setColumnIdentifiers(arr);
            
            // Load data to table
            while (rs.next()) {
                arr[0] = rs.getString(1);
                arr[1] = rs.getString(4);
                model.addRow(arr);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println(ex.toString());
        }
        
        chooseTable.setModel(model);
        sorter = new TableRowSorter<TableModel>(model);
        chooseTable.setRowSorter(sorter);
        chooseTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        chooseTable.getColumnModel().getColumn(0).setMinWidth(80);
        chooseTable.getColumnModel().getColumn(0).setMaxWidth(80);   
    }
    
    private void loadAllSach() {
        dataName = "sách";
        DefaultTableModel model = new DefaultTableModel();
        
        ResultSet rs = connectSach.getData();
        try {
            // Load title for each column
            ResultSetMetaData rsMD = rs.getMetaData();
            String[] arr = new String[2];
            arr[0] = rsMD.getColumnName(1);
            arr[1] = rsMD.getColumnName(2);
            
            model.setColumnIdentifiers(arr);
            
            // Load data to table
            while (rs.next()) {
                arr[0] = rs.getString(1);
                arr[1] = rs.getString(2);
                model.addRow(arr);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println(ex.toString());
        }
        
        chooseTable.setModel(model);
        sorter = new TableRowSorter<TableModel>(model);
        chooseTable.setRowSorter(sorter);
        chooseTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        chooseTable.getColumnModel().getColumn(0).setMinWidth(70);
        chooseTable.getColumnModel().getColumn(0).setMaxWidth(70);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        filterTf = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        chooseTable = new javax.swing.JTable();
        btnOK = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconImage((new javax.swing.ImageIcon(getClass().getResource("/Icon/library.png"))).getImage());

        jPanel1.setBackground(new java.awt.Color(200, 226, 251));

        chooseTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(chooseTable);

        btnOK.setText("OK");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(filterTf)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                    .addComponent(btnOK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(filterTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOK, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
        // TODO add your handling code here:
        int row = chooseTable.getSelectedRow();
        
        if (row < 0) {
            // Display error
            JOptionPane.showMessageDialog(null, "Hãy chọn một " + dataName, "Chọn " + dataName, JOptionPane.ERROR_MESSAGE);
            return ;
        } else {
            ( (ThuThuUI) this.getPreviousFrame()).onChooseDone((String) chooseTable.getValueAt(row, 0));
            onExit();
        }
        
    }//GEN-LAST:event_btnOKActionPerformed

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
            java.util.logging.Logger.getLogger(TimVaChonUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TimVaChonUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TimVaChonUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TimVaChonUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TimVaChonUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOK;
    private javax.swing.JTable chooseTable;
    private javax.swing.JTextField filterTf;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
