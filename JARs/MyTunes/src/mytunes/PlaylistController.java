package mytunes;

import java.util.List;

public class PlaylistController {
    private Database database;

    public PlaylistController(Database database) {
        this.database = database;
    }

    public void addSongToPlaylist(Song song, Playlist playlist) {
        playlist.addSong(song);
        database.addSongToPlaylist(song, playlist.getName());
    }

    public void deleteSongFromPlaylist(Song song, Playlist playlist) {
        playlist.removeSong(song);
        database.deleteSongFromPlaylist(song.getId(), playlist.getName());
    }

    public void deletePlaylist(Playlist playlist) {
        for (Song song : playlist.getSongs()) {
            database.deleteSongFromPlaylist(song.getId(), playlist.getName());
        }
        database.deletePlaylist(playlist.getName());
        playlist.clearSongs();
    }

    public List<Playlist> getAllPlaylists() {
        return database.getAllPlaylists();
    }

    public void createPlaylist(String playlistName) {
        database.createPlaylist(playlistName);
    }
    
    public List<Song> getAllPlaylistSongs(Playlist playlist){
        List<Song> songs = playlist.getSongs();
        return songs;
    }
}
