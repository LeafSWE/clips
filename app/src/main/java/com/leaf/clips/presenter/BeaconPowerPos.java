package com.leaf.clips.presenter;

/**
 * @author Cristian Andrighetto
 * @version 0.01
 * @since 0.00
 */

public class BeaconPowerPos extends BeaconPos {
    int rssi=0;

    BeaconPowerPos(float[] pos, int minor){
        super(pos,minor);
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }
}
