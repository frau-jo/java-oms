/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orderingsystem;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author VALLE & SOLON
 */
public class frmInvMain extends javax.swing.JFrame {
    //GLOBAL DECLARATIONS
    frmInvEdit1 frmEdit = new frmInvEdit1();
    frmInvDeduct1 frmDeduct = new frmInvDeduct1();
    frmInvAdd1 frmAdd = new frmInvAdd1();
    frmView frmView = new frmView();
    frmMessage frmMessage = new frmMessage();  
    //orderingsystem.POSNEW pos = new orderingsystem.POSNEW();


    /**
     * Creates new form frmInvMain
     */
    public frmInvMain(){        
        initComponents();
        
       
        // NOT VISIBLE
        btnDeduct.setVisible(false);
        // Textfields
        txtSearchIn.setVisible(false);
        txtSearchReport.setVisible(false);
        txtSearchRestock.setVisible(false);
        txtSearchCheck.setVisible(false);
        // Buttons
        btnSearch.setVisible(false);
        btnSearchReports.setVisible(false);
        btnSearchRestock.setVisible(false);
        btnSearchCheck.setVisible(false);
        // Reports tab Date
        lblDFromR.setVisible(false);
        lblDToR.setVisible(false);
        txtDateFromR.setVisible(false);
        txtDateToR.setVisible(false);
        // Checking tab Date
        lblDFrom.setVisible(false);
        lblDTo.setVisible(false);
        txtDateFrom.setVisible(false);
        txtDateTo.setVisible(false);
        
        refreshTables();  
        
        
       
    }
    public void refreshTables()
    {
        btnEdit.setVisible(false);
        btnView.setVisible(false);
        btnDeduct.setVisible(false);
        Date date = new Date();
        SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");
        txtDate.setText(sformat.format(date));
    
   try {
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ordering","root","")) {
                PreparedStatement ps = con.prepareStatement("select * from supplies");
                PreparedStatement pstemp = con.prepareStatement("select ITEM_CODE, ITEM_QTY, STOCK_LEVEL from supplies");
                ResultSet rstemp = pstemp.executeQuery();
                
                while(rstemp.next())
                {
                    String[] strITEM_CODE = {rstemp.getString("ITEM_CODE")};
                    if(rstemp.getInt("ITEM_QTY") < rstemp.getInt("STOCK_LEVEL") && rstemp.getInt("ITEM_QTY") != 0)
                    {
                        String update = "update supplies set ITEM_STATUS = 'Low on Stock' where ITEM_CODE LIKE'"+strITEM_CODE[0]+"'";
                        PreparedStatement pst = con.prepareStatement(update);
                        pst.executeUpdate();
                    }
                    else if(rstemp.getInt("ITEM_QTY") == 0)
                    {
                        String update = "update supplies set ITEM_STATUS = 'Out of Stock' where ITEM_CODE LIKE'"+strITEM_CODE[0]+"'";
                        PreparedStatement pst = con.prepareStatement(update);
                        pst.executeUpdate();
                    }
                    else if(rstemp.getInt("ITEM_QTY") > rstemp.getInt("STOCK_LEVEL"))
                    {
                        String update = "update supplies set ITEM_STATUS = 'In Stock' where ITEM_CODE LIKE'"+strITEM_CODE[0]+"'";
                        PreparedStatement pst = con.prepareStatement(update);
                        pst.executeUpdate();
                    }
                } 
                
                ResultSet rs = ps.executeQuery();                
                DefaultTableModel dtm = (DefaultTableModel)tblInStock.getModel();
                dtm.setRowCount(0);
                
                        while(rs.next())
                        {
                            Object[] objItem = {rs.getString("ITEM_CODE"),rs.getString("ITEM_NAME"),rs.getInt("ITEM_QTY"),rs.getString("ITEM_STATUS"),rs.getString("ITEM_CATEGORY"),rs.getDate("DATE_UPDATED")};
                            dtm.addRow(objItem);
                        }
            con.close();
            }
        }
    catch(SQLException ex){
            JOptionPane.showMessageDialog(this,ex);
        }
   
