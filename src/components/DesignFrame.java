/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import entities.Account;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TrayIcon;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import services.FileHandle;
import services.MyDatabase;
import services.StringUtils;

/**
 *
 * @author HuyTuan
 */
public class DesignFrame extends javax.swing.JFrame {

    /**
     * Creates new form DesignFrame
     */
    private Color colorActive = new Color(204, 0, 51);
    private Color colorUnActive = new Color(0, 0, 0);
    private CardLayout cardContentLayout = null;
    private ArrayList<ProductCard> listProductCard = new ArrayList<ProductCard>();
    private int categoryID = 0;
    private int producerId = 0;
    private int sortId = 0;
    private String txtSearch = "";
    private Account account = null;
    private MyDatabase db = null;
    private FileHandle fileHandle = null;

    public DesignFrame() {
        initComponents();
        db = new MyDatabase();
        fileHandle = new FileHandle();
        cardContentLayout = (CardLayout) this.contentPanel.getLayout();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        showListProducer();

        labelHello.setForeground(new Color(204, 0, 51));
        labelHello.setText("Đăng nhập hoặc đăng ký");
        labelHello.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.setVisible(false);

        ArrayList<String> ar = fileHandle.read();
        if (ar != null) {
            login(ar.get(0), ar.get(1), ar.get(2), Integer.parseInt(ar.get(4)));
        }
    }

    private void logout() {
        labelHello.setForeground(new Color(204, 0, 51));
        labelHello.setText("Đăng nhập hoặc đăng ký");
        labelHello.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.setVisible(false);
        account = null;
        fileHandle.clear();
        turnOffAllActive();
        this.ActiveHome.setBackground(colorActive);
        this.cardContentLayout.show(this.contentPanel, "HomeContent");
    }

    public void login(String id, String username, String name, int profileId) {
        account = new Account(id, username, name, profileId);
        labelHello.setForeground(new Color(255, 255, 255));
        labelHello.setText("Xin chào: " + name);
        labelHello.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        btnLogout.setVisible(true);
    }

