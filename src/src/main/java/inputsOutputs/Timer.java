package inputsOutputs;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Timer {

    double lastLoopTime;
    float timeCount;
    int fps;
    int fpsCount;
    int ups;
    int upsCount;


    public int getUps() {
        return this.ups;
    }

    public int getFps() {
        return this.fps;
    }



    public void update() {
        if (this.timeCount > 1f) {
            this.fps = this.fpsCount;
            this.fpsCount = 0;

            this.ups = this.upsCount;
            this.upsCount = 0;

            this.timeCount = 1f;
        }
    }

    public float getDelta() {
        double time = getTime();
        float delta = (float) (time - this.lastLoopTime);
        this.lastLoopTime = time;
        this.timeCount += delta;
        return delta;

    }

    public static double getTime() {
        return glfwGetTime();
    }

    public Timer() {
        this.lastLoopTime = getTime();
    }
}
