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
- shuffle, repeat
- controls menu
- recently played 10 songs
- session persistence with playlist
- keybord shortcuts - play with "SPACE" rest are done
- if same mp3 file uploaded

(need to)

---only features left----
- volume implementation

- skip forward and skip backward

- progressive bar skipping seconds because of flags incorrecty updating

Bugs:

1)** when new song is added, the action buttons are not updated with the song list, when next is clicked newly added song is not played.
current index is based on the first order read from MySQL. 
(not that imp)

2) if a song is deleted from main library, it should be deleted from all playlists, happening so in DB but GUI need to update (done problem with "playlits" object in UI, was not updating the list)

3) same as above but with adding, add new song to playlist, it should be added to main library , not updating in GUI, but working fine in DB

4) space bar accessible key for play song (done)


5) drag and drop is adding 2 files to the table (done)

6) skip forward and skip backward associated with next and previous

7) clicking next on last song should come to first same with previous (done)

8) if pause if clicked, its adding to recently played one more time. if song selected from recents (some other song is playing and added 2 times in recents)

9) when application closed, the recents list getting changed on startup (done)

10) progress bar not moving correctly, and for some songs its not working (working fine if stop is clicked before playimg new song)

11) error while playerFinished, and next, and stopped, the coordination is not correct.

12) progressive bar UI corrected(done)

