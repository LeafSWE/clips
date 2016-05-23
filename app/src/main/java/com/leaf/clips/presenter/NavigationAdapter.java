package com.leaf.clips.presenter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.leaf.clips.R;
import com.leaf.clips.model.navigator.ProcessedInformation;

import java.util.List;

/**
 * @author Andrea Tombolato
 * @version 0.25
 * @since 0.00
 */

/**
 * Si occupa del binding tra le informazioni di navigazione fornite dal Model e la View deputata
 * a mostrarle.
 */
public class NavigationAdapter extends BaseAdapter {

    /**
     * Contesto dell'applicazione.
     */
    private Context context;

    /**
     * Lista delle istruzioni di navigazione utili per raggiungere un determinato POI.
     */
    private List<ProcessedInformation> navigationInformation;

    /**
     * Costruttore della classe NavigationAdapter
     * @param context Contesto di esecuzione dell'applicazione
     * @param navigationInformation Informazioni di navigazione da mostrare
     */
    public NavigationAdapter(Context context, List<ProcessedInformation> navigationInformation) {
        this.context = context;
        this.navigationInformation = navigationInformation;
    }

    /**
     * Restituisce il numero di istruzioni di navigazione contenute nella lista
     * @return int
     */
    @Override
    public int getCount() {
        return navigationInformation.size();
    }

    /**
     * Restituisce l'istruzione di navigazione che, nella lista, si trova nella posizione fornita
     * come parametro.
     * @param position Posizione dell'istruzione
     * @return Object
     */
    @Override
    public Object getItem(int position) {
        return navigationInformation.get(position);
    }

    /**
     * Restituisce l'id dell'istruzione di navigazione che, nella lista, si trova nella posizione
     * fornita come parametro.
     * @param position Posizione dell'istruzione
     * @return long
     */
    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    /**
     * @inheritDoc
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.nav_info_row,null);
        }

        ProcessedInformation navigationInformation = (ProcessedInformation)getItem(position);

        ImageView directionImage = (ImageView)convertView.findViewById(R.id.imageView_direction);
        int direction = navigationInformation.getDirection();
        setDirectionArrow(direction, directionImage);

        TextView basicDescription = (TextView)convertView.findViewById(R.id.textView_short_description);
        basicDescription.setText(navigationInformation.getProcessedBasicInstruction());


        TextView distance = (TextView)convertView.findViewById(R.id.textView_distance);
        distance.setText(navigationInformation.getDistance());

        if (position == 0)
            convertView.setBackgroundColor(convertView.getResources().getColor(R.color.green));
        Log.i("NAVADP", ""+getCount());
        if (position == getCount()-1)
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //tap on last instruction do nothing
                }
            });
        return  convertView;
    }

    /**
     * Metodo di utilità che associa la freccia corretta (Drawable) alla istruzione, in modo che ne
     * indichi visivamente la direzione da seguire.
     * @param direction indicatore della direzione
     * @param image view da aggiornare
     */
    private void setDirectionArrow(int direction, ImageView image){
        switch (direction){
            case 0: image.setBackgroundResource(R.drawable.arrow_go_straight);
                    break;
            case 1: image.setBackgroundResource(R.drawable.arrow_turn_left);
                break;
            case 2: image.setBackgroundResource(R.drawable.arrow_turn_right);
                break;
            case 3: image.setBackgroundResource(R.drawable.arrow_stairs_up);
                break;
            case 4: image.setBackgroundResource(R.drawable.arrow_stairs_down);
                break;
            case 5: image.setBackgroundResource(R.drawable.arrow_elevator_up);
                break;
            case 6: image.setBackgroundResource(R.drawable.arrow_elevator_down);
                break;
            case 9: image.setBackgroundResource(R.drawable.destination);
            default: Log.d("DIRECTION_ARRAY_ERR","Non esiste una rappresentazione grafica per questa direzione");
                break;
        }

    }


}
