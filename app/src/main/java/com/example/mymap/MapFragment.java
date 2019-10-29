package com.example.mymap;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback, LocationListener {

  GoogleMap gMap;
  LocationManager lm;
  Location location;


 int tipo_mapa = 0 ;



  double longitude = 0;
  double latitude = 0;

  public MapFragment() {

  }


  public static String Ciudad = "" ;


  public static double latitud_a = 0;
  public static double longitud_a =0;




  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    final View v = inflater.inflate(R.layout.fragment_map, container, false);

    SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);


    mapFragment.getMapAsync(this);

    Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
    String[] letra = {"1) Japon","2) Alemania","3) Italia","4) Francia"};


    ArrayAdapter adapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_dropdown_item, letra);
    spinner.setAdapter(adapter);

    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item;
        item = (String) parent.getItemAtPosition(position);
        if(item.equals("1) Japon")){
          latitude = 35.680513;
          longitude = 139.769051;


          tipo_mapa = 4 ;
          Ciudad = "Japon" ;

          onMapReady(gMap);
        }
        if(item.equals("2) Alemania")){
          latitude = 52.516934;
          longitude = 13.403190;

          tipo_mapa = 2 ;

          Ciudad = "Alemania" ;

          onMapReady(gMap);
        }
        if(item.equals("3) Italia")){
          latitude = 41.902609;
          longitude = 12.494847;

          tipo_mapa = 2 ;

          Ciudad = "Italia" ;

          onMapReady(gMap);
        }
        if(item.equals("4) Francia")){
          latitude = 48.843489;
          longitude = 2.355331;


          tipo_mapa = 3 ;

          Ciudad = "Francia" ;

          onMapReady(gMap);
        }

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    return v;
  }



  @Override
  public void onMapReady(GoogleMap googleMap) {
    gMap = googleMap;

    gMap.setMapType(mMapTypes[tipo_mapa]);

    lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

    if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
    }
    /*lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,  this);
    location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

    longitude = location.getLongitude();
    latitude = location.getLatitude();*/

    LatLng aqui = new LatLng(latitude, longitude);

    CameraPosition cameraPosition = new CameraPosition.Builder()
            .target(aqui)
            .zoom(14)//zoom
            .bearing(30)//inclinacion
            .build();

    gMap.addMarker(new MarkerOptions().position(aqui).title("Mi ubicación"));
    gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    Toast.makeText(getContext(),"Nos fuimos a " + Ciudad,
            Toast.LENGTH_SHORT).show();

    try {

      Location location = new Location("localizacion 1");
      location.setLatitude(latitude);  //latitud
      location.setLongitude(longitude); //longitud
      Location location2 = new Location("localizacion 2");
      location2.setLatitude(latitud_a);  //latitud
      location2.setLongitude(longitud_a); //longitud
      double distance = location.distanceTo(location2);

      Toast.makeText(getContext(),"Existe una distancia de :  " + Double.toString(distance) + " de distancia ",
              Toast.LENGTH_SHORT).show();







    }
    catch(Exception e) {
      //  Block of code to handle errors
    }




  }


  @Override
  public void onLocationChanged(Location location) {
    if (location != null) {
      Log.v("Location Changed", latitude + " and " + longitude);
      lm.removeUpdates(this);
    }
  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {
    String A;
  }

  @Override
  public void onProviderEnabled(String provider) {
    String A;
  }

  @Override
  public void onProviderDisabled(String provider) {
    String A;
  }


  private int mMapTypes[] = {
          GoogleMap.MAP_TYPE_NONE,
          GoogleMap.MAP_TYPE_NORMAL,
          GoogleMap.MAP_TYPE_SATELLITE,
          GoogleMap.MAP_TYPE_HYBRID,
          GoogleMap.MAP_TYPE_TERRAIN
  };




}
