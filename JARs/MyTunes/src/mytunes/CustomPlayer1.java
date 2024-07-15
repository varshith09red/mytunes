package mytunes;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.*;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.io.BufferedInputStream;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackListener;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.decoder.JavaLayerException;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class CustomPlayer1 {

    private AdvancedPlayer player;
    private FileInputStream FIS;
    private BufferedInputStream BIS;
    private boolean canResume;
    private String path;
    private int total;
    private int stopped;
    private boolean valid;
    private int currentSongIndex;
    private List<Song> songs;
    private Clip audioClip;
    private int elapsedTime;
    private SongTableModel songTableModel;
    
    private boolean isPaused;
    private int currentFrame;


    public CustomPlayer1(SongTableModel songTableModel, List<Song> songs) {
        this.player = null;
        this.FIS = null;
        this.valid = false;
        this.BIS = null;
        this.path = null;
        this.total = 0;
        this.stopped = 0;
        this.canResume = false;
        this.currentSongIndex = 0;
        this.songs = songs;
        this.audioClip = null;
        this.elapsedTime = 0;
        this.songTableModel = songTableModel;
    }

    public void setSortedSongList(List<Song> songs){
        this.songs = songs;
        for(int i=0;i<songs.size();i++)
        {System.out.println("Song Sorted: " + songs.get(i).getTitle());}
        System.out.println("-----------------------") ;
        
//        //only for testing
//        List<Song> ss = songTableModel.getSortedSongs();
//        for(int i=0;i<ss.size();i++)
//        {System.out.println("Table get Sorted Song : " + ss.get(i).getTitle());}
//        System.out.println("-----------------------") ;
    }
    
    public boolean canResume() {
        return canResume;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void pause() {
        try {
            stopped = FIS.available();
            player.close();
            FIS = null;
            BIS = null;
            player = null;
            if (valid) canResume = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error pausing mp3 file");
        }
    }

    public void unpause() {
        if (!canResume) return;
        if (play(total - stopped)) canResume = false;
    }

    public boolean play(int pos) {
        stop();
        valid = true;
        canResume = false;
        try {
            path = getCurrentSongFilePath();
            System.out.println("play path:"+ path);
            
            FIS = new FileInputStream(path);
            total = FIS.available();
            if (pos > -1) FIS.skip(pos);
            BIS = new BufferedInputStream(FIS);
            player = new AdvancedPlayer(BIS);
            new Thread(() -> {
                try {
                    player.play();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error playing mp3 file");
                    valid = false;
                }
            }).start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error playing mp3 file");
            valid = false;
        }
        return valid;
    }

    public void stop() {
        try {
            if (player != null) {
                player.close();
                player = null;
                FIS = null;
                BIS = null;
                valid = false;
                canResume = false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error stopping mp3 file");
        }
    }

    public void next() {
        try {
            int index = getSortedSongPosition(currentSongIndex);
            if (index <= (songs.size() - 1)) {
                index++;
                Song s = songs.get(index);
                System.out.println("NextSorted: "+ s.getTitle());
                System.out.println("Next: "+ songTableModel.getValueAt(currentSongIndex, 0));
                stop();
                path = getCurrentSongFilePath();
                play(-1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error while playing next mp3 file");
        }
    }

    public void previous() {
        try {
            if (currentSongIndex >= 0) {
                currentSongIndex--;
                System.out.println("Previous: "+ songTableModel.getValueAt(currentSongIndex, 0));
                stop();
                path = getCurrentSongFilePath();
                play(-1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error while playing previous mp3 file");
        }
    }

    public void setCurrentSongIndex(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < songs.size()) {
            currentSongIndex = rowIndex;
            System.out.println("setCurrentSongIndex: "+currentSongIndex);
            path = getCurrentSongFilePath();
        }
    }

    private String getCurrentSongFilePath() {
        if (currentSongIndex >= 0 && currentSongIndex < songs.size()) {
            return (String) songTableModel.getValueAt(currentSongIndex, 7);
        }
        return null;
    }

    public int getSortedSongPosition(int currentSongIndex){
//        List<Song> s = songTableModel.getSortedSongs();
        int size = songs.size();
        String currentSongName = (String)songTableModel.getValueAt(currentSongIndex, 6);
        int currentSortedSongPos = 0;
        System.out.println(currentSongName);
        for(int i=0; i<size; i++){
            System.out.println("getSortedSongPosition: "+ currentSortedSongPos +" : "+songs.get(i).getId());
            if(currentSongName.equals(songs.get(i).getId()))
            {  
                System.out.println("getSortedSongPosition: Matched "+ currentSortedSongPos);
                return currentSortedSongPos;
            }
            currentSortedSongPos++;
        }
            return -1;
    }
    
    public void addSong(SongTableModel songTableModel, Database database) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            Song song = extractSongFromFile(file);
            if (song != null) {
                database.addSong(song);
                songTableModel.addSong(song);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to extract ID3 tags from the selected file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void deleteSong(JTable songTable, SongTableModel songTableModel, Database database) {
        int selectedRow = songTable.getSelectedRow();
        if (selectedRow != -1) {
            int songId = (int) songTableModel.getValueAt(selectedRow, 6);
            System.out.println("songId: "+songId);
            database.deleteSong(songId);
            songTableModel.removeSong(selectedRow);
        }
    }

    public Song extractSongFromFile(File file) {
        try {
            AudioFile audioFile = AudioFileIO.read(file);
            Tag tag = audioFile.getTag();

            String title = tag.getFirst(FieldKey.TITLE);
            String artist = tag.getFirst(FieldKey.ARTIST);
            String album = tag.getFirst(FieldKey.ALBUM);
            String year = tag.getFirst(FieldKey.YEAR);
            String genre = tag.getFirst(FieldKey.GENRE);
            String comment = tag.getFirst(FieldKey.COMMENT);

            return new Song(0, title, artist, album, year, genre, comment, file.getAbsolutePath());
        } catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public int getElapsedTime() {
        System.out.println("elapsedTime: "+elapsedTime);
        return elapsedTime;
    }

    public int getDuration() {
        if (audioClip != null) {
            System.out.println("getDuration: "+(int) (audioClip.getMicrosecondLength() / 1_000_000));
            return (int) (audioClip.getMicrosecondLength() / 1_000_000);
        }
        return 0;
    }

    public void setVolume(int volume) {
        if (audioClip != null) {
            FloatControl volumeControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            float range = max - min;
            float gain = (range * (volume / 100.0f)) + min;
            System.out.println("setVolume: "+gain);
            volumeControl.setValue(gain);
        }
    }
}
