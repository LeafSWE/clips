package com.leaf.clips.model.compass;
/**
 * @author Eduard Bicego
 * @version 0.00
 * @since 0.00
 *
 *
 */

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 *Classe che si occupa di gestire i dati ricavabili dai sensori e calcolare l'orientamento del device
 */
public class Compass implements SensorEventListener {

    /**
     * Sensore che misura l'accelerazione del device sui tre assi fisici
     */
    private Sensor accelerometer;

    /**
     * variabile di guardia per accertarsi il reperimento di almeno un dato dall'accelerometro
     */
    private boolean lastAccelerometerSet = false;

    /**
     * variabile di guardia per accertarsi il reperimento di almeno un dato dal magnetometro
     */
    private boolean lastMagnetometerSet = false;

    /**
     * array che contiene gli ultimi dati ricevuti dal magnetometro
     */
    private float[] lastMagnetometerData = new float[3];

    /**
     * array che contiene gli ultimi dati ricevuti dall'accelerometro
     */
    private float[] lastAccelerometerData = new float[3];

    /**
     * Sensore che misura il campo magnetico per i tre assi fisici
     */
    private Sensor magnetometer;

    /**
     * gradi di orientamento sui tre assi fisici
     */
    private float[] orientation = new float[3];

    /**
     * matrice di rotazione ottenuta dai dati rilevati dai sensori
     */
    private float[] rotationMatrix = new float[9];

    /**
     * oggetto fornito dal sistema Android per ottenere le istanze dei sensori
     */
    private SensorManager sensorManager;

    /**
     * Costruttore della classe Compass
     * @param sensorManager Classe Android che permette di ottenere i riferimenti dei sensori del device
     */
    public Compass(SensorManager sensorManager) {
        this.sensorManager = sensorManager;
        accelerometer = this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = this.sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    /**
     * Metodo che restituisce l'ultimo dato calcolato dai dati ricavati dai sensori che indica l'orientamento del dispositivo.
     * @return  float
     */
    public float getLastCoordinate() {
        return (float)(Math.toDegrees(orientation[0])+360)%360;
    }

    /**
     * Metodo che viene chiamato nel caso in cui l'accuratezza dei sensori è cambiata. Attualmente non viene utilizzato dall'applicativo
     * @param sensor Riferimento al sensore che ha scatenato l'evento
     * @param accuracy Nuova accuratezza impostata al sensore
     * @return  void
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // NON UTILIZZATO
    }

    /**
     * Metodo invocato ad ogni evento generato dai sensori attivi di Compass. Fornisce quindi i dati misurati dal sensore e elaborandoli ne ricava l'orientamento del device
     * @param event Rappresenta un evento scatenato da un sensore del dispositivo e detiene al suo interno tutti i dati rilevati da quel sensore
     * @return  void
     */
    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor == accelerometer) {
            System.arraycopy(event.values, 0, lastAccelerometerData, 0, event.values.length);
            lastAccelerometerSet = true;
        } else if (event.sensor == magnetometer) {
            System.arraycopy(event.values, 0, lastMagnetometerData, 0, event.values.length);
            lastMagnetometerSet = true;
        }

        if (lastAccelerometerSet && lastMagnetometerSet) {
            SensorManager.getRotationMatrix(rotationMatrix, null, lastAccelerometerData, lastMagnetometerData);
            SensorManager.getOrientation(rotationMatrix, orientation);
        }
    }

    /**
     * Metodo che permette all'oggetto Compass di ricevere dati dai sensori e quindi accenderli
     * @return  void
     */
    public void registerListener(){
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * Metodo che permette all'oggetto Compass di smettere di ricevere dati dai sensori e quindi spegnerli
     * @return  void
     */
    public void unregisterListener() {
        sensorManager.unregisterListener(this, accelerometer);
        sensorManager.unregisterListener(this, magnetometer);
    }

}

