package engine;

import engine.io.Input;
import math.Matrix4f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static java.lang.System.currentTimeMillis;
import static org.lwjgl.glfw.GLFW.*;

/**
 * The type Window.
 */
public class Window {
    /**
     * The Enable vsync.
     */
    static final boolean ENABLE_V_SYNC = true;
    private static boolean SHOULD_CENTER = false;
    /**
     * The Frames.
     */
    public int frames;
    /**
     * The Time.
     */
    public long time;
    /**
     * The Is glfw initialised.
     */
    public boolean isGlfwInitialised = false;
    private long window;
    private int height;
    private int width;
    private String title;
    private float backgroundR;
    private float backgroundG;
    private float backgroundB;
    private float backgroundAlpha;
    private GLFWWindowSizeCallback windowSizeCallback;
    private boolean hasResized = false;
    private int[] windowPosX = new int[1];
    private int[] windowPosY = new int[1];
    private Input input;
    private boolean isVisible = true;
    private Matrix4f projectionMatrix;
    private Matrix4f orthographicMatrix;
    private float fov = 70.0f;
    private float near = 0.1f;
    private float far = 1000f;
    private float aspect;
    private float xSpan;
    private float ySpan;

    /**
     * Instantiates a new Window.
     *
     * @param width  the width
     * @param height the height
     * @param title  the title
     */
    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
        setAspect();
        setSpans();
        createProjectionMatrix();
        createOrthographicMatrix();
    }

    /**
     * Sets error callback.
     */
    public static void setErrorCallback() {
        glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
    }

    public float getxSpan() {
        return xSpan;
    }

    public float getySpan() {
        return ySpan;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public float getFov() {
        return fov;
    }

    public float getNear() {
        return near;
    }

    public float getFar() {
        return far;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    private void setLocalCallbacks() {
        windowSizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long argWindow, int argWidth, int argHeight) {
                width = argWidth;
                height = argHeight;
                hasResized = true;

                // set spans for orthographic projection matrix and for repositioning gui
                float aspect = (float) width / (float) height;


            }
        };

        //Set Size Callback
        glfwSetWindowSizeCallback(window, windowSizeCallback);
        //Set input callbacks
        glfwSetKeyCallback(window, input.getKeyboard());
        glfwSetCursorPosCallback(window, input.getMouseMove());
        glfwSetScrollCallback(window, input.getMouseScroll());
        glfwSetMouseButtonCallback(window, input.getMouseButtons());

    }

    /**
     * Create.
     */
    public void create() {
        setErrorCallback();

        isGlfwInitialised = glfwInit();
        if (!isGlfwInitialised) {
            //  System.exit(1);
            throw new RuntimeException("GLFW Failed to start!");
        }

        input = new Input();

        glfwWindowHint(GLFW_VISIBLE, (isVisible() ? GLFW_TRUE : GLFW_FALSE));
        window = glfwCreateWindow(width, height, title, 0, 0);
        if (window == 0) {
            throw new IllegalStateException("Failed to create window!");
        }

        // Center the screen
        centerScreen();
        //Set Current Context to Window
        glfwMakeContextCurrent(window);
        // Enables OpenGL functionality
        GL.createCapabilities();
        // Set Depth Test
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        //Set local callbacks
        setLocalCallbacks();
        //Display Window
        if (isVisible()) {
            showWindow();
        }
        //Enable VSync
        setVSync(ENABLE_V_SYNC);
        // set timer for window
        time = currentTimeMillis();

    }

    /**
     * Center screen.
     */
    public void centerScreen() {
        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // X value
        windowPosX[0] = (videoMode.width() - width) / 2;
        // Y value
        windowPosY[0] = (videoMode.height() - height) / 2;
        glfwSetWindowPos(window, windowPosX[0], windowPosY[0]);
    }

    public Matrix4f getOrthographicMatrix() {
        return orthographicMatrix;
    }

    /**
     * Update.
     */
    public void update() {
        if (hasResized) {
            setAspect();
            setSpans();
            // Must update projection matrix to prevent distortion;
            createProjectionMatrix();
            createOrthographicMatrix();
            GL11.glViewport(0, 0, width, height);
            if (SHOULD_CENTER) {
                centerScreen();
            }
            hasResized = false;
        }
        GL11.glClearColor(backgroundR, backgroundG, backgroundB, backgroundAlpha);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        glfwPollEvents();
        updateFrameCounter();
    }

    /**
     * Destroy.
     */
    public void destroy() {
        Input.destroy();
        glfwWindowShouldClose(window);
        glfwDestroyWindow(window);
    }

    /**
     * Swap buffers.
     */
    public void swapBuffers() {
        glfwSwapBuffers(window);
    }

    /**
     * Should close boolean.
     *
     * @return the boolean
     */
    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    /**
     * Show window.
     */
    public void showWindow() {
        glfwShowWindow(window);
    }

    /**
     * Sets v sync.
     *
     * @param vsync the v sync
     */
    public void setVSync(boolean vsync) {
        glfwSwapInterval(vsync ? 1 : 0);
    }

    /**
     * Sets background colour.
     *
     * @param r     the r
     * @param g     the g
     * @param b     the b
     * @param alpha the alpha
     */
    public void setBackgroundColour(float r, float g, float b, float alpha) {
        backgroundR = r;
        backgroundG = g;
        backgroundB = b;
        backgroundAlpha = alpha;
    }

    /**
     * Update frame counter.
     */
    public void updateFrameCounter() {
        frames++;
        if (currentTimeMillis() > time + 1000) {
            glfwSetWindowTitle(window, title + " | FPS " + frames);
            time = currentTimeMillis();
            frames = 0;
        }
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param argTitle the arg title
     */
    public void setTitle(String argTitle) {
        this.title = argTitle;
    }

    /**
     * Is has resized boolean.
     *
     * @return the boolean
     */
    public boolean isHasResized() {
        return hasResized;
    }

    public float getAspect() {
        return aspect;
    }

    /**
     * Sets size.
     *
     * @param argWidth  the arg width
     * @param argHeight the arg height
     */
    public void setSize(int argWidth, int argHeight) {
        this.width = argWidth;
        this.height = argHeight;
        hasResized = true;
    }


    private void createProjectionMatrix() {
        this.projectionMatrix = Matrix4f.projection(
                fov,
                (float) width / (float) height,
                near,
                far);
    }

    private void createOrthographicMatrix() {
        this.orthographicMatrix = Matrix4f.orthographic(-1 * xSpan, xSpan, -1 * ySpan, ySpan, -1, 1);
    }

    private void setAspect() {
        this.aspect = (float) width / (float) height;
    }

    private void setSpans() {
        // set xSpan and ySpan for orthographic projection and for repositioning gui
        this.xSpan = 1;
        this.ySpan = 1;
        if (aspect >= 1) {
            this.xSpan *= aspect;
        } else {
            this.ySpan = this.xSpan / aspect;
        }
    }
}
