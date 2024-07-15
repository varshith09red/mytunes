package mytunes;

import com.mpatric.mp3agic.Mp3File;
import java.io.*;

public class Song{
    private int id;
    private String title;
    private String artist;
    private String album;
    private String year;
    private String genre;
    private String comment;
    private String filePath;
    
    private String songLength;
    private Mp3File mp3File;
    private double frameRatePerMilliseconds;

    public Song(int id, String title, String artist, String album, String year, String genre, String comment, String filePath) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.year = year;
        this.genre = genre;
        this.comment = comment;
        this.filePath = filePath;
        
        try{
        this.mp3File = new Mp3File(filePath);
        this.frameRatePerMilliseconds = (double) mp3File.getFrameCount() / mp3File.getLengthInMilliseconds();
        this.songLength = convertToSongLengthFormat();
        } catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public Song getSong(){
        return this;
    }
    
    // Getters and setters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public String getComment() {
        return comment;
    }
    
    public String setComment(String cmt) {
        return cmt;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public String getSongLength() {
        return songLength;
    }
    
    public Mp3File getMp3File(){return mp3File;}
    public double getFrameRatePerMilliseconds(){return frameRatePerMilliseconds;}
    
    private String convertToSongLengthFormat(){
        long hours = mp3File.getLengthInSeconds() / 3600;
        long minutes = (mp3File.getLengthInSeconds() % 3600) / 60;
        long seconds = mp3File.getLengthInSeconds() % 60;
        String formattedTime = String.format("%d:%02d:%02d",hours, minutes, seconds);

        return formattedTime;
    }
    
}
