package com.kodaskina.mapproject.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by EA on 11.7.2017.
 */

public class MyItem implements ClusterItem {
    private final LatLng mPosition;
    private  String mTitle;
    private  String mSnippet;

    public MyItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    public MyItem(double lat, double lng, String title, String snippet) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSnippet() {
        return mSnippet;
    }

    @Override
    public String toString() {
        return "MyItem{" +
                "mPosition=" + mPosition +
                ", mTitle='" + mTitle + '\'' +
                ", mSnippet='" + mSnippet + '\'' +
                '}';
    }
}
