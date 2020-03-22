package com.example.reto1;

import androidx.core.app.ActivityCompat;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private Marker ubicacionActual;
    private TextView site;
    private List<Marker> marcadores;
    private Polyline seguimiento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        marcadores = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        }, 11);

        site = findViewById(R.id.siteTxt);
        site.setTextColor(Color.RED);

    }
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng base = new LatLng(3.451647, -76.531982);
        ubicacionActual = mMap.addMarker(new MarkerOptions().position(base).title("Usted esta aca")
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_person_green_24dp)));


        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);

        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerClickListener(this);
        List<PatternItem> pattern = Arrays.<PatternItem>asList(
                new Dot(), new Gap(10));
        seguimiento =mMap.addPolyline(new PolylineOptions().color(Color.BLUE).clickable(false).pattern(pattern).visible(true));
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorRes){
        Drawable vectorDrawable= ContextCompat.getDrawable(context,vectorRes);
        vectorDrawable.setBounds(0,0,vectorDrawable.getIntrinsicWidth(),vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap= Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),vectorDrawable.getMinimumHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas= new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);

    }



    @Override
    public void onLocationChanged(Location location) {
        LatLng nuevo = new LatLng(location.getLatitude(), location.getLongitude());
        ubicacionActual.setPosition(nuevo);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(nuevo, 15));
        nuevoRastro(nuevo);

    }



    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    @Override
    public void onMapLongClick(final LatLng latLng) {

        LayoutInflater layoutInflater = getLayoutInflater();

        final View view = layoutInflater.inflate(R.layout.fragment, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage("Nombre del nuevo marcador");
        alertDialog.setCancelable(false);

        final EditText editText = (EditText) view.findViewById(R.id.markerName);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                MarkerOptions mo = new MarkerOptions().position(latLng).title(editText.getText().toString());

                Marker m = mMap.addMarker(mo);
                m.setIcon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_add_location_red_24dp));
                marcadores.add(m);
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }




    @Override
    public boolean onMarkerClick(final Marker marker) {
        if (!marker.equals(ubicacionActual)) {
            LayoutInflater layoutInflater = getLayoutInflater();

            final View view = layoutInflater.inflate(R.layout.fragment, null);
            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setMessage("Renombrar marcador");
            alertDialog.setCancelable(true);

            final EditText editText = (EditText) view.findViewById(R.id.markerName);

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    marker.setTitle(editText.getText().toString());
                }
            });

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.setView(view);
            alertDialog.show();

            Toast.makeText(this, "La distancia al marcador " + marker.getTitle() + " es de " + HaversineDistance(marker.getPosition().latitude,
                    ubicacionActual.getPosition().latitude, marker.getPosition().longitude, ubicacionActual.getPosition().longitude), Toast.LENGTH_SHORT).show();
        } else {
            Geocoder geo = new Geocoder(this, Locale.getDefault());
            String dir = "";
            try {
                dir = geo.getFromLocation(ubicacionActual.getPosition().latitude, ubicacionActual.getPosition().longitude, 1).get(0).getAddressLine(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(this, "la direccion actual es " + dir, Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public static double HaversineDistance(double lat1, double lat2, double lon1,
                                           double lon2) {

        double earthRadius = 6371; // km

        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double difLon = lon2-lon1;
        double difLat = lat2-lat1;

        double sinLat = Math.sin(difLat / 2);
        double sinLon = Math.sin(difLon / 2);

        double a = (sinLat * sinLat) + Math.cos(lat1)*Math.cos(lat2)*(sinLon*sinLon);
        double c = 2 * Math.asin (Math.min(1.0, Math.sqrt(a)));

        double distanceInMeters = earthRadius * c * 1000;

        return (int)distanceInMeters;
    }

    public void nuevoRastro(LatLng nuevo){

        List<LatLng> rastro = seguimiento.getPoints();
        rastro.add(nuevo);
        seguimiento.setPoints(rastro);
        actualizarTextoDistancias();


    }
    public void actualizarTextoDistancias() {
        double min = Double.MAX_VALUE;
        String nom = "";
        Marker mark;
        for(int i=0;i<marcadores.size();i++){
            mark=marcadores.get(i);
            double distance = HaversineDistance(mark.getPosition().latitude,
                    ubicacionActual.getPosition().latitude,
                    mark.getPosition().longitude,
                    ubicacionActual.getPosition().longitude);
            if (distance < min) {
                min = distance;
                nom = mark.getTitle();
            }

        }
        if (min == Double.MAX_VALUE) {
            site.setText("");
        } else if (min <= 50) {
            site.setText("Usted se encuentra en " + nom);
        } else {
            site.setText("El marcador mas cercano es a  " + nom + " y se encuentra a " + min + " metros");
        }

    }
}
