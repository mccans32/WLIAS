package engine.tools;

import java.util.concurrent.TimeUnit;

public class Timer {
  private long initTime;
  private long lastTime;
  private long currentTime;
  private long durationStart = -1;
  private long duration = -1;
  private boolean durationSet = false;
  private long durationElapsed;
  private boolean durationMet = false;

  /**
   * Instantiates a new Timer which records the system time of each update cycle.
   */
  public Timer() {
    initTime = System.nanoTime();
    lastTime = initTime;
    currentTime = initTime;
  }

  public long getInitTime() {
    return initTime;
  }

  public long getLastTime() {
    return lastTime;
  }

  public long getCurrentTime() {
    return currentTime;
  }

  public boolean isDurationMet() {
    return durationMet;
  }

  /**
   * Update.
   */
  public void update() {
    lastTime = currentTime;
    currentTime = System.nanoTime();
    if (durationSet) {
      if (durationStart == -1) {
        durationStart = currentTime;
        durationElapsed = 0;
      } else {
        durationElapsed += currentTime - lastTime;
        if (durationElapsed >= duration) {
          durationMet = true;
        }
      }
    }
  }

  /**
   * Sets the duration to be monitored.
   *
   * @param seconds the seconds
   */
  public void setDuration(int seconds) {
    clearDuration();
    duration = TimeUnit.SECONDS.toNanos(seconds);
    durationSet = true;
  }

  /**
   * Clears the duration.
   */
  public void clearDuration() {
    duration = -1;
    durationStart = -1;
    durationMet = false;
    durationSet = false;
  }
}
