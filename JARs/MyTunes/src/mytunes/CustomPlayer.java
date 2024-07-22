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

import com.mpatric.mp3agic.Mp3File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
public class CustomPlayer extends PlaybackListener {

    private AdvancedPlayer player;
    private String path;
//    private int total;
//    private int stopped;
//    private boolean valid;
    private int currentSongIndex;
    
    private List<Song> songs;
    private final SongTableModel songTableModel;
    
    private static final Object playSignal = new Object();
    private boolean isPaused;
    private int currentFrame;
//    private Mp3File mp3File;
//    private double frameRatePerMilliseconds;
    
    private FloatControl volumeControl;
    private Song currentSong;
    private boolean songFinished;
    private boolean pressedNext, pressedPrev;
    private boolean shuffle, repeat; 
    
    private ArrayList<Song> playlist;
    private List<Integer> songIds;
    // we will need to keep track the index we are in the playlist
//    private int currentPlaylistIndex;
    // track how many milliseconds has passed since playing the song (used for updating the slider)
    private int currentTimeInMilli;
    
    
    public void setCurrentTimeInMilli(int timeInMilli){
        currentTimeInMilli = timeInMilli;
    }

    public Song getCurrentSong(){
        return currentSong;
    }
    
    public void setCurrentSong(Song song){
         currentSong = song;
    }
    
    public void setCurrentFrame(int frame){
        currentFrame = frame;
    }
    private GUI1 gui;

    public CustomPlayer(GUI1 gui, SongTableModel songTableModel, List<Song> songs) {
        this.path = null;
//        this.total = 0;
//        this.stopped = 0;
        this.isPaused = false;
        this.currentSongIndex = 0;
        this.currentSong = null;
        this.songs = songs;
        this.songTableModel = songTableModel;
        this.gui = gui;
        this.pressedNext = false;
        this.pressedPrev = false;
//        this.shuffle
//        this.repeat =
//        initializeVolumeControl();
    }

    public void setSortedSongList(List<Song> songs){
        this.songs = songs;
        for(int i=0;i<songs.size();i++)
        {System.out.println("Song Sorted: " + songs.get(i).getTitle()+" : "+songs.get(i).getId());}
        System.out.println("-----------------------") ;
        loadPlaylist(songs);
        currentSong = songs.get(0);
        
//        //only for testing
//        List<Song> ss = songTableModel.getSortedSongs();
//        for(int i=0;i<ss.size();i++)
//        {System.out.println("Table get Sorted Song : " + ss.get(i).getTitle());}
//        System.out.println("-----------------------") ;
    }
    
//    public void loadSong(Song song){
//        currentSong = song;
//        System.out.println("Load Current Song: "+currentSong.getTitle());
//        playlist = null;
//
//        // stop the song if possible
//        if(!songFinished)
//            stop();
//
//        // play the current song if not null
//        if(currentSong != null){
//            // reset frame
//            currentFrame = 0;
//
//            // reset current time in milli
//            currentTimeInMilli = 0;
//            try{
//                // update gui
//            gui.setPlaybackSliderValue(currentFrame, currentTimeInMilli);
//            
//            } catch(Exception e){
//                e.printStackTrace();
//            }
//            
//
////            play();
//        }
//    }
//    
    public void loadPlaylist(List<Song> songs){
        playlist = new ArrayList<>(songs);
        
        songIds = new ArrayList<>();

        // Extract IDs and store them in the songIds list
        for (Song song : songs) {
            songIds.add(song.getId());
        }
        System.out.println("Ids: "+ songIds);
        // store the paths from the text file into the playlist array list
//        try{
//            FileReader fileReader = new FileReader(playlistFile);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//
//            // reach each line from the text file and store the text into the songPath variable
//            String songPath;
//            while((songPath = bufferedReader.readLine()) != null){
//                // create song object based on song path
//                Song song = new Song(songPath);
//
//                // add to playlist array list
//                playlist.add(song);
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }

//        if(playlist.size() > 0){
//            // reset playback slider
//            musicPlayerGUI.setPlaybackSliderValue(0);
//            currentTimeInMilli = 0;
//
//            // update current song to the first song in the playlist
//            currentSong = playlist.get(0);
//
//            // start from the beginning frame
//            currentFrame = 0;
//
//            // update gui
//            musicPlayerGUI.enablePauseButtonDisablePlayButton();
//            musicPlayerGUI.updateSongTitleAndArtist(currentSong);
//            musicPlayerGUI.updatePlaybackSlider(currentSong);
//
//            // start song
//            playCurrentSong();
//        }
    }


//    public void setPath(String path) {
//        this.path = path;
//    }

    public void pause() {
        try {
            if(player != null){
            isPaused = true;
            stop();
        }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error pausing mp3 file");
        }
    }

