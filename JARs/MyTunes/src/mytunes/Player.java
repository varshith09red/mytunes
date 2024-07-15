//package mytunes;
//
//import javazoom.jl.decoder.Bitstream;
//import javazoom.jl.decoder.JavaLayerException;
//import javazoom.jl.player.advanced.AdvancedPlayer;
//import javazoom.jl.player.advanced.PlaybackEvent;
//import javazoom.jl.player.advanced.PlaybackListener;
////
//import org.jaudiotagger.audio.AudioFile;
//import org.jaudiotagger.audio.AudioFileIO;
//import org.jaudiotagger.audio.exceptions.*;
//import org.jaudiotagger.tag.FieldKey;
//import org.jaudiotagger.tag.Tag;
//import org.jaudiotagger.tag.TagException;
//import org.jaudiotagger.audio.exceptions.CannotReadException;
//import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
//import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
////
//import javax.swing.*;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.List;
//
//
//public class Player {
//    private AdvancedPlayer player;
//    private boolean isPaused;
//    private int pauseFrame;
//    private int currentSongIndex;
//    private List<Song> songs;
//    private Thread playerThread;
//    private FileInputStream fileInputStream;
//    private Timer timer;
//    private long songDurationMillis;
//    private long songPositionMillis;
//
//    public Player(List<Song> songs) {
//        this.songs = songs;
//        this.isPaused = false;
//        this.pauseFrame = 0;
//        this.currentSongIndex = -1;
//    }
//
//    public void play() {
//        stop();
//        if (currentSongIndex != -1) {
//            try {
//                File file = new File(getCurrentSongFilePath());
//                if (!file.exists()) {
//                    System.err.println("File not found: " + file.getAbsolutePath());
//                    return;
//                }
//                fileInputStream = new FileInputStream(file);
//                if (pauseFrame > 0) {
//                    fileInputStream.skip(pauseFrame);
//                }
//                player = new AdvancedPlayer(fileInputStream);
//                playerThread = new Thread(() -> {
//                    try {
//                        player.play();
//                    } catch (JavaLayerException e) {
//                        e.printStackTrace();
//                    }
//                });
//                playerThread.start();
//
//                // Start timer for updating song position and duration
//                startTimer();
//
//                // Get song duration
//                AudioFile audioFile = AudioFileIO.read(file);
//                songDurationMillis = audioFile.getAudioHeader().getTrackLength() * 1000;
//
//            } catch (IOException | JavaLayerException | CannotReadException | TagException | ReadOnlyFileException | InvalidAudioFrameException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void stop() {
//        if (player != null) {
//            player.close();
//            player = null;
//        }
//        if (playerThread != null) {
//            playerThread.interrupt();
//            playerThread = null;
//        }
//        if (fileInputStream != null) {
//            try {
//                fileInputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        stopTimer();
//        resetPlayback();
//    }
//
//    public void pause() {
//        if (player != null && !isPaused) {
//            isPaused = true;
//            try {
//                pauseFrame = fileInputStream.available();
//                System.out.println("pauseFrame: "+ pauseFrame);
//                stop();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void unpause() {
//        if (isPaused) {
//            isPaused = false;
//            System.out.println("unpaused Triggered: "+ pauseFrame + " ,isPaused: "+isPaused);
//            play();
//        }
//    }
//
//    public void next() {
//        if (currentSongIndex < (songs.size() - 1)) {
//            currentSongIndex++;
//            pauseFrame = 0;
//            stop();
//            play();
//        }
//    }
//
//    public void previous() {
//        if (currentSongIndex > 0) {
//            currentSongIndex--;
//            pauseFrame = 0;
//            stop();
//            play();
//        }
//    }
//
//    // Set current song index based on selected row in table
//    public void setCurrentSongIndex(int rowIndex) {
//        if (rowIndex >= 0 && rowIndex < songs.size()) {
//            currentSongIndex = rowIndex;
////            play(); // Start playing the selected song
//        }
//    }
//    
//    // Utility method to get the file path of the current song
//    private String getCurrentSongFilePath() {
//        if (currentSongIndex >= 0 && currentSongIndex < songs.size()) {
//            return songs.get(currentSongIndex).getFilePath();
//        }
//        return null;
//    }
//    
//    // Utility method to get the current frame of the playing song
//    private int getCurrentFrame() {
//        try {
//            Bitstream bitstream = new Bitstream(fileInputStream);
//            int frames = 0;
//            while (bitstream.readFrame() != null) {
//                frames++;
//            }
//            System.out.println("current frame: " + frames);
//            return frames;
//        } catch (JavaLayerException e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }
//    
//    public void addSong(SongTableModel songTableModel,Database database) {
//        // Add song logic
//        JFileChooser fileChooser = new JFileChooser();
//        int result = fileChooser.showOpenDialog(null);
//        if (result == JFileChooser.APPROVE_OPTION) {
//            File file = fileChooser.getSelectedFile();
//            Song song = extractSongFromFile(file);
//            if (song != null) {
//                database.addSong(song);
//                songTableModel.addSong(song);
//            } else {
//                JOptionPane.showMessageDialog(null, "Failed to extract ID3 tags from the selected file.", "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        }
//    }
//    
//    public void deleteSong(JTable songTable,SongTableModel songTableModel,Database database) {
//        // Delete song logic
//         int selectedRow = songTable.getSelectedRow();
//        if (selectedRow != -1) {
//            int songId = (int) songTableModel.getValueAt(selectedRow, 0);
//            database.deleteSong(songId);
//            songTableModel.removeSong(selectedRow);
//        }
//    }
//    
//    public Song extractSongFromFile(File file) {
//        try {
//            AudioFile audioFile = AudioFileIO.read(file);
//            Tag tag = audioFile.getTag();
//
//            String title = tag.getFirst(FieldKey.TITLE);
//            String artist = tag.getFirst(FieldKey.ARTIST);
//            String album = tag.getFirst(FieldKey.ALBUM);
//            String year = tag.getFirst(FieldKey.YEAR);
//            String genre = tag.getFirst(FieldKey.GENRE);
//            String comment = tag.getFirst(FieldKey.COMMENT);
//
//            return new Song(0, title, artist, album, year, genre, comment, file.getAbsolutePath());
//        } catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//    
//    public void startTimer() {
//        timer = new Timer(1000, e -> {
//            if (player != null) {
////                songPositionMillis = player.getPosition();
//            }
//        });
//        timer.start();
//    }
//
//    public void stopTimer() {
//        if (timer != null) {
//            timer.stop();
//        }
//    }
//
//    public long getSongDurationMillis() {
//        return songDurationMillis;
//    }
//
//    public long getSongPositionMillis() {
//        return songPositionMillis;
//    }
//    
//    private void resetPlayback() {
//        pauseFrame = 0;
//        songDurationMillis = 0;
//        songPositionMillis = 0;
//    }
//}