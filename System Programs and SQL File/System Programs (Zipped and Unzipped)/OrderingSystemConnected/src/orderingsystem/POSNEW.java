/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orderingsystem;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static orderingsystem.Login.lblType;
import static orderingsystem.frmInvMain.cmbInvAccount;





/**
 *
 * @author John
 */
public class POSNEW extends javax.swing.JFrame {

    /**
     * Creates new form POSNEW
     */
    // GLOBAL DECLARATIONS
    Connection con;
    PreparedStatement pst;
    PreparedStatement pst2;
    ResultSet rs;
    orderingsystem.frmInvMain frmInvMain = new orderingsystem.frmInvMain();
   
    
    
    public static int ctr_cups500 = 0;
    public static int qty_cups500 = 0;
    public static int ctr_cups700 = 0;
    public static int qty_cups700 = 0;
    public static int ctr_bott350 = 0;
    public static int qty_bott350 = 0;
    public static int ctr_cups350 = 0;
    public static int qty_cups350 = 0;
    public static int ctr_CND = 0;
    public static int qty_CND = 0;
    public static int ctr_FRSOL= 0;
    public static int qty_FRSOL = 0;
    public static int ctr_FRFRIEND= 0;
    public static int qty_FRFRIEND = 0;
    public static int ctr_WOCSOL= 0;
    public static int qty_WOCSOL = 0;
    public static int ctr_WOCSQD= 0;
    public static int qty_WOCSQD = 0;
    
    public static int refresh_ctr = 0;
    public static int dele_ctr = 0;
    
    public POSNEW() {
        initComponents();
        setExtendedState(getExtendedState() | POSNEW.MAXIMIZED_BOTH);
        
                
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        lblDate.setText(sdf.format(date));
         float currentTotal = Float.parseFloat(lblTotal.getText());
         float currentDiscount = Float.parseFloat(lblAppliedDiscount.getText());
         float finalTotal = (float) (currentTotal - currentDiscount);
         String fintotGet = String.valueOf(finalTotal);
         lblPaymentAmount.setText(fintotGet);
         int ctr = 1;
         String ctr1 = String.valueOf(ctr);
         lblRecNo.setText(ctr1);
         
         if (!"0.0".equals(lblTotal.getText()) && rdbtnSenior.isSelected()== true)
         {
            pwd();
            senior();
         }
   
         if (Login.lblType.getText() == "CASHIER"){
      
            btnGoToInventory.setEnabled(false);
         }
        
        if (jTable1.getRowCount() != 0){
                  
           Connect();
                  try{
                      pst= con.prepareStatement("delete from customer");

                  pst.executeUpdate();
                  
                  //POSNEW.qty_cups500 = 0;
                  }
                  
                  catch(Exception e){
                Logger.getLogger(POSNEW.class.getName()).log(Level.SEVERE, null, e);
                  }
                  
              }
        
        Connect();
        info_Table1();
        jTable1.setVisible(true);
        qty_cups500 = 0;
        ctr_cups500 = 0;
        qty_cups700 = 0;
        ctr_cups700 = 0;
        qty_bott350 = 0;
        ctr_bott350 = 0;
        qty_cups350 = 0;
        ctr_cups350 = 0;
        qty_CND = 0;
        ctr_CND = 0;
        qty_FRSOL = 0;
        ctr_FRSOL= 0;
        qty_FRFRIEND = 0;
        ctr_FRFRIEND = 0;
        qty_WOCSOL = 0;
        ctr_WOCSOL = 0;
        qty_WOCSQD = 0;
        ctr_WOCSQD = 0;
        refresh_ctr = 0; 
        
       
       
        int count = jTable1.getRowCount();
        int count1 = jTable1.getColumnCount();
        if(count == 0 || count1 == 0)
        {
            btnRefresh.setEnabled(false);
             btnConfirm.setEnabled(false);
        }
        else
        {
            btnRefresh.setEnabled(true);
           
        }
        String type = Login.lblType.getText();
        
        
        if(type == "MANAGER" && Login.ctrCombo !=0)
        {
        String unameNew = Login.uname; 
        POSNEW.cmboxAccount.insertItemAt(unameNew,0);
        POSNEW.cmboxAccount.setSelectedIndex(0); 
        }
        
        
    }
    

