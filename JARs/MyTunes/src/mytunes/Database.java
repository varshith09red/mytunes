package mytunes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private Connection connection;

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

    private void createTables() throws SQLException {
        String createSongsTable = "CREATE TABLE IF NOT EXISTS songs (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "title VARCHAR(255)," +
                "artist VARCHAR(255)," +
                "album VARCHAR(255)," +
                "year VARCHAR(4)," +
                "genre VARCHAR(255)," +
                "comment TEXT," +
                "filePath VARCHAR(255))";
        Statement statement = connection.createStatement();
        statement.execute(createSongsTable);
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

    public void addSong(Song song) {
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
