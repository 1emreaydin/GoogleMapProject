package com.kodaskina.mapproject.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.kodaskina.mapproject.R;
import com.kodaskina.mapproject.apiService.ApiService;
import com.kodaskina.mapproject.adapter.CustomViewPagerOnChangeListener;
import com.kodaskina.mapproject.adapter.MapViewPagerAdapter;
import com.kodaskina.mapproject.apiClient.ApiClient;
import com.kodaskina.mapproject.model.Model;
import com.kodaskina.mapproject.model.MyItem;
import com.kodaskina.mapproject.renderer.CustomClusterRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap; //Activity üzerinde çağırdığımız GoogleMap nesnesi.
    private static ViewPager event_pager; //Sağ-Sol kaydırdığımız bilgi yeri alttaraf...
    List<Model> liste; //jsondan çektiklerimizi atadığımız yer.
    List<MyItem> myItems; //Cluster itemlerin nesneleri (location.tittle.vs.)
    ArrayList<ClusterItem> clusterItems = new ArrayList<>();
    HashMap<String, Integer> clusterItemHashMap = new HashMap<>();
    private ClusterManager<MyItem> mClusterManager;
    CustomClusterRenderer renderer ;

    //Jeneric java ... ARAŞTIR!!..
    //Marker Clustering....ARAŞTIR!!..

    String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        event_pager = (ViewPager) findViewById(R.id.event_pager);


        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getUserList();

        //alt taraftaki page değişmeye başladığında olan olaylar...
        event_pager.addOnPageChangeListener(new CustomViewPagerOnChangeListener() {
            //seni bir sayfa seçildiğinde yapılacaklar.
            @Override
            public void onViewPagerPageSelected(int position) {

                MyItem item=myItems.get(position);
                LatLng latLng = new LatLng(item.getPosition().latitude, item.getPosition().longitude);
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                Marker marker =renderer.getMarker(item);
                if (null!=marker)
                    marker.showInfoWindow();
                else if (marker==null){
                    float zoomLevel = 8;
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
                    renderer.setClusterItem(item.getTitle());

                    Log.i(TAG, "onViewPagerPageSelected: Kamera Odaklanma İşlemi" );
                 /*   marker=renderer.getMarker(renderer.getClusterItem());
                    marker.showInfoWindow();*/
                }
            }
        });
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.e(TAG, "onMapClick: ");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void getUserList() {

        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<List<Model>> call = service.getUserData();
        call.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(@NonNull Call<List<Model>> call, @NonNull Response<List<Model>> response) {

                List<Model> userList = response.body();
                liste = userList; //genel tanımlama
                myItems = new ArrayList<MyItem>();
                if (userList == null) {
                    Toast.makeText(MainActivity.this, "Liste boş", Toast.LENGTH_SHORT).show();
                    return;
                }

                googleMap.clear();
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                int i = 0;
                for (Model model : userList) {

                    MarkerOptions markerOptions = new MarkerOptions();
                    LatLng latLng = new LatLng(model.getLat(), model.getLnd());
                    builder.include(latLng);
                    markerOptions.position(latLng);
                    markerOptions.title(model.getPlaka());
                    MyItem Item = new MyItem(latLng.latitude, latLng.longitude, markerOptions.getTitle(), markerOptions.getSnippet());
                    clusterItems.add(Item);
                    myItems.add(Item);
                    mClusterManager.addItem(Item);
                    clusterItemHashMap.put(myItems.get(i).getTitle(), i);
                    i++;
                }

                LatLngBounds bounds = builder.build();
                googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));
                event_pager.setAdapter(new MapViewPagerAdapter(MainActivity.this, userList));

            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainActivity.this, "Bir hata olustu. ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;

        mClusterManager = new ClusterManager<>(this, googleMap);
        renderer = new CustomClusterRenderer(this,googleMap, mClusterManager);
        mClusterManager.setRenderer(renderer);
        googleMap.setOnCameraIdleListener(mClusterManager);
        googleMap.setOnMarkerClickListener(mClusterManager);

        //Cluster Click Event...!!!!!
        mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MyItem>() {
            @Override
            public boolean onClusterClick(Cluster<MyItem> cluster) {
                Log.e("info", "onClusterClick: " + cluster.toString());
                return true;
            }
        });
        //ClusterItemClick Event....!!!!!
        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
            @Override
            public boolean onClusterItemClick(MyItem myItem) {
                Log.e("info", "onClusterClick: " + myItem.getTitle());
                event_pager.setCurrentItem(clusterItemHashMap.get(myItem.getTitle()));
                return false;
            }
        });


    }

}