//    public void unpause() {
//        if (!canResume) return;
//        if (play(total - stopped)) canResume = false;
//    }

    public void play() {
        stop();
        try{
            // read mp3 audio data
            FileInputStream fileInputStream = new FileInputStream(currentSong.getFilePath());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            
            gui.updateSongTitleAndArtist(currentSong);
            gui.updatePlaybackSlider(currentSong);
            // create a new advanced player
            player = new AdvancedPlayer(bufferedInputStream);
            player.setPlayBackListener(this);

            // start music
            startMusicThread();

            // start playback slider thread
            startPlaybackSliderThread();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    // create a thread that will handle playing the music
    private void startMusicThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    if(isPaused){
                        synchronized(playSignal){
                            // update flag
                            isPaused = false;

                            // notify the other thread to continue (makes sure that isPaused is updated to false properly)
                            playSignal.notify();
                        }

                        // resume music from last frame
                        player.play(currentFrame, Integer.MAX_VALUE);
                    }else{
                        // play music from the beginning
                        player.play();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void stop() {
        try {
            if(player != null){
                player.stop();
                player.close();
                player = null;
                if(!isPaused){
                    currentFrame = 0; // reset frame
                    currentTimeInMilli = 0; // reset current time in milli
                    gui.setPlaybackSliderValue(currentFrame, currentTimeInMilli);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error stopping mp3 file");
        }
    }

    public void next() {
        try {
            int index = getSortedSongPosition(currentSongIndex);
            if (++index <= (songs.size())) {
               pressedNext = true;
//               int nextSongId = songIds.get(++index);
               Song nextSong = songTableModel.getSortedSongAt(index);
               currentSong = nextSong; //pointing to next song
//                Song s = songs.get(index);
//                System.out.println("NextSorted: "+ s.getTitle());
                System.out.println("Next: "+ nextSong.getTitle()+" : index: "+index);
//                stop();
                
//                path = getCurrentSongFilePath();
                play();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error while playing next mp3 file");
            e.printStackTrace();
        }
    }

    public void previous() {
        try {
            int index = getSortedSongPosition(currentSongIndex);
            if (index >= 0) {
               pressedNext = true;
//               int prevSongId = songIds.get(--index);
               Song prevSong = songTableModel.getSortedSongAt(--index);
               currentSong = prevSong; //pointing to previous song
//                Song s = songs.get(index);
//                System.out.println("NextSorted: "+ s.getTitle());
                System.out.println("Previous: "+ prevSong.getTitle());
//                stop();
                
//                path = getCurrentSongFilePath();
                play();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error while playing previous mp3 file");
        }
    }

    public void setCurrentSongIndex(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < songs.size()) {
            currentSongIndex = rowIndex;
            path = getCurrentSongFilePath();
        }
    }

    private String getCurrentSongFilePath() {
        if (currentSongIndex >= 0 && currentSongIndex < songs.size()) {
            currentSong = (Song)songTableModel.getValueAt(currentSongIndex,8);
            System.out.println("currentSong: "+currentSong.getTitle());
            return (String) songTableModel.getValueAt(currentSongIndex, 7);
        }
        return null;
    }

    public int getSortedSongPosition(int currentSongIndex){
//        List<Song> s = songTableModel.getSortedSongs();
        int size = songs.size(); //sorted songs
        int currentSongId = currentSong.getId();
        int currentSortedSongPos = -1;
        System.out.println(currentSong.getTitle());
        for(int i=0; i<size; i++){
//            System.out.println("getSortedSongPosition: "+ currentSortedSongPos +" : "+songs.get(i).getId());
            if(currentSongId == (songs.get(i).getId()))
            {  
                currentSortedSongPos = i;
                System.out.println("getSortedSongPosition: Matched "+ currentSortedSongPos);
                return currentSortedSongPos;
            }
        }
//        if(pressedNext){
//            
//            pressedNext = false;
//            return currentSortedSongPos;
//        }else if(pressedPrev){
//            
//            pressedPrev = false;
//            return currentSortedSongPos;
//        }
            
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
            System.out.println("deleted songId: "+songId);
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
    
    @Override
    public void playbackStarted(PlaybackEvent e){
        System.out.println("Playback Started");
        songFinished = false;
        pressedNext = false;
        pressedPrev = false;
    }
    
    @Override
    public void playbackFinished(PlaybackEvent e){
        System.out.println("Playback Finished");
        if(isPaused){
            currentFrame += (int) ((double) e.getFrame() * currentSong.getFrameRatePerMilliseconds());
        }else{
            // if the user pressed next or prev we don't need to execute the rest of the code
            if(pressedNext || pressedPrev) return;

            // when the song ends
            songFinished = true;

//            if(playlist == null){
//                // update gui
//                GUI1.enablePlayButtonDisablePauseButton();
//            }else{
//                // last song in the playlist
//                if(currentPlaylistIndex == playlist.size() - 1){
//                    // update gui
//                    musicPlayerGUI.enablePlayButtonDisablePauseButton();
//                }else{
//                    // go to the next song in the playlist
                    next();
//                }
//            }
        }
    }


    public void setVolume(int volume) {
        System.out.println("Volume: "+ volume);
//        if (audioClip != null) {
//            FloatControl volumeControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
//            float min = volumeControl.getMinimum();
//            float max = volumeControl.getMaximum();
//            float range = max - min;
//            float gain = (range * (volume / 100.0f)) + min;
//            System.out.println("setVolume: "+gain);
//            volumeControl.setValue(gain);
//        }
    }
    
    // create a thread that will handle updating the slider
    private void startPlaybackSliderThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(isPaused){
                    try{
                        // wait till it gets notified by other thread to continue
                        // makes sure that isPaused boolean flag updates to false before continuing
                        synchronized(playSignal){
                            playSignal.wait();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }

                while(!isPaused && !songFinished && !pressedNext && !pressedPrev){
                    try{
                        // increment current time milli
                        currentTimeInMilli++;

                        // calculate into frame value
                        int calculatedFrame = (int) (currentTimeInMilli * currentSong.getFrameRatePerMilliseconds());


                        // update gui
                        gui.setPlaybackSliderValue(calculatedFrame, currentTimeInMilli);

                        // mimic 1 millisecond using thread.sleep
                        Thread.sleep(1);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
