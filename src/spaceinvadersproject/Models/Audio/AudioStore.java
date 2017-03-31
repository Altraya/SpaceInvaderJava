package spaceinvadersproject.Models.Audio;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import sun.audio.AudioStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Jordan on 31/03/2017.
 */
public class AudioStore {

    private static AudioStore single = new AudioStore();

    /**
     * Get the single instance of this class
     *
     * @return The single instance of this class
     */
    public static AudioStore get() {
        return single;
    }

    /** The cached sprite map, from reference to sprite instance */
    private HashMap audios = new HashMap();

    /**
     * Retrieve a sprite from the store
     *
     * @param ref The reference to the image to use for the sprite
     * @return A sprite instance containing an accelerate image of the request reference
     */
    public Audio getAudio(String ref) {
        // if we've already got the sprite in the cache
        // then just return the existing version
        if (audios.get(ref) != null) {
            return (Audio) audios.get(ref);
        }

        // otherwise, go away and grab the audio from the resource
        // loader

        URL url = this.getClass().getClassLoader().getResource(ref);

        if (url == null) {
            fail("Can't find ref: "+ref);
        }

        Audio audio = new Audio(url);
        audios.put(ref,audio);

        return audio;
    }

    /**
     * Utility method to handle resource loading failure
     *
     * @param message The message to display on failure
     */
    private void fail(String message) {
        // we're pretty dramatic here, if a resource isn't available
        // we dump the message and exit the game
        System.err.println(message);
        System.exit(0);
    }
}
