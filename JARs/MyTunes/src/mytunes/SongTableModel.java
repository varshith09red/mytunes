package mytunes;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.io.*;

public class SongTableModel extends AbstractTableModel {
    private List<Song> songs;
    private final String[] columnNames = { "Title", "Artist", "Album", "Year", "Genre", "Comment" };
    private TableUpdateListener tableUpdateListener;
    final private List<Boolean> columnVisibility;
    private List<Song> sortedSongs;

    public SongTableModel(List<Song> songs) {
        this.songs = songs;
        this.columnVisibility = new ArrayList<>();
        loadColumnVisibility();
        this.sortedSongs = new ArrayList<>();
    }
    

    @Override
    public int getRowCount() {
        return songs.size();
    }

    public int getAllColumnsCount(){
        return columnNames.length;
    }

    @Override
    public int getColumnCount() {
        int count = 0;
        for (Boolean visible : columnVisibility) {
            if (visible) count++;
        }
        return count;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Song song = songs.get(rowIndex);
//        int visibleColumnIndex = getVisibleColumnIndex(columnIndex);
        switch (columnIndex) {
            case 0:
                return song.getTitle();
            case 1:
                return song.getArtist();
            case 2:
                return song.getAlbum();
            case 3:
                return song.getYear();
            case 4:
                return song.getGenre();
            case 5:
                return song.getComment();
            case 6:
                return song.getId();
            case 7:
                return song.getFilePath();
            case 8:
                return song.getSong();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        int visibleColumnIndex = getVisibleColumnIndex(columnIndex);
        return columnNames[visibleColumnIndex];
    }
    
    //for GUI colummn visibility popup menu
    public String getColumnNames(int columnIndex){
        return columnNames[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // Allow editing of the comment field only
        int visibleColumnIndex = getVisibleColumnIndex(columnIndex);
        return visibleColumnIndex == 5;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Song song = songs.get(rowIndex);
        int visibleColumnIndex = getVisibleColumnIndex(columnIndex);
        if (visibleColumnIndex == 5) {
            song.setComment((String) value);
            fireTableCellUpdated(rowIndex, columnIndex);
            Database db = new Database();
            db.updateSongComment(song.getId(), (String) value);
            if (tableUpdateListener != null) {
                tableUpdateListener.onTableUpdate();
            }
        }
    }
    
    public void setSongs(List<Song> songs) {
        this.songs = songs;
        fireTableDataChanged();
    }
    
    public void addSong(Song song) {
        songs.add(song);
        fireTableRowsInserted(songs.size() - 1, songs.size() - 1);
    }

    public void removeSong(int rowIndex) {
        songs.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void setTableUpdateListener(TableUpdateListener listener) {
        this.tableUpdateListener = listener;
    }

    public interface TableUpdateListener {
        void onTableUpdate();
    }
    
    public void setColumnVisibility(int columnIndex, boolean isVisible) {
        columnVisibility.set(columnIndex, isVisible);
        saveColumnVisibility();
        fireTableStructureChanged();
    }

    public boolean isColumnVisible(int columnIndex) {
        return columnVisibility.get(columnIndex);
    }

    private int getVisibleColumnIndex(int columnIndex) {
        int visibleColumnIndex = -1;
        for (int i = 0, visibleCount = 0; i < columnVisibility.size(); i++) {
            if (columnVisibility.get(i)) {
                if (visibleCount == columnIndex) {
                    visibleColumnIndex = i;
                    break;
                }
                visibleCount++;
            }
        }
        return visibleColumnIndex;
    }
    
    private void loadColumnVisibility() {
        try (InputStream input = new FileInputStream("column_visibility.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            for (int i = 0; i < columnNames.length; i++) {
                String key = "column_" + i;
                String value = prop.getProperty(key, "true");
                columnVisibility.add(Boolean.parseBoolean(value));
            }
        } catch (IOException ex) {
            for (int i = 0; i < columnNames.length; i++) {
                columnVisibility.add(true);
            }
        }
    }

    private void saveColumnVisibility() {
        try (OutputStream output = new FileOutputStream("column_visibility.properties")) {
            Properties prop = new Properties();
            for (int i = 0; i < columnVisibility.size(); i++) {
                prop.setProperty("column_" + i, columnVisibility.get(i).toString());
//                System.out.println( "column_" + i +" : "+ columnVisibility.get(i).toString());
            }
            prop.store(output, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    //for generating sorted songs list
    public Song getSongAt(int rowIndex) {
        return songs.get(rowIndex);
    }
    
    //read sorted song at index
    public Song getSortedSongAt(int rowIndex) {
        return sortedSongs.get(rowIndex);
    }
    
    public List<Song> getSortedSongs() {
        return sortedSongs;
    }
    
    public void setSortedSongs(List<Song> sortedSongs) {
        this.sortedSongs = sortedSongs;  
//        fireTableDataChanged();
    }
    
    public void clearSongs() {
        songs.clear();
//        fireTableDataChanged();
    }
}
