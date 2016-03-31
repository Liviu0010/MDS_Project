package Interface;

/**
 *
 * @author Dragos-Alexandru
 */
public class MultiplayerServerPanel extends javax.swing.JPanel {

    private final MainFrame rootFrame;
    
    /**
     * Creates new form MultiplayerServerBrowser
     * @param rootFrame
     */
    public MultiplayerServerPanel(MainFrame rootFrame) {
        
        this.rootFrame = rootFrame;
        
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        joinMatchButton = new javax.swing.JButton();
        createMatchButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        scrollPlayers = new javax.swing.JScrollPane();
        listPlayers = new javax.swing.JList();
        scrollAvailableMatches = new javax.swing.JScrollPane();
        listAvailableMatches = new javax.swing.JList();
        players = new javax.swing.JLabel();
        matches = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        refreshButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(600, 400));
        setMinimumSize(new java.awt.Dimension(600, 400));

        joinMatchButton.setBackground(new java.awt.Color(153, 153, 255));
        joinMatchButton.setText("Join match");
        joinMatchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                joinMatchButtonActionPerformed(evt);
            }
        });

        createMatchButton.setBackground(new java.awt.Color(102, 255, 102));
        createMatchButton.setText("Create match");
        createMatchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createMatchButtonActionPerformed(evt);
            }
        });

        backButton.setBackground(new java.awt.Color(255, 255, 255));
        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        listPlayers.setMaximumSize(new java.awt.Dimension(40, 80));
        listPlayers.setMinimumSize(new java.awt.Dimension(40, 80));
        scrollPlayers.setViewportView(listPlayers);

        listAvailableMatches.setMaximumSize(new java.awt.Dimension(40, 80));
        listAvailableMatches.setMinimumSize(new java.awt.Dimension(40, 80));
        scrollAvailableMatches.setViewportView(listAvailableMatches);

        players.setText("Players");

        matches.setText("Matches");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("Select server");

        refreshButton.setBackground(new java.awt.Color(255, 153, 51));
        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollAvailableMatches, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                    .addComponent(matches))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jLabel3)
                        .addGap(55, 55, 55))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(createMatchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(joinMatchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPlayers, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(players))
                .addGap(7, 7, 7))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                        .addComponent(joinMatchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(createMatchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(players)
                            .addComponent(matches))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(scrollPlayers, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                            .addComponent(scrollAvailableMatches))))
                .addContainerGap(46, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void joinMatchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_joinMatchButtonActionPerformed
        rootFrame.changePanel(new MultiplayerMatchPanel(rootFrame));
    }//GEN-LAST:event_joinMatchButtonActionPerformed

    private void createMatchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createMatchButtonActionPerformed
        rootFrame.changePanel(new MultiplayerCreateMatch(rootFrame));
    }//GEN-LAST:event_createMatchButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        rootFrame.changePanel(new MultiplayerChooserPanel(rootFrame));
    }//GEN-LAST:event_backButtonActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_refreshButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JButton createMatchButton;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton joinMatchButton;
    private javax.swing.JList listAvailableMatches;
    private javax.swing.JList listPlayers;
    private javax.swing.JLabel matches;
    private javax.swing.JLabel players;
    private javax.swing.JButton refreshButton;
    private javax.swing.JScrollPane scrollAvailableMatches;
    private javax.swing.JScrollPane scrollPlayers;
    // End of variables declaration//GEN-END:variables
}
