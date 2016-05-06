package com.leaf.clips.model.beacon;

/**
 * @author Federico Tavella
 * @version 0.00
 * @since 0.00
 */

import android.os.Parcel;
import android.os.Parcelable;

import org.altbeacon.beacon.Beacon;


/**
 * Classe che implementa l’interfaccia MyBeacon. Offre tutti i
 * metodi per accedere alle informazioni di un beacon. Estende la classe
 * org.altbeacon.beacon.Beacon
 */
public class MyBeaconImp extends Beacon implements MyBeacon, Comparable {

    public static final Parcelable.Creator<MyBeacon> CREATOR = new Parcelable.Creator<MyBeacon>() {
        public MyBeacon createFromParcel(Parcel in) {
            return new MyBeaconImp(in);
        }

        public MyBeacon[] newArray(int size) {
            return new MyBeacon[size];
        }
    };

    private MyBeaconImp(Parcel in) {
        super(in);
    }{
        Beacon.setDistanceCalculator(new MyDistanceCalculator());
    }

    public MyBeaconImp(Beacon beacon){
        super(beacon);
        recalculateDistance();
    }

    /**
     * Metodo che permette di ricalcolare la distanza tra il beacon e il dispositivo
     */

    private void recalculateDistance(){
        calculateDistance(this.getTxPower(), this.getRssi());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
    }

    /**
     * Metodo che ritorna l'identificativo UUID del beacon
     * @return String UUID del beacon rilevato
     */
    @Override
    public String getUUID() {
        return super.getId1().toString();
    }

    /**
     * Metodo che ritorna l’identificativo Major del beacon
     * @return int Major del beacon rilevato
     */
    @Override
    public int getMajor() {
        return super.getId2().toInt();
    }

    /**
     * Metodo che ritorna l’identificativo Minor del beacon
     * @return int Minor del beacon rilevato
     */
    @Override
    public int getMinor() {
        return super.getId3().toInt();
    }

    /**
     * Metodo permette di fornire una conversione di MyBeaconImp a tipo String
     * @return String Stringa rappresentante il beacon rilevato
     */
    @Override
    public String toString(){
        // TODO: 28/04/2016  
        return super.toString();
    }

    /**
     * Metodo che ritorna l'indirizzo Bluetooth del beacon
     * @return String indirizzo bluetooth del beacon rilevato
     */
    @Override
    public String getBluetoothAddress() {
        return super.getBluetoothAddress();
    }

    /**
     * Metodo che ritorna la distanza del beacon dal dispositivo che lo ha rilevato
     * @return double distanza del beacon dal dispositivo che lo ha rilevato
     */
    @Override
    public double getDistance() {
        return super.getDistance();
    }

    /**
     * Metodo che ritorna la potenza ricevuta del segnale del beacon
     * @return int potenza ricevuta del segnale del beacon
     */
    @Override
    public int getRssi() {
        return super.getRssi();
    }

    /**
     * Metodo che ritorna la potenza di trasmissione del beacon
     * @return int potenza di trasmissione del beacon
     */
    @Override
    public int getTxPower() {
        return super.getTxPower();
    }

    /**
     * Metodo che ritorna il livello di batteria del beacon rilevato
     * @return long livello di batteria del beacon rilevato
     */
    @Override
    public long getBatteryLevel() {
        return super.getDataFields().get(0);
    }

    /**
     * Metodo che ritorna il codice rappresentante il tipo di beacon che è stato rilevato
     * @return int codice rappresentante il tipo di beacon rilevato
     */
    @Override
    public int getBeaconTypeCode() {
        return super.getBeaconTypeCode();
    }

    @Override
    public int compareTo(Object another) {
        if(this.getTxPower()==((MyBeacon)another).getTxPower())
            return 0;
        return this.getTxPower()<((MyBeacon)another).getTxPower() ? -1 : 1;
    }
}
