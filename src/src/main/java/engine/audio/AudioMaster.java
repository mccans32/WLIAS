package engine.audio;

import static org.lwjgl.openal.AL10.AL_FORMAT_MONO16;
import static org.lwjgl.openal.AL10.AL_FORMAT_STEREO16;
import static org.lwjgl.openal.AL10.alBufferData;
import static org.lwjgl.openal.AL10.alDeleteBuffers;
import static org.lwjgl.openal.AL10.alGenBuffers;
import static org.lwjgl.openal.AL10.alListener3f;
import static org.lwjgl.openal.ALC10.alcCloseDevice;
import static org.lwjgl.openal.ALC10.alcCreateContext;
import static org.lwjgl.openal.ALC10.alcDestroyContext;
import static org.lwjgl.openal.ALC10.alcGetString;
import static org.lwjgl.openal.ALC10.alcMakeContextCurrent;
import static org.lwjgl.openal.ALC10.alcOpenDevice;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_filename;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.libc.LibCStdlib.free;
import static org.lwjglx.openal.ALC10.ALC_DEFAULT_DEVICE_SPECIFIER;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import math.Vector3f;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.system.MemoryStack;
import org.lwjglx.openal.AL10;

public class AudioMaster {
  private static long device;
  private static long context;
  private static ALCCapabilities alcCapabilities;
  private static ALCapabilities alCapabilities;
  private static ArrayList<Integer> buffers = new ArrayList<>();

  /**
   * Initialise openAl.
   */
  public static void init() {
    String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
    device = alcOpenDevice(defaultDeviceName);
    // Make the context
    int[] attributes = {0};
    context = alcCreateContext(device, attributes);
    alcMakeContextCurrent(context);
    // Set up capabilities
    alcCapabilities = ALC.createCapabilities(device);
    alCapabilities = AL.createCapabilities(alcCapabilities);
  }

  public static void setListener(Vector3f position) {
    alListener3f(AL10.AL_POSITION, position.getX(), position.getY(), position.getZ());
  }

  /**
   * Given a .ogg files path load the contents into memory.
   *
   * @param filePath the file path
   * @return the int
   */
  public static int loadSound(String filePath) {
    ShortBuffer rawAudioBuffer;

    int channels;
    int sampleRate;

    try (MemoryStack stack = stackPush()) {
      //Allocate space to store return information from the function
      IntBuffer channelsBuffer = stack.mallocInt(1);
      IntBuffer sampleRateBuffer = stack.mallocInt(1);

      rawAudioBuffer = stb_vorbis_decode_filename(filePath, channelsBuffer, sampleRateBuffer);

      //Retreive the extra information that was stored in the buffers by the function
      channels = channelsBuffer.get(0);
      sampleRate = sampleRateBuffer.get(0);

      //Find the correct OpenAL format
      int format = -1;
      if (channels == 1) {
        format = AL_FORMAT_MONO16;
      } else if (channels == 2) {
        format = AL_FORMAT_STEREO16;
      }

      // Request space for the buffer
      int bufferPointer = alGenBuffers();

      // Send the data to OpenAL
      assert rawAudioBuffer != null;
      alBufferData(bufferPointer, format, rawAudioBuffer, sampleRate);
      buffers.add(bufferPointer);

      // Free the memory allocated by STB
      free(rawAudioBuffer);

      return bufferPointer;
    }
  }

  /**
   * Clean up all of the buffers and openAL.
   */
  public static void cleanUp() {
    for (int buffer : buffers) {
      alDeleteBuffers(buffer);
    }
    alcDestroyContext(context);
    alcCloseDevice(device);
  }
}
