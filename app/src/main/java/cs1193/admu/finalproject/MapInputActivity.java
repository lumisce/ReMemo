package cs1193.admu.finalproject;

import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Created by Dion Velasco on 7/21/2016.
 */
public class MapInputActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapLongClickListener {

    public static final String ADDRESS = "addressKey";

    private GoogleMap mMap;
    private LatLng sydney;
    private Marker point;
    private EditText address;
    private SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        address = (EditText) findViewById(R.id.et_address);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        sydney = new LatLng(14.63965,121.0766);
        point = mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Faura"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,19));
        mMap.setOnMapLongClickListener(this);
        address.setText(reverseGeoCode(sydney));
    }

    public String reverseGeoCode(LatLng l){

        Geocoder geocoder = new Geocoder(this);
        double latitude = l.latitude;
        double longitude = l.longitude;

        List<Address> addresses = null;
        String addressText="";

        try {
            addresses = geocoder.getFromLocation(latitude, longitude,1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(addresses != null && addresses.size() > 0 ){
            Address address = addresses.get(0);

            addressText = String.format("%s, %s, %s",
                    address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                    address.getLocality(),
                    address.getCountryName());
        }

        return addressText;

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        sydney = latLng;
        point.remove();
        point = mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in " + reverseGeoCode(sydney)));
        address.setText(reverseGeoCode(sydney));
    }

    public void finishMap(View v){


        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor e = prefs.edit();
        e.putString(MapInputActivity.ADDRESS,address.getText().toString());
        e.commit();

        finish();

    }
}
