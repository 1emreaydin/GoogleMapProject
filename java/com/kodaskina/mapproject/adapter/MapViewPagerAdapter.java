package com.kodaskina.mapproject.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kodaskina.mapproject.model.Model;
import com.kodaskina.mapproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by EA on 6.7.2017.
 */

public class MapViewPagerAdapter extends PagerAdapter {

    Context context;
    List<Model> modellist;

    public MapViewPagerAdapter(Context context,List<Model> modellist) {
            this.context = context;
            this.modellist=modellist;
    }

    @Override
    public int getCount() {
        return modellist.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {

        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.row_map,null);
        Log.e("Bildiri","Oluştu");
        TextView plaka_name = (TextView)  itemView.findViewById(R.id.plaka_name);
        TextView address = (TextView)  itemView.findViewById(R.id.address);
        ImageView image = (ImageView) itemView.findViewById(R.id.image_view);
        plaka_name.setText(modellist.get(position).getPlaka().toString());
        address.setText(modellist.get(position).getAddress().toString());
        Picasso.with(context).load(modellist.get(position).getImage()).into(image);
        Log.e("Bildiri-İçerik","Plaka-> "+modellist.get(position).getPlaka());
        Log.e("Bildiri-İçerik","Adres-> "+modellist.get(position).getAddress());
        container.addView(itemView);

        return itemView;
    }
}
