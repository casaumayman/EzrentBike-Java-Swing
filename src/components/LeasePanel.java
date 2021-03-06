/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import entities.Account;
import javax.swing.JFrame;
import entities.Product;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import services.MyDatabase;
import services.StringUtils;

/**
 *
 * @author HuyTuan
 */
public class LeasePanel extends javax.swing.JDialog {

    private Product product = null;
    private long startTime;
    private long endTime;
    private Calendar calendar;
    private MyDatabase db = null;
    private Account account = null;

    public LeasePanel(JFrame parent, Product product, Account ac) {
        super(parent, true);
        initComponents();
        setLocationRelativeTo(null);
        this.product = product;
        labelName.setText(this.product.getName());
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/resources/sanpham/" + this.product.getImg()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(500, 270, img.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        labelImage.setIcon(imageIcon);
        startTime = new Date().getTime();
        endTime = new Date(startTime + 86400000).getTime();
        dpkEnd.setDate(new Date(endTime));
        labelNumOfDate.setText("1");
        labelPrice.setText(new StringUtils().convertToPrice(this.product.getPrice()));
        calendar = Calendar.getInstance();
        db = new MyDatabase();
        account = ac;
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBackground = new javax.swing.JPanel();
        labelName = new javax.swing.JLabel();
        btnClose = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        labelPrice = new javax.swing.JLabel();
        btnSubmit = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        dpkEnd = new com.toedter.calendar.JDateChooser();
        dpkStart = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        labelNumOfDate = new javax.swing.JLabel();
        labelImage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelBackground.setBackground(new java.awt.Color(0, 0, 0));
        panelBackground.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 51), 2));
        panelBackground.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelName.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        labelName.setForeground(new java.awt.Color(255, 255, 255));
        labelName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelName.setText("Honda 140px");
        panelBackground.add(labelName, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 13, 465, 63));

        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons_close.png"))); // NOI18N
        btnClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCloseMouseClicked(evt);
            }
        });
        panelBackground.add(btnClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(543, 13, 45, 34));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Giá:");
        panelBackground.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, 160, 36));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Ngày bắt đầu thuê:");
        panelBackground.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 417, 161, 36));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Ngày kết thúc thuê:");
        panelBackground.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, 161, 36));

        labelPrice.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelPrice.setForeground(new java.awt.Color(204, 0, 51));
        labelPrice.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelPrice.setText("1.000.000 VND");
        panelBackground.add(labelPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 540, 280, 36));

        btnSubmit.setBackground(new java.awt.Color(0, 0, 0));
        btnSubmit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 51), 2));
        btnSubmit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSubmit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSubmitMouseClicked(evt);
            }
        });
        btnSubmit.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Thuê ngay");
        btnSubmit.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 4, 210, 30));

        panelBackground.add(btnSubmit, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 590, 220, 40));

        dpkEnd.setDateFormatString("dd-MM-yyyy");
        dpkEnd.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dpkEnd.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dpkEndPropertyChange(evt);
            }
        });
        panelBackground.add(dpkEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 460, 310, 30));

        dpkStart.setDateFormatString("dd-MM-yyyy");
        dpkStart.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dpkStart.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dpkStartPropertyChange(evt);
            }
        });
        panelBackground.add(dpkStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 420, 310, 30));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Số ngày thuê:");
        panelBackground.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, 160, 36));

        labelNumOfDate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelNumOfDate.setForeground(new java.awt.Color(255, 255, 255));
        labelNumOfDate.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelNumOfDate.setText("5");
        panelBackground.add(labelNumOfDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 500, 160, 36));

        labelImage.setMaximumSize(new java.awt.Dimension(500, 270));
        labelImage.setMinimumSize(new java.awt.Dimension(500, 270));
        labelImage.setPreferredSize(new java.awt.Dimension(500, 270));
        panelBackground.add(labelImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, -1, -1));

        getContentPane().add(panelBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseMouseClicked
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnCloseMouseClicked

    private void dpkStartPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dpkStartPropertyChange
        if (!(evt.getNewValue() instanceof Date)) {
            return;
        }
        Date newDate = (Date) evt.getNewValue();
        if (newDate.before(new Date())) {
            JOptionPane.showMessageDialog(this, "Không được chọn ngày đã qua!");
            dpkStart.setDate((Date) evt.getOldValue());
            return;
        }
        startTime = newDate.getTime();
        // TODO add your handling code here:
    }//GEN-LAST:event_dpkStartPropertyChange

    private void dpkEndPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dpkEndPropertyChange
        // TODO add your handling code here:
        if (!(evt.getNewValue() instanceof Date)) {
            return;
        }
        Date newDate = (Date) evt.getNewValue();
        if (newDate.before(new Date(startTime)) || newDate.equals(new Date(startTime))) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc phải sau ngày bắt đầu thuê!");
            dpkEnd.setDate((Date) evt.getOldValue());
            return;
        }
        endTime = newDate.getTime();
        long numOfDay = (endTime - startTime) / 86400000;
        labelNumOfDate.setText("" + numOfDay);
        labelPrice.setText(new StringUtils().convertToPrice(numOfDay * product.getPrice()) + " VNĐ");
    }//GEN-LAST:event_dpkEndPropertyChange

    private void btnSubmitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubmitMouseClicked
        // TODO add your handling code here:
        String sql = "INSERT INTO lease (startTime, endTime, productId, accountId)\n"
                + "VALUES (" + startTime + ", " + endTime + "\n"
                + ", " + product.getId() + ", '" + account.getId() + "')";
        db.execStatment(sql);
        JOptionPane.showMessageDialog(this, "Thêm vào giỏ hàng thành công!");
        dispose();
    }//GEN-LAST:event_btnSubmitMouseClicked

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
            java.util.logging.Logger.getLogger(LeasePanel.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LeasePanel.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LeasePanel.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LeasePanel.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LeasePanel dialog = new LeasePanel(new javax.swing.JFrame(), null, null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnClose;
    private javax.swing.JPanel btnSubmit;
    private com.toedter.calendar.JDateChooser dpkEnd;
    private com.toedter.calendar.JDateChooser dpkStart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel labelImage;
    private javax.swing.JLabel labelName;
    private javax.swing.JLabel labelNumOfDate;
    private javax.swing.JLabel labelPrice;
    private javax.swing.JPanel panelBackground;
    // End of variables declaration//GEN-END:variables
}
