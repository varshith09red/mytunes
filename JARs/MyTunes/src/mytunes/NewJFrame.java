package mytunes;

public class NewJFrame extends javax.swing.JFrame {

    public NewJFrame() {
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuBar1 = new java.awt.MenuBar();
        menu1 = new java.awt.Menu();
        menu2 = new java.awt.Menu();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        popupMenu1 = new java.awt.PopupMenu();
        jTextField2 = new javax.swing.JTextField();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        ActionButtonsjPanel = new javax.swing.JPanel();
        stop = new java.awt.Button();
        play = new java.awt.Button();
        pause = new java.awt.Button();
        next = new java.awt.Button();
        previous = new java.awt.Button();
        volumeSlider = new javax.swing.JSlider();
        SongNamejPanel = new javax.swing.JPanel();
        songNameLbl = new javax.swing.JLabel();
        songAuthorLbl = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        progressBarjPanel = new javax.swing.JPanel();
        startTimerLbl = new javax.swing.JLabel();
        stopTimerLbl = new javax.swing.JLabel();
        progressBarjSlider = new javax.swing.JSlider();
        TablejScrollPane = new javax.swing.JScrollPane();
        TreejPanel = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        FileMenu = new javax.swing.JMenu();
        playASongMenuItem = new javax.swing.JMenuItem();
        addSongMenuItem = new javax.swing.JMenuItem();
        deleteSongMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();

        menu1.setLabel("File");
        menu1.setName("File");
        menuBar1.add(menu1);

        menu2.setLabel("Edit");
        menu2.setName("File");
        menuBar1.add(menu2);

        popupMenu1.setLabel("popupMenu1");

        jTextField2.setText("jTextField2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ActionButtonsjPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        stop.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        stop.setLabel("stop");
        stop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stopMouseClicked(evt);
            }
        });

        play.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        play.setLabel("Play");
        play.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                playMouseClicked(evt);
            }
        });

        pause.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pause.setLabel("Pause");
        pause.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pauseMouseClicked(evt);
            }
        });

        next.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        next.setLabel("Next >>");
        next.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nextMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                nextMousePressed(evt);
            }
        });
        next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextActionPerformed(evt);
            }
        });

        previous.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        previous.setLabel("<< Previous");
        previous.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                previousMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                previousMousePressed(evt);
            }
        });
        previous.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousActionPerformed(evt);
            }
        });

        volumeSlider.setBackground(new java.awt.Color(255, 255, 255));
        volumeSlider.setForeground(new java.awt.Color(51, 153, 255));
        volumeSlider.setPaintLabels(true);
        volumeSlider.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Volume", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 18), new java.awt.Color(51, 204, 255))); // NOI18N
        volumeSlider.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        volumeSlider.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                volumeSliderMouseDragged(evt);
            }
        });

        javax.swing.GroupLayout ActionButtonsjPanelLayout = new javax.swing.GroupLayout(ActionButtonsjPanel);
        ActionButtonsjPanel.setLayout(ActionButtonsjPanelLayout);
        ActionButtonsjPanelLayout.setHorizontalGroup(
            ActionButtonsjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ActionButtonsjPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(previous, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(stop, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(play, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(pause, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(next, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(volumeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        ActionButtonsjPanelLayout.setVerticalGroup(
            ActionButtonsjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ActionButtonsjPanelLayout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(ActionButtonsjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(next, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                    .addComponent(stop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(play, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pause, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(previous, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(volumeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        SongNamejPanel.setBackground(new java.awt.Color(255, 153, 153));
        SongNamejPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        songNameLbl.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        songNameLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        songNameLbl.setText("Song name");
        songNameLbl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        songNameLbl.setIconTextGap(0);
        songNameLbl.setMaximumSize(new java.awt.Dimension(609, 512));
        songNameLbl.setMinimumSize(new java.awt.Dimension(609, 512));
        songNameLbl.setPreferredSize(new java.awt.Dimension(609, 512));

        songAuthorLbl.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        songAuthorLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        songAuthorLbl.setText("Author");

        javax.swing.GroupLayout SongNamejPanelLayout = new javax.swing.GroupLayout(SongNamejPanel);
        SongNamejPanel.setLayout(SongNamejPanelLayout);
        SongNamejPanelLayout.setHorizontalGroup(
            SongNamejPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SongNamejPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SongNamejPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(songNameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 105, Short.MAX_VALUE)
                    .addComponent(songAuthorLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        SongNamejPanelLayout.setVerticalGroup(
            SongNamejPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SongNamejPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(songNameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(songAuthorLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                .addGap(11, 11, 11))
        );

        jTextField1.setText("jTextField1");

        progressBarjPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        startTimerLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        startTimerLbl.setText("0:00:00");
        startTimerLbl.setMaximumSize(new java.awt.Dimension(100, 80));

        stopTimerLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stopTimerLbl.setText("0:00:00");
        stopTimerLbl.setMaximumSize(new java.awt.Dimension(100, 80));

        progressBarjSlider.setValue(0);
        progressBarjSlider.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        progressBarjSlider.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        progressBarjSlider.setPreferredSize(new java.awt.Dimension(300, 50));

        javax.swing.GroupLayout progressBarjPanelLayout = new javax.swing.GroupLayout(progressBarjPanel);
        progressBarjPanel.setLayout(progressBarjPanelLayout);
        progressBarjPanelLayout.setHorizontalGroup(
            progressBarjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(progressBarjPanelLayout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addComponent(startTimerLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(progressBarjSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(stopTimerLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        progressBarjPanelLayout.setVerticalGroup(
            progressBarjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(startTimerLbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(progressBarjPanelLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(progressBarjSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(stopTimerLbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        TablejScrollPane.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        TreejPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jSplitPane1.setDividerSize(0);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout TreejPanelLayout = new javax.swing.GroupLayout(TreejPanel);
        TreejPanel.setLayout(TreejPanelLayout);
        TreejPanelLayout.setHorizontalGroup(
            TreejPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
        );
        TreejPanelLayout.setVerticalGroup(
            TreejPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
        );

        FileMenu.setText("File");

        playASongMenuItem.setText("Open a Song");
        playASongMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playASongMenuItemActionPerformed(evt);
            }
        });
        FileMenu.add(playASongMenuItem);

        addSongMenuItem.setText("Add Song");
        addSongMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSongMenuItemActionPerformed(evt);
            }
        });
        FileMenu.add(addSongMenuItem);

        deleteSongMenuItem.setText("Delete Song");
        FileMenu.add(deleteSongMenuItem);

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        FileMenu.add(exitMenuItem);

        jMenuBar1.add(FileMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(SongNamejPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ActionButtonsjPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(TreejPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TablejScrollPane))
                    .addComponent(progressBarjPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TablejScrollPane)
                    .addComponent(TreejPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBarjPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SongNamejPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ActionButtonsjPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void playASongMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playASongMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_playASongMenuItemActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void volumeSliderMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_volumeSliderMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_volumeSliderMouseDragged

    private void previousMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_previousMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_previousMouseClicked

    private void nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nextActionPerformed

    private void playMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_playMouseClicked

    private void pauseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pauseMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pauseMouseClicked

    private void nextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nextMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_nextMouseClicked

    private void nextMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nextMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nextMousePressed

    private void stopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stopMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_stopMouseClicked

    private void previousMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_previousMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_previousMousePressed

    private void addSongMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSongMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addSongMenuItemActionPerformed

    private void previousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_previousActionPerformed

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
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ActionButtonsjPanel;
    private javax.swing.JMenu FileMenu;
    private javax.swing.JPanel SongNamejPanel;
    private javax.swing.JScrollPane TablejScrollPane;
    private javax.swing.JPanel TreejPanel;
    private javax.swing.JMenuItem addSongMenuItem;
    private javax.swing.JMenuItem deleteSongMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private java.awt.Menu menu1;
    private java.awt.Menu menu2;
    private java.awt.MenuBar menuBar1;
    private java.awt.Button next;
    private java.awt.Button pause;
    private java.awt.Button play;
    private javax.swing.JMenuItem playASongMenuItem;
    private java.awt.PopupMenu popupMenu1;
    private java.awt.Button previous;
    private javax.swing.JPanel progressBarjPanel;
    private javax.swing.JSlider progressBarjSlider;
    private javax.swing.JLabel songAuthorLbl;
    private javax.swing.JLabel songNameLbl;
    private javax.swing.JLabel startTimerLbl;
    private java.awt.Button stop;
    private javax.swing.JLabel stopTimerLbl;
    private javax.swing.JSlider volumeSlider;
    // End of variables declaration//GEN-END:variables
}
