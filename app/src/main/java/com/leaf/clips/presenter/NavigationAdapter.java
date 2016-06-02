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
import com.leaf.clips.model.navigator.NavigationDirection;
import com.leaf.clips.model.navigator.ProcessedInformation;

import java.util.List;

/**
 * @author Andrea Tombolato
 * @version 0.25
 * @since 0.00
 */

// TODO: 25/05/16 aggiornare tracy/uml

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
        NavigationDirection direction = navigationInformation.getDirection();
        setDirectionArrow(direction, directionImage);

        TextView basicDescription = (TextView)convertView.findViewById(R.id.textView_short_description);
        basicDescription.setText(navigationInformation.getProcessedBasicInstruction());


        TextView distance = (TextView)convertView.findViewById(R.id.textView_distance);
        distance.setText(navigationInformation.getDistance());

        if (position == 0)
            convertView.setBackgroundColor(convertView.getResources().getColor(R.color.currentInstructionColor));
        /*if (position == getCount() - 1)
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    
                }
            });*/
        Log.i("NAVADP", "" + getCount());
        return  convertView;
    }


    /**
     * Metodo di utilità che associa la freccia corretta (Drawable) alla istruzione, in modo che ne
     * indichi visivamente la direzione da seguire.
     * @param direction indicatore della direzione
     * @param image view da aggiornare
     */
    private void setDirectionArrow(NavigationDirection direction, ImageView image){
        switch (direction){
            case STRAIGHT:
                image.setBackgroundResource(R.drawable.arrow_go_straight);
                break;
            case LEFT:
                image.setBackgroundResource(R.drawable.arrow_turn_left);
                break;
            case RIGHT:
                image.setBackgroundResource(R.drawable.arrow_turn_right);
                break;
            case STAIR_UP:
                image.setBackgroundResource(R.drawable.arrow_stairs_up);
                break;
            case STAIR_DOWN:
                image.setBackgroundResource(R.drawable.arrow_stairs_down);
                break;
            case ELEVATOR_UP:
                image.setBackgroundResource(R.drawable.arrow_elevator_up);
                break;
            case ELEVATOR_DOWN:
                image.setBackgroundResource(R.drawable.arrow_elevator_down);
                break;
            case TURN:
                image.setBackgroundResource(R.drawable.arrow_turn_around);
                break;
            case DESTINATION:
                image.setBackgroundResource(R.drawable.destination);
                break;
            default: Log.d("DIRECTION_ARRAY_ERR","Non esiste una rappresentazione grafica per questa direzione");
                break;
        }

    }

    /**
     * Metodo che permette di verificare se un certo elemento della lista è attivo oppure no
     * @param position Posizione dell'elemento
     * @return boolean
     */
    @Override
    public boolean isEnabled(int position) {
        return position != (navigationInformation.size() - 1) && super.isEnabled(position);
    }
}
