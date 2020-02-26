package engine.objects.gui;

import engine.graphics.Mesh;
import math.Vector2f;

public class GuiObject {
    private Vector2f defaultPosition;
    private Vector2f position;
    private Vector2f scale;
    private Mesh mesh;
    private float xEdge;
    private float xOffset;
    private float yEdge;
    private float yOffset;

    /**
     * Instantiates a new Gui object.
     *
     * @param position the position
     * @param scale    the scale
     * @param mesh     the mesh.
     */
    public GuiObject(Vector2f position, Vector2f scale, Mesh mesh, float xEdge, float xOffset, float yEdge, float yOffset) {
        this.defaultPosition = position;
        this.position = position;
        this.scale = scale;
        this.mesh = mesh;
        this.xEdge = xEdge;
        this.xOffset = xOffset;
        this.yEdge = yEdge;
        this.yOffset = yOffset;

    }

    public float getxEdge() {
        return xEdge;
    }

    public float getxOffset() {
        return xOffset;
    }

    public float getyEdge() {
        return yEdge;
    }

    public float getYOffset() {
        return yOffset;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getScale() {
        return scale;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void create() {
        mesh.create();
    }

    public void reposition(float xSpan, float ySpan) {
        position.setX(xEdge * xSpan + xOffset);
        position.setY(yEdge * ySpan + yOffset);
    }
}
