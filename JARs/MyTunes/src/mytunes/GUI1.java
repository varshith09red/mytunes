package mytunes;

import java.awt.Font;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class GUI1 extends javax.swing.JFrame{
    
    final private CustomPlayer player;
    final private Database database;
    private JTable songTable;
    final private SongTableModel songTableModel;
    
    //GUI Variables declaration                   

    private javax.swing.JPanel SongNamejPanel;
    private javax.swing.JLabel songAuthorLbl;
    private javax.swing.JLabel songNameLbl;

    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JPopupMenu columnPopupMenu;
    
    private javax.swing.JScrollPane TreejScrollPane;
    private javax.swing.JTree rootTree;
//    private javax.swing.JTree libraryTree;
//    private javax.swing.JTree playlistTree;
    private javax.swing.tree.DefaultMutableTreeNode rootNode;
    private javax.swing.tree.DefaultMutableTreeNode libraryNode;
    private javax.swing.tree.DefaultMutableTreeNode playlistNode;
    private javax.swing.tree.DefaultTreeModel rootTreeModel;
//    private javax.swing.tree.DefaultTreeModel playlistTreeModel;
    private List<Playlist> playlists;
    public final PlaylistController playlistController;
    public Playlist playlistSelectedNode; //to store which playlist id selected
    
    private javax.swing.JScrollPane TablejScrollPane;
    private TableRowSorter<SongTableModel> sorter;
    
    private javax.swing.JPanel ActionButtonsjPanel;
    private java.awt.Button next;
    private java.awt.Button previous;
    private java.awt.Button pause;
    private java.awt.Button play;
    private java.awt.Button stop;
    private JCheckBox shuffleCheckBox;
    private JCheckBox repeatCheckBox;
    public boolean shuffleEnabled = false;
    public boolean repeatEnabled = false;
        
    private javax.swing.JSlider volumeSlider; 
    
    private javax.swing.JMenu FileMenu;
    private javax.swing.JMenuItem playASongMenuItem;
    private javax.swing.JMenuItem addSongMenuItem;
    private javax.swing.JMenuItem deleteSongMenuItem;
    private javax.swing.JMenuItem createNewPlaylistMenuItem;
    private javax.swing.JMenuItem addSongToPlaylistMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenuItem refresh;
    
    private javax.swing.JMenu ControlsMenu;
    private javax.swing.JMenuItem playControl;
    private javax.swing.JMenuItem nextControl;
    private javax.swing.JMenuItem previousControl;
    private javax.swing.JMenu playRecent;
    private javax.swing.JMenuItem goToCurrentSong;
    private javax.swing.JMenuItem increaseVolume;
    private javax.swing.JMenuItem decreaseVolume;
    private javax.swing.JCheckBoxMenuItem shuffleControl;
    private javax.swing.JCheckBoxMenuItem repeatControl;
    
    private Song currentSong;
    private javax.swing.JSlider progressBarjSlider;
    private javax.swing.JPanel progressBarjPanel;
    private javax.swing.JLabel startTimerLbl;  
    private javax.swing.JLabel stopTimerLbl;
    

    //GUI Constructor
    public GUI1(Database database) {
        this.database = database;
        this.database.addChangeListener(this); // Register as listener
        List<Song> songs = database.getAllSongs();
        songTableModel = new SongTableModel(songs);
        songTableModel.setTableUpdateListener(() -> {
            refreshSongTable();
        });
        player = new CustomPlayer(this, songTableModel, songs);
        playlistController = new PlaylistController(database);
        playlists = playlistController.getAllPlaylists();
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
        shuffleCheckBox = new javax.swing.JCheckBox("Shuffle");
        repeatCheckBox = new javax.swing.JCheckBox("Repeat");
        
        SongNamejPanel = new javax.swing.JPanel();
        songNameLbl = new javax.swing.JLabel();
        songAuthorLbl = new javax.swing.JLabel();
        
        TreejScrollPane = new javax.swing.JScrollPane();
        // Create the "Root" tree and make it invisible
        rootNode = new javax.swing.tree.DefaultMutableTreeNode("Root");
        rootTreeModel = new javax.swing.tree.DefaultTreeModel(rootNode);
        rootTree = new javax.swing.JTree(rootTreeModel);
        rootTree.setRootVisible(true);
        rootTree.setShowsRootHandles(true);
        rootTree.setEditable(false);

        // Create the "Library" node
        libraryNode = new javax.swing.tree.DefaultMutableTreeNode("Library"){
            @Override
            public boolean isLeaf() {
                return true; // Prevent adding children to "Library" node
            }
            
        };
        
        // Create the "Playlist" node with user created playlists from Database
        playlistNode = new javax.swing.tree.DefaultMutableTreeNode("Playlists");
        for(Playlist p : playlists){
            System.out.println(p.getName() + " : "+(p.getSongs()));
            javax.swing.tree.DefaultMutableTreeNode playList = new javax.swing.tree.DefaultMutableTreeNode(p.getName());
            playlistNode.add(playList);
        }
        
        // Add "Library" and "Playlists" as children of "Root"
        rootNode.add(libraryNode);
        rootNode.add(playlistNode);
        // Expand all nodes
        expandAllNodes(rootTree, 0, rootTree.getRowCount());

        // Set root node visibility to false
        rootTree.setRootVisible(false);
        
        // Add TreeSelectionListener
        rootTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                treeSelectionChanged(evt);
            }
        });
        
        // Add mouse listener to the playlist node
        rootTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) && rootTree.getSelectionPath() != null) {
                    TreePath path = rootTree.getPathForLocation(e.getX(), e.getY());
                    rootTree.setSelectionPath(path);
                    if (path != null && path.getLastPathComponent() instanceof DefaultMutableTreeNode) {
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                        if (node.getParent() == playlistNode) {
                            showPlaylistPopup(e, node);
                        }
                        if (node == playlistNode) {
                            showAddPlaylistPopup(e);
                        }
                    }
                }
            }
        });

        progressBarjPanel = new javax.swing.JPanel();
        startTimerLbl = new javax.swing.JLabel();
        stopTimerLbl = new javax.swing.JLabel();
        progressBarjSlider = new javax.swing.JSlider();
        
        jMenuBar1 = new javax.swing.JMenuBar();
        FileMenu = new javax.swing.JMenu();
        playASongMenuItem = new javax.swing.JMenuItem();
        addSongMenuItem = new javax.swing.JMenuItem();
        deleteSongMenuItem = new javax.swing.JMenuItem();
        createNewPlaylistMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        refresh = new javax.swing.JMenuItem();
        
        ControlsMenu = new javax.swing.JMenu();
        playControl = new javax.swing.JMenuItem();
        nextControl = new javax.swing.JMenuItem();
        previousControl = new javax.swing.JMenuItem();
        playRecent = new javax.swing.JMenu();
        goToCurrentSong = new javax.swing.JMenuItem();
        increaseVolume = new javax.swing.JMenuItem();
        decreaseVolume = new javax.swing.JMenuItem();
        shuffleControl = new javax.swing.JCheckBoxMenuItem("Shuffle");
        repeatControl = new javax.swing.JCheckBoxMenuItem("Repeat");
        
      
        setTitle("MyTunes");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ActionButtonsjPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        stop.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        stop.setLabel("Stop");
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
        
