package com.leaf.clips.model.beacon;
/**
 * @author Federico Tavella
 * @version 1.00
 * @since 0.00
 */

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.PriorityQueue;

/**
 * Classe che implementa Logger, per la gestione di un log
 */
public class LoggerImp implements Logger {

    /**
     * Path della directory in cui vengono salvati i log
     */
    final static String DIRECTORY = "C:\\Users\\Federico"; // TODO: 28/04/2016  inserire il path in cui vengono salvati i log

    /**
     * Rappresenta il contenuto di un log
     */
    StringBuffer data;

    public LoggerImp(){
        data = new StringBuffer();
    }

    /**
     * Metodo che restituisce il path della directory in cui vengono salvati
     i log
     * @return String Path della directory in cui vengono salvati i log
     */
    public static String getPath(){
        return DIRECTORY;
    }

    /**
     * Metodo che salva le informazioni contenute nell'oggetto su di un
     * file
     */
    @Override
    public void log(){
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        String filename;

        filename = DIRECTORY+"/Log"+ts+".txt";

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(filename));
            outputStreamWriter.write(data.toString());
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
        data.delete(0,data.length());
    }

    /**
     * Metodo che aggiunge all’insieme di informazioni di beacon già
     * eventualmente presenti le informazioni riguardanti i beacon passati
     * in ingresso
     * @param beacons Insieme dei beacon di cui salvare le informazioni
     */
    @Override
    public void add(PriorityQueue<MyBeacon> beacons){
        for(MyBeacon beacon : beacons){
            data.append(beacon.toString());
        }
    }

    /**
     * Metodo che, dato il nome di un file di log, ritorna l’informazione
     * in esso contenuta sotto forma di stringa
     * @param name Nome del file di log da cui reperire le informazioni
     * @return String Contenuto del log sotto forma di stringa
     */
    @Override
    public String open(String name){

        File file = new File(DIRECTORY,name+".txt");

        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();

            while (line != null) {
                text.append(line);
                text.append('\n');
                line = br.readLine();
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return text.toString();
    }

    /**
     * Metodo per la rimozione di un log precedentemente salvato
     * @param name Nome del log da rimuovere
     */
    @Override
    public boolean remove(String name){
        File fileToDelete = new File(DIRECTORY, name);
        return fileToDelete.delete();

    }

    /**
     * Metodo che salva le informazioni contenute nell’oggetto su di un
     * file con nome uguale alla stringa passata come parametro
     * @param name Nome da dare al file
     */
    @Override
    public void save(String name){
        String filename;

        filename = DIRECTORY+"/"+name+".txt";

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(filename));
            outputStreamWriter.write(data.toString());
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
        data.delete(0,data.length());
    }

    /**
     * Metodo per l'accesso alle informazioni nel log temporaneo
     * @return StringBuffer Informazioni nel log temporaneo
     */
    @Override
    public StringBuffer getData(){
        return data;
    }
}
