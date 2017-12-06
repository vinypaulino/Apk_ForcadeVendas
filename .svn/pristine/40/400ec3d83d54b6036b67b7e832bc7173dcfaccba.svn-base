package com.najasoftware.fdv.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.najasoftware.fdv.FdvApplication;
import com.najasoftware.fdv.R;
import com.najasoftware.fdv.model.Cliente;
import com.najasoftware.fdv.util.PermissionUtils;

public class ClienteLocationActivity extends BaseActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private static final String TAG = "forcadevenda";
    protected GoogleMap map;
    private SupportMapFragment mapFragment;
    private GoogleApiClient mGoogleApiClient;
    private FdvApplication app;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_location);
        setUpToolBar();
        getSupportActionBar().setTitle("Localização do Cliente");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        app = FdvApplication.getInstance();

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Configura o objeto GoogleApiClient
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Solicita as permissões
        String[] permissoes = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
        };
        PermissionUtils.validate(this, 0, permissoes);

        findViewById(R.id.fab_add_location_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicou em FAB: ");
                Location l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                Log.d(TAG, "lastLocation: " + l);
                // Atualiza a localização do mapa
                setMapLocation(l);
            }
        });

        cliente = app.getCliente();

        if (cliente == null) {
            cliente = new Cliente();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                // Alguma permissão foi negada, agora é com você :-)
                alertAndFinish();
                return;
            }
        }

    }

    private void alertAndFinish() {
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name).setMessage("Para utilizar este aplicativo, você precisa aceitar as permissões.");
            // Add the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
            android.support.v7.app.AlertDialog dialog = builder.create();
            dialog.show();

        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        Log.d(TAG, "onMapReady: " + map);
        this.map = map;

        // Configura o tipo do mapa
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Conecta no Google Play Services
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        // Para o GPS
        stopLocationUpdates();
        // Desconecta
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        // Inicia o GPS
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        toast("Conexão interrompida.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        toast("Erro ao conectar: " + connectionResult);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_salvar_location:
                if (cliente.getLatitude() == 0) {
                    toast("Click no icone de localização na parte inferior da tela");
                    return false;
                } else {
                    finish();
                    toast("Localização do cliente salvo com sucesso!");
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // Atualiza a coordenada do mapa
    private void setMapLocation(Location l) {
        if (map != null && l != null) {
            LatLng latLng = new LatLng(l.getLatitude(), l.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 15);
            map.animateCamera(update);

            salvarLatLng(l);

            Log.d(TAG, "setMapLocation: " + l);
            TextView text = (TextView) findViewById(R.id.text);
            text.setText(String.format("Lat/Lnt %f/%f, provider: %s", l.getLatitude(), l.getLongitude(), l.getProvider()));

            // Desenha uma bolinha vermelha
            CircleOptions circle = new CircleOptions().center(latLng);
            circle.fillColor(Color.RED);
            circle.radius(25); // Em metros
            map.clear();
            map.addCircle(circle);
        }
    }

    private void salvarLatLng(Location l) {
        cliente.setLatitude(l.getLatitude());
        cliente.setLongitude(l.getLongitude());
        app.setCliente(cliente);
    }

    protected void startLocationUpdates() {
        Log.d(TAG, "startLocationUpdates()");
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        return;
    }

    protected void stopLocationUpdates() {
        Log.d(TAG, "stopLocationUpdates()");
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged(): " + location);
        // Nova localizaçao: atualiza a interface
        setMapLocation(location);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_cliente_location, menu);
        return true;
    }

}
