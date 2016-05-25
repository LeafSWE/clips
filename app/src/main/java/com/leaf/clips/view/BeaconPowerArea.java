package com.leaf.clips.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.leaf.clips.R;
import com.leaf.clips.presenter.BeaconPowerPos;

import java.util.Collection;

/**
 * @author Cristian Andrighetto
 * @version 0.01
 * @since 0.00
 */
public class BeaconPowerArea extends View {
    private boolean background=false;
    private static Collection<BeaconPowerPos> map;
    private static Collection<BeaconPowerPos> updateMap;

    public BeaconPowerArea(Context context) {
        super(context);
    }

    public BeaconPowerArea(Context context, AttributeSet attrs) {
        super(context,attrs);
    }

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


    public void drawBeacon(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        if(map != null) {
            for (BeaconPowerPos bp : map) {
                canvas.drawCircle(bp.getPosition()[0], bp.getPosition()[1], 5.0f, paint);
            }
        }
    }

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

    public static void setMap(Collection<BeaconPowerPos> map) {
        BeaconPowerArea.map = map;
    }

    public static void setUpdateMap(Collection<BeaconPowerPos> updateMap) {
        BeaconPowerArea.updateMap = updateMap;
    }
}
