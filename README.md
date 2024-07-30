# mytunes
Software Testing Project (Java Swing, MySQL)

Features:
- play, stop, next, previos, pause buttons
- table
- add, delete, drag and drop (popup and file menu bar)
- table sort, and add/Delete columns persist over session
- extract data from id3 tags
- play from file menu bar (playing file into mytune)
- playlists
- drag and drop songs from library to playlist
- delete playlists
- playlist open in new windows
<<<<<<< HEAD
- shuffle, repeat
- controls menu
- recently played 10 songs

(need to)


- session persistence with playlist
- keybord shortcuts - play with "SPACE" rest are done
- if same mp3 file uploaded
=======
- keybord shortcuts
- control menu with recently last ten played, songs
- shuffle mode and diff other controls
>>>>>>> bb348947968a36ae58395e31cfe2bd5541e2eced

Bugs:

<<<<<<< HEAD
1)** when new song is added, the action buttons are not updated with the song list, when next is clicked newly added song is not played.
current index is based on the first order read from MySQL. 
(not that imp)

2) if a song is deleted from main library, it should be deleted from all playlists, happening so in DB but GUI need to update (done problem with "playlits" object in UI, was not updating the list)
=======
2) when new song is added, the action buttons are not updated with the song list, when next is clicked newly added song is not played.

3) deleting next song in the list
>>>>>>> bb348947968a36ae58395e31cfe2bd5541e2eced

3) same as above but with adding, add new song to playlist, it should be added to main library , not updating in GUI, but working fine in DB

4) space bar accessible key for play song

5) need to integrate volume

6) need to adjust progressive bar UI and timing

7) drag and drop is adding 2 files to the table (done)

<<<<<<< HEAD

=======
5) songs in playlist are getting empty after few selections
>>>>>>> bb348947968a36ae58395e31cfe2bd5541e2eced