    try{
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ordering","root","")) {
            //PreparedStatement ps = con.prepareStatement("SELECT s.ITEM_CODE, s.ITEM_NAME, s.ITEM_CATEGORY, ITEM_QTY, IFNULL(itemss.QUANTITY,0) as QUANTITY, s.DATE_UPDATED FROM supplies s LEFT JOIN (SELECT ITEM_Code, QUANTITY FROM activity) as itemss ON s.ITEM_CODE = itemss.ITEM_Code GROUP BY s.ITEM_CODE");
            PreparedStatement ps = con.prepareStatement("SELECT s.ITEM_CODE, s.ITEM_NAME, s.ITEM_CATEGORY, ITEM_QTY, s.DATE_UPDATED FROM supplies s GROUP BY s.ITEM_CODE");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel dtm = (DefaultTableModel)tblRestock.getModel();
            dtm.setRowCount(0);
            while(rs.next())
            {
                Object[] objItem = {rs.getString("ITEM_CODE"),rs.getString("ITEM_NAME"),rs.getString("ITEM_CATEGORY"),rs.getInt("ITEM_QTY"), rs.getDate("DATE_UPDATED")};
                dtm.addRow(objItem);
            }
            con.close();
        }
 
    }
    catch(SQLException e)
    {
       JOptionPane.showMessageDialog(this,e);
    }
    try{
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ordering","root","")) {
                PreparedStatement ps = con.prepareStatement("SELECT a.ITEM_Code, itemss.ITEM_NAME, a.QUANTITY, a.ACTIVITY, a.DATE, a.Remarks FROM activity a LEFT JOIN (SELECT ITEM_CODE, ITEM_NAME FROM supplies) AS itemss ON a.ITEM_Code = itemss.ITEM_CODE ORDER BY a.DATE DESC");
                ResultSet rs = ps.executeQuery();
                DefaultTableModel dtm = (DefaultTableModel)tblReports.getModel();
                dtm.setRowCount(0);
                while(rs.next())
                {
                    Object[] objItem = {rs.getString("ITEM_Code"),rs.getString("ITEM_NAME"),rs.getInt("QUANTITY"),rs.getString("ACTIVITY"),rs.getDate("DATE"),rs.getString("Remarks")};
                    dtm.addRow(objItem);
                }
            }
    }
     catch(SQLException e)
    {
       JOptionPane.showMessageDialog(this,e);
    }
    
    try{
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ordering","root","")) {
            //PreparedStatement ps = con.prepareStatement("SELECT s.ITEM_CODE, s.ITEM_NAME, IFNULL(s.ITEM_QTY,0) AS 'IN', IFNULL(itemss.Qty,0) AS 'OUT', IFNULL(s.ITEM_QTY - itemss.Qty,s.ITEM_QTY) as BAL FROM supplies s LEFT JOIN (SELECT Item_Codes, Qty FROM items) AS itemss ON s.ITEM_CODE = itemss.Item_Codes");
            //PreparedStatement ps = con.prepareStatement("SELECT s.ITEM_CODE, s.ITEM_NAME, IFNULL(s.ITEM_QTY,0), IFNULL(itemss.Qty,0), IFNULL(s.ITEM_QTY - itemss.Qty,0) FROM supplies s LEFT JOIN (SELECT Item_Codes, Qty FROM items) AS itemss ON s.ITEM_CODE = itemss.Item_Codes");
            PreparedStatement ps = con.prepareStatement("SELECT s.ITEM_CODE, s.ITEM_NAME, IFNULL(s.ITEM_QTY + itemss.Qty,s.ITEM_QTY) AS 'IN', IFNULL(itemss.Qty,0) AS 'OUT', IFNULL(s.ITEM_QTY,0) as BAL FROM supplies s LEFT JOIN (SELECT Item_Codes, Qty FROM items) AS itemss ON s.ITEM_CODE = itemss.Item_Codes");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel dtm = (DefaultTableModel)tblCheck.getModel();
            dtm.setRowCount(0);
            while(rs.next())
            {
                Object[] objItem = {rs.getString("ITEM_CODE"), rs.getString("ITEM_NAME"), rs.getInt("IN"), rs.getInt("OUT"), rs.getInt("BAL")};
                dtm.addRow(objItem);
            }
        }
    }
    catch(SQLException e)
    {
       JOptionPane.showMessageDialog(this,e);
    }
    
    }

    private void filterInStock(String category){
        DefaultTableModel dtm = (DefaultTableModel)tblInStock.getModel();        
        TableRowSorter<DefaultTableModel> filter = new TableRowSorter(dtm);
        tblInStock.setRowSorter(filter); 
        
        if(!("All".equals(category)))
        {
            filter.setRowFilter(RowFilter.regexFilter(category));
        }
        else
        {
            tblInStock.setRowSorter(filter);
        }
   
    }
    
    private void filterRestock(String category){
        
        DefaultTableModel dtm = (DefaultTableModel)tblRestock.getModel();        
        TableRowSorter<DefaultTableModel> filter = new TableRowSorter(dtm);
        tblRestock.setRowSorter(filter); 
        
        if(!("All".equals(category)))
        {
            filter.setRowFilter(RowFilter.regexFilter(category));
        }
        else
        {
            tblRestock.setRowSorter(filter);
        }
    }
    
    private void filterReport(String category)
    {
        DefaultTableModel dtm = (DefaultTableModel)tblReports.getModel();        
        TableRowSorter<DefaultTableModel> filter = new TableRowSorter(dtm);
        tblReports.setRowSorter(filter);
        
        if(!("All".equals(category)))
        {
            filter.setRowFilter(RowFilter.regexFilter(category));
        }
        else
        {
            tblReports.setRowSorter(filter);
        }
    }
    
    private void filterICode(String category)
    {
        DefaultTableModel dtm = (DefaultTableModel)tblReports.getModel();        
        TableRowSorter<DefaultTableModel> filter = new TableRowSorter(dtm);
        tblReports.setRowSorter(filter);
        
        if(!("All".equals(category)))
        {
            filter.setRowFilter(RowFilter.regexFilter(category));
        }
        else
        {
            tblReports.setRowSorter(filter);
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

        pnlWhole = new javax.swing.JPanel();
        pnlPath = new javax.swing.JPanel();
        txtPath = new javax.swing.JTextField();
        lblPath = new javax.swing.JLabel();
        cmbInvAccount = new javax.swing.JComboBox<>();
        lblGetName = new javax.swing.JLabel();
        pnlMenubtns = new javax.swing.JPanel();
        pnlMenu = new javax.swing.JTabbedPane();
        tabDashboard = new javax.swing.JPanel();
        lblImg = new javax.swing.JLabel();
        tabInStock = new javax.swing.JPanel();
        pnlSearch = new javax.swing.JPanel();
        txtSearchIn = new javax.swing.JTextField();
        lblQSearch = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        lblCategory = new javax.swing.JLabel();
        cmbCatInStock = new javax.swing.JComboBox<>();
        btnAdd = new javax.swing.JButton();
        btnView = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        sclInStock = new javax.swing.JScrollPane();
        tblInStock = new javax.swing.JTable();
        tabReports = new javax.swing.JPanel();
        pnlSearchR = new javax.swing.JPanel();
        txtSearchReport = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnSearchReports = new javax.swing.JButton();
        lblActivity = new javax.swing.JLabel();
        cmbCatReports = new javax.swing.JComboBox<>();
        lblCategoryItemCode = new javax.swing.JLabel();
        cmbItemCode = new javax.swing.JComboBox<>();
        pnltblReports = new javax.swing.JScrollPane();
        tblReports = new javax.swing.JTable();
        txtDateToR = new javax.swing.JTextField();
        txtDateFromR = new javax.swing.JTextField();
        lblDFromR = new javax.swing.JLabel();
        lblDToR = new javax.swing.JLabel();
        lblREPORTS = new javax.swing.JLabel();
        tabRestock = new javax.swing.JPanel();
        pnlRestock = new javax.swing.JPanel();
        txtSearchRestock = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnSearchRestock = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        cmbCatRestock = new javax.swing.JComboBox<>();
        txtDate = new javax.swing.JTextField();
        lblDate = new javax.swing.JLabel();
        lblRestockForm = new javax.swing.JLabel();
        scrlRestock = new javax.swing.JScrollPane();
        tblRestock = new javax.swing.JTable();
        pnlRestockForm = new javax.swing.JPanel();
        lblRefCode = new javax.swing.JLabel();
        lblItemCode = new javax.swing.JLabel();
        lblItemName = new javax.swing.JLabel();
        lblInQty = new javax.swing.JLabel();
        lblRestockQty = new javax.swing.JLabel();
        txtRestockQty = new javax.swing.JTextField();
        txtInQty = new javax.swing.JTextField();
        lblBal = new javax.swing.JLabel();
        txtBalQty = new javax.swing.JTextField();
        lblRemarks = new javax.swing.JLabel();
        txtRemarks = new javax.swing.JTextField();
        txtRefCode = new javax.swing.JTextField();
        btnConfirm = new javax.swing.JButton();
        tabChecking = new javax.swing.JPanel();
        pnlSearchC = new javax.swing.JPanel();
        txtSearchCheck = new javax.swing.JTextField();
        btnSearchCheck = new javax.swing.JButton();
        btnDeduct = new javax.swing.JButton();
        btnRefreshCheck = new javax.swing.JButton();
        txtDateFrom = new javax.swing.JTextField();
        txtDateTo = new javax.swing.JTextField();
        lblDFrom = new javax.swing.JLabel();
        lblDTo = new javax.swing.JLabel();
        lblCheckForm = new javax.swing.JLabel();
        pnlCheck = new javax.swing.JScrollPane();
        tblCheck = new javax.swing.JTable();
        pnlCheckingForm = new javax.swing.JPanel();
        lblItemCodeCheck = new javax.swing.JLabel();
        lblItemNameCheck = new javax.swing.JLabel();
        lblRestockQty1 = new javax.swing.JLabel();
        txtOnHand = new javax.swing.JTextField();
        lblBal1 = new javax.swing.JLabel();
        txtShort = new javax.swing.JTextField();
        lblRemarks1 = new javax.swing.JLabel();
        txtRemarksCheck = new javax.swing.JTextField();
        btnCheck = new javax.swing.JButton();
        lblRestockQty2 = new javax.swing.JLabel();
        txtBalance = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(new java.awt.Dimension(1366, 768));

        pnlWhole.setBackground(new java.awt.Color(226, 176, 1));
        pnlWhole.setPreferredSize(new java.awt.Dimension(1366, 768));

        pnlPath.setBackground(new java.awt.Color(153, 153, 153));
        pnlPath.setForeground(new java.awt.Color(153, 153, 153));

        txtPath.setEditable(false);
        txtPath.setBackground(new java.awt.Color(153, 153, 153));
        txtPath.setFont(new java.awt.Font("Bahnschrift", 1, 15)); // NOI18N
        txtPath.setForeground(new java.awt.Color(255, 255, 255));
        txtPath.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPath.setText("W E L C O M E   T O   T H E   I N V E N T O R Y   S Y S T E M");
        txtPath.setToolTipText("");
        txtPath.setBorder(null);
        txtPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPathActionPerformed(evt);
            }
        });

        lblPath.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        lblPath.setForeground(new java.awt.Color(255, 255, 255));
        lblPath.setText("Suntea Ph");

        cmbInvAccount.setBackground(new java.awt.Color(252, 207, 44));
        cmbInvAccount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cmbInvAccount.setForeground(new java.awt.Color(255, 255, 255));
        cmbInvAccount.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Go to Ordering System" }));
        cmbInvAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbInvAccountActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlPathLayout = new javax.swing.GroupLayout(pnlPath);
        pnlPath.setLayout(pnlPathLayout);
        pnlPathLayout.setHorizontalGroup(
            pnlPathLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPathLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblPath, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtPath, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblGetName, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 331, Short.MAX_VALUE)
                .addComponent(cmbInvAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(151, 151, 151))
        );
        pnlPathLayout.setVerticalGroup(
            pnlPathLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPathLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(pnlPathLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPath)
                    .addComponent(txtPath))
                .addGap(120, 120, 120))
            .addGroup(pnlPathLayout.createSequentialGroup()
                .addGroup(pnlPathLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblGetName, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbInvAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pnlMenubtns.setBackground(new java.awt.Color(254, 196, 0));

        javax.swing.GroupLayout pnlMenubtnsLayout = new javax.swing.GroupLayout(pnlMenubtns);
        pnlMenubtns.setLayout(pnlMenubtnsLayout);
        pnlMenubtnsLayout.setHorizontalGroup(
            pnlMenubtnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 103, Short.MAX_VALUE)
        );
        pnlMenubtnsLayout.setVerticalGroup(
            pnlMenubtnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 708, Short.MAX_VALUE)
        );

        pnlMenu.setBackground(new java.awt.Color(255, 255, 255));
        pnlMenu.setForeground(new java.awt.Color(51, 51, 51));
        pnlMenu.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        pnlMenu.setToolTipText("");
        pnlMenu.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        pnlMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlMenuMouseClicked(evt);
            }
        });

        tabDashboard.setBackground(new java.awt.Color(255, 204, 51));
        tabDashboard.setToolTipText("");
        tabDashboard.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tabDashboard.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N

        lblImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orderingsystem/Dashboards.jpg"))); // NOI18N

        javax.swing.GroupLayout tabDashboardLayout = new javax.swing.GroupLayout(tabDashboard);
        tabDashboard.setLayout(tabDashboardLayout);
        tabDashboardLayout.setHorizontalGroup(
            tabDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblImg, javax.swing.GroupLayout.PREFERRED_SIZE, 1203, Short.MAX_VALUE)
        );
        tabDashboardLayout.setVerticalGroup(
            tabDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabDashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImg, javax.swing.GroupLayout.PREFERRED_SIZE, 639, Short.MAX_VALUE))
        );

        pnlMenu.addTab("DASHBOARD  ", null, tabDashboard, "");

        pnlSearch.setBackground(new java.awt.Color(153, 153, 153));

        txtSearchIn.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txtSearchIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchInActionPerformed(evt);
            }
        });

        lblQSearch.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        lblQSearch.setForeground(new java.awt.Color(255, 255, 255));
        lblQSearch.setText("Filter By:");

        btnSearch.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        btnSearch.setText("Search");

        lblCategory.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        lblCategory.setForeground(new java.awt.Color(255, 255, 255));
        lblCategory.setText("Category");

        cmbCatInStock.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbCatInStock.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Drinks", "Snacks" }));
        cmbCatInStock.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCatInStockItemStateChanged(evt);
            }
        });

        btnAdd.setBackground(new java.awt.Color(254, 255, 0));
        btnAdd.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        btnAdd.setText("Add");
        btnAdd.setMaximumSize(new java.awt.Dimension(89, 31));
        btnAdd.setMinimumSize(new java.awt.Dimension(89, 31));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnView.setBackground(new java.awt.Color(254, 255, 0));
        btnView.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        btnView.setText("View History");
        btnView.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnView.setPreferredSize(new java.awt.Dimension(120, 30));

        btnRefresh.setBackground(new java.awt.Color(254, 255, 0));
        btnRefresh.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        btnRefresh.setText("Refresh");
        btnRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnEdit.setBackground(new java.awt.Color(254, 255, 0));
        btnEdit.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSearchLayout = new javax.swing.GroupLayout(pnlSearch);
        pnlSearch.setLayout(pnlSearchLayout);
        pnlSearchLayout.setHorizontalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(lblQSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSearchIn, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblCategory)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbCatInStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlSearchLayout.setVerticalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlSearchLayout.createSequentialGroup()
                        .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtSearchIn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cmbCatInStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlSearchLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnEdit)
                                    .addComponent(btnRefresh))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(lblQSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblCategory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tblInStock.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblInStock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Item Code", "Item Name", "Quantity", "Status", "Category", "Date Updated"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblInStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblInStockMouseClicked(evt);
            }
        });
        sclInStock.setViewportView(tblInStock);
        if (tblInStock.getColumnModel().getColumnCount() > 0) {
            tblInStock.getColumnModel().getColumn(0).setMinWidth(30);
            tblInStock.getColumnModel().getColumn(0).setMaxWidth(100);
            tblInStock.getColumnModel().getColumn(1).setResizable(false);
            tblInStock.getColumnModel().getColumn(2).setMaxWidth(100);
            tblInStock.getColumnModel().getColumn(3).setResizable(false);
            tblInStock.getColumnModel().getColumn(5).setResizable(false);
        }

        javax.swing.GroupLayout tabInStockLayout = new javax.swing.GroupLayout(tabInStock);
        tabInStock.setLayout(tabInStockLayout);
        tabInStockLayout.setHorizontalGroup(
            tabInStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabInStockLayout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(sclInStock, javax.swing.GroupLayout.PREFERRED_SIZE, 1150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        tabInStockLayout.setVerticalGroup(
            tabInStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabInStockLayout.createSequentialGroup()
                .addComponent(pnlSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(sclInStock, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlMenu.addTab("IN-STOCK  ", tabInStock);

        pnlSearchR.setBackground(new java.awt.Color(153, 153, 153));

        txtSearchReport.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txtSearchReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchReportActionPerformed(evt);
            }
        });
        txtSearchReport.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchReportKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Filter By:");

        btnSearchReports.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        btnSearchReports.setText("Search");

        lblActivity.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        lblActivity.setForeground(new java.awt.Color(255, 255, 255));
        lblActivity.setText("Activity");

        cmbCatReports.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbCatReports.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Deducted", "Restock" }));
        cmbCatReports.setAlignmentX(0.0F);
        cmbCatReports.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cmbCatReportsFocusLost(evt);
            }
        });
        cmbCatReports.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCatReportsActionPerformed(evt);
            }
        });

        lblCategoryItemCode.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        lblCategoryItemCode.setForeground(new java.awt.Color(255, 255, 255));
        lblCategoryItemCode.setText("Item Code");

        cmbItemCode.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbItemCode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "BOTT350", "CND", "CUPS350", "CUPS500", "CUPS700", "FRFRIEND", "FRSOL", "WOCSOL", "WOCSQD" }));
        cmbItemCode.setAlignmentX(0.0F);
        cmbItemCode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cmbItemCodeFocusLost(evt);
            }
        });
        cmbItemCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbItemCodeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSearchRLayout = new javax.swing.GroupLayout(pnlSearchR);
        pnlSearchR.setLayout(pnlSearchRLayout);
        pnlSearchRLayout.setHorizontalGroup(
            pnlSearchRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchRLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel4)
                .addGap(10, 10, 10)
                .addComponent(txtSearchReport, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearchReports)
                .addGap(27, 27, 27)
                .addComponent(lblActivity, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbCatReports, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(lblCategoryItemCode)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbItemCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(365, Short.MAX_VALUE))
        );
        pnlSearchRLayout.setVerticalGroup(
            pnlSearchRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchRLayout.createSequentialGroup()
                .addGroup(pnlSearchRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlSearchRLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(pnlSearchRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSearchReport, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearchReports)
                            .addComponent(lblActivity, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbCatReports, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbItemCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblCategoryItemCode, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tblReports.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Item Code", "Item Name", "Quantity", "Activity", "Date", "Remarks"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        pnltblReports.setViewportView(tblReports);

        txtDateToR.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txtDateFromR.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        lblDFromR.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        lblDFromR.setText("Date From");

        lblDToR.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        lblDToR.setText("Date To");

        lblREPORTS.setFont(new java.awt.Font("Impact", 0, 36)); // NOI18N
        lblREPORTS.setText("REPORTS  &  ACTIVITY");

        javax.swing.GroupLayout tabReportsLayout = new javax.swing.GroupLayout(tabReports);
        tabReports.setLayout(tabReportsLayout);
        tabReportsLayout.setHorizontalGroup(
            tabReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSearchR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabReportsLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(tabReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnltblReports)
                    .addGroup(tabReportsLayout.createSequentialGroup()
                        .addComponent(lblREPORTS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(tabReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabReportsLayout.createSequentialGroup()
                                .addComponent(lblDToR)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtDateToR, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabReportsLayout.createSequentialGroup()
                                .addComponent(lblDFromR)
                                .addGap(10, 10, 10)
                                .addComponent(txtDateFromR, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(23, 23, 23))
        );
        tabReportsLayout.setVerticalGroup(
            tabReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabReportsLayout.createSequentialGroup()
                .addComponent(pnlSearchR, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(tabReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblREPORTS)
                    .addGroup(tabReportsLayout.createSequentialGroup()
                        .addGroup(tabReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDateFromR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDFromR))
                        .addGap(6, 6, 6)
                        .addGroup(tabReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDToR)
                            .addComponent(txtDateToR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(27, 27, 27)
                .addComponent(pnltblReports, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlMenu.addTab("REPORTS  ", tabReports);

        pnlRestock.setBackground(new java.awt.Color(153, 153, 153));

        txtSearchRestock.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txtSearchRestock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchRestockActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Filter By:");

        btnSearchRestock.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        btnSearchRestock.setText("Search");

        jLabel9.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Category");

        cmbCatRestock.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbCatRestock.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Drinks", "Snacks" }));
        cmbCatRestock.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCatRestockItemStateChanged(evt);
            }
        });
        cmbCatRestock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCatRestockActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlRestockLayout = new javax.swing.GroupLayout(pnlRestock);
        pnlRestock.setLayout(pnlRestockLayout);
        pnlRestockLayout.setHorizontalGroup(
            pnlRestockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRestockLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel8)
                .addGap(10, 10, 10)
                .addComponent(txtSearchRestock, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearchRestock)
                .addGap(30, 30, 30)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(cmbCatRestock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlRestockLayout.setVerticalGroup(
            pnlRestockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRestockLayout.createSequentialGroup()
                .addGroup(pnlRestockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRestockLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(pnlRestockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSearchRestock, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearchRestock)))
                    .addGroup(pnlRestockLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(cmbCatRestock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        txtDate.setEditable(false);
        txtDate.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtDate.setText("YYYY-MM-DD");
        txtDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDateMouseClicked(evt);
            }
        });

        lblDate.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        lblDate.setText("Date");

        lblRestockForm.setFont(new java.awt.Font("Impact", 0, 36)); // NOI18N
        lblRestockForm.setText("RESTOCK");

        tblRestock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Item Code", "Item Name", "Category", "Balance", "Date Updated"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
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
        tblRestock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRestockMouseClicked(evt);
            }
        });
        scrlRestock.setViewportView(tblRestock);
        if (tblRestock.getColumnModel().getColumnCount() > 0) {
            tblRestock.getColumnModel().getColumn(1).setMinWidth(200);
        }

        pnlRestockForm.setBackground(new java.awt.Color(255, 255, 255));
        pnlRestockForm.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 204, 0)));

        lblRefCode.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        lblRefCode.setText("Reference Code");

        lblItemCode.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblItemCode.setText("ITEM CODE");

        lblItemName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblItemName.setText("ITEM NAME");

        lblInQty.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblInQty.setText("In (QTY):");

        lblRestockQty.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblRestockQty.setText("Restock: ");

        txtRestockQty.setEditable(false);
        txtRestockQty.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtRestockQty.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtRestockQty.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtRestockQtyMouseClicked(evt);
            }
        });
        txtRestockQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRestockQtyActionPerformed(evt);
            }
        });
        txtRestockQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRestockQtyKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRestockQtyKeyTyped(evt);
            }
        });

        txtInQty.setEditable(false);
        txtInQty.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblBal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBal.setText("Bal (QTY): ");

        txtBalQty.setEditable(false);
        txtBalQty.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblRemarks.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblRemarks.setText("Remarks:");

        txtRemarks.setEditable(false);
        txtRemarks.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtRemarks.setText("Restock");

        txtRefCode.setEditable(false);
        txtRefCode.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtRefCode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtRefCodeMouseClicked(evt);
            }
        });
        txtRefCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRefCodeActionPerformed(evt);
            }
        });
        txtRefCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRefCodeKeyTyped(evt);
            }
        });

        btnConfirm.setText("Confirm");
        btnConfirm.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConfirm.setEnabled(false);
        btnConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlRestockFormLayout = new javax.swing.GroupLayout(pnlRestockForm);
        pnlRestockForm.setLayout(pnlRestockFormLayout);
        pnlRestockFormLayout.setHorizontalGroup(
            pnlRestockFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRestockFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlRestockFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRestockFormLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlRestockFormLayout.createSequentialGroup()
                        .addGroup(pnlRestockFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlRestockFormLayout.createSequentialGroup()
                                .addGroup(pnlRestockFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblBal)
                                    .addComponent(lblRemarks))
                                .addGap(18, 18, 18)
                                .addGroup(pnlRestockFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtBalQty, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                                    .addComponent(txtRemarks)))
                            .addComponent(lblItemName)
                            .addGroup(pnlRestockFormLayout.createSequentialGroup()
                                .addGroup(pnlRestockFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblInQty)
                                    .addComponent(lblRestockQty))
                                .addGap(28, 28, 28)
                                .addGroup(pnlRestockFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtRestockQty)
                                    .addComponent(txtInQty, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lblItemCode))
                        .addGap(0, 109, Short.MAX_VALUE))
                    .addGroup(pnlRestockFormLayout.createSequentialGroup()
                        .addComponent(lblRefCode, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtRefCode, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlRestockFormLayout.setVerticalGroup(
            pnlRestockFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRestockFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlRestockFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblRefCode)
                    .addComponent(txtRefCode, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblItemCode)
                .addGap(1, 1, 1)
                .addComponent(lblItemName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlRestockFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblInQty)
                    .addComponent(txtInQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlRestockFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRestockQty)
                    .addComponent(txtRestockQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(pnlRestockFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBal)
                    .addComponent(txtBalQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlRestockFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRemarks)
                    .addComponent(txtRemarks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(btnConfirm)
                .addContainerGap())
        );

        javax.swing.GroupLayout tabRestockLayout = new javax.swing.GroupLayout(tabRestock);
        tabRestock.setLayout(tabRestockLayout);
        tabRestockLayout.setHorizontalGroup(
            tabRestockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabRestockLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(tabRestockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRestockForm)
                    .addComponent(scrlRestock, javax.swing.GroupLayout.PREFERRED_SIZE, 796, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(tabRestockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabRestockLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblDate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlRestockForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23))
            .addComponent(pnlRestock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        tabRestockLayout.setVerticalGroup(
            tabRestockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabRestockLayout.createSequentialGroup()
                .addComponent(pnlRestock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(tabRestockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabRestockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblDate, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblRestockForm))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(tabRestockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlRestockForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scrlRestock, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnlMenu.addTab("RESTOCK  ", tabRestock);

        pnlSearchC.setBackground(new java.awt.Color(153, 153, 153));

        txtSearchCheck.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txtSearchCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchCheckActionPerformed(evt);
            }
        });

        btnSearchCheck.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        btnSearchCheck.setText("Search");

        btnDeduct.setBackground(new java.awt.Color(254, 255, 0));
        btnDeduct.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        btnDeduct.setText("Deduct");
        btnDeduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeductActionPerformed(evt);
            }
        });

        btnRefreshCheck.setBackground(new java.awt.Color(254, 255, 0));
        btnRefreshCheck.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        btnRefreshCheck.setText("Refresh");
        btnRefreshCheck.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRefreshCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshCheckActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSearchCLayout = new javax.swing.GroupLayout(pnlSearchC);
        pnlSearchC.setLayout(pnlSearchCLayout);
        pnlSearchCLayout.setHorizontalGroup(
            pnlSearchCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchCLayout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addComponent(txtSearchCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearchCheck)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDeduct)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRefreshCheck)
                .addGap(26, 26, 26))
        );
        pnlSearchCLayout.setVerticalGroup(
            pnlSearchCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchCLayout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addGroup(pnlSearchCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSearchCLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(pnlSearchCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSearchCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearchCheck)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSearchCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnDeduct)
                        .addComponent(btnRefreshCheck)))
                .addGap(11, 11, 11))
        );

        txtDateFrom.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtDateFrom.setText("DD/MM/YYYY");
        txtDateFrom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDateFromMouseClicked(evt);
            }
        });

        txtDateTo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtDateTo.setText("DD/MM/YYYY");
        txtDateTo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDateToMouseClicked(evt);
            }
        });

        lblDFrom.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        lblDFrom.setText("Date From");

        lblDTo.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        lblDTo.setText("Date To");

        lblCheckForm.setFont(new java.awt.Font("Impact", 0, 36)); // NOI18N
        lblCheckForm.setText("CHECKING FORM");

        tblCheck.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Item Code", "Item Name", "IN", "OUT", "BAL"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
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
        tblCheck.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCheckMouseClicked(evt);
            }
        });
        pnlCheck.setViewportView(tblCheck);
        if (tblCheck.getColumnModel().getColumnCount() > 0) {
            tblCheck.getColumnModel().getColumn(1).setMinWidth(100);
        }

        pnlCheckingForm.setBackground(new java.awt.Color(255, 255, 255));
        pnlCheckingForm.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 204, 0)));

        lblItemCodeCheck.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblItemCodeCheck.setText("ITEM CODE");

        lblItemNameCheck.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblItemNameCheck.setText("ITEM NAME");

        lblRestockQty1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblRestockQty1.setText("On - Hand:");

        txtOnHand.setEditable(false);
        txtOnHand.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtOnHand.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtOnHand.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtOnHandMouseClicked(evt);
            }
        });
        txtOnHand.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtOnHandKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtOnHandKeyTyped(evt);
            }
        });

        lblBal1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBal1.setText("Short:");

        txtShort.setEditable(false);
        txtShort.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblRemarks1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblRemarks1.setText("Remarks:");

        txtRemarksCheck.setEditable(false);
        txtRemarksCheck.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtRemarksCheck.setText("Short");

        btnCheck.setText("Confirm");
        btnCheck.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCheck.setEnabled(false);
        btnCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckActionPerformed(evt);
            }
        });

        lblRestockQty2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblRestockQty2.setText("Balance:");

        txtBalance.setEditable(false);

        javax.swing.GroupLayout pnlCheckingFormLayout = new javax.swing.GroupLayout(pnlCheckingForm);
        pnlCheckingForm.setLayout(pnlCheckingFormLayout);
        pnlCheckingFormLayout.setHorizontalGroup(
            pnlCheckingFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCheckingFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCheckingFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblItemNameCheck)
                    .addComponent(lblItemCodeCheck)
                    .addGroup(pnlCheckingFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(pnlCheckingFormLayout.createSequentialGroup()
                            .addComponent(lblRemarks1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtRemarksCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnlCheckingFormLayout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addGroup(pnlCheckingFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(btnCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(pnlCheckingFormLayout.createSequentialGroup()
                                    .addComponent(lblBal1)
                                    .addGap(64, 64, 64)
                                    .addComponent(txtShort, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlCheckingFormLayout.createSequentialGroup()
                                    .addGroup(pnlCheckingFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblRestockQty1)
                                        .addComponent(lblRestockQty2))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(pnlCheckingFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtOnHand, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                                        .addComponent(txtBalance)))))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        pnlCheckingFormLayout.setVerticalGroup(
            pnlCheckingFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCheckingFormLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblItemCodeCheck)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblItemNameCheck)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(pnlCheckingFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRestockQty2)
                    .addComponent(txtBalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCheckingFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOnHand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRestockQty1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCheckingFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtShort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBal1))
                .addGap(18, 18, 18)
                .addGroup(pnlCheckingFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRemarksCheck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRemarks1))
                .addGap(28, 28, 28)
                .addComponent(btnCheck)
                .addContainerGap())
        );

        javax.swing.GroupLayout tabCheckingLayout = new javax.swing.GroupLayout(tabChecking);
        tabChecking.setLayout(tabCheckingLayout);
        tabCheckingLayout.setHorizontalGroup(
            tabCheckingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSearchC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(tabCheckingLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(tabCheckingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabCheckingLayout.createSequentialGroup()
                        .addComponent(lblCheckForm)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(pnlCheck, javax.swing.GroupLayout.DEFAULT_SIZE, 843, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(tabCheckingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabCheckingLayout.createSequentialGroup()
                        .addGroup(tabCheckingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDFrom, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblDTo, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(tabCheckingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDateTo, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                            .addComponent(txtDateFrom))
                        .addGap(95, 95, 95))
                    .addComponent(pnlCheckingForm, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );
        tabCheckingLayout.setVerticalGroup(
            tabCheckingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabCheckingLayout.createSequentialGroup()
                .addComponent(pnlSearchC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(tabCheckingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabCheckingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblCheckForm)
                        .addComponent(lblDTo)
                        .addComponent(txtDateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(tabCheckingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblDFrom)
                        .addComponent(txtDateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(tabCheckingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlCheckingForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlMenu.addTab("CHECKING  ", tabChecking);

        javax.swing.GroupLayout pnlWholeLayout = new javax.swing.GroupLayout(pnlWhole);
        pnlWhole.setLayout(pnlWholeLayout);
        pnlWholeLayout.setHorizontalGroup(
            pnlWholeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlWholeLayout.createSequentialGroup()
                .addGroup(pnlWholeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlPath, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlWholeLayout.createSequentialGroup()
                        .addComponent(pnlMenubtns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 1208, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlWholeLayout.setVerticalGroup(
            pnlWholeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlWholeLayout.createSequentialGroup()
                .addComponent(pnlPath, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlWholeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlWholeLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlMenubtns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlWholeLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlWhole, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlWhole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchInActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchInActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        frmAdd.setVisible(true);
        frmAdd.setLocationRelativeTo(this);
    }//GEN-LAST:event_btnAddActionPerformed

    private void txtSearchRestockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchRestockActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchRestockActionPerformed

    private void cmbCatRestockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCatRestockActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbCatRestockActionPerformed

    private void txtSearchCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchCheckActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchCheckActionPerformed

    private void txtSearchReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchReportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchReportActionPerformed

    private void tblInStockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblInStockMouseClicked
        // TODO add your handling code here:
        btnEdit.setVisible(true);
        btnView.setVisible(false);
        btnDeduct.setEnabled(true);
    }//GEN-LAST:event_tblInStockMouseClicked

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        btnEdit.setVisible(false);
        btnView.setVisible(false);
        btnDeduct.setEnabled(false);
        frmEdit.setVisible(true);
        frmEdit.setLocationRelativeTo(this);

        DefaultTableModel dtm = (DefaultTableModel)tblInStock.getModel();
        //Display Data
        int intSelectedRow = tblInStock.getSelectedRow();
        frmEdit.lblItemNameHead.setText(dtm.getValueAt(intSelectedRow,1).toString());
        frmEdit.lblItemCode.setText(dtm.getValueAt(intSelectedRow,0).toString());
        frmEdit.txtItemCode.setText(dtm.getValueAt(intSelectedRow,0).toString());
        frmEdit.txtItemName.setText(dtm.getValueAt(intSelectedRow,1).toString());
        frmEdit.cmbStatus.setSelectedItem(dtm.getValueAt(intSelectedRow,3).toString());
        frmEdit.cmbCategory.setSelectedItem(dtm.getValueAt(intSelectedRow,4).toString());
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeductActionPerformed
        // TODO add your handling code here:
        btnEdit.setVisible(false);
        btnView.setVisible(false);
        btnDeduct.setEnabled(false);
        frmDeduct.setVisible(true);
        frmDeduct.setLocationRelativeTo(null);
        DefaultTableModel dtm = (DefaultTableModel)tblInStock.getModel();
        //Display Data
        int intSelectedRow = tblInStock.getSelectedRow();
        frmDeduct.lblItemName.setText(dtm.getValueAt(intSelectedRow,1).toString());
        frmDeduct.lblItemCode.setText(dtm.getValueAt(intSelectedRow,0).toString());
        frmDeduct.txtLastQty.setText(dtm.getValueAt(intSelectedRow,2).toString()); 
    }//GEN-LAST:event_btnDeductActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        refreshTables();
        cmbCatInStock.setSelectedItem("All");
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void txtDateFromMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDateFromMouseClicked
        // TODO add your handling code here:
        txtDateFrom.setText("");
    }//GEN-LAST:event_txtDateFromMouseClicked

    private void txtDateToMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDateToMouseClicked
        // TODO add your handling code here:
        txtDateTo.setText("");
    }//GEN-LAST:event_txtDateToMouseClicked

    private void txtDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDateMouseClicked
        // TODO add your handling code here:
        txtDateTo.setText("");
    }//GEN-LAST:event_txtDateMouseClicked

    private void pnlMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlMenuMouseClicked
        // TODO add your handling code here:
        btnEdit.setVisible(false);
        btnView.setVisible(false);
        btnDeduct.setEnabled(false);
        refreshTables();
        lblItemCode.setText("ITEM CODE");
        lblItemName.setText("ITEM NAME");
        txtInQty.setText("");
        txtRefCode.setText("");
       
        
    }//GEN-LAST:event_pnlMenuMouseClicked

    private void tblRestockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRestockMouseClicked
        // TODO add your handling code here:
        DefaultTableModel dtm;
        txtRefCode.setEditable(true);
        txtRestockQty.setEditable(true);
        txtRefCode.requestFocus();
        
        dtm = (DefaultTableModel)tblRestock.getModel();
        int intSelectedRow = tblRestock.getSelectedRow();
        lblItemCode.setText(dtm.getValueAt(intSelectedRow,0).toString());
        lblItemName.setText(dtm.getValueAt(intSelectedRow,1).toString());
        txtInQty.setText(dtm.getValueAt(intSelectedRow,3).toString());
        
    }//GEN-LAST:event_tblRestockMouseClicked

    private void txtRestockQtyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtRestockQtyMouseClicked
        // TODO add your handling code here:
        txtRestockQty.setForeground(Color.black);
        txtRestockQty.setText("");
        txtBalQty.setText("");
    }//GEN-LAST:event_txtRestockQtyMouseClicked

    private void txtRefCodeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtRefCodeMouseClicked
        // TODO add your handling code here:
        txtRefCode.setForeground(Color.black);
        txtRefCode.setText("");
    }//GEN-LAST:event_txtRefCodeMouseClicked
   
    private void cmbCatInStockItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCatInStockItemStateChanged
        // TODO add your handling code here:
        String category = cmbCatInStock.getSelectedItem().toString();
        filterInStock(category);
    }//GEN-LAST:event_cmbCatInStockItemStateChanged

    private void cmbCatRestockItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCatRestockItemStateChanged
        // TODO add your handling code here:
        String category = cmbCatRestock.getSelectedItem().toString();
        filterRestock(category);
    }//GEN-LAST:event_cmbCatRestockItemStateChanged

    private void txtOnHandMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtOnHandMouseClicked
        // TODO add your handling code here:
        txtOnHand.setForeground(Color.black);
        txtOnHand.setText("");
    }//GEN-LAST:event_txtOnHandMouseClicked

    private void txtRestockQtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRestockQtyKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if(!(Character.isDigit(c)|| (c == KeyEvent.VK_BACK_SPACE)||(c==KeyEvent.VK_DELETE)))
        {
            evt.consume();
        }
    }//GEN-LAST:event_txtRestockQtyKeyTyped

    private void txtOnHandKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOnHandKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if(!(Character.isDigit(c)|| (c == KeyEvent.VK_BACK_SPACE)||(c==KeyEvent.VK_DELETE)))
        {
            evt.consume();
        }
    }//GEN-LAST:event_txtOnHandKeyTyped

    private void txtRestockQtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRestockQtyKeyReleased
        // TODO add your handling code here:
            if("".equals(txtRestockQty.getText()))
            {
                txtBalQty.setText("");
            }
            else{
            int intInQty = Integer.parseInt(txtInQty.getText());
            int intRestockQty = Integer.parseInt(txtRestockQty.getText());
            txtBalQty.setText(String.valueOf(intInQty + intRestockQty));
            btnConfirm.setEnabled(true);
            }
        
    }//GEN-LAST:event_txtRestockQtyKeyReleased

    private void btnConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmActionPerformed
                                                  
        // TODO add your handling code here:
        if("".equals(txtRefCode.getText()))
        {
            btnConfirm.setEnabled(false);
            txtRefCode.setForeground(Color.red);
            txtRefCode.setText("Field Required");
        }
        else if("0".equals(txtRestockQty.getText()) || "".equals(txtRestockQty.getText()))
        {
            btnConfirm.setEnabled(false);
            txtRestockQty.setForeground(Color.red);
            txtRestockQty.setText("0");
        }
        else{
        PreparedStatement ps;
        try{
            //ADD ACTIVITY in DB
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ordering","root","")) {
                //ADD ACTIVITY in DB
                ps = con.prepareStatement("insert into activity(ACTV_ID,REF_Code,ITEM_CODE,QUANTITY,ACTIVITY,DATE)VALUES(NULL,?,?,?,?,CURRENT_TIMESTAMP)");
                ps.setString(1,txtRefCode.getText());
                ps.setString(2,lblItemCode.getText());
                ps.setInt(3,Integer.valueOf(txtRestockQty.getText()));
                ps.setString(4,txtRemarks.getText());
                ps.executeUpdate();

                //UPDATE QTY
                String prev = lblItemCode.getText();
                PreparedStatement pst = con.prepareStatement("SELECT SUPPLIES_ID from supplies where ITEM_CODE = '"+prev+"'");
                ResultSet rs = pst.executeQuery();

                while(rs.next())
                {
                    int intRow = rs.getInt("SUPPLIES_ID");
                    String update = "UPDATE supplies set ITEM_QTY = '"+txtBalQty.getText()+"',DATE_UPDATED = CURRENT_TIMESTAMP where SUPPLIES_ID ="+intRow;
                    ps = con.prepareStatement(update);
                    ps.executeUpdate();
                }
                con.close();
            }
            lblItemName.setText("ITEM NAME");
            lblItemCode.setText("ITEM CODE");
            txtInQty.setText("");
            txtRestockQty.setText("");
            txtBalQty.setText("");
            txtRefCode.setText("");
            frmMessage.setVisible(true);
            frmMessage.lblMessage.setText("Your Restock Transaction is Successful");
            refreshTables();
            /*DefaultTableModel dtm = (DefaultTableModel)tblRestock.getModel();
            int intRow = tblRestock.getSelectedRow();
            dtm.removeRow(intRow);*/
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(this, ex);
        }
        }
    
    }//GEN-LAST:event_btnConfirmActionPerformed

    private void btnRefreshCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshCheckActionPerformed
        // TODO add your handling code here:
        refreshTables();
        lblItemCodeCheck.setText("ITEM CODE");
        lblItemNameCheck.setText("ITEM NAME");
        txtBalance.setText("");
        txtOnHand.setText("");
        txtShort.setText("");        
    }//GEN-LAST:event_btnRefreshCheckActionPerformed

    private void txtSearchReportKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchReportKeyTyped
        // TODO add your handling code here:        
        DefaultTableModel dtm = (DefaultTableModel)tblReports.getModel();
        PreparedStatement ps = null;
        String search = "select a.ITEM_Code, itemss.ITEM_NAME, a.QUANTITY, a.ACTIVITY, a.DATE, a.Remarks from activity a LEFT JOIN (SELECT ITEM_CODE, ITEM_NAME from supplies) AS itemss ON a.ITEM_Code = itemss.ITEM_CODE where itemss.ITEM_NAME LIKE %'"+txtSearchReport.getText()+"%' OR ";
        try {
            ResultSet rs = ps.executeQuery(search);
            
        } catch (SQLException ex) {
            Logger.getLogger(frmInvMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtSearchReportKeyTyped

    private void cmbItemCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbItemCodeActionPerformed
        // TODO add your handling code here:
        String category = cmbItemCode.getSelectedItem().toString();        
        filterICode(category);
    }//GEN-LAST:event_cmbItemCodeActionPerformed

    private void cmbCatReportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCatReportsActionPerformed
        // TODO add your handling code here:
        String category = cmbCatReports.getSelectedItem().toString();
        filterReport(category);
    }//GEN-LAST:event_cmbCatReportsActionPerformed

    private void cmbItemCodeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbItemCodeFocusLost
        // TODO add your handling code here:
        cmbItemCode.setSelectedItem("All");
    }//GEN-LAST:event_cmbItemCodeFocusLost

    private void cmbCatReportsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbCatReportsFocusLost
        // TODO add your handling code here:
        cmbCatReports.setSelectedItem("All");
    }//GEN-LAST:event_cmbCatReportsFocusLost

    private void txtOnHandKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOnHandKeyReleased
        // TODO add your handling code here:
        int intValBal = Integer.parseInt(txtBalance.getText());
        int intValOnHand = Integer.parseInt(txtOnHand.getText());
        if("".equals(txtOnHand.getText()))
            {
                txtShort.setText("");
            }
        else if(intValBal > intValOnHand)
        {
            txtOnHand.setEditable(true);
            int intBal = Integer.parseInt(txtBalance.getText());
            int intOnHand = Integer.parseInt(txtOnHand.getText());       
            txtShort.setText(String.valueOf(intBal - intOnHand));
            txtShort.setText(String.valueOf(intBal - intOnHand));
            btnCheck.setEnabled(true);

        }
        else if(intValBal < intValOnHand)
        {
            txtOnHand.setText("");
            txtOnHand.setForeground(Color.red);
            txtOnHand.setText("Invalid");        
            txtShort.setText("");
            btnCheck.setEnabled(false);
        }
        else if(intValBal == intValOnHand)
        {
            txtShort.setText("0");
        }
        
    }//GEN-LAST:event_txtOnHandKeyReleased

    private void tblCheckMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCheckMouseClicked
        // TODO add your handling code here:
        txtOnHand.requestFocus();
        txtOnHand.setEditable(true);
        if((!(txtOnHand.getText().equals(""))) || (!(txtShort.getText().equals(""))))
        {
            txtOnHand.setForeground(Color.black);
            txtOnHand.setText("");
            txtShort.setText("");
        }
        DefaultTableModel dtm = (DefaultTableModel)tblCheck.getModel();
        int intSelectedRow = tblCheck.getSelectedRow();
        lblItemCodeCheck.setText(dtm.getValueAt(intSelectedRow,0).toString());
        lblItemNameCheck.setText(dtm.getValueAt(intSelectedRow,1).toString());
        txtBalance.setText(dtm.getValueAt(intSelectedRow,4).toString());        
    }//GEN-LAST:event_tblCheckMouseClicked

    private void btnCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckActionPerformed
        // TODO add your handling code here:
        PreparedStatement ps;
        String update;
        try{
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ordering","root","")) {
            if(Integer.valueOf(txtShort.getText()) == 0)
            {       
            update = "update items set Qty = 0 where Item_Codes LIKE '"+lblItemCodeCheck.getText()+"'";          
            ps = con.prepareStatement(update);
            ps.executeUpdate();
            con.close();
            }
            else{
            ps = con.prepareStatement("insert into activity(ACTV_ID,ITEM_CODE,QUANTITY,ACTIVITY,DATE,Remarks)VALUES(NULL,?,?,?,CURRENT_TIMESTAMP,'Short')");
            ps.setString(1,lblItemCodeCheck.getText());
            ps.setInt(2,Integer.valueOf(txtShort.getText()));
            ps.setString(3,"Deducted");
            ps.executeUpdate();
            
            //Deduct qty in Supplies
            update = "update supplies set ITEM_QTY ='"+txtOnHand.getText()+"', DATE_UPDATED = CURRENT_TIMESTAMP where ITEM_CODE LIKE '"+lblItemCodeCheck.getText()+"'";            
            ps = con.prepareStatement(update);
            ps.executeUpdate();
            
            // Zero Qty in itemss Table
            update = "update items set Qty = 0 where Item_Codes LIKE '"+lblItemCodeCheck.getText()+"'";          
            ps = con.prepareStatement(update);
            ps.executeUpdate();
            con.close();
            } 
          }
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(this,e);
        }
        btnCheck.setEnabled(false);
        txtBalance.setEditable(false);
        txtOnHand.setEditable(false);
        txtShort.setEditable(false);
        lblItemCodeCheck.setText("ITEM CODE");
        lblItemNameCheck.setText("ITEM NAME");
        txtBalance.setText("");
        txtOnHand.setText("");
        txtShort.setText("");
        refreshTables();
    }//GEN-LAST:event_btnCheckActionPerformed

    private void cmbInvAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbInvAccountActionPerformed
        // TODO add your handling code here:
        String strcmbAcc = cmbInvAccount.getSelectedItem().toString();
        if("Go to Ordering System".equalsIgnoreCase(strcmbAcc))
        {
            dispose();
            Login.ctrCombo++;
            POSNEW pos = new POSNEW();

            pos.setVisible(true);
        }
    }//GEN-LAST:event_cmbInvAccountActionPerformed

    private void txtPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPathActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPathActionPerformed

    private void txtRefCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRefCodeActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtRefCodeActionPerformed

    private void txtRestockQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRestockQtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRestockQtyActionPerformed

    private void txtRefCodeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRefCodeKeyTyped
        char c = evt.getKeyChar();
        if(!(Character.isDigit(c)|| (c == KeyEvent.VK_BACK_SPACE)||(c==KeyEvent.VK_DELETE)))
        {
            evt.consume();
        }        
    }//GEN-LAST:event_txtRefCodeKeyTyped

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmInvMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new frmInvMain().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnAdd;
    public javax.swing.JButton btnCheck;
    public javax.swing.JButton btnConfirm;
    public javax.swing.JButton btnDeduct;
    public javax.swing.JButton btnEdit;
    public javax.swing.JButton btnRefresh;
    public javax.swing.JButton btnRefreshCheck;
    public javax.swing.JButton btnSearch;
    public javax.swing.JButton btnSearchCheck;
    public javax.swing.JButton btnSearchReports;
    public javax.swing.JButton btnSearchRestock;
    public javax.swing.JButton btnView;
    public javax.swing.JComboBox<String> cmbCatInStock;
    public javax.swing.JComboBox<String> cmbCatReports;
    public javax.swing.JComboBox<String> cmbCatRestock;
    public static javax.swing.JComboBox<String> cmbInvAccount;
    public javax.swing.JComboBox<String> cmbItemCode;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel9;
    public javax.swing.JLabel lblActivity;
    public javax.swing.JLabel lblBal;
    public javax.swing.JLabel lblBal1;
    public javax.swing.JLabel lblCategory;
    public javax.swing.JLabel lblCategoryItemCode;
    public javax.swing.JLabel lblCheckForm;
    public javax.swing.JLabel lblDFrom;
    public javax.swing.JLabel lblDFromR;
    public javax.swing.JLabel lblDTo;
    public javax.swing.JLabel lblDToR;
    public javax.swing.JLabel lblDate;
    public static javax.swing.JLabel lblGetName;
    public javax.swing.JLabel lblImg;
    public javax.swing.JLabel lblInQty;
    public javax.swing.JLabel lblItemCode;
    public javax.swing.JLabel lblItemCodeCheck;
    public javax.swing.JLabel lblItemName;
    public javax.swing.JLabel lblItemNameCheck;
    public javax.swing.JLabel lblPath;
    public javax.swing.JLabel lblQSearch;
    public javax.swing.JLabel lblREPORTS;
    public javax.swing.JLabel lblRefCode;
    public javax.swing.JLabel lblRemarks;
    public javax.swing.JLabel lblRemarks1;
    public javax.swing.JLabel lblRestockForm;
    public javax.swing.JLabel lblRestockQty;
    public javax.swing.JLabel lblRestockQty1;
    public javax.swing.JLabel lblRestockQty2;
    public javax.swing.JScrollPane pnlCheck;
    public javax.swing.JPanel pnlCheckingForm;
    public javax.swing.JTabbedPane pnlMenu;
    public javax.swing.JPanel pnlMenubtns;
    public javax.swing.JPanel pnlPath;
    public javax.swing.JPanel pnlRestock;
    public javax.swing.JPanel pnlRestockForm;
    public javax.swing.JPanel pnlSearch;
    public javax.swing.JPanel pnlSearchC;
    public javax.swing.JPanel pnlSearchR;
    public javax.swing.JPanel pnlWhole;
    public javax.swing.JScrollPane pnltblReports;
    public javax.swing.JScrollPane sclInStock;
    public javax.swing.JScrollPane scrlRestock;
    public javax.swing.JPanel tabChecking;
    public javax.swing.JPanel tabDashboard;
    public javax.swing.JPanel tabInStock;
    public javax.swing.JPanel tabReports;
    public javax.swing.JPanel tabRestock;
    public javax.swing.JTable tblCheck;
    public javax.swing.JTable tblInStock;
    public javax.swing.JTable tblReports;
    public javax.swing.JTable tblRestock;
    public javax.swing.JTextField txtBalQty;
    public javax.swing.JTextField txtBalance;
    public javax.swing.JTextField txtDate;
    public javax.swing.JTextField txtDateFrom;
    public javax.swing.JTextField txtDateFromR;
    public javax.swing.JTextField txtDateTo;
    public javax.swing.JTextField txtDateToR;
    public javax.swing.JTextField txtInQty;
    public javax.swing.JTextField txtOnHand;
    public javax.swing.JTextField txtPath;
    public javax.swing.JTextField txtRefCode;
    public javax.swing.JTextField txtRemarks;
    public javax.swing.JTextField txtRemarksCheck;
    public javax.swing.JTextField txtRestockQty;
    public javax.swing.JTextField txtSearchCheck;
    public javax.swing.JTextField txtSearchIn;
    public javax.swing.JTextField txtSearchReport;
    public javax.swing.JTextField txtSearchRestock;
    public javax.swing.JTextField txtShort;
    // End of variables declaration//GEN-END:variables
}
