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
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Properties;
import javax.sound.sampled.SourceDataLine;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.JavaSoundAudioDevice;

public class CustomPlayer extends PlaybackListener {

    private AdvancedPlayer player;
    private AudioDevice audioDevice;
    private String path;
    private int currentSongIndex;
    
    private List<Song> songs;
    private final SongTableModel songTableModel;
    
    private static final Object playSignal = new Object();
    private boolean isPaused, isPlaying;
    private int currentFrame;
    
    private FloatControl volumeControl;
    private Song currentSong;
    private Song currentlyPlayingSong;
    private boolean songFinished;
    public boolean pressedNext, pressedPrev, pressedStop;
    private List<Song> recentSongs;
    public boolean songSelectedFromRecentSongs;
    
//    private ArrayList<Song> playlist;
//    private List<Integer> songIds;
    
    // we will need to keep track the index we are in the playlist
//    private int currentPlaylistIndex;
    
    // track how many milliseconds has passed since playing the song (used for updating the slider)
    private int currentTimeInSec;
    private GUI1 gui;
    
    private Timer longPressTimer;
    private boolean longPressDetected;

    public CustomPlayer(GUI1 gui, SongTableModel songTableModel, List<Song> songs) {
        this.path = null;
        this.isPaused = false;
        this.isPlaying = false;
        this.currentSongIndex = -1;
        this.currentSong = null;
        this.songs = songs;
        this.songTableModel = songTableModel;
        this.gui = gui;
        this.pressedNext = false;
        this.pressedPrev = false;
        this.recentSongs = new LinkedList<>();
        this.pressedStop = false;
        this.songSelectedFromRecentSongs = false;
        this.longPressDetected = false;
        this.volumeControl = null;
        this.currentlyPlayingSong = null;
        loadRecentSongs();
//        initializeVolumeControl();
    }

