package mytunes;

import javazoom.jl.player.JavaSoundAudioDevice;
import javazoom.jl.decoder.JavaLayerException;

import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;
import java.lang.reflect.Field;

public class VolumeControlAudioDevice extends JavaSoundAudioDevice {
    private FloatControl volumeControl;

    @Override
    protected void createSource() throws JavaLayerException {
        super.createSource();
        try {
            Field sourceField = JavaSoundAudioDevice.class.getDeclaredField("source");
            sourceField.setAccessible(true);
            SourceDataLine source = (SourceDataLine) sourceField.get(this);

            if (source != null) {
                if (source.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    volumeControl = (FloatControl) source.getControl(FloatControl.Type.MASTER_GAIN);
                } else if (source.isControlSupported(FloatControl.Type.VOLUME)) {
                    volumeControl = (FloatControl) source.getControl(FloatControl.Type.VOLUME);
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setVolume(float volume) {
        if (volumeControl != null) {
        float min = volumeControl.getMinimum();
        float max = volumeControl.getMaximum();
        
        // Ensure the volume is within the valid range
        float volumeValue = min + (max - min) * volume;
        volumeValue = Math.max(min, Math.min(max, volumeValue));
        
        volumeControl.setValue(volumeValue);
    }
        
    }
}
