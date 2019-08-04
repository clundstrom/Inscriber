package se.umu.chlu0125.inscriber.controllers;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import se.umu.chlu0125.inscriber.R;

/**
 * @author: Christoffer Lundstrom
 * @date: 28/07/2019
 * <p>
 * Description: Represents the map view of the application. Requests permission to use location and
 * controls markers and camera changes of the map.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "MapFragment";
    private static final String CONFIRM_ADD = "ConfirmAdd";
    private static final int REQUEST_PERMISSION = 9000;
    private static final int REQUEST_MARKER_CONFIRM = 9001;
    private static final int DEFAULT_MAP_ZOOM = 5;

    private MapView mapView;
    private GoogleMap mMap;
    private boolean mLocationPermission;
    private Button mInscribe;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location mLocation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        mInscribe = getActivity().findViewById(R.id.map_inscribe);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermission = true;
            Log.d(TAG, "onMapReady: Location permission granted.");
            mMap.setMyLocationEnabled(true);
        } else {
            Log.d(TAG, "onMapReady: Location permission denied. Requesting..");
            ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_PERMISSION);
        }

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), location -> {
                    if (location != null) {
                        mLocation = location;
                        Log.d(TAG, "onMapReady: Location found. " + location.getLatitude() + " " + location.getLongitude());
                        zoomToPosition();
                    }
                });

        mInscribe.setOnClickListener((click) -> {
            if (mLocationPermission != true) {
                Toast successToast = Toast.makeText(getContext(), R.string.location_perm_denied, Toast.LENGTH_LONG);
                successToast.setGravity(Gravity.TOP, 0, 50);
                successToast.show();
            } else if (mLocation == null) {
                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(getActivity(), location -> {
                            if (location != null) {
                                mLocation = location;
                                Log.d(TAG, "onMapReady: Location found. " + location.getLatitude() + " " + location.getLongitude());
                            }
                        });
            } else {
                DialogFragment dialog = AddDialogFragment.newInstance(mLocation);
                dialog.setTargetFragment(this, REQUEST_MARKER_CONFIRM);
                dialog.show(getFragmentManager(), CONFIRM_ADD);
            }
        });
    }

    private void zoomToPosition() {
        if (mLocation != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mLocation.getLatitude(), mLocation.getLongitude())));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(DEFAULT_MAP_ZOOM));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == REQUEST_MARKER_CONFIRM) {
            Log.d(TAG, "onActivityResult: Map marker added.");
            addMapMarker();

        }
    }

    public void addMapMarker() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(mLocation.getLatitude(), mLocation.getLongitude())).title("Test"));
    }

}
