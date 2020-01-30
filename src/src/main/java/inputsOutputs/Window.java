package inputsOutputs;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

public class Window {
    private long window;

    private int height;
    private int width;
    private boolean fullscreen;
    private boolean hasResized;
    private GLFWWindowSizeCallback windowSizeCallback;

    private Inputs input;

    public static void setCallbacks(){
        glfwSetErrorCallback(new GLFWErrorCallback() {
            @Override
            public void invoke(int error, long description) {
                throw new IllegalStateException(GLFWErrorCallback.getDescription(description));
            }
        });
    }

    private void setLocalCallbacks() {
        windowSizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long argWindow, int argWidth, int argHeight) {
                width = argWidth;
                height = argHeight;
                hasResized = true;
            }
        };

        glfwSetWindowSizeCallback(window, windowSizeCallback);
    }

    public Window() {
        setSize(1280, 768);
        setFullscreen(false);
        hasResized = false;
    }

    public void createWindow(String title) {
        window = glfwCreateWindow(width, height, title, fullscreen ? glfwGetPrimaryMonitor() : 0, 0);

        if (window == 0) throw new IllegalStateException("Failed to create window!");

        if (!fullscreen) {
            GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(window, (vid.width() - width) / 2, (vid.height() - height) / 2);
        }

        glfwShowWindow(window);

        glfwMakeContextCurrent(window);

        input = new Inputs(window);
        setLocalCallbacks();
    }

    public void cleanUp() {
        glfwFreeCallbacks(window);
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    public void swapBuffers(){
        glfwSwapBuffers(window);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setFullscreen(boolean fullscreen){
        this.fullscreen = fullscreen;
    }

    public void update() {
        hasResized = false;
        input.update();
        glfwPollEvents();
    }

    public void showWindow() {
        glfwShowWindow(window);
    }

    public void VSync(boolean V_sync) {
        glfwSwapInterval( V_sync ? 1 : 0);
    }

    public long getWindow() {
        return window;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public boolean isHasResized() {
        return hasResized;
    }

    public Inputs getInput() {
        return input;
    }


}