    public void setCurrentTimeInSec(int timeInSec){
        currentTimeInSec = timeInSec;
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
    
    public void setSortedSongList(List<Song> songs){
        
        // Shuffle songs if shuffle is enabled
        List<Song> songsToLoad = gui.shuffleEnabled ? shuffleSongs(this.songs) : songs;
        this.songs = songsToLoad;
        for(int i=0;i<this.songs.size();i++)
        {System.out.println("Song Sorted: " + this.songs.get(i).getTitle()+" : "+this.songs.get(i).getId());}
        System.out.println("-----------------------") ;
        
        // Set the current song
//        currentSong = songsToLoad.get(0);
        
//        //only for testing
//        List<Song> ss = songTableModel.getSortedSongs();
//        for(int i=0;i<ss.size();i++)
//        {System.out.println("Table get Sorted Song : " + ss.get(i).getTitle());}
//        System.out.println("-----------------------") ;
    }
    
    private List<Song> shuffleSongs(List<Song> songs) {
        System.out.println("Shuffle Songs Called");
        List<Song> shuffledSongs = new ArrayList<>(songs);
        Collections.shuffle(shuffledSongs);
        return shuffledSongs;
    }
    
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
            if(currentSong == null)
                currentSong = songs.get(0);
            FileInputStream fileInputStream = new FileInputStream(currentSong.getFilePath());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            
            gui.updateSongTitleAndArtist(currentSong);
            gui.updatePlaybackSlider(currentSong);
            addRecentSong(currentSong);
            currentlyPlayingSong = currentSong;
//            audioDevice = new VolumeControlAudioDevice();  // Use custom audio device
            this.audioDevice = FactoryRegistry.systemRegistry().createAudioDevice();
            if (this.audioDevice != null) {
                initializeVolumeControl();  // Ensure volume control is initialized only if audioDevice is not null
            } else {
                System.out.println("AudioDevice is null. Cannot initialize volume control.");
            }
            player = new AdvancedPlayer(bufferedInputStream, this.audioDevice);
//            setVolume(-12.0f);
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
                isPlaying = false;
                player.close();
                player = null; 
            }
            if(!isPaused){
                    currentFrame = 0; // reset frame
                    currentTimeInSec = 0; // reset current time in milli
                    gui.setPlaybackSliderValue(currentFrame, currentTimeInSec);
                }            
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Error stopping mp3 file");
        }
    }

    public void next() {
        try {
            
            int index = getSortedSongPosition(currentSongIndex);
            if (++index <= (songs.size())) {
               pressedNext = true;
               Song nextSong = null;
               if(index == songs.size())
                   index = 0;
               if(gui.shuffleEnabled && !gui.repeatEnabled)
                    nextSong = this.songs.get(index);
               if(gui.repeatEnabled)
                   nextSong = this.songs.get(--index);
               else if(!gui.shuffleEnabled && !gui.repeatEnabled)
                    nextSong = songTableModel.getSortedSongAt(index);
               currentSong = nextSong; //pointing to next song

                System.out.println("Next: "+ nextSong.getTitle()+" : index: "+index);
                stop();
                pressedStop = true;
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
               pressedPrev = true;
               Song prevSong = null;
               if(index == 0)
                   index = songs.size();
               if(gui.shuffleEnabled && !gui.repeatEnabled)
                    prevSong = this.songs.get(--index);
               if(gui.repeatEnabled)
                   prevSong = this.songs.get(index);
               else if(!gui.shuffleEnabled && !gui.repeatEnabled)
                    prevSong = songTableModel.getSortedSongAt(--index);
               currentSong = prevSong; //pointing to previous song

                System.out.println("Previous: "+ prevSong.getTitle());
                stop();
                pressedStop = true;
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
    
    
    public int getCurrentSongIndex() {
        int SongIndex = -1;
        if (currentSong == null) {
            return -1;
        }
            for(Song song: songTableModel.getSortedSongs()){
                SongIndex++;
                if(currentSong.getId() == song.getId())
                    return SongIndex;

//                System.out.println("CurrentSongIndex: "+SongIndex);
            }
        return SongIndex;
    }
    
    public int getCurrentPlayingSongIndex() {
        int SongIndex = -1;
            for(Song song: songTableModel.getSortedSongs()){
                SongIndex++;
                if(currentlyPlayingSong.getId() == song.getId())
                    return SongIndex;

//                System.out.println("Currently Playing Song Index: "+SongIndex);
            }
        return SongIndex;
    }

    private String getCurrentSongFilePath() {
        if (currentSongIndex >= 0 && currentSongIndex < songs.size()) {
            currentSong = (Song)songTableModel.getValueAtGUI(currentSongIndex,8);
            System.out.println("currentSong: "+currentSong.getTitle());
            return (String) songTableModel.getValueAtGUI(currentSongIndex, 7);
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
            return -1;
    }
    
    public void addSong(SongTableModel songTableModel, Database database, Song song) {
        try {
            database.addSong(song);
            songTableModel.addSong(song);
            setSortedSongList(songTableModel.getSortedSongs());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteSong(JTable songTable, SongTableModel songTableModel, Database database) {
        int selectedRow = songTable.getSelectedRow();
        if (selectedRow != -1) {
            int songId = (int) songTableModel.getSortedSongAt(selectedRow).getId();
            System.out.println("deleted songId: "+songId+ " rowIndex:"+ selectedRow);
            database.deleteSong(songId);
            songTableModel.removeSong(selectedRow);
        }
        setSortedSongList(songTableModel.getSortedSongs());
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
//        songSelectedFromRecentSongs = false;
        pressedStop = false;
        isPlaying = true;
    }
    
    @Override
    public void playbackFinished(PlaybackEvent e){
        System.out.println("Playback Finished");
        if(isPaused){
            currentFrame += (int) ((double) e.getFrame() * currentSong.getFrameRatePerMilliseconds());
        }else{
            // if the user pressed next or prev we don't need to execute the rest of the code
            if(pressedNext || pressedPrev || pressedStop || songSelectedFromRecentSongs)
            {
                songFinished = false;
                return;
            }
            // when the song ends (uncommenting this effecting recent and lot other features, need to handle playing next song when  current is completed)
            songFinished = true;
            if(songFinished){
               System.out.println("CustomPlayer: Current Song finished, playing next song");
               next(); 
            }
             
        }
    }


//    public void setVolume(int volume) {
//        if (audioDevice != null) {
//            float volumeValue = volume / 100.0f;
//            audioDevice.setVolume(volumeValue);
////            System.out.println("Volume set to: " + volumeValue);
//        }
//    }
    
    public void setVolume(float gain) throws JavaLayerException {
        if (this.volumeControl == null) {
            initializeVolumeControl();
        }
        if (this.volumeControl != null) {
            float newGain = Math.min(Math.max(gain, volumeControl.getMinimum()), volumeControl.getMaximum());
            System.out.println("Was: " + volumeControl.getValue() + " Will be: " + newGain);
            volumeControl.setValue(newGain);
        }
    }

    private void initializeVolumeControl() throws JavaLayerException {
        if (this.audioDevice == null) {
            System.out.println("AudioDevice is null. Cannot initialize volume control.");
            return;
        }

//        this.audioDevice = FactoryRegistry.systemRegistry().createAudioDevice();
        
        Class<JavaSoundAudioDevice> clazz = JavaSoundAudioDevice.class;
        Field[] fields = clazz.getDeclaredFields();
        try {
            SourceDataLine source;
            for (Field field : fields) {
                if ("source".equals(field.getName())) {
                    field.setAccessible(true);
                    source = (SourceDataLine) field.get(this.audioDevice);
                    field.setAccessible(false);
                    if (source != null) {
                        this.volumeControl = (FloatControl) source.getControl(FloatControl.Type.MASTER_GAIN);
                        System.out.println("VolumeControl initialized: " + (this.volumeControl != null));
                    } else {
                        System.out.println("SourceDataLine is null.");
                    }
                    return;  // Exit after setting volume control or identifying that source is null
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("VolumeControl not initialized.");
    }


    
    private void addRecentSong(Song song) {
        if (!gui.shuffleEnabled && !isPaused) {
            if (recentSongs.size() == 10) {
                recentSongs.remove(0);
            }
            recentSongs.add(song);
            saveRecentSongs();
            gui.updateRecentMenu();

        }
    }
    
    public List<Song> getRecentSongs(){
        return this.recentSongs;
    }
    
    private void saveRecentSongs() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("recentSongs.txt"))) {
            for (Song song : recentSongs) {
                writer.write(song.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void loadRecentSongs() {
        recentSongs = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("recentSongs.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Song song = Song.fromString(line);
                if (song != null) {
                    recentSongs.add(song);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                
                while(!isPaused && !songFinished && !pressedNext && !pressedPrev && !pressedStop){
                    try{
                        if(songSelectedFromRecentSongs && !isPlaying){
                            songSelectedFromRecentSongs = false;
                            break;
                        }
                        
                        // increment current time in seconds (as milliseconds is making too many calculations, making it slow)
                        currentTimeInSec += 1000;

                        // calculate into frame value
                        int calculatedFrame = (int) (currentTimeInSec * currentSong.getFrameRatePerMilliseconds());

                        // update gui
                        gui.setPlaybackSliderValue(calculatedFrame, currentTimeInSec/ 1000);

                        // mimic 1 second using thread.sleep
                        Thread.sleep(1000);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
