package mytunes;

public class Song {
    private int id;
    private String title;
    private String artist;
    private String album;
    private String year;
    private String genre;
    private String comment;
    private String filePath;

    public Song(int id, String title, String artist, String album, String year, String genre, String comment, String filePath) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.year = year;
        this.genre = genre;
        this.comment = comment;
        this.filePath = filePath;
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
}
