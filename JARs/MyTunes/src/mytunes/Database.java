package mytunes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private Connection connection;
    private List<GUI1> listeners = new ArrayList<>();

    public Database() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mytunes", "root", "root");
            createTables();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    // Register a listener
    public void addChangeListener(GUI1 windows) {
        listeners.add(windows);
    }
    
    // remove a listener
    public void removeListener(GUI1 windows) {
        listeners.remove(windows);
    }
    
    // get listener
    public List<GUI1> getListeners() {
        return listeners;
    }

    // Notify all listeners
    private void notifyListeners() {
        for (GUI1 listener : listeners) {
            System.out.println("listener Name: "+listener.getName());
            listener.refreshSongTable();
        }
    }

    private void createTables() throws SQLException {
        String createSongsTable = "CREATE TABLE IF NOT EXISTS songs (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "title VARCHAR(255) UNIQUE," +
                "artist VARCHAR(255)," +
                "album VARCHAR(255)," +
                "year VARCHAR(4)," +
                "genre VARCHAR(255)," +
                "comment TEXT," +
                "filePath VARCHAR(255) UNIQUE)";
        Statement statement = connection.createStatement();
        statement.execute(createSongsTable);
        
        String createPlaylistsTable = "CREATE TABLE IF NOT EXISTS playlists (" +
            "id INT AUTO_INCREMENT PRIMARY KEY," +
            "name VARCHAR(255) UNIQUE)";
        statement.execute(createPlaylistsTable);

        String createPlaylistSongsTable = "CREATE TABLE IF NOT EXISTS playlist_songs (" +
                "playlist_id INT," +
                "song_id INT," +
                "FOREIGN KEY (playlist_id) REFERENCES playlists(id) ON DELETE CASCADE," +
                "FOREIGN KEY (song_id) REFERENCES songs(id) ON DELETE CASCADE )" ; // this deletes the song from playlist_songs when a song is deleted
//                "PRIMARY KEY (playlist_id, song_id))";
        statement.execute(createPlaylistSongsTable);
    }

    public List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();
        try {
            String query = "SELECT * FROM songs";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                songs.add(new Song(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("artist"),
                        resultSet.getString("album"),
                        resultSet.getString("year"),
                        resultSet.getString("genre"),
                        resultSet.getString("comment"),
                        resultSet.getString("filePath")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }

    public void addSong(Song song) throws Exception {
        try {
            String checkSongQuery = "SELECT * FROM songs WHERE title = ? AND filePath = ?";
            PreparedStatement checkSongStmt = connection.prepareStatement(checkSongQuery);
            checkSongStmt.setString(1, song.getTitle());
            checkSongStmt.setString(2, song.getFilePath());
            ResultSet resultSet = checkSongStmt.executeQuery();

            if (resultSet.next()) {
                throw new Exception("Song already exists in the database.");
            }
            
            String insertSong = "INSERT INTO songs (title, artist, album, year, genre, comment, filePath) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSong);
            preparedStatement.setString(1, song.getTitle());
            preparedStatement.setString(2, song.getArtist());
            preparedStatement.setString(3, song.getAlbum());
            preparedStatement.setString(4, song.getYear());
            preparedStatement.setString(5, song.getGenre());
            preparedStatement.setString(6, song.getComment());
            preparedStatement.setString(7, song.getFilePath());
            preparedStatement.executeUpdate();
            notifyListeners(); // Notify listeners of change
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createPlaylist(String playlistName) {
        try {
            String insertPlaylist = "INSERT INTO playlists (name) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertPlaylist);
            preparedStatement.setString(1, playlistName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Playlist> getAllPlaylists(){
        List<Playlist> playlists = new ArrayList<>();
        try {
            String query = "SELECT * FROM playlists";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int playlistId = resultSet.getInt("id");
                String playlistName = resultSet.getString("name");
                Playlist playlist = new Playlist(playlistId, playlistName);

                // Get songs in the playlist
                String getSongsQuery = "SELECT s.id, s.title, s.artist, s.album, s.year, s.genre, s.comment, s.filePath " +
                        "FROM songs s " +
                        "JOIN playlist_songs ps ON s.id = ps.song_id " +
                        "WHERE ps.playlist_id = ?";
                PreparedStatement getSongsStmt = connection.prepareStatement(getSongsQuery);
                getSongsStmt.setInt(1, playlistId);
                ResultSet songsResultSet = getSongsStmt.executeQuery();
                while (songsResultSet.next()) {
                    Song song = new Song(
                            songsResultSet.getInt("id"),
                            songsResultSet.getString("title"),
                            songsResultSet.getString("artist"),
                            songsResultSet.getString("album"),
                            songsResultSet.getString("year"),
                            songsResultSet.getString("genre"),
                            songsResultSet.getString("comment"),
                            songsResultSet.getString("filePath")
                    );
                    playlist.addSong(song);
                }

                playlists.add(playlist);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playlists;
    }
    
    public List<Song> getSongsByPlaylistName(String playlistName) {
    List<Song> songs = new ArrayList<>();
    try {
        String getPlaylistIdQuery = "SELECT id FROM playlists WHERE name = ?";
        PreparedStatement getPlaylistIdStmt = connection.prepareStatement(getPlaylistIdQuery);
        getPlaylistIdStmt.setString(1, playlistName);
        ResultSet resultSet = getPlaylistIdStmt.executeQuery();
        if (resultSet.next()) {
            int playlistId = resultSet.getInt("id");

            String getSongsQuery = "SELECT s.id, s.title, s.artist, s.album, s.year, s.genre, s.comment, s.filePath " +
                    "FROM songs s " +
                    "JOIN playlist_songs ps ON s.id = ps.song_id " +
                    "WHERE ps.playlist_id = ?";
            PreparedStatement getSongsStmt = connection.prepareStatement(getSongsQuery);
            getSongsStmt.setInt(1, playlistId);
            ResultSet songsResultSet = getSongsStmt.executeQuery();
            while (songsResultSet.next()) {
                Song song = new Song(
                        songsResultSet.getInt("id"),
                        songsResultSet.getString("title"),
                        songsResultSet.getString("artist"),
                        songsResultSet.getString("album"),
                        songsResultSet.getString("year"),
                        songsResultSet.getString("genre"),
                        songsResultSet.getString("comment"),
                        songsResultSet.getString("filePath")
                );
                songs.add(song);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return songs;
}

    
    public void deletePlaylist(String playlistName) {
        try {
            String getPlaylistIdQuery = "SELECT id FROM playlists WHERE name = ?";
            PreparedStatement getPlaylistIdStmt = connection.prepareStatement(getPlaylistIdQuery);
            getPlaylistIdStmt.setString(1, playlistName);
            ResultSet resultSet = getPlaylistIdStmt.executeQuery();
            if (resultSet.next()) {
                int playlistId = resultSet.getInt("id");

                // Delete all songs from this playlist in the playlist_songs table
                String deletePlaylistSongs = "DELETE FROM playlist_songs WHERE playlist_id = ?";
                PreparedStatement deletePlaylistSongsStmt = connection.prepareStatement(deletePlaylistSongs);
                deletePlaylistSongsStmt.setInt(1, playlistId);
                deletePlaylistSongsStmt.executeUpdate();

                // Delete the playlist itself
                String deletePlaylist = "DELETE FROM playlists WHERE id = ?";
                PreparedStatement deletePlaylistStmt = connection.prepareStatement(deletePlaylist);
                deletePlaylistStmt.setInt(1, playlistId);
                deletePlaylistStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void addSongToPlaylist(Song song, String playlistName) {
        try {
            // Check if song already exists in the songs table
            String checkSongQuery = "SELECT id FROM songs WHERE filePath = ?";
            PreparedStatement checkSongStmt = connection.prepareStatement(checkSongQuery);
            checkSongStmt.setString(1, song.getFilePath());
            ResultSet songResultSet = checkSongStmt.executeQuery();

            int songId;
            if (songResultSet.next()) {
                // Song already exists, get its ID
                songId = songResultSet.getInt("id");
            } else {
                // Song doesn't exist, add it to the songs table
                String insertSong = "INSERT INTO songs (title, artist, album, year, genre, comment, filePath) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement insertSongStmt = connection.prepareStatement(insertSong, Statement.RETURN_GENERATED_KEYS);
                insertSongStmt.setString(1, song.getTitle());
                insertSongStmt.setString(2, song.getArtist());
                insertSongStmt.setString(3, song.getAlbum());
                insertSongStmt.setString(4, song.getYear());
                insertSongStmt.setString(5, song.getGenre());
                insertSongStmt.setString(6, song.getComment());
                insertSongStmt.setString(7, song.getFilePath());
                insertSongStmt.executeUpdate();

                ResultSet generatedKeys = insertSongStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    songId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to retrieve song ID.");
                }
                notifyListeners();
            }

            // Get playlist ID
            String getPlaylistIdQuery = "SELECT id FROM playlists WHERE name = ?";
            PreparedStatement getPlaylistIdStmt = connection.prepareStatement(getPlaylistIdQuery);
            getPlaylistIdStmt.setString(1, playlistName);
            ResultSet playlistResultSet = getPlaylistIdStmt.executeQuery();
            if (playlistResultSet.next()) {
                int playlistId = playlistResultSet.getInt("id");

                // Add song to playlist
                String insertSongToPlaylist = "INSERT INTO playlist_songs (playlist_id, song_id) VALUES (?, ?)";
                PreparedStatement insertSongToPlaylistStmt = connection.prepareStatement(insertSongToPlaylist);
                insertSongToPlaylistStmt.setInt(1, playlistId);
                insertSongToPlaylistStmt.setInt(2, songId);
                insertSongToPlaylistStmt.executeUpdate();
                notifyListeners(); // Notify listeners of change
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void deleteSong(int songId) {
        try {
            String deleteSong = "DELETE FROM songs WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSong);
            preparedStatement.setInt(1, songId);
            preparedStatement.executeUpdate();
            notifyListeners(); // Notify listeners of change
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteSongFromPlaylist(int songId, String playlistName) {
        try {
            String getPlaylistIdQuery = "SELECT id FROM playlists WHERE name = ?";
            PreparedStatement getPlaylistIdStmt = connection.prepareStatement(getPlaylistIdQuery);
            getPlaylistIdStmt.setString(1, playlistName);
            ResultSet resultSet = getPlaylistIdStmt.executeQuery();
            if (resultSet.next()) {
                int playlistId = resultSet.getInt("id");
                String deleteSongFromPlaylist = "DELETE FROM playlist_songs WHERE playlist_id = ? AND song_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(deleteSongFromPlaylist);
                preparedStatement.setInt(1, playlistId);
                preparedStatement.setInt(2, songId);
                preparedStatement.executeUpdate();
                notifyListeners(); // Notify listeners of change
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSongComment(int songId, String comment) {
        try {
            String updateComment = "UPDATE songs SET comment = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateComment);
            preparedStatement.setString(1, comment);
            preparedStatement.setInt(2, songId);
            preparedStatement.executeUpdate();
            notifyListeners(); // Notify listeners of change
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
