package mytunes;

import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;

public class GUI1 extends javax.swing.JFrame {
    
    final private CustomPlayer player;
    final private Database database;
    private JTable songTable;
    final private SongTableModel songTableModel;
    
    //GUI Variables declaration                   
    
    private javax.swing.JMenu FileMenu;
    private javax.swing.JPanel SongNamejPanel;
    private javax.swing.JLabel songAuthorLbl;
    private javax.swing.JLabel songNameLbl;
    
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JPopupMenu columnPopupMenu;
    
    private javax.swing.JScrollPane TreejScrollPane;
    private javax.swing.JTree jTree1;
    
    private javax.swing.JScrollPane TablejScrollPane;
    private TableRowSorter<SongTableModel> sorter;
    
    private javax.swing.JPanel ActionButtonsjPanel;
    private java.awt.Button next;
    private java.awt.Button previous;
    private java.awt.Button pause;
    private java.awt.Button play;
    private java.awt.Button stop;
    
    private javax.swing.JSlider volumeSlider; 
    
    private javax.swing.JMenuItem playASongMenuItem;
    private javax.swing.JMenuItem addSongMenuItem;
    private javax.swing.JMenuItem deleteSongMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    
    private Song currentSong;
    private javax.swing.JSlider progressBarjSlider;
    private javax.swing.JPanel progressBarjPanel;
    private javax.swing.JLabel startTimerLbl;  
    private javax.swing.JLabel stopTimerLbl;
    

    //GUI Constructor
    public GUI1() {
        database = new Database();
        List<Song> songs = database.getAllSongs();
        songTableModel = new SongTableModel(songs);
        songTableModel.setTableUpdateListener(() -> {
            refreshSongTable();
        });
        player = new CustomPlayer(this, songTableModel, songs);
        initComponents();
    }
                     
    private void initComponents() {

        popupMenu = new javax.swing.JPopupMenu();
        columnPopupMenu = new javax.swing.JPopupMenu();
        
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
        
        jLabel3 = new javax.swing.JLabel();
        TreejScrollPane = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        
        progressBarjPanel = new javax.swing.JPanel();
        startTimerLbl = new javax.swing.JLabel();
        stopTimerLbl = new javax.swing.JLabel();
        progressBarjSlider = new javax.swing.JSlider();
        
        jMenuBar1 = new javax.swing.JMenuBar();
        FileMenu = new javax.swing.JMenu();
        playASongMenuItem = new javax.swing.JMenuItem();
        addSongMenuItem = new javax.swing.JMenuItem();
        deleteSongMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
      
        setTitle("MyTunes");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ActionButtonsjPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        stop.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        stop.setLabel("stop");
        stop.addActionListener((java.awt.event.ActionEvent evt) -> {
            stopActionPerformed(evt);
        });

        play.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        play.setLabel("Play");
        play.addActionListener((java.awt.event.ActionEvent evt) -> {
            playActionPerformed(evt);
        });

        pause.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pause.setLabel("Pause");
        pause.addActionListener((java.awt.event.ActionEvent evt) -> {
            pauseActionPerformed(evt);
        });

        next.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        next.setLabel("Next >>");
        next.addActionListener((java.awt.event.ActionEvent evt) -> {
            nextActionPerformed(evt);
        });

        previous.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        previous.setLabel("<< Previous");
        previous.addActionListener((java.awt.event.ActionEvent evt) -> {
            previousActionPerformed(evt);
        });

        volumeSlider.setBackground(new java.awt.Color(255, 255, 255));
        volumeSlider.setForeground(new java.awt.Color(51, 153, 255));
        volumeSlider.setPaintLabels(true);
        volumeSlider.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Volume", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 18), new java.awt.Color(51, 204, 255))); // NOI18N
        volumeSlider.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        volumeSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                volumeSliderStateChanged(evt);
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

        TreejScrollPane.setViewportView(jTree1);

        progressBarjPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        progressBarjPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        startTimerLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        startTimerLbl.setText("0:00:00");
        startTimerLbl.setMaximumSize(new java.awt.Dimension(100, 80));
        progressBarjPanel.add(startTimerLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 1, 80, -1));

        stopTimerLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stopTimerLbl.setText("0:00:00");
        stopTimerLbl.setMaximumSize(new java.awt.Dimension(100, 80));
        progressBarjPanel.add(stopTimerLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(689, 1, 86, -1));

        progressBarjSlider.setValue(0);
        progressBarjSlider.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        progressBarjSlider.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        progressBarjSlider.setPreferredSize(new java.awt.Dimension(300, 80));
        progressBarjPanel.add(progressBarjSlider, new org.netbeans.lib.awtextra.AbsoluteConstraints(243, 5, 417, 11));
           
