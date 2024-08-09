//package mytunes;
//
//import javazoom.jl.decoder.JavaLayerException;
//import javazoom.jl.player.advanced.AdvancedPlayer;
//import javazoom.jl.player.Player;
//import javax.swing.*;
//import java.awt.*;
//import java.io.BufferedInputStream;
//import java.io.FileInputStream;
//import java.io.IOException;
//import javazoom.jl.player.JavaSoundAudioDevice;
//import javazoom.jl.player.advanced.PlaybackEvent;
//import javazoom.jl.player.advanced.PlaybackListener;
//
//
//public class MusicPlayer extends PlaybackListener {
//    private JavaSoundAudioDevice audioDevice;
//    private Player player;
//    private JSlider volumeSlider;
//
//    public MusicPlayer() {
//        JFrame frame = new JFrame("Music Player");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(400, 300);
//
//        volumeSlider = new JSlider(0, 100, 50); // Volume range from 0 to 100, initial value 50
//        volumeSlider.addChangeListener(e -> setVolume(volumeSlider.getValue()));
//        frame.add(volumeSlider, BorderLayout.SOUTH);
//
//        JButton playButton = new JButton("Play");
//        playButton.addActionListener(e -> {
//            try {
//                playAudio("C:\\Users\\030834321\\Desktop\\Songs\\mytunes\\songs\\dancing-in-the-stars-219514.mp3");
//            } catch (JavaLayerException | IOException ex) {
//                ex.printStackTrace();
//            }
//        });
//        frame.add(playButton, BorderLayout.NORTH);
//
//        frame.setVisible(true);
//    }
//
//    private void playAudio(String filePath) throws JavaLayerException, IOException {
//        try{
//        FileInputStream fileInputStream = new FileInputStream(filePath);
//        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
//            
//        audioDevice = new JavaSoundAudioDevice();
//        player = new Player(bufferedInputStream);
////        player.setVolume(volumeSlider.getValue());
////        player.setPlayBackListener(this);
//        new Thread(() -> {
//            try {
//                player.play();
//                System.out.println("playing");
//            } catch (JavaLayerException e) {
//                e.printStackTrace();
//            }
//        }).start();
//        setVolume(volumeSlider.getValue()); // Set initial volume
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    private void setVolume(int volume) {
//        if (audioDevice != null) {
//            float volumeValue = volume / 10.0f;
////            audioDevice.setVolume(volumeValue);
//
//            System.out.println("Volume set to: " + volumeValue);
//            JavaSoundAudioDevice jsAudio = (JavaSoundAudioDevice) audio;
//            jsAudio.setLineGain(volume);
//        }
//    }
//
//    @Override
//    public void playbackStarted(PlaybackEvent e){
//        System.out.println("Playback Started");
//    }
//    
//    @Override
//    public void playbackFinished(PlaybackEvent e){
//        System.out.println("Playback Finished");
//    }
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(MusicPlayer::new);
//    }
//}
