package com.leaf.clips.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.leaf.clips.R;
import com.leaf.clips.model.navigator.graph.navigationinformation.NavigationInformation;

import java.util.List;

/**
 * @author Andrea Tombolato
 * @version 0.01
 * @since 0.00
 */
public class NavigationAdapter extends BaseAdapter {
    private Context context;
    private List<NavigationInformation> navigationInformation;

    public NavigationAdapter(Context context, List<NavigationInformation> navigationInformation) {
        this.context = context;
        this.navigationInformation = navigationInformation;
    }

    @Override
    public int getCount() {
        return navigationInformation.size();
    }

    @Override
    public Object getItem(int position) {
        return navigationInformation.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.nav_info_row,null);
        }

        NavigationInformation navigationInformation = (NavigationInformation)getItem(position);

        /**TODO: where to find the direction arrow image?
         * ImageView directionImage = (ImageView)convertView.findViewById(R.id.imageView_direction);
         * directionImage.setBackgroundResource(navigationInformation.getIcon());
         */

        TextView basicDescription = (TextView)convertView.findViewById(R.id.textView_short_description);
        basicDescription.setText(navigationInformation.getBasicInformation());

        /**TODO: where to find the distance?
         * TextView distance = (TextView)convertView.findViewById(R.id.textView_distance);
         * distance.setText(navigationInformation.getDistance());
         */

        return  convertView;
    }
}
