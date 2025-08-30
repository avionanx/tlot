package lod.thelegendoftides;

import org.joml.Vector3f;

public class BorderStruct {
    public int framesUntilRender;
    public float size;
    public float angle;
    public Vector3f colour;
    public boolean visible;

    public BorderStruct(float size, float angle, Vector3f colour, boolean visible, int framesUntilRender) {
        this.size = size;
        this.angle = angle;
        this.colour = colour;
        this.visible = visible;
        this.framesUntilRender = framesUntilRender;
    }
}
