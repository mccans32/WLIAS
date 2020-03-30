package engine.audio;

import static org.lwjgl.openal.AL10.AL_BUFFER;
import static org.lwjgl.openal.AL10.AL_FALSE;
import static org.lwjgl.openal.AL10.AL_LOOPING;
import static org.lwjgl.openal.AL10.AL_SOURCE_RELATIVE;
import static org.lwjgl.openal.AL10.AL_TRUE;
import static org.lwjgl.openal.AL10.alDeleteSources;
import static org.lwjgl.openal.AL10.alGenSources;
import static org.lwjgl.openal.AL10.alSourcePlay;
import static org.lwjgl.openal.AL10.alSourcef;
import static org.lwjgl.openal.AL10.alSourcei;

import math.Vector3f;
import org.lwjgl.openal.AL10;

public class Source {
  private int sourceId;

  public Source() {
    sourceId = alGenSources();
  }

  public void setRelative(Boolean isRelative) {
    alSourcei(sourceId, AL_SOURCE_RELATIVE, isRelative ? AL_TRUE : AL_FALSE);
  }

  public void setLooping(Boolean shouldLoop) {
    alSourcei(sourceId, AL_LOOPING, shouldLoop ? AL_TRUE : AL_FALSE);
  }

  public void setGain(float gain) {
    AL10.alSourcef(sourceId, AL10.AL_GAIN, gain);
  }

  public void setPitch(float pitch) {
    AL10.alSourcef(sourceId, AL10.AL_PITCH, pitch);
  }

  public void setPosition(Vector3f position) {
    AL10.alSource3f(sourceId, AL10.AL_POSITION, position.getX(), position.getY(), position.getZ());
  }

  /**
   * Play sound that is stored in a buffer.
   *
   * @param soundBuffer the sound buffer
   */
  public void playSound(int soundBuffer) {
    // Assign the sound we just loaded to the source
    alSourcei(sourceId, AL_BUFFER, soundBuffer);
    // Play the sound
    alSourcePlay(sourceId);
  }

  public void destroy() {
    alDeleteSources(sourceId);
  }
}
