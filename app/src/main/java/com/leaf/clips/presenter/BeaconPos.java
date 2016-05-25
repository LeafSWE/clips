package com.leaf.clips.presenter;

/**
 * @author Cristian Andrighetto
 * @version 0.01
 * @since 0.00
 */
public class BeaconPos{
    float[] position;
    int minor;

    BeaconPos(float[] pos, int minor){
        this.position=pos;
        this.minor=minor;
    }

    public float[] getPosition() {
        return position;
    }

    public void setPosition(float[] position) {
        this.position = position;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }
}