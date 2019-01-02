package com.kodaskina.mapproject.renderer;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.kodaskina.mapproject.model.MyItem;

import java.util.Set;

/**
 * Created by EA on 12.7.2017.
 */

public class CustomClusterRenderer extends DefaultClusterRenderer<MyItem> {

    String TAG = CustomClusterRenderer.class.getSimpleName();
    public String value;
    private Marker marker;
    private final Context mContext;
    public CustomClusterRenderer(Context context, GoogleMap map,
                                 ClusterManager clusterManager) {
        super(context, map, clusterManager);
        mContext = context;
    }
       @Override
    protected void onClusterItemRendered(MyItem item, Marker marker) {
        Log.e(TAG, "onClusterItemRendered: "+item.getTitle());

        if (value==null){
            Log.i(TAG,"Value  ++++ Boşşşşş");
        }
        else if (value.equals(item.getTitle())){
            setClusterItemMarker(marker);
            Log.i(TAG,"onBeforeClusterItemRendered: ++"+item.getTitle());
            marker.showInfoWindow();
            Log.i(TAG,"onBeforeClusterItemRendered: ++"+this.marker.getTitle());
        }

        super.onClusterItemRendered(item, marker);
    }

    @Override
    protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {
        Log.i(TAG,"onBeforeClusterItemRendered:"+item.getTitle());
        super.onBeforeClusterItemRendered(item, markerOptions);
    }

    @Override
    public void onClustersChanged(Set set) {
        Log.i(TAG,"onClustersChanged:"+set.toString());
        super.onClustersChanged(set);
    }

    public void setClusterItem(String value) {

        this.value = value;
        Log.e(TAG, "setClusterItem: Gelen Değer "+value.toString()  );
    }

    public void setClusterItemMarker(Marker marker){
        this.marker=marker;
    }


}