//        ImageIcon shuffleIcon = new ImageIcon("C:\\Users\\030834321\\Downloads\\shuffle.png");
//        shuffleCheckBox.setIcon(new ImageIcon(shuffleIcon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH)));
//        shuffleCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
//        shuffleCheckBox.addItemListener((e) -> ShuffleActionPerformed(e, songTableModel.getSortedSongs()));
        
//        ImageIcon repeatIcon = new ImageIcon("C:\\Users\\030834321\\Downloads\\repeat.png");
//        repeatCheckBox.setIcon(new ImageIcon(repeatIcon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH)));
//        repeatCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
//        repeatCheckBox.addItemListener((e) -> RepeatActionPerformed(e));
        
        
        javax.swing.GroupLayout ActionButtonsjPanelLayout = new javax.swing.GroupLayout(ActionButtonsjPanel);
        ActionButtonsjPanel.setLayout(ActionButtonsjPanelLayout);
        ActionButtonsjPanelLayout.setHorizontalGroup(
            ActionButtonsjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ActionButtonsjPanelLayout.createSequentialGroup()
                .addGap(25, 25, 500)
//                .addComponent(shuffleCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addGap(20, 20, 20)
//                .addComponent(repeatCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addGap(20, 20, 20)
                .addComponent(previous, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(stop, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(play, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(pause, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(next, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(volumeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        ActionButtonsjPanelLayout.setVerticalGroup(
            ActionButtonsjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ActionButtonsjPanelLayout.createSequentialGroup()
                .addContainerGap(20, 25)
                .addGroup(ActionButtonsjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(next, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(stop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(play, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pause, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(previous, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(volumeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
//                    .addComponent(shuffleCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addComponent(repeatCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        SongNamejPanel.setBackground(new java.awt.Color(0, 0, 0));
        SongNamejPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        songNameLbl.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        songNameLbl.setForeground(new java.awt.Color(255, 255, 255));
        songNameLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        songNameLbl.setText("Song Name");
        songNameLbl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        songNameLbl.setIconTextGap(0);
        songNameLbl.setMaximumSize(new java.awt.Dimension(609, 512));
        songNameLbl.setMinimumSize(new java.awt.Dimension(609, 512));
        songNameLbl.setPreferredSize(new java.awt.Dimension(609, 512));

        songAuthorLbl.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        songAuthorLbl.setForeground(new java.awt.Color(255, 255, 255));
        songAuthorLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        songAuthorLbl.setText("Author");
        songAuthorLbl.setMaximumSize(new java.awt.Dimension(14, 14));
        songAuthorLbl.setMinimumSize(new java.awt.Dimension(14, 14));
        songAuthorLbl.setPreferredSize(new java.awt.Dimension(14, 14));

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

        TreejScrollPane.setViewportView(rootTree);

        progressBarjPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
//        progressBarjPanel.setLayout(new javax.swing.GroupLayout));

        startTimerLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        startTimerLbl.setText("0:00:00");
        startTimerLbl.setMaximumSize(new java.awt.Dimension(100, 80));
//        progressBarjPanel.add(startTimerLbl);

        progressBarjSlider.setValue(0);
//        progressBarjSlider.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        progressBarjSlider.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        progressBarjSlider.setPreferredSize(new java.awt.Dimension(300, 80));
//        progressBarjPanel.add(progressBarjSlider, BorderLayout.CENTER);

        stopTimerLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stopTimerLbl.setText("0:00:00");
        stopTimerLbl.setMaximumSize(new java.awt.Dimension(100, 80));
//        progressBarjPanel.add(stopTimerLbl);

        javax.swing.GroupLayout progressBarjPanelLayout = new javax.swing.GroupLayout(progressBarjPanel);
        progressBarjPanel.setLayout(progressBarjPanelLayout);
        progressBarjPanelLayout.setHorizontalGroup(
            progressBarjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addGroup(progressBarjPanelLayout.createSequentialGroup()
//                .addGap(100, 100, 650)
                .addGap(30, 30, 30)
                .addComponent(startTimerLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(progressBarjSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 620, 2000)
                .addGap(30, 30, 30)
                .addComponent(stopTimerLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        progressBarjPanelLayout.setVerticalGroup(
            progressBarjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addComponent(startTimerLbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(progressBarjSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(stopTimerLbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        
           
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
                        System.out.println("GUI Table Selected Song: "+ songTableModel.getValueAt(songIndex, 7));
                        player.songSelectedFromRecentSongs = true;
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
                    if(!sortedSongs.isEmpty()){
                        songTableModel.setSortedSongs(sortedSongs);
                        player.setSortedSongList(sortedSongs);
                    }
                }
            }
        });
        
        sorter.toggleSortOrder(0);  // Default sorting on "Title" column
        
        // Enable drag support
        songTable.setDragEnabled(true);
        songTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        songTable.setTransferHandler(new SongTransferHandler());
        
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
        popupMenu.addSeparator();
        
        addSongToPlaylistMenuItem = new JMenu("Add Song To Playlist");

        updateAddSongToPlaylistSubMenu();
        popupMenu.add(addSongToPlaylistMenuItem);

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
//                        songTableModel.addSong(song);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(GUI1.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        
        FileMenu.addSeparator();
        createNewPlaylistMenuItem.setText("Create New Playlist");
        createNewPlaylistMenuItem.addActionListener((java.awt.event.ActionEvent evt) -> {
            addPlaylist();
        });
        FileMenu.add(createNewPlaylistMenuItem);
        FileMenu.addSeparator();
        
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener((java.awt.event.ActionEvent evt) -> {
            exitMenuItemActionPerformed(evt);
        });
        FileMenu.add(exitMenuItem); 
        FileMenu.addSeparator();
        
        refresh.setText("Refresh");
        refresh.addActionListener((java.awt.event.ActionEvent evt) -> {
            refreshMenuItemActionPerformed(evt);
        });
        FileMenu.add(refresh);
        
        ControlsMenu.setText("Controls");
        
//        songTable.setFocusable(true);
        InputMap inputMap = songTable.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ActionMap actionMap = songTable.getActionMap();
        
        KeyStroke playSongkeyStroke = KeyStroke.getKeyStroke("SPACE");

        // Add key binding for "Space" play
        inputMap.put(playSongkeyStroke, "playSong");
        actionMap.put("playSong", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("play triggered from control menu");
                playActionPerformed(e);
            }
        });
        
//        songTable.addKeyListener(new java.awt.event.KeyAdapter() {
//            public void keyPressed(java.awt.event.KeyEvent evt) {
////                System.out.println("Key pressed: " + KeyEvent.getKeyText(evt.getKeyCode()));
//                if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
//                    System.out.println("Space key pressed");
//                    playActionPerformed(null); // Call the play action
//                }
//            }
//        });
        
        playControl.setText("Play");
        playControl.addActionListener((java.awt.event.ActionEvent evt) -> {
            playActionPerformed(evt);
        });
        playControl.setAccelerator(playSongkeyStroke);
        playControl.addActionListener(this::playActionPerformed);
        ControlsMenu.add(playControl);
        playControl.setFocusable(false);
        
        KeyStroke nextSongkeyStroke = KeyStroke.getKeyStroke("control RIGHT");

        // Add key binding for "ctrl + right arrow" next
        inputMap.put(nextSongkeyStroke, "nextSong");
        actionMap.put("nextSong", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("next triggered from control menu");
                nextActionPerformed(e);
//                player.skipForward();
            }
        });
        
        nextControl.setText("Next");
        nextControl.addActionListener((java.awt.event.ActionEvent evt) -> {
            nextActionPerformed(evt);
//            player.skipForward();
        });
        nextControl.setAccelerator(nextSongkeyStroke);
        ControlsMenu.add(nextControl);

        KeyStroke prevSongkeyStroke = KeyStroke.getKeyStroke("control LEFT");

        // Add key binding for "ctrl + left arrow" previous
        inputMap.put(prevSongkeyStroke, "prevSong");
        actionMap.put("prevSong", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("previous triggered from control menu");
                previousActionPerformed(e);
            }
        });
        previousControl.setText("Previous");
        previousControl.addActionListener((java.awt.event.ActionEvent evt) -> {
            previousActionPerformed(evt);
        });
        previousControl.setAccelerator(prevSongkeyStroke);
        ControlsMenu.add(previousControl);
        
        playRecent.setText("Play Recent");
        ControlsMenu.add(playRecent);
        updateRecentMenu();
        
        // Add key binding for "Ctrl-L"
        KeyStroke goToCurrentSongKeyStroke = KeyStroke.getKeyStroke("control L");

        inputMap.put(goToCurrentSongKeyStroke, "goToCurrentSong");
        actionMap.put("goToCurrentSong", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToCurrentSong();
            }
        });
        
        goToCurrentSong.setText("Go To Current Song");
        goToCurrentSong.addActionListener((java.awt.event.ActionEvent evt) -> {
            goToCurrentSong();
        });
        goToCurrentSong.setAccelerator(goToCurrentSongKeyStroke);
        ControlsMenu.add(goToCurrentSong);
        
        ControlsMenu.addSeparator();
        
        // Add key binding for "Ctrl-I"
        KeyStroke increaseVolKeyStroke = KeyStroke.getKeyStroke("control I");

        inputMap.put(increaseVolKeyStroke, "increaseVolume");
        actionMap.put("increaseVolume", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                increaseVolume();
            }
        });
        increaseVolume.setText("Increase Volume");
        increaseVolume.addActionListener((java.awt.event.ActionEvent evt) -> {
            increaseVolume();
        });
        increaseVolume.setAccelerator(increaseVolKeyStroke);
        ControlsMenu.add(increaseVolume);
        
        // Add key binding for "Ctrl-D"
        KeyStroke decreaseVolKeyStroke = KeyStroke.getKeyStroke("control D");

        inputMap.put(decreaseVolKeyStroke, "decreaseVolume");
        actionMap.put("decreaseVolume", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decreaseVolume();
            }
        });
        decreaseVolume.setText("Decrease Volume");
        decreaseVolume.addActionListener((java.awt.event.ActionEvent evt) -> {
            decreaseVolume();
        });
        decreaseVolume.setAccelerator(decreaseVolKeyStroke);
        ControlsMenu.add(decreaseVolume);
        
        ControlsMenu.addSeparator();
        
        shuffleControl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        shuffleControl.addItemListener((e) -> ShuffleActionPerformed(e, songTableModel.getSortedSongs()));
        ControlsMenu.add(shuffleControl);
        
        repeatControl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        repeatControl.addItemListener((e) -> RepeatActionPerformed(e));
        ControlsMenu.add(repeatControl);
        
        
        jMenuBar1.add(FileMenu);
        jMenuBar1.add(ControlsMenu);

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
                    .addComponent(ActionButtonsjPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }                       

    //need to edit this
    private void playASongMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        try{
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(GUI1.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                Song song = player.extractSongFromFile(file);
                if (song != null) {
//                    database.addSong(song);
//                    songTableModel.addSong(song);
//                    refreshSongTable();
                    player.setCurrentSong(song);
                    player.play();
                } else {
                    JOptionPane.showMessageDialog(GUI1.this, "Failed to extract ID3 tags from the selected file.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(GUI1.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }                                                 

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                             
        System.exit(0);
    }
    
    private void refreshMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        this.revalidate();
        this.repaint();
        this.playlists = playlistController.getAllPlaylists();
        this.refreshSongTable();
    }
    
    private void addSongMenuItemActionPerformed(java.awt.event.ActionEvent evt) { 
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            Song song = player.extractSongFromFile(file);
            if (song != null) {
                if(playlistSelectedNode == null)
                    player.addSong(songTableModel,database, song);
                else
                    playlistController.addSongToPlaylist(song, playlistSelectedNode);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to extract ID3 tags from the selected file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        this.refreshSongTable();
    }  
    
    private void deleteSongMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        if(playlistSelectedNode == null)
            player.deleteSong(songTable,songTableModel,database);
        else{
            System.out.println("Deleting: "+player.getCurrentSong().getTitle()+" from: "+playlistSelectedNode.getName()+ " playlist");
            playlistController.deleteSongFromPlaylist(player.getCurrentSong(), playlistSelectedNode);
        }
        this.refreshSongTable();
        for (GUI1 listener : this.database.getListeners()) {
            listener.refreshSongTable();
        }
    }
    
    private void addSongToPlaylistMenuItemActionPerformed(Playlist playlist) {                                                
        int selectedRow = songTable.getSelectedRow();
        if (selectedRow != -1) {
            int songIndex = songTable.convertRowIndexToModel(selectedRow);
            Song selectedSong = songTableModel.getSongAt(songIndex);
            playlistController.addSongToPlaylist(selectedSong, playlist);
        }
        this.refreshSongTable();
    }

    private void increaseVolume() {
        int currentVolume = volumeSlider.getValue();
        int newVolume = Math.min(currentVolume + 5, 100); // Ensure volume doesn't exceed 100%
        volumeSlider.setValue(newVolume);
        float gain = (float) (Math.log(newVolume / 100.0) / Math.log(10.0) * 20.0);  // Convert to decibels
        player.setVolume((int) gain);
    }

    private void decreaseVolume() {
        int currentVolume = volumeSlider.getValue();
        int newVolume = Math.max(currentVolume - 5, 0); // Ensure volume doesn't go below 0%
        volumeSlider.setValue(newVolume);
        float gain = (float) (Math.log(newVolume / 100.0) / Math.log(10.0) * 20.0);  // Convert to decibels
        player.setVolume((int) gain);
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
//        player.pressedPrev = true;
        player.previous();
    }   

    private void nextActionPerformed(java.awt.event.ActionEvent evt) {                                     
//        player.pressedNext = true;
        player.next();
    }                                    

    private void playActionPerformed(java.awt.event.ActionEvent evt) {                                  
        player.play();
        updateRecentMenu();
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
    
    private void ShuffleActionPerformed(java.awt.event.ItemEvent evt, List<Song> songs) {   
        shuffleEnabled = shuffleControl.isSelected(); 
        System.out.println("shuffleEnabled: "+shuffleEnabled);
        player.setSortedSongList(songs);
    }  
    
    private void RepeatActionPerformed(java.awt.event.ItemEvent evt) {   
        repeatEnabled = repeatControl.isSelected(); 
        System.out.println("repeatEnabled: "+repeatEnabled);
    }
    

    private void stopActionPerformed(java.awt.event.ActionEvent evt) { 
        player.pressedStop = true;
        player.stop();
    }                                                                          
    
    public void refreshSongTable() {
        if(playlistSelectedNode != null){
            this.playlists = playlistController.getAllPlaylists();
            songTableModel.setSongs(playlistController.getAllPlaylistSongs(playlistSelectedNode));
//            songTableModel.fireTableDataChanged();
        } else{ //if library is selected, we need to get all the songs
            songTableModel.setSongs(database.getAllSongs());
//            songTableModel.fireTableDataChanged();
        }      
    }
    
    public void setPlaybackSliderValue(int frame, int currentTimeInSeconds) {
        // Get the current song
        currentSong = player.getCurrentSong();
        if (currentSong == null) return;

        // Calculate elapsed time
        long totalSeconds = currentTimeInSeconds;
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        String currentTime = String.format("%d:%02d:%02d", hours, minutes, seconds);

        // Calculate remaining time
        long songLengthSeconds = currentSong.getMp3File().getLengthInMilliseconds() / 1000;
        long remainingSeconds = songLengthSeconds - totalSeconds;
        long remainingHours = remainingSeconds / 3600;
        long remainingMinutes = (remainingSeconds % 3600) / 60;
        long remainingSecs = remainingSeconds % 60;
        String remainingTime = String.format("%d:%02d:%02d", remainingHours, remainingMinutes, remainingSecs);

        // Update elapsed time label
        startTimerLbl.setText(currentTime);

        // Update remaining time label
        stopTimerLbl.setText(remainingTime);

        // Update the progress bar position
        progressBarjSlider.setValue(currentTimeInSeconds);
    }

    public void updateSongTitleAndArtist(Song song){
        songNameLbl.setText(song.getTitle());
        songAuthorLbl.setText(song.getArtist());
    }
    
    public void updatePlaybackSlider(Song song){
        // update max count for slider
        progressBarjSlider.setMaximum((int) (song.getMp3File().getLengthInMilliseconds() / 1000));

        // create the song length label
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();

        startTimerLbl.setText("0:00:00");

        stopTimerLbl.setText(song.getSongLength());

        labelTable.put(0, startTimerLbl);
        labelTable.put(progressBarjSlider.getMaximum(), stopTimerLbl);

    }
    
    private void expandAllNodes(javax.swing.JTree tree, int startingIndex, int rowCount) {
        for (int i = startingIndex; i < rowCount; ++i) {
            tree.expandRow(i);
        }

        if (tree.getRowCount() != rowCount) {
            expandAllNodes(tree, rowCount, tree.getRowCount());
        }
    }
    
    private void updateAddSongToPlaylistSubMenu() {
        addSongToPlaylistMenuItem.removeAll();
        for (Playlist playlist : playlists) {
            JMenuItem playlistItem = new JMenuItem(playlist.getName());
            playlistItem.addActionListener(e -> addSongToPlaylistMenuItemActionPerformed(playlist));
            addSongToPlaylistMenuItem.add(playlistItem);
        }
    }
    
    public void updateRecentMenu() {
        playRecent.removeAll();
        List<Song> recentSongs = player.getRecentSongs();
        for (Song song : recentSongs) {
            JMenuItem recentSongsItem = new JMenuItem(song.getTitle());
            recentSongsItem.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("recentSongs CLicked: "+song.getTitle());
                    player.setCurrentSong(song);
                    player.play();
                }
            });
            playRecent.add(recentSongsItem);
        }
    }

    public void goToCurrentSong() {
        int currentSongIndex = player.getCurrentSongIndex();
        if (currentSongIndex >= 0) {
            songTable.setRowSelectionInterval(currentSongIndex, currentSongIndex);
            songTable.scrollRectToVisible(songTable.getCellRect(currentSongIndex, 0, true));
        }
    }

    // Method to handle tree selection changes
    private void treeSelectionChanged(javax.swing.event.TreeSelectionEvent evt) {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) rootTree.getLastSelectedPathComponent();
        if (selectedNode == null) {
            playlistSelectedNode = null;
            return;
        }

        String nodeName = selectedNode.getUserObject().toString();

        if (nodeName.equals("Library")) {
            playlistSelectedNode = null;
            // Fetch all songs from the db and display in the library
            System.out.println("library selected: "+ songTableModel.getSortedSongs());
//            updateSongTable(songTableModel.getSortedSongs());
            this.refreshSongTable();
        } else if (selectedNode.getParent() == playlistNode) {
            // Fetch songs from the selected playlist and display in the JTable
            Playlist selectedPlaylist = getPlaylistByName(nodeName); // You need to implement this method
            if (selectedPlaylist != null) {
//                updateSongTable(selectedPlaylist.getSongs());
                
                playlistSelectedNode = selectedPlaylist;
                this.refreshSongTable();
            }
        }
    }

    // Method to update the JTable with songs
    private void updateSongTable(List<Song> songs) {
        songTableModel.clearSongs(); // Clear the table
//        for (Song song : songs) {
//            songTableModel.addSong(song);
//        }
        songTableModel.setSongs(songs);
        songTableModel.fireTableDataChanged();
    }

    // Placeholder method to get a playlist by name, you need to implement this
    private Playlist getPlaylistByName(String name) {
        // Iterate through your playlists and return the matching one
        for (Playlist playlist : playlists) {
            if (playlist.getName().equals(name)) {
                return playlist;
            }
        }
        return null;
    }
    
    private void showPlaylistPopup(MouseEvent e, DefaultMutableTreeNode node) {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem deletePlaylist = new JMenuItem("Delete Playlist");
        deletePlaylist.addActionListener(evt -> deletePlaylist(node));
        menu.add(deletePlaylist);
        JMenuItem openInNewWindow = new JMenuItem("Open in New Window");
        openInNewWindow.addActionListener(evt -> openPlaylistInNewWindow(node));
        menu.add(openInNewWindow);
        menu.show(rootTree, e.getX(), e.getY());
    }
    
    private void showAddPlaylistPopup(MouseEvent e) {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem addPlaylist = new JMenuItem("Add Playlist");
        addPlaylist.addActionListener(evt -> addPlaylist());
        menu.add(addPlaylist);
        menu.show(rootTree, e.getX(), e.getY());
    }

    private void addPlaylist() {
        String name = JOptionPane.showInputDialog(this, "Enter Playlist Name:");
        if (name != null && !name.trim().isEmpty()) {
            playlistController.createPlaylist(name);
            Playlist newPlaylist = new Playlist(0, name); // ID will be managed by the database
            playlists.add(newPlaylist);
            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(newPlaylist.getName());
            playlistNode.add(newNode);
            rootTreeModel.reload(playlistNode);
            expandAllNodes(rootTree, 0, rootTree.getRowCount());
            
            //select the new node
            TreePath path = new TreePath(newNode.getPath());
            rootTree.setSelectionPath(path);
            rootTree.scrollPathToVisible(path);
        }
        updateAddSongToPlaylistSubMenu();
    }
    
    private void deletePlaylist(DefaultMutableTreeNode node) {
        int confirm = JOptionPane.showConfirmDialog(this, "Deleting the playlist will remove all songs in the playlist. Are you sure?", "Warning", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String playlistName = node.getUserObject().toString();
            Playlist playlistToDelete = playlists.stream().filter(p -> p.getName().equals(playlistName)).findFirst().orElse(null);
            if (playlistToDelete != null) {
                playlistController.deletePlaylist(playlistToDelete);
                playlists.remove(playlistToDelete);
                playlistNode.remove(node);
                rootTreeModel.reload(playlistNode);
            }
        }
        updateAddSongToPlaylistSubMenu();
    }

    private void openPlaylistInNewWindow(DefaultMutableTreeNode node) {
        System.out.println("new window click triggered");
//        if (node.getUserObject() instanceof Playlist) {
//            System.out.println("new windoe click triggered: inside if");
            String PlaylistName = node.getUserObject().toString();
            Playlist p = getPlaylistByName(PlaylistName);
            GUI1 playlistWindow = new GUI1(this.database);
            this.database.addChangeListener(playlistWindow);
            playlistWindow.playlistSelectedNode = playlistSelectedNode;
            playlistSelectedNode = null;
            playlistWindow.setTitle(PlaylistName +" Playlist Songs");
            playlistWindow.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            // Add a WindowListener to remove the listener when the window is closed
            playlistWindow.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.out.println("Window is closing, removing the listener");
                    database.removeListener(playlistWindow);
                }
            });
            
            // Disable treejScrollPane in the new window
//            playlistWindow.rootTree.setSelectionPath(new TreePath(this.playlistSelectedNode.getPath()));
            playlistWindow.TreejScrollPane.getHorizontalScrollBar().setEnabled(false);
            playlistWindow.TreejScrollPane.getVerticalScrollBar().setEnabled(false);
            playlistWindow.TreejScrollPane.getViewport().getView().setEnabled(false);
            
            this.rootTree.setSelectionPath(new TreePath(this.libraryNode.getPath()));
           
            if (p != null) {
//                playlistWindow.updateSongTable(p.getSongs());
                    playlistWindow.refreshSongTable();
            }
            
            // Move the selected playlist to the new window
            playlistWindow.setVisible(true);
            
            // Drag and Drop
            playlistWindow.songTable.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent dtde) {
                try {
                    if (dtde.isDataFlavorSupported(new DataFlavor(Song.class, "Songs"))) {
                        dtde.acceptDrop(DnDConstants.ACTION_MOVE);
                        Transferable transferable = dtde.getTransferable();
                        List<Song> droppedSongs = (List<Song>) transferable.getTransferData(new DataFlavor(Song.class, "Songs"));
                        for(Song song: droppedSongs){
                            playlistWindow.playlistController.addSongToPlaylist(song, playlistWindow.playlistSelectedNode);
                        }
                        dtde.dropComplete(true);
                        playlistWindow.refreshSongTable();
                    } else {
                        dtde.rejectDrop();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    dtde.rejectDrop();
                }
                }
            });
            
    }
    
    private class SongTransferHandler extends TransferHandler {
        @Override
        protected Transferable createTransferable(JComponent c) {
            int[] selectedRows = songTable.getSelectedRows();
            List<Song> selectedSongs = new ArrayList<>();
            for (int row : selectedRows) {
                selectedSongs.add(songTableModel.getSongAt(songTable.convertRowIndexToModel(row)));
            }
            return new SongsTransferable(selectedSongs);
        }

        @Override
        public int getSourceActions(JComponent c) {
            return MOVE;
        }
    }

    private class SongsTransferable implements Transferable {
        private final List<Song> songs;
        private final DataFlavor songFlavor = new DataFlavor(Song.class, "Songs");

        public SongsTransferable(List<Song> songs) {
            this.songs = songs;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{songFlavor};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor.equals(songFlavor);
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
            if (!isDataFlavorSupported(flavor)) {
                throw new UnsupportedFlavorException(flavor);
            }
            return songs;
        }
    }
}