package Game;

import inputsOutputs.Timer;
import inputsOutputs.Window;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.glfwInit;

public class Game {
    public void start() {
        initialize();
        gameLoop();
        dispose();
    }

    private void dispose() {

    }

    private void gameLoop() {

    }

    private void initialize() {
        Window.setCallbacks();

        if (!glfwInit()) {
            System.err.println(("GLFW Failed to start!"));
            System.exit(1);
        }

        Window window = new Window();
        window.setSize(640, 480);
        window.setFullscreen(false);
        window.createWindow("We Live in a Society");

        window.VSync(true);
        window.showWindow();

        Timer timer = new Timer(); //Initializes Timer

        GL.createCapabilities();
    }
}