    private void showListProducer() {
        ResultSet rs = db.query("select id, name from producer");
        ArrayList<String> listProducer = new ArrayList<>();
        try {
            while (rs.next()) {
                listProducer.add(rs.getString("name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DesignFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        listProducer.forEach(name -> producerFilterCombobox.addItem(name));
    }

    private void turnOffAllActive() {
        this.ActiveAccount.setBackground(colorUnActive);
        this.ActiveFeedback.setBackground(colorUnActive);
        this.ActiveHome.setBackground(colorUnActive);
        this.ActiveXeso.setBackground(colorUnActive);
        this.ActiveXetayga.setBackground(colorUnActive);
    }

    private void showProduct() {
        int n = listProductCard.size();
        int row = (n % 2 == 0) ? n / 2 : (n / 2 + 1);
        row = row < 1 ? 1 : row;
        XetaygaListProduct.removeAll();
        this.XetaygaListProduct.setMinimumSize(new Dimension(780, row * 500));
        this.XetaygaListProduct.setPreferredSize(new Dimension(780, row * 500));
        GridLayout gl = (GridLayout) this.XetaygaListProduct.getLayout();
        gl.setColumns(2);
        gl.setRows(row);
        TitledBorder border = (TitledBorder) this.jScrollPane1.getBorder();
        border.setTitle("Có " + n + " sản phẩm");
        listProductCard.forEach(cardPd -> XetaygaListProduct.add(cardPd));
        if (n % 2 != 0) {
            XetaygaListProduct.add(new EmtyCardProduct());
        }
        jScrollPane1.revalidate();
        jScrollPane1.repaint();
    }

    private void repaintAll() {
        producerFilterCombobox.setSelectedIndex(0);
        sortCombobox.setSelectedIndex(0);
    }

    private void queryProduct(int categoryId, int productId, int sortId) {
        String sql;
        ArrayList<ProductCard> data = new ArrayList<>();
        listProductCard.clear();
        String sort = sortId == 0 ? "asc" : "desc";
        sql = "select pd.id, pd.name, pd.cost as 'price', pd.image, ct.name as 'category', pdc.name as 'producer'\n"
                + "from product pd, category ct, producer pdc\n"
                + "where\n"
                + "    pd.categoryId = ct.id and\n"
                + "    pd.producerId = pdc.id\n"
                + (categoryId == 0 ? "and pd.name LIKE '%" + txtSearch + "%'\n" : " and pd.categoryId = " + categoryId + "\n")
                + (productId == 0 ? "\n" : " and pd.producerId = " + productId + "\n")
                + "    order by cost " + sort;
        ResultSet list = db.query(sql);
        try {
            if (!list.isBeforeFirst()) {
                showProduct();
                return;
            }
            while (list.next()) {
                data.add(new ProductCard(
                        list.getInt("id"),
                        list.getString("name"),
                        list.getString("producer"),
                        list.getString("category"),
                        list.getInt("price"),
                        list.getString("image"),
                        this,
                        account
                )
                );
            }
            listProductCard = data;
            showProduct();
        } catch (SQLException ex) {
            Logger.getLogger(DesignFrame.class.getName()).log(Level.SEVERE, null, ex);
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

        panelBackground = new javax.swing.JPanel();
        panelMenu = new javax.swing.JPanel();
        MenuItemXetayga = new javax.swing.JPanel();
        ActiveXetayga = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        MenuItemHome = new javax.swing.JPanel();
        ActiveHome = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        MenuItemFeedback = new javax.swing.JPanel();
        ActiveFeedback = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        MenuItemAccount = new javax.swing.JPanel();
        ActiveAccount = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        MenuItemXeso = new javax.swing.JPanel();
        ActiveXeso = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Title = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        ToolbarPanel = new javax.swing.JPanel();
        labelHello = new javax.swing.JLabel();
        btnCard = new javax.swing.JLabel();
        searchTextField = new javax.swing.JTextField();
        btnSearch = new javax.swing.JLabel();
        btnLogout = new javax.swing.JLabel();
        contentPanel = new javax.swing.JPanel();
        HomeContent = new javax.swing.JPanel();
        banner = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        XetaygaContent = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        producerFilterCombobox = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        sortCombobox = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        XetaygaListProduct = new javax.swing.JPanel();
        FeedbackContent = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtFeedback = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        btnSubmit = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        AccountContent = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtEditUsername = new javax.swing.JTextField();
        txtEditName = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        btnEditName = new javax.swing.JLabel();
        btnChangePassword = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        CardContent = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane3 = new javax.swing.JScrollPane();
        CardTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Thuê xe máy EzrentBike");
        setResizable(false);

        panelBackground.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelMenu.setBackground(new java.awt.Color(0, 0, 0));
        panelMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MenuItemXetayga.setBackground(new java.awt.Color(0, 0, 0));
        MenuItemXetayga.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        MenuItemXetayga.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuItemXetaygaMouseClicked(evt);
            }
        });

        ActiveXetayga.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout ActiveXetaygaLayout = new javax.swing.GroupLayout(ActiveXetayga);
        ActiveXetayga.setLayout(ActiveXetaygaLayout);
        ActiveXetaygaLayout.setHorizontalGroup(
            ActiveXetaygaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );
        ActiveXetaygaLayout.setVerticalGroup(
            ActiveXetaygaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Xe tay ga");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons_xetayga.png"))); // NOI18N

        javax.swing.GroupLayout MenuItemXetaygaLayout = new javax.swing.GroupLayout(MenuItemXetayga);
        MenuItemXetayga.setLayout(MenuItemXetaygaLayout);
        MenuItemXetaygaLayout.setHorizontalGroup(
            MenuItemXetaygaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuItemXetaygaLayout.createSequentialGroup()
                .addComponent(ActiveXetayga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        MenuItemXetaygaLayout.setVerticalGroup(
            MenuItemXetaygaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ActiveXetayga, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(MenuItemXetaygaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MenuItemXetaygaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        panelMenu.add(MenuItemXetayga, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, -1, -1));

        MenuItemHome.setBackground(new java.awt.Color(0, 0, 0));
        MenuItemHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        MenuItemHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuItemHomeMouseClicked(evt);
            }
        });

        ActiveHome.setBackground(new java.awt.Color(204, 0, 51));

        javax.swing.GroupLayout ActiveHomeLayout = new javax.swing.GroupLayout(ActiveHome);
        ActiveHome.setLayout(ActiveHomeLayout);
        ActiveHomeLayout.setHorizontalGroup(
            ActiveHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );
        ActiveHomeLayout.setVerticalGroup(
            ActiveHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Trang chủ");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons_home.png"))); // NOI18N

        javax.swing.GroupLayout MenuItemHomeLayout = new javax.swing.GroupLayout(MenuItemHome);
        MenuItemHome.setLayout(MenuItemHomeLayout);
        MenuItemHomeLayout.setHorizontalGroup(
            MenuItemHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuItemHomeLayout.createSequentialGroup()
                .addComponent(ActiveHome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        MenuItemHomeLayout.setVerticalGroup(
            MenuItemHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ActiveHome, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(MenuItemHomeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MenuItemHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        panelMenu.add(MenuItemHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 138, -1, -1));

        MenuItemFeedback.setBackground(new java.awt.Color(0, 0, 0));
        MenuItemFeedback.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        MenuItemFeedback.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuItemFeedbackMouseClicked(evt);
            }
        });

        ActiveFeedback.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout ActiveFeedbackLayout = new javax.swing.GroupLayout(ActiveFeedback);
        ActiveFeedback.setLayout(ActiveFeedbackLayout);
        ActiveFeedbackLayout.setHorizontalGroup(
            ActiveFeedbackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );
        ActiveFeedbackLayout.setVerticalGroup(
            ActiveFeedbackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Liên hệ");

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons_feedback.png"))); // NOI18N

        javax.swing.GroupLayout MenuItemFeedbackLayout = new javax.swing.GroupLayout(MenuItemFeedback);
        MenuItemFeedback.setLayout(MenuItemFeedbackLayout);
        MenuItemFeedbackLayout.setHorizontalGroup(
            MenuItemFeedbackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuItemFeedbackLayout.createSequentialGroup()
                .addComponent(ActiveFeedback, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        MenuItemFeedbackLayout.setVerticalGroup(
            MenuItemFeedbackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ActiveFeedback, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(MenuItemFeedbackLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MenuItemFeedbackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        panelMenu.add(MenuItemFeedback, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 440, -1, -1));

        MenuItemAccount.setBackground(new java.awt.Color(0, 0, 0));
        MenuItemAccount.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        MenuItemAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuItemAccountMouseClicked(evt);
            }
        });

        ActiveAccount.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout ActiveAccountLayout = new javax.swing.GroupLayout(ActiveAccount);
        ActiveAccount.setLayout(ActiveAccountLayout);
        ActiveAccountLayout.setHorizontalGroup(
            ActiveAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );
        ActiveAccountLayout.setVerticalGroup(
            ActiveAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Tài khoản");

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons_user.png"))); // NOI18N

        javax.swing.GroupLayout MenuItemAccountLayout = new javax.swing.GroupLayout(MenuItemAccount);
        MenuItemAccount.setLayout(MenuItemAccountLayout);
        MenuItemAccountLayout.setHorizontalGroup(
            MenuItemAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuItemAccountLayout.createSequentialGroup()
                .addComponent(ActiveAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        MenuItemAccountLayout.setVerticalGroup(
            MenuItemAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ActiveAccount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(MenuItemAccountLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MenuItemAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        panelMenu.add(MenuItemAccount, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 540, -1, -1));

        MenuItemXeso.setBackground(new java.awt.Color(0, 0, 0));
        MenuItemXeso.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        MenuItemXeso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuItemXesoMouseClicked(evt);
            }
        });

        ActiveXeso.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout ActiveXesoLayout = new javax.swing.GroupLayout(ActiveXeso);
        ActiveXeso.setLayout(ActiveXesoLayout);
        ActiveXesoLayout.setHorizontalGroup(
            ActiveXesoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );
        ActiveXesoLayout.setVerticalGroup(
            ActiveXesoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Xe số");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons_xeso.png"))); // NOI18N

        javax.swing.GroupLayout MenuItemXesoLayout = new javax.swing.GroupLayout(MenuItemXeso);
        MenuItemXeso.setLayout(MenuItemXesoLayout);
        MenuItemXesoLayout.setHorizontalGroup(
            MenuItemXesoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuItemXesoLayout.createSequentialGroup()
                .addComponent(ActiveXeso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        MenuItemXesoLayout.setVerticalGroup(
            MenuItemXesoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ActiveXeso, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(MenuItemXesoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MenuItemXesoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        panelMenu.add(MenuItemXeso, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 340, -1, -1));

        Title.setFont(new java.awt.Font("Goudy Stout", 1, 18)); // NOI18N
        Title.setForeground(new java.awt.Color(255, 255, 255));
        Title.setText("EZRENTBIKE");
        panelMenu.add(Title, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 240, 80));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Version: 1.0.0");
        panelMenu.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 650, 90, 20));

        panelBackground.add(panelMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 690));

        ToolbarPanel.setBackground(new java.awt.Color(51, 51, 51));

        labelHello.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        labelHello.setForeground(new java.awt.Color(255, 255, 255));
        labelHello.setText("Xin chào: Huỳnh Huy Tuấn");
        labelHello.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelHelloMouseClicked(evt);
            }
        });

        btnCard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons_cart.png"))); // NOI18N
        btnCard.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCardMouseClicked(evt);
            }
        });

        searchTextField.setText("Nhập tên sản phẩm...");
        searchTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchTextFieldFocusLost(evt);
            }
        });
        searchTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchTextFieldMouseClicked(evt);
            }
        });

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons_search.png"))); // NOI18N
        btnSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSearchMouseClicked(evt);
            }
        });

        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons_logout.png"))); // NOI18N
        btnLogout.setToolTipText("Đăng xuất");
        btnLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLogoutMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout ToolbarPanelLayout = new javax.swing.GroupLayout(ToolbarPanel);
        ToolbarPanel.setLayout(ToolbarPanelLayout);
        ToolbarPanelLayout.setHorizontalGroup(
            ToolbarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ToolbarPanelLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 114, Short.MAX_VALUE)
                .addComponent(btnCard, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelHello, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogout)
                .addGap(20, 20, 20))
        );
        ToolbarPanelLayout.setVerticalGroup(
            ToolbarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelHello, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnCard, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(ToolbarPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchTextField)
                .addContainerGap())
        );

        panelBackground.add(ToolbarPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 880, 50));

        contentPanel.setBackground(new java.awt.Color(255, 255, 255));
        contentPanel.setFocusable(false);
        contentPanel.setRequestFocusEnabled(false);
        contentPanel.setLayout(new java.awt.CardLayout());

        HomeContent.setBackground(new java.awt.Color(255, 255, 255));
        HomeContent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        banner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/banner.png"))); // NOI18N
        HomeContent.add(banner, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 670, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(204, 0, 0));
        jLabel16.setText(" Dịch vụ cho thuê xe máy");
        HomeContent.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 300, 480, 60));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/infomation.PNG"))); // NOI18N
        HomeContent.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 360, 740, 280));

        contentPanel.add(HomeContent, "HomeContent");

        XetaygaContent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setText("Lọc theo nhà sản xuất:");
        XetaygaContent.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 200, 30));

        producerFilterCombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        producerFilterCombobox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        producerFilterCombobox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                itemProducerChange(evt);
            }
        });
        XetaygaContent.add(producerFilterCombobox, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, 170, 30));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel22.setText("Sắp sếp theo giá:");
        XetaygaContent.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, 160, 30));

        sortCombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tăng dần", "Giảm dần" }));
        sortCombobox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sortCombobox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                sortComboboxItemStateChanged(evt);
            }
        });
        XetaygaContent.add(sortCombobox, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 20, 150, 30));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Có 5 sản phẩm"));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setMinimumSize(new java.awt.Dimension(810, 523));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(810, 523));

        XetaygaListProduct.setMinimumSize(new java.awt.Dimension(780, 1500));
        XetaygaListProduct.setPreferredSize(new java.awt.Dimension(780, 1500));
        XetaygaListProduct.setLayout(new java.awt.GridLayout(0, 2, 2, 2));
        jScrollPane1.setViewportView(XetaygaListProduct);

        XetaygaContent.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 810, -1));

        contentPanel.add(XetaygaContent, "XetaygaContent");

        FeedbackContent.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        FeedbackContent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel12.setText("Góp ý phát triển sản phẩm");
        FeedbackContent.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 510, 70));

        txtFeedback.setColumns(20);
        txtFeedback.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtFeedback.setRows(5);
        jScrollPane2.setViewportView(txtFeedback);

        FeedbackContent.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 190, 730, 310));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel13.setText("Vui lòng để lại lời nhắn của bạn:");
        FeedbackContent.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 154, 350, 30));

        btnSubmit.setBackground(new java.awt.Color(153, 0, 51));
        btnSubmit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSubmit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSubmitMouseClicked(evt);
            }
        });
        btnSubmit.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Gửi");
        btnSubmit.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 70, 40));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons_sendFeedback.png"))); // NOI18N
        btnSubmit.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 50, 40));

        FeedbackContent.add(btnSubmit, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 520, 190, 60));

        contentPanel.add(FeedbackContent, "FeedbackContent");

        AccountContent.setBackground(new java.awt.Color(255, 255, 255));
        AccountContent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel20.setText("Hồ sơ cá nhân");
        AccountContent.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 40, 270, 50));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel21.setText("Tên tài khoản:");
        AccountContent.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 200, 190, 40));

        txtEditUsername.setEditable(false);
        txtEditUsername.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtEditUsername.setText("dsadfaf");
        AccountContent.add(txtEditUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 250, 440, 40));

        txtEditName.setEditable(false);
        txtEditName.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        AccountContent.add(txtEditName, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 370, 390, 40));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel23.setText("Tên đầy đủ:");
        AccountContent.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 320, 140, 40));

        btnEditName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons_edit_name.png"))); // NOI18N
        btnEditName.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditNameMouseClicked(evt);
            }
        });
        AccountContent.add(btnEditName, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 370, 50, 40));

        btnChangePassword.setBackground(new java.awt.Color(0, 0, 0));
        btnChangePassword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0), 2));
        btnChangePassword.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnChangePassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnChangePasswordMouseClicked(evt);
            }
        });
        btnChangePassword.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(204, 0, 51));
        jLabel24.setText("Đổi mật khẩu");
        btnChangePassword.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 160, 30));

        AccountContent.add(btnChangePassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 470, 210, 50));

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/avatar/default.png"))); // NOI18N
        AccountContent.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, 260, 310));

        contentPanel.add(AccountContent, "AccountContent");

        CardContent.setBackground(new java.awt.Color(255, 255, 255));
        CardContent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel14.setText("Thông tin giỏ hàng");
        CardContent.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, 360, 70));

        jSeparator1.setForeground(new java.awt.Color(204, 0, 0));
        jSeparator1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        CardContent.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 110, 500, 10));

        CardTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên xe", "Ngày bắt đầu thuê", "Ngày trả xe", "Số ngày thuê", "Giá thuê"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Long.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(CardTable);
        if (CardTable.getColumnModel().getColumnCount() > 0) {
            CardTable.getColumnModel().getColumn(0).setResizable(false);
            CardTable.getColumnModel().getColumn(1).setResizable(false);
            CardTable.getColumnModel().getColumn(2).setResizable(false);
            CardTable.getColumnModel().getColumn(3).setResizable(false);
            CardTable.getColumnModel().getColumn(4).setResizable(false);
        }

        CardContent.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 160, 730, -1));

        contentPanel.add(CardContent, "CardContent");

        panelBackground.add(contentPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, 880, 640));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelBackground, javax.swing.GroupLayout.PREFERRED_SIZE, 1176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelBackground, javax.swing.GroupLayout.PREFERRED_SIZE, 689, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MenuItemHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuItemHomeMouseClicked
        // TODO add your handling code here:
        turnOffAllActive();
        this.ActiveHome.setBackground(colorActive);
        this.cardContentLayout.show(this.contentPanel, "HomeContent");
    }//GEN-LAST:event_MenuItemHomeMouseClicked

    private void MenuItemXetaygaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuItemXetaygaMouseClicked
        // TODO add your handling code here:
        categoryID = 2;
        producerId = 0;
        sortId = 0;
        repaintAll();
        turnOffAllActive();
        this.ActiveXetayga.setBackground(colorActive);
        this.cardContentLayout.show(this.contentPanel, "XetaygaContent");
        queryProduct(categoryID, producerId, sortId);
    }//GEN-LAST:event_MenuItemXetaygaMouseClicked

    private void MenuItemXesoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuItemXesoMouseClicked
        turnOffAllActive();
        this.ActiveXeso.setBackground(colorActive);
        this.cardContentLayout.show(this.contentPanel, "XetaygaContent");
        categoryID = 1;
        producerId = 0;
        sortId = 0;
        repaintAll();
        queryProduct(categoryID, producerId, sortId);
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuItemXesoMouseClicked

    private void MenuItemFeedbackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuItemFeedbackMouseClicked
        turnOffAllActive();
        if (account == null) {
            new AlertUnknowAccountDialog(this).showAlert();
            return;
        }
        this.ActiveFeedback.setBackground(colorActive);
        this.cardContentLayout.show(this.contentPanel, "FeedbackContent");
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuItemFeedbackMouseClicked

    private void MenuItemAccountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuItemAccountMouseClicked
        turnOffAllActive();
        if (account == null) {
            new AlertUnknowAccountDialog(this).showAlert();
            return;
        }
        this.ActiveAccount.setBackground(colorActive);
        this.cardContentLayout.show(this.contentPanel, "AccountContent");
        txtEditUsername.setText(account.getUsername());
        txtEditName.setText(account.getName());
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuItemAccountMouseClicked

    private void searchTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTextFieldMouseClicked
        // TODO add your handling code here:
        String placeholder = "Nhập tên sản phẩm...";
        if (searchTextField.getText().equals(placeholder)) {
            searchTextField.setText("");
        }
    }//GEN-LAST:event_searchTextFieldMouseClicked

    private void searchTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchTextFieldFocusLost
        // TODO add your handling code here:
        String placeholder = "Nhập tên sản phẩm...";
        if (searchTextField.getText().trim().equals("")) {
            searchTextField.setText(placeholder);
        }
    }//GEN-LAST:event_searchTextFieldFocusLost

    private void itemProducerChange(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_itemProducerChange
        // TODO add your handling code here:
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            producerId = producerFilterCombobox.getSelectedIndex();
            queryProduct(categoryID, producerId, sortId);
        }
    }//GEN-LAST:event_itemProducerChange

    private void sortComboboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_sortComboboxItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            sortId = sortCombobox.getSelectedIndex();
            queryProduct(categoryID, producerId, sortId);
        }
    }//GEN-LAST:event_sortComboboxItemStateChanged

    private void btnSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchMouseClicked
        txtSearch = searchTextField.getText();
        if (txtSearch.equals("Nhập tên sản phẩm...")) {
            txtSearch = "";
        }
        sortId = 0;
        producerId = 0;
        turnOffAllActive();
        cardContentLayout.show(this.contentPanel, "XetaygaContent");
        sortCombobox.setSelectedIndex(0);
        producerFilterCombobox.setSelectedIndex(0);
        queryProduct(0, 0, 0);
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchMouseClicked

    private void labelHelloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelHelloMouseClicked
        if (account == null) {
            new LoginDialog(this, true).setVisible(true);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_labelHelloMouseClicked

    private void btnLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseClicked
        // TODO add your handling code here:
        logout();
    }//GEN-LAST:event_btnLogoutMouseClicked

    private void btnSubmitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubmitMouseClicked
        String feedbackContent = txtFeedback.getText();
        String uniId = UUID.randomUUID().toString();
        String sql = "insert into feedback(id, accountId, content) \n"
                + "values ('" + uniId + "', '" + account.getId() + "', '" + feedbackContent + "');";
        db.execStatment(sql);
        new FeedbackSuccessDialog(this).showDialog();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSubmitMouseClicked

    private boolean statusIsEditName = true;
    private void btnEditNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditNameMouseClicked
        // TODO add your handling code here:
        if (statusIsEditName) {
            btnEditName.setIcon(new ImageIcon(getClass().getResource("/resources/icons_save.png")));
            txtEditName.setEditable(true);
            statusIsEditName = !statusIsEditName;
        } else {
            btnEditName.setIcon(new ImageIcon(getClass().getResource("/resources/icons_edit_name.png")));
            txtEditName.setEditable(false);
            statusIsEditName = !statusIsEditName;
            String sql = "UPDATE profile\n"
                    + "SET name = '" + txtEditName.getText().trim() + "'\n"
                    + "WHERE id = " + account.getProfileId();
            db.execStatment(sql);
            labelHello.setText("Xin chào: " + txtEditName.getText().trim());
            account.setName(txtEditName.getText().trim());
            if (fileHandle.isExist()) {
                fileHandle.clear();
            }
        }
    }//GEN-LAST:event_btnEditNameMouseClicked

    private void btnChangePasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnChangePasswordMouseClicked
        // TODO add your handling code here:
        new ChangePassDialog(this, account);
    }//GEN-LAST:event_btnChangePasswordMouseClicked

    private void btnCardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCardMouseClicked
        // TODO add your handling code here:
        if (account == null) {
            new AlertUnknowAccountDialog(this).showAlert();
            return;
        }
        turnOffAllActive();
        this.cardContentLayout.show(this.contentPanel, "CardContent");
        String sql = "Select a.startTime, a.endTime, b.name as productName, b.cost as price\n"
                + "from lease a, product b, account d\n"
                + "where a.productId = b.id\n"
                + "and a.accountId = d.id\n"
                + "and d.id = '" + account.getId() + "'";
        ResultSet rs = db.query(sql);
        Vector rowData = new Vector();
        StringUtils su = new StringUtils();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Vector columnNames = new Vector();
        columnNames.add("Tên sản phẩm");
        columnNames.add("Ngày bắt đầu thuê");
        columnNames.add("Ngày kết thúc thuê");
        columnNames.add("Số ngày thuê");
        columnNames.add("Giá");
        try {
            while (rs.next()){
                long endTime = rs.getLong("endTime");
                long startTime = rs.getLong("startTime");
                Date dateEnd = new Date(endTime);
                if (dateEnd.before(new Date())) continue;
                Vector tempRow = new Vector();
                tempRow.add(rs.getString("productName"));
                tempRow.add(dateFormat.format(startTime));
                tempRow.add(dateFormat.format(endTime));
                long numOfDay = (endTime - startTime) / 86400000;
                tempRow.add(numOfDay);
                String price = su.convertToPrice(numOfDay * (rs.getInt("price")));
                tempRow.add(price + " VNĐ");
                rowData.add(tempRow);
            }
            CardTable.setModel(new DefaultTableModel(rowData, columnNames));
        } catch (SQLException ex) {
            Logger.getLogger(DesignFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCardMouseClicked

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
            java.util.logging.Logger.getLogger(DesignFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DesignFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DesignFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DesignFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DesignFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AccountContent;
    private javax.swing.JPanel ActiveAccount;
    private javax.swing.JPanel ActiveFeedback;
    private javax.swing.JPanel ActiveHome;
    private javax.swing.JPanel ActiveXeso;
    private javax.swing.JPanel ActiveXetayga;
    private javax.swing.JPanel CardContent;
    private javax.swing.JTable CardTable;
    private javax.swing.JPanel FeedbackContent;
    private javax.swing.JPanel HomeContent;
    private javax.swing.JPanel MenuItemAccount;
    private javax.swing.JPanel MenuItemFeedback;
    private javax.swing.JPanel MenuItemHome;
    private javax.swing.JPanel MenuItemXeso;
    private javax.swing.JPanel MenuItemXetayga;
    private javax.swing.JLabel Title;
    private javax.swing.JPanel ToolbarPanel;
    private javax.swing.JPanel XetaygaContent;
    private javax.swing.JPanel XetaygaListProduct;
    private javax.swing.JLabel banner;
    private javax.swing.JLabel btnCard;
    private javax.swing.JPanel btnChangePassword;
    private javax.swing.JLabel btnEditName;
    private javax.swing.JLabel btnLogout;
    private javax.swing.JLabel btnSearch;
    private javax.swing.JPanel btnSubmit;
    private javax.swing.JPanel contentPanel;
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
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelHello;
    private javax.swing.JPanel panelBackground;
    private javax.swing.JPanel panelMenu;
    private javax.swing.JComboBox<String> producerFilterCombobox;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JComboBox<String> sortCombobox;
    private javax.swing.JTextField txtEditName;
    private javax.swing.JTextField txtEditUsername;
    private javax.swing.JTextArea txtFeedback;
    // End of variables declaration//GEN-END:variables
}
