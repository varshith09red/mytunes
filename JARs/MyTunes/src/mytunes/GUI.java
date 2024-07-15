//package mytunes;
//
//import javax.swing.*;
//import java.awt.*;
//import javax.swing.table.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import javax.swing.table.TableModel;
//import javax.swing.event.ListSelectionEvent;
//import javax.swing.event.ListSelectionListener;
//import javax.swing.table.TableColumn;
//import javax.swing.table.TableColumnModel;
//import javax.swing.event.ChangeListener;
//import javax.swing.event.ChangeEvent;
//
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.util.List;
//import java.awt.event.*;
//import java.awt.dnd.*;
//import java.io.*;
//import java.awt.datatransfer.DataFlavor;
//
//
//public class GUI extends JFrame {
//    private CustomPlayer player;
//    private Database database;
//    private JTable songTable;
//    private SongTableModel songTableModel;
//    private JPopupMenu columnPopupMenu;
//    private TableColumn titleColumn;
//    private JSlider volumeSlider;
//    private JLabel elapsedTimeLabel;
//    private JLabel remainingTimeLabel;
//    private JProgressBar progressBar;
//    private Timer timer;
//    
//    public GUI() {
//        database = new Database();
//        List<Song> songs = database.getAllSongs();
//        player = new CustomPlayer(songs);
//        songTableModel = new SongTableModel(songs);
//        songTableModel.setTableUpdateListener(new SongTableModel.TableUpdateListener() {
//            @Override
//            public void onTableUpdate() {
//                refreshSongTable();
//            }
//        });
//    }
//
//    public void createAndShowGUI() {
//        setTitle("MyTunes");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(800, 600);
//        
//        JPanel controlPanel = new JPanel();
//        controlPanel.setLayout(new FlowLayout());
//         
//        JButton playButton = new JButton("Play");
//        playButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                player.play(-1);
//                startTimer();
//            }
//        });
//        controlPanel.add(playButton);
//
//        JButton stopButton = new JButton("Stop");
//        stopButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                player.stop();
//                stopTimer();
//            }
//        });
//        controlPanel.add(stopButton);
//
//        JButton pauseButton = new JButton("Pause");
//        pauseButton.addActionListener((ActionEvent e) -> {
//            player.pause();
//            stopTimer();
//        });
//        controlPanel.add(pauseButton);
//
//        JButton unpauseButton = new JButton("Unpause");
//        unpauseButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                player.unpause();
//                startTimer();
//            }
//        });
//        controlPanel.add(unpauseButton);
//
//        JButton nextButton = new JButton("Next");
//        nextButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                player.next();
//                startTimer();
//            }
//        });
//        controlPanel.add(nextButton);
//
//        JButton prevButton = new JButton("Previous");
//        prevButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                player.previous();
//                startTimer();
//            }
//        });
//        controlPanel.add(prevButton);
//        
//        // Volume slider
//        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
//        volumeSlider.addChangeListener(new ChangeListener() {
//            
//            @Override
//            public void stateChanged(ChangeEvent e) {
//                int volume = volumeSlider.getValue();
//                player.setVolume(volume);
//            }
//        });
//        controlPanel.add(volumeSlider);
//
//        // Timers and Progress Bar
//        JPanel timerPanel = new JPanel();
//        timerPanel.setLayout(new FlowLayout());
//
//        elapsedTimeLabel = new JLabel("00:00:00");
//        remainingTimeLabel = new JLabel("00:00:00");
//
//        progressBar = new JProgressBar(0, 100);
//
//        timerPanel.add(elapsedTimeLabel);
//        timerPanel.add(progressBar);
//        timerPanel.add(remainingTimeLabel);
//        
//        songTable = new JTable(songTableModel);
//        songTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow only single row selection
//        songTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent e) {
//                if (!e.getValueIsAdjusting()) {
//                    int selectedRow = songTable.getSelectedRow();
//                    if (selectedRow != -1) {
//                        int songIndex = songTable.convertRowIndexToModel(selectedRow);
//                        player.setCurrentSongIndex(songIndex);
//                    }
//                }
//            }
//        });
//        
//        songTable.setAutoCreateRowSorter(true);
//        TableRowSorter<SongTableModel> sorter = new TableRowSorter<>(songTableModel);
//        songTable.setRowSorter(sorter);
//        sorter.toggleSortOrder(0);  // Default sorting on "Title" column
//        
//        JScrollPane scrollPane = new JScrollPane(songTable);
//        songTable.setFillsViewportHeight(true);
//
//        JPanel mainPanel = new JPanel();
//        mainPanel.setLayout(new BorderLayout());
//        mainPanel.add(controlPanel, BorderLayout.SOUTH);
//        mainPanel.add(scrollPane, BorderLayout.CENTER);
//        mainPanel.add(timerPanel, BorderLayout.NORTH);
//
//        // Create popup menu
//        JPopupMenu popupMenu = new JPopupMenu();
//        JMenuItem addMenuItem = new JMenuItem("Add Song");
//        JMenuItem deleteMenuItem = new JMenuItem("Delete Song");
//
//        popupMenu.add(addMenuItem);
//        popupMenu.add(deleteMenuItem);
//
//        // Add song action to popup menu
//        addMenuItem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                player.addSong(songTableModel,database);
//                refreshSongTable();
//            }
//        });
//
//        // Delete song action to popup menu
//        deleteMenuItem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                player.deleteSong(songTable,songTableModel,database);
//                refreshSongTable();
//            }
//        });
//
//        // Add mouse listener to show popup menu
//        songTable.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                if (e.isPopupTrigger()) {
//                    showPopup(e);
//                }
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                if (e.isPopupTrigger()) {
//                    showPopup(e);
//                }
//            }
//
//            private void showPopup(MouseEvent e) {
//                popupMenu.show(e.getComponent(), e.getX(), e.getY());
//            }
//        });
//        
//        // Create column selection popup menu
//        columnPopupMenu = new JPopupMenu();
//        for (int i = 1; i < songTableModel.getAllColumnsCount(); i++) {
//            final int columnIndex = i;
//            JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(songTableModel.getColumnNames(columnIndex), songTableModel.isColumnVisible(columnIndex));
//            menuItem.addActionListener(e -> {
//                songTableModel.setColumnVisibility(columnIndex, menuItem.isSelected());
//                refreshSongTable();
//            });
//            columnPopupMenu.add(menuItem);
//        }
//
//        songTable.getTableHeader().addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                if (e.isPopupTrigger()) {
//                    columnPopupMenu.show(e.getComponent(), e.getX(), e.getY());
//                }
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                if (e.isPopupTrigger()) {
//                    columnPopupMenu.show(e.getComponent(), e.getX(), e.getY());
//                }
//            }
//        });
//
//        // Drag and Drop
//        songTable.setDropTarget(new DropTarget() {
//            public synchronized void drop(DropTargetDropEvent evt) {
//                try {
//                    evt.acceptDrop(DnDConstants.ACTION_COPY);
//                    List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
//                    for (File file : droppedFiles) {
//                        Song song = player.extractSongFromFile(file);
//                        database.addSong(song);
//                        songTableModel.addSong(song);
//                    }
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//        });
//
//        // Create menu bar
//        JMenuBar menuBar = new JMenuBar();
//        JMenu fileMenu = new JMenu("File");
//
//        JMenuItem fileAddMenuItem = new JMenuItem("Add Song");
//        JMenuItem fileDeleteMenuItem = new JMenuItem("Delete Song");
//        JMenuItem exitMenuItem = new JMenuItem("Exit");
//        JMenuItem openFileMenuItem = new JMenuItem("Open File");
//        
//        fileMenu.add(openFileMenuItem);
//        fileMenu.addSeparator();
//        fileMenu.add(fileAddMenuItem);
//        fileMenu.addSeparator();
//        fileMenu.add(fileDeleteMenuItem);
//        fileMenu.addSeparator();
//        fileMenu.add(exitMenuItem);
//
//        // Add song action to file menu
//        fileAddMenuItem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                player.addSong(songTableModel,database);
//                refreshSongTable();
//            }
//        });
//
//        // Delete song action to file menu
//        fileDeleteMenuItem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                player.deleteSong(songTable,songTableModel,database);
//                refreshSongTable();
//            }
//        });
//        
//        // Exit action
//        exitMenuItem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                System.exit(0);
//            }
//        });
//
//        // Open file action
//        openFileMenuItem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                JFileChooser fileChooser = new JFileChooser();
//                int result = fileChooser.showOpenDialog(GUI.this);
//                if (result == JFileChooser.APPROVE_OPTION) {
//                    File file = fileChooser.getSelectedFile();
//                    Song song = player.extractSongFromFile(file);
//                    if (song != null) {
//                        database.addSong(song);
//                        songTableModel.addSong(song);
//                        refreshSongTable();
//                    } else {
//                        JOptionPane.showMessageDialog(GUI.this, "Failed to extract ID3 tags from the selected file.", "Error", JOptionPane.ERROR_MESSAGE);
//                    }
//                }
//            }
//        });
//
//        menuBar.add(fileMenu);
//        setJMenuBar(menuBar);
//        
//        setContentPane(mainPanel);
//        setVisible(true);
//    }
//
//    private void refreshSongTable() {
//        songTableModel.setSongs(database.getAllSongs());
//        songTableModel.fireTableDataChanged();
//    }
//    
//    private void startTimer() {
//        timer = new Timer(100, new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                int elapsed = player.getElapsedTime();
//                int duration = player.getDuration();
//                int remaining = duration - elapsed;
//                
//                elapsedTimeLabel.setText(formatTime(elapsed));
//                remainingTimeLabel.setText(formatTime(remaining));
//                progressBar.setValue((int) ((elapsed / (float) duration) * 100));
//            }
//        });
//        timer.start();
//    }
//
//    private void stopTimer() {
//        if (timer != null) {
//            timer.stop();
//        }
//    }
//
//    private String formatTime(int timeInSeconds) {
//        int hours = timeInSeconds / 3600;
//        int minutes = (timeInSeconds % 3600) / 60;
//        int seconds = timeInSeconds % 60;
//        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
//    }
//}
//
//  