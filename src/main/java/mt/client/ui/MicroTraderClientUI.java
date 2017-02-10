package mt.client.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import mt.client.controller.Controller;
import mt.client.Session;
import mt.client.exception.AuthenticationException;
import mt.client.exception.ConnectionClosedException;

/**
 * Main screen of the Micro Trader.
 *
 */
public class MicroTraderClientUI extends javax.swing.JFrame {

    private Timer timer;

    private final String screenTitle = "Micro Trader";
    
    private final Controller controller = new Controller();
    
    public boolean teste = false;
    
    public MicroTraderClientUI() {
        initComponents();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {

        placeOrderBtn = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        unfulfilledOrdersScrollPane = new javax.swing.JScrollPane();
        unfulfilledOrdersTable = new javax.swing.JTable();
        myOrdersScrollPane = new javax.swing.JScrollPane();
        myOrdersTable = new javax.swing.JTable();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        connect = new javax.swing.JMenuItem();
        disconnect = new javax.swing.JMenuItem();
        jSeparator = new javax.swing.JPopupMenu.Separator();
        exit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(screenTitle + " | (Disconnected)");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                MicroTraderClientUI.this.windowClosing(evt);
            }
        });

        placeOrderBtn.setText("Place Order");
        placeOrderBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                placeOrderBtnActionPerformed(evt);
            }
        });

        unfulfilledOrdersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        unfulfilledOrdersScrollPane.setViewportView(unfulfilledOrdersTable);

        jTabbedPane1.addTab("Unfulfilled Orders", unfulfilledOrdersScrollPane);

        myOrdersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        myOrdersScrollPane.setViewportView(myOrdersTable);

        jTabbedPane1.addTab("My Orders", myOrdersScrollPane);

        fileMenu.setText("File");

        connect.setText("Connect");
        connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectActionPerformed(evt);
            }
        });
        fileMenu.add(connect);

        disconnect.setText("Disconnect");
        disconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectActionPerformed(evt);
            }
        });
        fileMenu.add(disconnect);
        fileMenu.add(jSeparator);

        exit.setText("Exit");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });
        fileMenu.add(exit);

        menuBar.add(fileMenu);

        jMenu2.setText("Orders");

        jMenuItem2.setText("Create Batch");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        menuBar.add(jMenu2);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(placeOrderBtn))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(placeOrderBtn)
                .addGap(6, 6, 6))
        );

        setBounds(0, 0, 640, 502);
    }                        

    private void connectActionPerformed(java.awt.event.ActionEvent evt) {                                        
        if (!controller.isConnected()) {
            ConnectForm form = new ConnectForm(this, true);
            form.setLocationRelativeTo(this);
            form.setVisible(true);
            if (controller.isConnected()) {
                browseMessages(this);
                setTitle(screenTitle + " | Connected user: " + controller.getLoggedUser());
            }
        } else {
            JOptionPane.showMessageDialog(this, "You are already connected to a server. \nNavigate to File > Disconnect before connecting with new nickname.", "Warning", JOptionPane.WARNING_MESSAGE);
        }

    }                                       

    private void disconnectActionPerformed(java.awt.event.ActionEvent evt) {                                           
        if (controller.isConnected()) {
            controller.disconnect();
            timer.stop();
            unfulfilledOrdersTable.setModel(new DefaultTableModel());
            myOrdersTable.setModel(new DefaultTableModel());
            setTitle(screenTitle + " | (Disconnected)");
        } else {
            JOptionPane.showMessageDialog(this, "You are already disconnected from the server.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }                                          

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {                                     
        if (controller.isConnected()) {
            controller.disconnect();
        }
        
        this.dispose();
        
        try {
            notifyObject(this);
        } catch (InterruptedException ex) {
            Logger.getLogger(MicroTraderClientUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }                                    

    private void placeOrderBtnActionPerformed(java.awt.event.ActionEvent evt) {                                              
        if (controller.isConnected()) {
            PlaceOrderForm form = new PlaceOrderForm(this, true);
            form.setLocationRelativeTo(this);
            form.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "You must be connected to a server to place orders. \nNavigate to File > Connect.", "Warning", JOptionPane.WARNING_MESSAGE);
        }

    }                                             

    private void windowClosing(java.awt.event.WindowEvent evt) {                               
        if (controller.isConnected()) {
            controller.disconnect();
        }

        try {
            notifyObject(this);
        } catch (InterruptedException ex) {
            Logger.getLogger(MicroTraderClientUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }                              

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        if (controller.isConnected()) {
            try {
                controller.sendBatchOrders();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "You must be connected to a server to place orders. \nNavigate to File > Connect.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }                                          

    private javax.swing.JMenuItem connect;
    private javax.swing.JMenuItem disconnect;
    private javax.swing.JMenuItem exit;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPopupMenu.Separator jSeparator;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JScrollPane myOrdersScrollPane;
    private javax.swing.JTable myOrdersTable;
    private javax.swing.JButton placeOrderBtn;
    private javax.swing.JScrollPane unfulfilledOrdersScrollPane;
    private javax.swing.JTable unfulfilledOrdersTable;

    private void browseMessages(final Component parentComponent) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Browse orders.");
        timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Controller().browseMessages();
                    unfulfilledOrdersTable.setModel(new OrderTableModel(Session.orders));
                    myOrdersTable.setModel(new OrderTableModel(Session.history));
                } catch (Exception ex) {
                    
                    if (ex instanceof AuthenticationException || ex instanceof ConnectionClosedException) {
                        timer.stop();
                        unfulfilledOrdersTable.setModel(new DefaultTableModel());
                        myOrdersTable.setModel(new DefaultTableModel());
                        setTitle(screenTitle + " | (Disconnected)");
                    }
                    
                    JOptionPane.showMessageDialog(parentComponent, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        timer.start();
    }
    
    public void notifyObject(MicroTraderClientUI object) throws InterruptedException {
        synchronized(object) {
            object.notify();
        }
    }
    
}