        //Songs Table
        songTable = new JTable(songTableModel);
        songTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow only single row selection
        songTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = songTable.getSelectedRow();
                    if (selectedRow != -1) {
                        int songIndex = songTable.convertRowIndexToModel(selectedRow);
                        System.out.println("GUI selected SOng: "+ songTableModel.getValueAt(songIndex, 7));
                        player.setCurrentSongIndex(songIndex);
                    }
                }
            }
        });
        
        
        songTable.setAutoCreateRowSorter(true);
        sorter = new TableRowSorter<>(songTableModel);
        songTable.setRowSorter(sorter);
        
        sorter.addRowSorterListener(new RowSorterListener() {
            @Override
            public void sorterChanged(RowSorterEvent e) {
                if (e.getType() == RowSorterEvent.Type.SORTED) {
                    // Perform actions when the table has been sorted
                    List<Song> sortedSongs = new ArrayList<>();
                    for (int i = 0; i < songTableModel.getRowCount(); i++) {
                        int modelIndex = sorter.convertRowIndexToModel(i);
                        sortedSongs.add(songTableModel.getSongAt(modelIndex));
                    }
                    
                    songTableModel.setSortedSongs(sortedSongs);
                    player.setSortedSongList(sortedSongs);
                }
            }
        });
        
        sorter.toggleSortOrder(0);  // Default sorting on "Title" column
        
        TablejScrollPane = new JScrollPane(songTable);
        songTable.setFillsViewportHeight(true);
        
        //PopupMenu
        JMenuItem addMenuItem = new JMenuItem("Add Song");
        addMenuItem.addActionListener((ActionEvent e) -> {
            addSongMenuItemActionPerformed(e);
        });
        popupMenu.add(addMenuItem);
        
        JMenuItem deleteMenuItem = new JMenuItem("Delete Song");
        deleteMenuItem.addActionListener((ActionEvent e) -> {
            deleteSongMenuItemActionPerformed(e);
        });
        popupMenu.add(deleteMenuItem);

        // Add mouse listener to show popup menu
        songTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopup(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopup(e);
                }
            }

            private void showPopup(MouseEvent e) {
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
        
        // column selection popup menu
        columnPopupMenu = new JPopupMenu();
        for (int i = 1; i < songTableModel.getAllColumnsCount(); i++) {
            final int columnIndex = i;
            JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(songTableModel.getColumnNames(columnIndex), songTableModel.isColumnVisible(columnIndex));
            menuItem.addActionListener(e -> {
                songTableModel.setColumnVisibility(columnIndex, menuItem.isSelected());
                refreshSongTable();
            });
            columnPopupMenu.add(menuItem);
        }

        songTable.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopup(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopup(e);
                }
            }
            
            private void showPopup(MouseEvent e) {
                columnPopupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });

        // Drag and Drop
        songTable.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {
                        Song song = player.extractSongFromFile(file);
                        database.addSong(song);
                        songTableModel.addSong(song);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        TablejScrollPane.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

//        javax.swing.GroupLayout TablejScrollPaneLayout = new javax.swing.GroupLayout(TablejScrollPane);
//        TablejScrollPane.setLayout(TablejScrollPaneLayout);
//        TablejScrollPaneLayout.setHorizontalGroup(
//            TablejScrollPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 0, Short.MAX_VALUE)
//        );
//        TablejScrollPaneLayout.setVerticalGroup(
//            TablejScrollPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 0, Short.MAX_VALUE)
//        );

        FileMenu.setText("File");

        playASongMenuItem.setText("Open a Song");
        playASongMenuItem.addActionListener((java.awt.event.ActionEvent evt) -> {
            playASongMenuItemActionPerformed(evt);
        });
        FileMenu.add(playASongMenuItem);

        addSongMenuItem.setText("Add Song");
        addSongMenuItem.addActionListener((java.awt.event.ActionEvent evt) -> {
            addSongMenuItemActionPerformed(evt);
        });
        FileMenu.add(addSongMenuItem);

        deleteSongMenuItem.setText("Delete Song");
        deleteSongMenuItem.addActionListener((java.awt.event.ActionEvent evt) -> {
            deleteSongMenuItemActionPerformed(evt);
        });
        FileMenu.add(deleteSongMenuItem);

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener((java.awt.event.ActionEvent evt) -> {
            exitMenuItemActionPerformed(evt);
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
                        .addComponent(TreejScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(TreejScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                    .addComponent(TablejScrollPane))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBarjPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SongNamejPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ActionButtonsjPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }                       

    //need to edit this
    private void playASongMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(GUI1.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    Song song = player.extractSongFromFile(file);
                    if (song != null) {
                        database.addSong(song);
                        songTableModel.addSong(song);
                        refreshSongTable();
                        player.setCurrentSong(song);
                        player.play();
                    } else {
                        JOptionPane.showMessageDialog(GUI1.this, "Failed to extract ID3 tags from the selected file.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
    }                                                 

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                             
        System.exit(0);
    }
    
    private void addSongMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                
        player.addSong(songTableModel,database);
        refreshSongTable();
    }  
    
    private void deleteSongMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                
        player.deleteSong(songTable,songTableModel,database);
        refreshSongTable();
    }

    private void volumeSliderStateChanged(ChangeEvent evt) {                                          
        JSlider source = (JSlider) evt.getSource();
        if (!source.getValueIsAdjusting()) {
            int volume = (int) source.getValue();
            float gain = (float) (Math.log(volume / 100.0) / Math.log(10.0) * 20.0);  // Convert to decibels
            player.setVolume((int) gain);
        }
    }                                         
           
    private void previousActionPerformed(java.awt.event.ActionEvent evt) {                                         
        player.previous();
    }   

    private void nextActionPerformed(java.awt.event.ActionEvent evt) {                                     
        player.next();
    }                                    

    private void playActionPerformed(java.awt.event.ActionEvent evt) {                                  
        player.play();
    }                                 

    private void pauseActionPerformed(java.awt.event.ActionEvent evt) {   
//        if(!unpause){
            player.pause();
//            pause.setLabel("Un-Pause");
//            unpause = true;
//        } else if(unpause){
//            player.unpause();
//            pause.setLabel("Pause");
//            unpause = false;
//        }
        
    }                                                              

    private void stopActionPerformed(java.awt.event.ActionEvent evt) {                                         
        player.stop();
    }                                                                          
    
    private void refreshSongTable() {
        songTableModel.setSongs(database.getAllSongs());
        songTableModel.fireTableDataChanged();
    }
    
    public void setPlaybackSliderValue(int frame, int currentTimeInMilli) {
    
    //adjusts the timers
    currentSong = player.getCurrentSong();
    
    long totalMilliseconds = currentTimeInMilli;
    long totalSeconds = totalMilliseconds / 1000;
    long hours = totalSeconds / 3600;
    long minutes = (totalSeconds % 3600) / 60;
    long seconds = totalSeconds % 60;
    String currentTime = String.format("%d:%02d:%02d", hours, minutes, seconds);

    // Update elapsed time label
    startTimerLbl.setText(currentTime);

    // Calculate remaining time
    long songLengthMilliseconds = currentSong.getMp3File().getLengthInMilliseconds();
    long remainingMilliseconds = songLengthMilliseconds - totalMilliseconds;
    long remainingSeconds = remainingMilliseconds / 1000;
    long remainingHours = remainingSeconds / 3600;
    long remainingMinutes = (remainingSeconds % 3600) / 60;
    long remainingSecs = remainingSeconds % 60;
    String remainingTime = String.format("%d:%02d:%02d", remainingHours, remainingMinutes, remainingSecs);

    // Update remaining time label
    stopTimerLbl.setText(remainingTime);
    
    //updates the progressBar position   
    progressBarjSlider.setValue(frame);
}


    public void updateSongTitleAndArtist(Song song){
        songNameLbl.setText(song.getTitle());
        songAuthorLbl.setText(song.getArtist());
    }
    
    public void updatePlaybackSlider(Song song){
        // update max count for slider
        progressBarjSlider.setMaximum(song.getMp3File().getFrameCount());

        // create the song length label
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();

        // beginning will be 00:00
        startTimerLbl.setText("0:00:00");
//        JLabel labelBeginning = new JLabel("00:00");
//        labelBeginning.setFont(new Font("Dialog", Font.BOLD, 18));
//        labelBeginning.setForeground(TEXT_COLOR);

        // end will vary depending on the song
        stopTimerLbl.setText(song.getSongLength());
//        JLabel labelEnd =  new JLabel(song.getSongLength());
//        labelEnd.setFont(new Font("Dialog", Font.BOLD, 18));
//        labelEnd.setForeground(TEXT_COLOR);

        labelTable.put(0, startTimerLbl);
        labelTable.put(song.getMp3File().getFrameCount(), stopTimerLbl);

//        progressBarjSlider.setLabelTable(labelTable);
//        progressBarjSlider.setPaintLabels(true);
    }

    
//    public List<Song> getSortedSongs() {
//        int rowCount = sorter.getViewRowCount();
//        List<Song> sortedSongs = new ArrayList<>();
//        for (int i = 0; i < rowCount; i++) {
//            int modelRow = sorter.convertRowIndexToModel(i);
//            sortedSongs.add(songTableModel.getSongAt(modelRow));
//            System.out.println("sortedSongs:" + sortedSongs);
//        }
//        return sortedSongs;
//    }
    
}