    public void Connect()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/ordering", "root", "");
        }
        
        catch (ClassNotFoundException ex){
            Logger.getLogger(POSNEW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(POSNEW.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
   
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGoToInventory = new javax.swing.JButton();
        btnConfirm = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        scrpaneMenu = new javax.swing.JScrollPane();
        pnlMenu = new javax.swing.JPanel();
        lblLychee = new javax.swing.JLabel();
        lblRedVelvet = new javax.swing.JLabel();
        lblPassion = new javax.swing.JLabel();
        lblTaro = new javax.swing.JLabel();
        lblBoba = new javax.swing.JLabel();
        lblCacao = new javax.swing.JLabel();
        lblMango = new javax.swing.JLabel();
        lblBlueberry = new javax.swing.JLabel();
        lblStrawberry = new javax.swing.JLabel();
        lblOreo = new javax.swing.JLabel();
        lblDarkWintermelon = new javax.swing.JLabel();
        lblNutella = new javax.swing.JLabel();
        lblRoastedSugar = new javax.swing.JLabel();
        lblWintermelon = new javax.swing.JLabel();
        lblWing = new javax.swing.JLabel();
        lblMilkyBoba = new javax.swing.JLabel();
        lblChic = new javax.swing.JLabel();
        lblFries = new javax.swing.JLabel();
        lblMatchiya = new javax.swing.JLabel();
        scrpaneListofOrders = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lblOrderNo = new javax.swing.JLabel();
        lblNote = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        lblNote1 = new javax.swing.JLabel();
        lblNote2 = new javax.swing.JLabel();
        lblRecNo = new javax.swing.JLabel();
        lblDate1 = new javax.swing.JLabel();
        lblListOfOrders = new javax.swing.JLabel();
        lblSOS = new javax.swing.JLabel();
        lblYellowBg = new javax.swing.JLabel();
        lblMenuLegends = new javax.swing.JLabel();
        lblLogo = new javax.swing.JLabel();
        lblPaymentAmount = new javax.swing.JLabel();
        lblAppliedDiscount = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        rdbtnPWD = new javax.swing.JRadioButton();
        rdbtnSenior = new javax.swing.JRadioButton();
        cmboxAccount = new javax.swing.JComboBox<>();
        lblPOSBg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(null);

        btnGoToInventory.setBackground(new java.awt.Color(252, 207, 44));
        btnGoToInventory.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        btnGoToInventory.setForeground(new java.awt.Color(255, 255, 255));
        btnGoToInventory.setText("GO TO INVENTORY");
        btnGoToInventory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGoToInventoryActionPerformed(evt);
            }
        });
        getContentPane().add(btnGoToInventory);
        btnGoToInventory.setBounds(995, 20, 170, 29);

        btnConfirm.setBackground(new java.awt.Color(226, 176, 1));
        btnConfirm.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        btnConfirm.setForeground(new java.awt.Color(255, 255, 255));
        btnConfirm.setText("CONFIRM ORDER");
        btnConfirm.setBorder(null);
        btnConfirm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnConfirmMouseClicked(evt);
            }
        });
        getContentPane().add(btnConfirm);
        btnConfirm.setBounds(1140, 460, 190, 40);

        btnRefresh.setBackground(new java.awt.Color(226, 176, 1));
        btnRefresh.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("REFRESH");
        btnRefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRefreshMouseClicked(evt);
            }
        });
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        getContentPane().add(btnRefresh);
        btnRefresh.setBounds(10, 644, 110, 30);

        btnDelete.setBackground(new java.awt.Color(255, 51, 51));
        btnDelete.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("DELETE");
        btnDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeleteMouseClicked(evt);
            }
        });
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        getContentPane().add(btnDelete);
        btnDelete.setBounds(10, 720, 110, 30);

        scrpaneMenu.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrpaneMenu.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        lblLychee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orderingsystem/menu4.png"))); // NOI18N
        lblLychee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLycheeMouseClicked(evt);
            }
        });

        lblRedVelvet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orderingsystem/menu9.png"))); // NOI18N
        lblRedVelvet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRedVelvetMouseClicked(evt);
            }
        });

        lblPassion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orderingsystem/menu5.png"))); // NOI18N
        lblPassion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPassionMouseClicked(evt);
            }
        });

        lblTaro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orderingsystem/menu14.png"))); // NOI18N
        lblTaro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTaroMouseClicked(evt);
            }
        });

        lblBoba.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orderingsystem/menu15.png"))); // NOI18N
        lblBoba.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBobaMouseClicked(evt);
            }
        });

        lblCacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orderingsystem/menu10.png"))); // NOI18N
        lblCacao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCacaoMouseClicked(evt);
            }
        });

        lblMango.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orderingsystem/menu3.png"))); // NOI18N
        lblMango.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMangoMouseClicked(evt);
            }
        });

        lblBlueberry.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orderingsystem/menu1.png"))); // NOI18N
        lblBlueberry.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBlueberryMouseClicked(evt);
            }
        });

        lblStrawberry.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orderingsystem/menu2.png"))); // NOI18N
        lblStrawberry.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblStrawberryMouseClicked(evt);
            }
        });

        lblOreo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orderingsystem/menu7.png"))); // NOI18N
        lblOreo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblOreoMouseClicked(evt);
            }
        });

        lblDarkWintermelon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orderingsystem/menu8.png"))); // NOI18N
        lblDarkWintermelon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDarkWintermelonMouseClicked(evt);
            }
        });

        lblNutella.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orderingsystem/menu6.png"))); // NOI18N
        lblNutella.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNutellaMouseClicked(evt);
            }
        });

        lblRoastedSugar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orderingsystem/menu13.png"))); // NOI18N
        lblRoastedSugar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRoastedSugarMouseClicked(evt);
            }
        });

        lblWintermelon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orderingsystem/menu11.png"))); // NOI18N
        lblWintermelon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblWintermelonMouseClicked(evt);
            }
        });

        lblWing.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orderingsystem/menu19.png"))); // NOI18N
        lblWing.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblWingMouseClicked(evt);
            }
        });

        lblMilkyBoba.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orderingsystem/menu16.png"))); // NOI18N
        lblMilkyBoba.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMilkyBobaMouseClicked(evt);
            }
        });

        lblChic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orderingsystem/menu17.png"))); // NOI18N
        lblChic.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblChicMouseClicked(evt);
            }
        });

        lblFries.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orderingsystem/menu18.png"))); // NOI18N
        lblFries.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblFriesMouseClicked(evt);
            }
        });

        lblMatchiya.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orderingsystem/menu12.png"))); // NOI18N
        lblMatchiya.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMatchiyaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
        pnlMenu.setLayout(pnlMenuLayout);
        pnlMenuLayout.setHorizontalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMenuLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBlueberry, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNutella, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMilkyBoba, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblWintermelon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlMenuLayout.createSequentialGroup()
                        .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblOreo, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblStrawberry, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMatchiya, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlMenuLayout.createSequentialGroup()
                                .addComponent(lblDarkWintermelon)
                                .addGap(18, 18, 18)
                                .addComponent(lblRedVelvet))
                            .addGroup(pnlMenuLayout.createSequentialGroup()
                                .addComponent(lblMango)
                                .addGap(18, 18, 18)
                                .addComponent(lblLychee))
                            .addGroup(pnlMenuLayout.createSequentialGroup()
                                .addComponent(lblRoastedSugar)
                                .addGap(18, 18, 18)
                                .addComponent(lblTaro, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlMenuLayout.createSequentialGroup()
                        .addComponent(lblChic, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblFries, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblWing, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCacao, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPassion, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBoba, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblMango, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblLychee, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPassion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblStrawberry, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblBlueberry, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23)
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblOreo, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDarkWintermelon, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNutella, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRedVelvet, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCacao, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMenuLayout.createSequentialGroup()
                        .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblBoba, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblRoastedSugar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMatchiya, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTaro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(28, 28, 28)
                        .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblFries, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblChic, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblWing, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlMenuLayout.createSequentialGroup()
                        .addComponent(lblWintermelon)
                        .addGap(27, 27, 27)
                        .addComponent(lblMilkyBoba, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        scrpaneMenu.setViewportView(pnlMenu);

        getContentPane().add(scrpaneMenu);
        scrpaneMenu.setBounds(10, 100, 1080, 460);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Order #", "Quantity", "Product Code", "Amount", "Special Instructions"
            }
        ));
        scrpaneListofOrders.setViewportView(jTable1);

        getContentPane().add(scrpaneListofOrders);
        scrpaneListofOrders.setBounds(130, 590, 1220, 160);

        lblOrderNo.setFont(new java.awt.Font("Arial Narrow", 1, 18)); // NOI18N
        lblOrderNo.setForeground(new java.awt.Color(255, 255, 255));
        lblOrderNo.setText("ORDER NO.");
        getContentPane().add(lblOrderNo);
        lblOrderNo.setBounds(20, 580, 100, 21);

        lblNote.setFont(new java.awt.Font("Arial Narrow", 1, 10)); // NOI18N
        lblNote.setForeground(new java.awt.Color(102, 102, 102));
        lblNote.setText("the list of orders)");
        getContentPane().add(lblNote);
        lblNote.setBounds(30, 666, 200, 70);

        lblDate.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        lblDate.setForeground(new java.awt.Color(51, 51, 51));
        getContentPane().add(lblDate);
        lblDate.setBounds(710, 27, 100, 20);

        lblNote1.setFont(new java.awt.Font("Arial Narrow", 1, 10)); // NOI18N
        lblNote1.setForeground(new java.awt.Color(102, 102, 102));
        lblNote1.setText("every new order to update ");
        getContentPane().add(lblNote1);
        lblNote1.setBounds(10, 666, 250, 50);

        lblNote2.setFont(new java.awt.Font("Arial Narrow", 1, 10)); // NOI18N
        lblNote2.setForeground(new java.awt.Color(102, 102, 102));
        lblNote2.setText("(Please click REFRESH for");
        getContentPane().add(lblNote2);
        lblNote2.setBounds(10, 656, 180, 50);

        lblRecNo.setFont(new java.awt.Font("Bahnschrift", 1, 28)); // NOI18N
        lblRecNo.setForeground(new java.awt.Color(51, 51, 51));
        lblRecNo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRecNo.setText("1");
        getContentPane().add(lblRecNo);
        lblRecNo.setBounds(20, 600, 87, 40);

        lblDate1.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        lblDate1.setForeground(new java.awt.Color(51, 51, 51));
        lblDate1.setText("DATE:");
        getContentPane().add(lblDate1);
        lblDate1.setBounds(660, 27, 60, 20);

        lblListOfOrders.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orderingsystem/list of orders.png"))); // NOI18N
        getContentPane().add(lblListOfOrders);
        lblListOfOrders.setBounds(1130, 520, 280, 110);

        lblSOS.setFont(new java.awt.Font("Bahnschrift", 0, 40)); // NOI18N
        lblSOS.setForeground(new java.awt.Color(255, 255, 255));
        lblSOS.setText("SUNTEA ORDERING SYSTEM");
        getContentPane().add(lblSOS);
        lblSOS.setBounds(90, -3, 720, 60);

        lblYellowBg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orderingsystem/Yellow.png"))); // NOI18N
        lblYellowBg.setText("jLabel2");
        getContentPane().add(lblYellowBg);
        lblYellowBg.setBounds(-10, 574, 1380, 300);

        lblMenuLegends.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orderingsystem/LABEL (POS).png"))); // NOI18N
        getContentPane().add(lblMenuLegends);
        lblMenuLegends.setBounds(50, 20, 1110, 100);

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orderingsystem/logosmall.png"))); // NOI18N
        lblLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLogoMouseClicked(evt);
            }
        });
        getContentPane().add(lblLogo);
        lblLogo.setBounds(10, -10, 80, 110);

        lblPaymentAmount.setFont(new java.awt.Font("Agency FB", 1, 40)); // NOI18N
        lblPaymentAmount.setForeground(new java.awt.Color(255, 255, 255));
        lblPaymentAmount.setText("0.0");
        getContentPane().add(lblPaymentAmount);
        lblPaymentAmount.setBounds(1240, 383, 120, 40);

        lblAppliedDiscount.setFont(new java.awt.Font("Agency FB", 1, 40)); // NOI18N
        lblAppliedDiscount.setForeground(new java.awt.Color(255, 255, 255));
        lblAppliedDiscount.setText("0.0");
        getContentPane().add(lblAppliedDiscount);
        lblAppliedDiscount.setBounds(1240, 305, 120, 70);

        lblTotal.setFont(new java.awt.Font("Agency FB", 1, 40)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblTotal.setText("0.0");
        getContentPane().add(lblTotal);
        lblTotal.setBounds(1240, 252, 120, 50);

        rdbtnPWD.setFont(new java.awt.Font("Agency FB", 1, 24)); // NOI18N
        rdbtnPWD.setForeground(new java.awt.Color(255, 255, 255));
        rdbtnPWD.setText("PWD");
        rdbtnPWD.setOpaque(false);
        rdbtnPWD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbtnPWDActionPerformed(evt);
            }
        });
        getContentPane().add(rdbtnPWD);
        rdbtnPWD.setBounds(1130, 150, 140, 20);

        rdbtnSenior.setFont(new java.awt.Font("Agency FB", 1, 24)); // NOI18N
        rdbtnSenior.setForeground(new java.awt.Color(255, 255, 255));
        rdbtnSenior.setText("Senior Citizen");
        rdbtnSenior.setOpaque(false);
        rdbtnSenior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbtnSeniorActionPerformed(evt);
            }
        });
        getContentPane().add(rdbtnSenior);
        rdbtnSenior.setBounds(1130, 120, 140, 20);

        cmboxAccount.setBackground(new java.awt.Color(252, 207, 44));
        cmboxAccount.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        cmboxAccount.setForeground(new java.awt.Color(255, 255, 255));
        cmboxAccount.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Log Out" }));
        cmboxAccount.setBorder(null);
        cmboxAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmboxAccountMouseClicked(evt);
            }
        });
        cmboxAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmboxAccountActionPerformed(evt);
            }
        });
        getContentPane().add(cmboxAccount);
        cmboxAccount.setBounds(1186, 20, 160, 40);

        lblPOSBg.setFont(new java.awt.Font("Agency FB", 1, 40)); // NOI18N
        lblPOSBg.setForeground(new java.awt.Color(255, 255, 255));
        lblPOSBg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orderingsystem/last na talaga.jpg"))); // NOI18N
        lblPOSBg.setText("0.00");
        getContentPane().add(lblPOSBg);
        lblPOSBg.setBounds(0, -10, 1400, 790);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRefreshMouseClicked

        Connect();
        info_Table1();
        jTable1.setVisible(true);
        
        int count = jTable1.getRowCount();
        int count1 = jTable1.getColumnCount();
        if(count == 0 || count1 == 0)
        {
            btnConfirm.setEnabled(false);
            btnRefresh.setEnabled(false);
        }
        else
        {
            btnConfirm.setEnabled(true);
            btnRefresh.setEnabled(true);
        }
        
        
       
    }//GEN-LAST:event_btnRefreshMouseClicked

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        pwd();
        senior();
        float currentTotal = Float.parseFloat(lblTotal.getText());
        float currentDiscount = Float.parseFloat(lblAppliedDiscount.getText());
        float finalTotal = (float) (currentTotal - currentDiscount);
        String fintotGet = String.valueOf(finalTotal);
        lblPaymentAmount.setText(fintotGet);
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteMouseClicked

        dele_ctr++;
        if (jTable1.getRowCount() == 0){
            new NoRecordToDelete().setVisible(true);
        }

        else{

            try{

                if(jTable1.getSelectionModel().isSelectionEmpty() == false){
                    int row = jTable1.getSelectedRow();
                    String cell = jTable1.getModel().getValueAt(row,0).toString();
                    String min = jTable1.getModel().getValueAt(row,3).toString();
                    String qty = jTable1.getModel().getValueAt(row,1).toString();
                    String product = jTable1.getModel().getValueAt(row,2).toString();
                    
                    int updatedQty = Integer.parseInt(qty);
 
                    
                    if(product.equals("FLEXIBOBAD500")){
                      //qty_cups500 + updatedQty;
                    qty_cups500 = qty_cups500 + updatedQty;
                    System.out.print("after delete");
                    System.out.print(qty_cups500);
                    
                    
                    }
                    
                    else if(product.equals("FLEXIBOBAD700")){
                     
                    int newcups700 = qty_cups700 + updatedQty;
                    qty_cups700 = newcups700;
                    
                    
                    }
                    else if(product.equals("FLEXIMATCH500")){
                     
                    int newcups500 = qty_cups500 + updatedQty;
                    qty_cups500 = newcups500;
                    
                    
                    }
                    else if(product.equals("FLEXIMATCH700")){
                     
                    int newcups700 = qty_cups700 + updatedQty;
                    qty_cups700 = newcups700;
                    
                    
                    }
                    else if(product.equals("FLEXISUGAR500")){
                     
                    int newcups500 = qty_cups500 + updatedQty;
                    qty_cups500 = newcups500;
                    
                    
                    }
                    else if(product.equals("FLEXISUGAR700")){
                     
                    int newcups700 = qty_cups700 + updatedQty;
                    qty_cups700 = newcups700;
                    
                    
                    }
                    else if(product.equals("FLEXITAROD500")){
                     
                    int newcups500 = qty_cups500 + updatedQty;
                    qty_cups500 = newcups500;
                    
                    
                    }
                    else if(product.equals("FLEXITAROD700")){
                     
                    int newcups700 = qty_cups700 + updatedQty;
                    qty_cups700 = newcups700;
                    
                    
                    }
                    else if(product.equals("FLEXIWINTE500")){
                     
                    int newcups500 = qty_cups500 + updatedQty;
                    qty_cups500 = newcups500;
                    
                    
                    }
                    else if(product.equals("FLEXIWINTE700")){
                     
                    int newcups700 = qty_cups700 + updatedQty;
                    qty_cups700 = newcups700;
                    
                    
                    }
                    else if(product.equals("INFBLUEBERRYD")){
                     
                    int newbott350 = qty_bott350 + updatedQty;
                    qty_bott350 = newbott350;
                    
                    
                    }
                    else if(product.equals("INFLYCHEED500")){
                     
                    int newcups500 = qty_cups500 + updatedQty;
                    qty_cups500 = newcups500;
                    
                    
                    }
                    else if(product.equals("INFLYCHEED700")){
                     
                    int newcups700 = qty_cups700 + updatedQty;
                    qty_cups700 = newcups700;
                    
                    
                    }
                    else if(product.equals("INFMANGODRINK")){
                     
                    int newbott350 = qty_bott350 + updatedQty;
                    qty_bott350 = newbott350;
                    
                    
                    }
                    else if(product.equals("INFPASSION500")){
                     
                    int newcups500 = qty_cups500 + updatedQty;
                    qty_cups500 = newcups500;
                    
                    
                    }
                    else if(product.equals("INFPASSION700")){
                     
                    int newcups700 = qty_cups700 + updatedQty;
                    qty_cups700 = newcups700;
                    
                    
                    }
                   else if(product.equals("INFSTRAWBERRY")){
                     
                    int newbott350 = qty_bott350 + updatedQty;
                    qty_bott350 = newbott350;
                    
                    
                    }
                   else if(product.equals("MAXICACAOD500")){
                     
                    int newcups500 = qty_cups500 + updatedQty;
                    qty_cups500 = newcups500;
                    
                    
                    }
                    else if(product.equals("MAXICACAOD700")){
                     
                    int newcups700 = qty_cups700 + updatedQty;
                    qty_cups700 = newcups700;
                    
                    
                    }
                    
                    else if(product.equals("MAXIWINTER500")){
                     
                    int newcups500 = qty_cups500 + updatedQty;
                    qty_cups500 = newcups500;
                    
                    
                    }
                    else if(product.equals("MAXIWINTER700")){
                     
                    int newcups700 = qty_cups700 + updatedQty;
                    qty_cups700 = newcups700;
                    
                    
                    }
                    else if(product.equals("MAXINUTELL500")){
                     
                    int newcups500 = qty_cups500 + updatedQty;
                    qty_cups500 = newcups500;
                    
                    
                    }
                    else if(product.equals("MAXINUTELL700")){
                     
                    int newcups700 = qty_cups700 + updatedQty;
                    qty_cups700 = newcups700;
                    
                    
                    }
                    else if(product.equals("MAXIOREOOV500")){
                     
                    int newcups500 = qty_cups500 + updatedQty;
                    qty_cups500 = newcups500;
                    
                    
                    }
                    else if(product.equals("MAXIOREOOV700")){
                     
                    int newcups700 = qty_cups700 + updatedQty;
                    qty_cups700 = newcups700;
                    
                    
                    }
                    else if(product.equals("MAXIVELVET500")){
                     
                    int newcups500 = qty_cups500 + updatedQty;
                    qty_cups500 = newcups500;
                    
                    
                    }
                    else if(product.equals("MAXIVELVET700")){
                     
                    int newcups700 = qty_cups700 + updatedQty;
                    qty_cups700 = newcups700;
                    
                    
                    }
                    else if(product.equals("PREMMILKBROWN")){
                     
                    int newcups350 = qty_cups350 + updatedQty;
                    qty_cups350 = newcups350;
                    
                    
                    }
                    else if(product.equals("SNACKCHICNDIP")){
                     
                    int new_cnd = qty_CND + updatedQty;
                    qty_CND = new_cnd;
                    
                    
                    }
                    else if(product.equals("SNACKFRIESSOL")){
                     
                    int frsol = qty_FRSOL + updatedQty;
                    qty_FRSOL = frsol;
                    
                    
                    }
                    else if(product.equals("SNACKFIRESFRS")){
                     
                    int frfriend = qty_FRFRIEND + updatedQty;
                    qty_FRFRIEND = frfriend;
                  
                    
                    }
                    else if(product.equals("SNACKWOCLSOLO")){
                     
                    int wocsol = qty_WOCSOL + updatedQty;
                    qty_WOCSOL = wocsol;
                    
                    
                    }
                    else if(product.equals("SNACKWOCLSQAD")){
                     
                    int wocsqd = qty_WOCSQD + updatedQty;
                    qty_WOCSQD = wocsqd;
                    
                    
                    }
                    refresh_ctr = refresh_ctr - 1;
                    
                    pst= con.prepareStatement("delete from customer where Record_num =" + cell);

                    pst.executeUpdate();
                    
                    

                    String OldTotal = lblTotal.getText();
                    String MinusTotal = min;
                    float old = Float.parseFloat(OldTotal);
                    float new1 = Float.parseFloat(MinusTotal);
                    

                    if (old==0 || old==0.0 || old==0.00){
                        lblTotal.setText(OldTotal);
                    }
                    else{
                        float new2 = (old-new1);
                        String new2Get = String.valueOf(new2);
                        lblTotal.setText(new2Get);
                        
                        
                        
                        float currentDiscount1 = Float.parseFloat(POSNEW.lblAppliedDiscount.getText());
        float finalTotal1 = (float) (new2 - currentDiscount1);
        String fintot1Get = String.valueOf(finalTotal1);
        POSNEW.lblPaymentAmount.setText(fintot1Get);
        
        if (POSNEW.rdbtnSenior.isSelected()== true || POSNEW.rdbtnPWD.isSelected()== true)
         {
            
            float new4 = (float) (new2 * 0.20);
            String new4Get = String.valueOf(new4);
            POSNEW.lblAppliedDiscount.setText(new4Get);
            
           
            
         
            float finalTotal = (float) (new2 - new4);
            String fintotGet = String.valueOf(finalTotal);
            POSNEW.lblPaymentAmount.setText(fintotGet);
         }
        
     
                    }
                    new RecordDeleted().setVisible(true);
                }

                else if (jTable1.getSelectionModel().isSelectionEmpty()){
                    new SelectToDelete().setVisible(true);
                }

            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_btnDeleteMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed

    }//GEN-LAST:event_btnDeleteActionPerformed

    private void lblLycheeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLycheeMouseClicked
        new InfLOrdering().setVisible(true);
    }//GEN-LAST:event_lblLycheeMouseClicked

    private void lblRedVelvetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRedVelvetMouseClicked
        new MaxROrdering().setVisible(true);
    }//GEN-LAST:event_lblRedVelvetMouseClicked

    private void lblPassionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPassionMouseClicked
        new InfPOrdering().setVisible(true);
    }//GEN-LAST:event_lblPassionMouseClicked

    private void lblTaroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTaroMouseClicked
        new FlxTOrdering().setVisible(true);
    }//GEN-LAST:event_lblTaroMouseClicked

    private void lblBobaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBobaMouseClicked

        new FlxBOrdering().setVisible(true);
    }//GEN-LAST:event_lblBobaMouseClicked

    private void lblCacaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCacaoMouseClicked
        new MaxCOrdering().setVisible(true);
    }//GEN-LAST:event_lblCacaoMouseClicked

    private void lblMangoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMangoMouseClicked
        new InfMOrdering().setVisible(true);
    }//GEN-LAST:event_lblMangoMouseClicked

    private void lblBlueberryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBlueberryMouseClicked
        new InfBlOrdering().setVisible(true);
    }//GEN-LAST:event_lblBlueberryMouseClicked

    private void lblStrawberryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblStrawberryMouseClicked
        new InfSOrdering().setVisible(true);
    }//GEN-LAST:event_lblStrawberryMouseClicked

    private void lblOreoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblOreoMouseClicked
        new MaxOOrdering().setVisible(true);
    }//GEN-LAST:event_lblOreoMouseClicked

    private void lblDarkWintermelonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDarkWintermelonMouseClicked
        new MaxDOrdering().setVisible(true);
    }//GEN-LAST:event_lblDarkWintermelonMouseClicked

    private void lblNutellaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNutellaMouseClicked
        new MaxNOrdering().setVisible(true);
    }//GEN-LAST:event_lblNutellaMouseClicked

    private void lblRoastedSugarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRoastedSugarMouseClicked
        new FlxROrdering().setVisible(true);
    }//GEN-LAST:event_lblRoastedSugarMouseClicked

    private void lblWintermelonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblWintermelonMouseClicked
        new FlxWOrdering().setVisible(true);
    }//GEN-LAST:event_lblWintermelonMouseClicked

    private void lblWingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblWingMouseClicked
        new SnkWOrdering().setVisible(true);
    }//GEN-LAST:event_lblWingMouseClicked

    private void lblMilkyBobaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMilkyBobaMouseClicked
        new PrmMOrdering().setVisible(true);
    }//GEN-LAST:event_lblMilkyBobaMouseClicked

    private void lblChicMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblChicMouseClicked
        new SnkCOrdering().setVisible(true);
    }//GEN-LAST:event_lblChicMouseClicked

    private void lblFriesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFriesMouseClicked
        new SnkFOrdering().setVisible(true);
    }//GEN-LAST:event_lblFriesMouseClicked

    private void lblMatchiyaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMatchiyaMouseClicked
        new FlxMOrdering().setVisible(true);
    }//GEN-LAST:event_lblMatchiyaMouseClicked

    private void lblLogoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogoMouseClicked
     
    }//GEN-LAST:event_lblLogoMouseClicked

    private void rdbtnPWDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbtnPWDActionPerformed
        pwd();
    }//GEN-LAST:event_rdbtnPWDActionPerformed

    private void rdbtnSeniorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbtnSeniorActionPerformed
        senior();
    }//GEN-LAST:event_rdbtnSeniorActionPerformed

    private void cmboxAccountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmboxAccountMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cmboxAccountMouseClicked

    private void cmboxAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmboxAccountActionPerformed
         String choice = (String)cmboxAccount.getSelectedItem();
          
          if (choice.equals("Log Out"))
          {
              new ConfirmLogout().setVisible(true);
              this.dispose();
          }
          
          
    }//GEN-LAST:event_cmboxAccountActionPerformed

    private void btnConfirmMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConfirmMouseClicked
        int tableCount = jTable1.getRowCount();
        int count = jTable1.getRowCount();
        int count1 = jTable1.getColumnCount();
        
        if(count == 0 || count1 == 0)
        {
            btnConfirm.setEnabled(false);
        }
        else
        {
            if(refresh_ctr == tableCount)
            {
                btnConfirm.setEnabled(true);
                new Calcu().setVisible(true);
            }
            else
            {
              btnConfirm.setEnabled(false);
            }
        }
       
        
    }//GEN-LAST:event_btnConfirmMouseClicked

    private void btnGoToInventoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGoToInventoryActionPerformed
        // TODO add your handling code here:
        if (Login.ctrCombo !=0)
        {
        frmInvMain.cmbInvAccount.insertItemAt(Login.uname,0);
        frmInvMain.cmbInvAccount.setSelectedIndex(0);
        }
        dispose();
        frmInvMain.setVisible(true);
        frmInvMain.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnGoToInventoryActionPerformed
    public void senior(){
        if(rdbtnSenior.isSelected()){    
            String OldTotal = lblTotal.getText();
            float total = Float.parseFloat(OldTotal);
            float new2 = (float) (total * 0.20);
            String new2Get = String.valueOf(new2);
            lblAppliedDiscount.setText(new2Get);
            float currentTotal = Float.parseFloat(lblTotal.getText());
            float currentDiscount = Float.parseFloat(new2Get);
            float finalTotal = (float) (currentTotal - currentDiscount);
            String fintotGet = String.valueOf(finalTotal);
            lblPaymentAmount.setText(fintotGet);
            
            String customer_name = "Senior Citizen";

            Connect();
        try {
            pst = con.prepareStatement("update customer set Discount = ?");
            pst.setString(1, customer_name);

            pst.executeUpdate();

            
          
        } catch (SQLException ex) {
            Logger.getLogger(POSNEW.class.getName()).log(Level.SEVERE, null, ex);
        }
                
      }
      else if(rdbtnPWD.isSelected() && rdbtnSenior.isSelected())
            {
               
                String customer_name = "Senior Citizen";

                
            Connect();
        try {
            pst = con.prepareStatement("update customer set Discount = ?");
            pst.setString(1, customer_name);
            pst.executeUpdate();
            

            
          
        } catch (SQLException ex) {
            Logger.getLogger(POSNEW.class.getName()).log(Level.SEVERE, null, ex);
        }   
            }
      else if(rdbtnPWD.isSelected()==false && rdbtnSenior.isSelected()==false)
            {
                
                lblAppliedDiscount.setText("0.0");
                float currentTotal = Float.parseFloat(lblTotal.getText());
                float currentDiscount = Float.parseFloat(lblAppliedDiscount.getText());
                float finalTotal = (float) (currentTotal - currentDiscount);
            String fintotGet = String.valueOf(finalTotal);
            lblPaymentAmount.setText(fintotGet);
                 String customer_name = "none";
                 
            Connect();
        try {
            pst = con.prepareStatement("update customer set Discount = ?");
            pst.setString(1, customer_name);
            pst.executeUpdate();

            
          
        } catch (SQLException ex) {
            Logger.getLogger(POSNEW.class.getName()).log(Level.SEVERE, null, ex);
        }
            }
    }    public  void pwd(){
        if(rdbtnPWD.isSelected()){      
            String OldTotal = lblTotal.getText();
            float total = Float.parseFloat(OldTotal);
            float new2 = (float) (total * 0.20);
            String new2Get = String.valueOf(new2);
            lblAppliedDiscount.setText(new2Get);
            
            float currentTotal = Float.parseFloat(lblTotal.getText());
            float currentDiscount = Float.parseFloat(new2Get);
            float finalTotal = (float) (currentTotal - currentDiscount);
            String fintotGet = String.valueOf(finalTotal);
            lblPaymentAmount.setText(fintotGet);
            
             String customer_name = "PWD";

            Connect();
        try {
            pst = con.prepareStatement("update customer set Discount = ?");
            pst.setString(1, customer_name);
            pst.executeUpdate();

            
          
        } catch (SQLException ex) {
            Logger.getLogger(POSNEW.class.getName()).log(Level.SEVERE, null, ex);
        }
                
      }
     else if(rdbtnPWD.isSelected() && rdbtnSenior.isSelected())
            {
              String customer_name = "Senior Citizen";
            Connect();
        try {
            pst = con.prepareStatement("update customer set Discount = ?");
            pst.setString(1, customer_name);
            pst.executeUpdate();

            
          
        } catch (SQLException ex) {
            Logger.getLogger(POSNEW.class.getName()).log(Level.SEVERE, null, ex);
        }   
            } 
     
      else if(rdbtnPWD.isSelected()==false && rdbtnSenior.isSelected()==false)
            {
                 
            lblAppliedDiscount.setText("0.0");
            float currentTotal = Float.parseFloat(lblTotal.getText());
            float currentDiscount = Float.parseFloat(lblAppliedDiscount.getText());
            float finalTotal = (float) (currentTotal - currentDiscount);
            String fintotGet = String.valueOf(finalTotal);
            lblPaymentAmount.setText(fintotGet);
                 String customer_name = "none";

            Connect();
        try {
            pst = con.prepareStatement("update customer set Discount = ?");
            pst.setString(1, customer_name);
            pst.executeUpdate();

            
          
        } catch (SQLException ex) {
            Logger.getLogger(POSNEW.class.getName()).log(Level.SEVERE, null, ex);
        }
            } 
    }     
    void info_Table1(){
         try {
            
            pst = con.prepareStatement("select * from customer");
            rs = pst.executeQuery();
            ResultSetMetaData Rsm = rs.getMetaData();
            int colCount;
            colCount = Rsm.getColumnCount();
            
            DefaultTableModel df = (DefaultTableModel)jTable1.getModel();
            df.setRowCount(0);
            
            
            while(rs.next())
            {
                Vector v2 = new Vector();
                
                for(int i = 1; i <= colCount; i++)
                {
                v2.add(rs.getString("Record_num"));
                v2.add(rs.getString("Qty"));
                v2.add(rs.getString("Prod_Code"));
                v2.add(rs.getString("Amt")); 
                v2.add(rs.getString("Instructions")); 
                }
                
                df.addRow(v2);
            }
            
        }catch (SQLException ex) {
            Logger.getLogger(POSNEW.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
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
            java.util.logging.Logger.getLogger(POSNEW.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(POSNEW.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(POSNEW.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(POSNEW.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new POSNEW().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnConfirm;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnGoToInventory;
    public static javax.swing.JButton btnRefresh;
    public static javax.swing.JComboBox<String> cmboxAccount;
    public static javax.swing.JTable jTable1;
    public static javax.swing.JLabel lblAppliedDiscount;
    private javax.swing.JLabel lblBlueberry;
    private javax.swing.JLabel lblBoba;
    private javax.swing.JLabel lblCacao;
    private javax.swing.JLabel lblChic;
    private javax.swing.JLabel lblDarkWintermelon;
    public static javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblDate1;
    private javax.swing.JLabel lblFries;
    private javax.swing.JLabel lblListOfOrders;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblLychee;
    private javax.swing.JLabel lblMango;
    private javax.swing.JLabel lblMatchiya;
    private javax.swing.JLabel lblMenuLegends;
    private javax.swing.JLabel lblMilkyBoba;
    private javax.swing.JLabel lblNote;
    private javax.swing.JLabel lblNote1;
    private javax.swing.JLabel lblNote2;
    private javax.swing.JLabel lblNutella;
    private javax.swing.JLabel lblOrderNo;
    private javax.swing.JLabel lblOreo;
    private javax.swing.JLabel lblPOSBg;
    private javax.swing.JLabel lblPassion;
    public static javax.swing.JLabel lblPaymentAmount;
    public static javax.swing.JLabel lblRecNo;
    private javax.swing.JLabel lblRedVelvet;
    private javax.swing.JLabel lblRoastedSugar;
    private javax.swing.JLabel lblSOS;
    private javax.swing.JLabel lblStrawberry;
    private javax.swing.JLabel lblTaro;
    public static javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblWing;
    private javax.swing.JLabel lblWintermelon;
    private javax.swing.JLabel lblYellowBg;
    private javax.swing.JPanel pnlMenu;
    public static javax.swing.JRadioButton rdbtnPWD;
    public static javax.swing.JRadioButton rdbtnSenior;
    private javax.swing.JScrollPane scrpaneListofOrders;
    private javax.swing.JScrollPane scrpaneMenu;
    // End of variables declaration//GEN-END:variables
}
