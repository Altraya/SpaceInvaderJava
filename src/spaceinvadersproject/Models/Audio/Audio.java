package spaceinvadersproject.Models.Audio;

import java.net.URL;


public class Audio {

    private URL audioStream;

    public Audio(URL audioStream) {
        this.audioStream = audioStream;
    }

    public URL getAudioStream() {
        return audioStream;
    }

    public void setAudioStream(URL audioStream) {
        this.audioStream = audioStream;
    }
}
