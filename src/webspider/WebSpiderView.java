package webspider;

import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.UIManager;

import mobilecrawler.MobileAgentMasterView;

public class WebSpiderView extends FrameView {

    public WebSpiderView(SingleFrameApplication app) {
        super(app);
        initComponents();
        searchLimit.setText("30");
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);        
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {               
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;                
            }
        });  
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }                    
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();                  
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());                    
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());                    
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = WebSpiderApp.getApplication().getMainFrame();
            aboutBox = new WebSpiderAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        WebSpiderApp.getApplication().show(aboutBox);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        titleImage = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        searchLimit = new javax.swing.JTextField();
        exit = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        seedURL = new javax.swing.JTextField();
        statusLabel = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        magent = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        View = new javax.swing.JMenu();
        CrawlHistory = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        jLabel3 = new javax.swing.JLabel();

        mainPanel.setAutoscrolls(true);
        mainPanel.setFocusTraversalPolicyProvider(true);
        mainPanel.setMaximumSize(new java.awt.Dimension(750, 450));
        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(750, 450));

        jPanel1.setMaximumSize(new java.awt.Dimension(750, 450));
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(750, 450));

        titleImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(webspider.WebSpiderApp.class).getContext().getResourceMap(WebSpiderView.class);
        titleImage.setIcon(resourceMap.getIcon("imgLabel.icon")); // NOI18N
        titleImage.setIconTextGap(0);
        titleImage.setName("imgLabel"); // NOI18N
        titleImage.setPreferredSize(new java.awt.Dimension(750, 450));

        jLabel4.setFont(resourceMap.getFont("searchLimit_label.font")); // NOI18N
        jLabel4.setText(resourceMap.getString("searchLimit_label.text")); // NOI18N
        jLabel4.setName("searchLimit_label"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(webspider.WebSpiderApp.class).getContext().getActionMap(WebSpiderView.class, this);
        jButton1.setAction(actionMap.get("startCrawling")); // NOI18N
        jButton1.setFont(resourceMap.getFont("Crawl.font")); // NOI18N
        jButton1.setText(resourceMap.getString("Crawl.text")); // NOI18N
        jButton1.setName("Crawl"); // NOI18N
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        searchLimit.setName("searchLimit_text"); // NOI18N

        exit.setAction(actionMap.get("quit")); // NOI18N
        exit.setFont(resourceMap.getFont("exit.font")); // NOI18N
        exit.setText(resourceMap.getString("exit.text")); // NOI18N
        exit.setName("exit"); // NOI18N

        jLabel2.setFont(resourceMap.getFont("seedUrl_label.font")); // NOI18N
        jLabel2.setLabelFor(seedURL);
        jLabel2.setText(resourceMap.getString("seedUrl_label.text")); // NOI18N
        jLabel2.setName("seedUrl_label"); // NOI18N

        seedURL.setText(resourceMap.getString("seedUrl_text.text")); // NOI18N
        seedURL.setName("seedUrl_text"); // NOI18N

        statusLabel.setText(resourceMap.getString("statusLabel.text")); // NOI18N
        statusLabel.setName("statusLabel"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(titleImage, javax.swing.GroupLayout.PREFERRED_SIZE, 676, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(70, 70, 70)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(seedURL, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(searchLimit, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(169, 169, 169)
                        .addComponent(jButton1)
                        .addGap(186, 186, 186)
                        .addComponent(exit, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
            .addComponent(statusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 696, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleImage, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seedURL, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchLimit, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(exit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE))
                .addGap(20, 20, 20)
                .addComponent(statusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 694, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        magent.setAction(actionMap.get("startMobileAgent")); // NOI18N
        magent.setText(resourceMap.getString("magent.text")); // NOI18N
        magent.setName("magent"); // NOI18N
        fileMenu.add(magent);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        jMenu1.setAction(actionMap.get("callfunc")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N

        jMenuItem1.setAction(actionMap.get("callfunc")); // NOI18N
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        jMenuItem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem1MouseClicked(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        menuBar.add(jMenu1);

        View.setText(resourceMap.getString("View.text")); // NOI18N
        View.setName("View"); // NOI18N

        CrawlHistory.setAction(actionMap.get("callfunc")); // NOI18N
        CrawlHistory.setText(resourceMap.getString("CrawlHistory.text")); // NOI18N
        CrawlHistory.setName("CrawlHistory"); // NOI18N
        View.add(CrawlHistory);

        menuBar.add(View);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        jLabel3.setName("jLabel3"); // NOI18N

        setComponent(mainPanel);
        setMenuBar(menuBar);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
                

    }//GEN-LAST:event_jButton1MouseClicked

    private void jMenuItem1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem1MouseClicked
            
    }//GEN-LAST:event_jMenuItem1MouseClicked

    @Action
    public void callfunc() {
        try
        {
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }  
    }

    @Action
    public void startCrawling() 
    {
        String seed = seedURL.getText();
        int search = Integer.parseInt(searchLimit.getText());
        
        WebSpider ws = new WebSpider(seed,search,this);
        ws.start();
    }

    @Action
    public void startMobileAgent() 
    {        
        try
        {           
            File dir = new File("G:\\aglets\\bin");
            if(port_number == 0)
            {
                Process process = Runtime.getRuntime().exec("cmd /c start G:\\aglets\\bin\\agletsd.bat");
                port_number += 1;
            }
            else if(port_number == 1)
            {
                Process process = Runtime.getRuntime().exec("cmd /c start G:\\aglets\\bin\\agletsd.bat -port 9000");
                port_number += 1;
            }
            else
            {
                Process process = Runtime.getRuntime().exec("cmd /c start G:\\aglets\\bin\\agletsd.bat -port 5000");
                port_number += 1;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem CrawlHistory;
    private javax.swing.JMenu View;
    private javax.swing.JButton exit;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JMenuItem magent;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JTextField searchLimit;
    private javax.swing.JTextField seedURL;
    public javax.swing.JLabel statusLabel;
    private javax.swing.JLabel titleImage;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
    public int port_number = 0;

}
