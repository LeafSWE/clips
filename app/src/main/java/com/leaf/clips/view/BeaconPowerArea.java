package com.leaf.clips.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.leaf.clips.R;
import com.leaf.clips.presenter.BeaconPowerPos;

import java.util.Collection;

/**
 * @author Cristian Andrighetto
 * @version 0.01
 * @since 0.00
 */

/**
 * Classe ausiliaria che permette definire una View in grado di aggiornarsi in tempo reale attraverso
 * l'aiuto dei Canvas. La classe in questione permette di visualizzare una mappa dell'edificio
 * sotto forma di immagine e i Beacon con i relativi segnali.
 */
public class BeaconPowerArea extends View {
    /**
     * Booleano che indica se la mappa dell'edificio è stata caricata
     */
    private boolean background = false;
    /**
     * Mappa dei Beacon
     */
    private static Collection<BeaconPowerPos> map;
    /**
     * Mappa dei Beacon con valore del segnale rssi aggiornato
     */
    private static Collection<BeaconPowerPos> updateMap;

    /**
     * Costruttore della classe BeaconPowerArea
     * @param context Contesto dell'applicazione
     */
    public BeaconPowerArea(Context context) {
        super(context);
    }

    /**
     * Costruttore della class BeaconPowerArea
     * @param context Contesto dell'applicazione
     * @param attrs Attribiuti che permettono di configurare la View
     */
    public BeaconPowerArea(Context context, AttributeSet attrs) {
        super(context,attrs);
    }

    /**
     * Metodo che permette di definire il comportamento dell'aggiornamento della View
     * @param canvas Oggetto Canvas fornito dall'Applicazione
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!background){
            Drawable myDrawable;
            if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
                myDrawable = getContext().getDrawable(R.drawable.torre_1piano);
            } else {
                myDrawable = getContext().getResources().getDrawable(R.drawable.torre_1piano);
            }
            setBackground(myDrawable);
            background=true;
        }
        drawBeacon(canvas);
        drawBeaconPower(canvas);
        invalidate();
    }

    /**
     * Metodo che permette di disegnare i Beacon sulla View
     * @param canvas Oggetto Canvas che fornisce le primitive per poter disegnare
     */
    public void drawBeacon(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        if(map != null) {
            for (BeaconPowerPos bp : map) {
                canvas.drawCircle(bp.getPosition()[0], bp.getPosition()[1], 5.0f, paint);
            }
        }
    }

    /**
     * Metodo che permette di disegnare l'area del segnale rssi coperta da ogni Beacon
     * @param canvas Oggetto Canvas che fornisce le primitive per poter disegnare
     */
    public void drawBeaconPower(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.argb(128,255,255,0));
        if(updateMap != null) {
            for (BeaconPowerPos bp : updateMap) {
                float radius=0.0f;
                if(bp.getRssi() != 0)
                    radius = -1.0f/((float) bp.getRssi());
                canvas.drawCircle(bp.getPosition()[0], bp.getPosition()[1] ,radius*10000.0f,paint);
            }
        }
    }

    /**
     * Metodo che permette di settare la mappa dei Beacon
     * @param map mappa dei Beacon
     */
    public static void setMap(Collection<BeaconPowerPos> map) {
        BeaconPowerArea.map = map;
    }
    /**
     * Metodo che permette di settare la mappa dei Beacon con il segnale rssi rilevato
     * @param updateMap mappa dei Beacon rilevati con il relativo segnale rssi
     */
    public static void setUpdateMap(Collection<BeaconPowerPos> updateMap) {
        BeaconPowerArea.updateMap = updateMap;
    }
}
