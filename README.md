# mytunes
Software Testing Project (Java Swing, MySQL)

Features:
- play, stop, next, previos, pause buttons
- table
- add, delete, drag and drop (popup and file menu bar)
- table sort, and add/Delete columns persist over session
- extract data from id3 tags
- play from file menu bar (playing file into mytune)

(need to)

- playlists
- drag and drop songs from library to playlist
- delete playlists
- session persistence with playlist
- playlist open in new windows
- keybord shortcuts
- control menu with recently last ten played, songs
- shuffle mode and diff other controls
- if same mp3 file uploaded

Bugs:
1) if next song is playing, the current song index should hover on that song, and on play the first song should get played.

2) when new song is added, the action buttons are not updated with the song list, when next is clicked newly added song is not played.
current index is based on the first order read from MySQL.

<<<<<<< HEAD
3) deleting next song in the list

4)if first is deleted, next song is getting deleted

5) songs in playlist are getting empty after few selections
=======
3) Next, previous, the song list is updated only when a row is selected not everytime.

4) 
>>>>>>> 20a435313ea6c8d2588a2a519ca9596ad959edfe
