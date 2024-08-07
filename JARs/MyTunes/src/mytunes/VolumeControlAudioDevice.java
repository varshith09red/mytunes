package mytunes;

import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.decoder.Obuffer;
import javazoom.jl.player.AudioDeviceBase;

public class VolumeControlAudioDevice extends AudioDeviceBase {
    private float volume = 1.0f; // Default volume (1.0 = 100%)

    @Override
    public void open(Decoder decoder) throws JavaLayerException {
        super.open(decoder);
    }

    @Override
    protected void closeImpl() {
        // Close implementation if needed
    }

    @Override
    protected void writeImpl(short[] samples, int offs, int len) throws JavaLayerException {
        for (int i = offs; i < len; i++) {
            samples[i] = (short) (samples[i] * volume);
        }
        super.writeImpl(samples, offs, len); // Call the parent class's writeImpl method
    }

    @Override
    protected void flushImpl() {
        super.flushImpl(); // Call the parent class's flushImpl method
    }

    @Override
    public void close() {
        super.close();
    }

    public void setVolume(float volume) {
        if (volume < 0.0f) volume = 0.0f;
        if (volume > 1.0f) volume = 1.0f;
        this.volume = volume;
    }

//    protected void setOutputImpl(Obuffer output) {
//        super.setOutputImpl(output); // Call the parent class's setOutputImpl method
//    }

    @Override
    public int getPosition() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        return 1;
    }
}
