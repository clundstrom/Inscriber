package se.umu.chlu0125.inscriber.controllers;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

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
    private static final int REQUEST_CODE = 9000;

    private MapView mapView;
    private GoogleMap mMap;
    private boolean mLocationPermissionGranted;
    private Button mInscribe;


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

        mInscribe.setOnClickListener( (click) -> {

            DialogFragment dialog = AddDialogFragment.newInstance();
            dialog.show(getFragmentManager(), null);
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION };

        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            Log.d(TAG, "onMapReady: Permission granted.");
        } else {
            Log.d(TAG, "onMapReady: Location permission denied. Requesting..");
            ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_CODE);
        }
        mMap.setMyLocationEnabled(true);

    }
}
