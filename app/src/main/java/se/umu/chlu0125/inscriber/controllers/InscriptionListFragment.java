package se.umu.chlu0125.inscriber.controllers;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import se.umu.chlu0125.inscriber.R;
import se.umu.chlu0125.inscriber.models.Inscription;
import se.umu.chlu0125.inscriber.models.User;


/**
 * @author: Christoffer Lundstrom
 * @date: 22/07/2019
 * <p>
 * Description: Handles RecyclerView of Users Inscriptions.
 */
public class InscriptionListFragment extends Fragment {

    private static final String TAG = "InscriptionListFragment";
    private static final String KEY_RECYCLER_STATE = "KEY_RECYCLER_STATE";
    private static InscriptionListFragment mFragment;
    private RecyclerView mInscriptionRecyclerView;
    private InscriptionAdapter mAdapter;
    private List<Inscription> mDisplayCollection;
    private LinearLayoutManager mLayoutManager;
    private Bundle mBundleRecyclerViewState;
    private TextView noDataView;

    public static InscriptionListFragment getInstance() {
        if (mFragment == null) {
            mFragment = new InscriptionListFragment();
            return mFragment;
        } else {
            return mFragment;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDisplayCollection = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.inscription_recyclerview, container, false);

        noDataView = (TextView) view.findViewById(R.id.empty_view);
        mInscriptionRecyclerView = (RecyclerView) view.findViewById(R.id.inscription_recycler);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mInscriptionRecyclerView.setLayoutManager(mLayoutManager);
        updateUI();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Listen for changes.
        InscriptionService.getInstance().getUserDataTask().addSnapshotListener( ((snapshot, e) -> {
            if(e != null){
                Log.e(TAG, "onCreateView: " + e.getMessage());
            }

            if(snapshot != null && snapshot.exists()){
                mDisplayCollection = snapshot.toObject(User.class).getCollection();
                updateAdapter(mDisplayCollection);
                setViewIfEmpty(mDisplayCollection);
            }
            else{
                setViewIfEmpty(mDisplayCollection);
            }
        }));



    }

    @Override
    public void onPause() {
        super.onPause();
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = mInscriptionRecyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            mInscriptionRecyclerView.getLayoutManager().onRestoreInstanceState(listState);
            mInscriptionRecyclerView.setAdapter(mAdapter);
        }
    }

    /**
     * Internal class responsible for handling clicks and inflation of each Inscription in the RecyclerView.
     */
    private class InscriptionItem extends RecyclerView.ViewHolder implements View.OnClickListener, OnMapReadyCallback {

        private Inscription mInscription;
        private TextView mDate;
        private TextView mMessage;
        private MapView mLocationPreview;
        GoogleMap map;

        public InscriptionItem(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.inscription_item, parent, false));

            mLocationPreview = itemView.findViewById(R.id.inscription_item_map);
            mDate = itemView.findViewById(R.id.inscription_item_date);
            mMessage = itemView.findViewById(R.id.inscription_item_message);

            if(mLocationPreview != null){
                mLocationPreview.onCreate(null);
                mLocationPreview.onResume();
                mLocationPreview.getMapAsync(this);
            }
        }

        /**
         * Binds data to appropriate UI views of the Recycler-Items.
         * @param inscription
         */
        public void bind(Inscription inscription) {
            mInscription = inscription;
            mMessage.setText(inscription.getMessage());
            Date date = inscription.getDate().toDate();
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            mDate.setText(formatter.format(date));
        }

        @Override
        public void onClick(View v) {
        }

        /**
         * Sets the Preview-Map location of each Inscription in the Adapter-class.
         * @param googleMap A google Lite-mode map object.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            MapsInitializer.initialize(getActivity().getApplicationContext());
            map = googleMap;
            setMapLocation();
        }

        /**
         * Sets the marker and Location of each saved Inscription in "Your Inscription" view.
         */
        private void setMapLocation() {
            if (map == null) return;

            if (mInscription == null) return;

            // Add a marker for this item and set the camera
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                    mInscription.getLocation().getLatitude(), mInscription.getLocation().getLongitude()
            ), 15f));

            map.addMarker(new MarkerOptions().position(new LatLng(
                    mInscription.getLocation().getLatitude(), mInscription.getLocation().getLongitude()
            )));

        }
    }


    /**
     * Internal class which implements custom mutable Recycler list.
     */
    private class InscriptionAdapter extends RecyclerView.Adapter<InscriptionItem> {

        private List<Inscription> mInscriptions;


        public InscriptionAdapter(List<Inscription> list) {
            mInscriptions = list;
        }


        @NonNull
        @Override
        public InscriptionItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new InscriptionItem(layoutInflater, parent);
        }


        /**
         * Bind Inscription to the placeholder in the Recycler List.
         */
        @Override
        public void onBindViewHolder(@NonNull InscriptionItem holder, int position) {
            Inscription inscription = mInscriptions.get(position);
            holder.bind(inscription);
        }

        @Override
        public int getItemCount() {
            return mInscriptions.size();
        }

        /**
         * Clears list and replaces all instances with a new list.
         * @param list A list of Inscriptions
         */
        public void updateData(List<Inscription> list) {
            mInscriptions.clear();
            mInscriptions.addAll(list);
        }
    }

    /**
     * Update UI function which prioritizes global data over locally stored.
     */
    public void updateUI() {
        if (mAdapter == null) {
            mAdapter = new InscriptionAdapter(mDisplayCollection);
            mInscriptionRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
        Log.d(TAG, "updateUI: UI updated.");
    }

    /**
     * Sends changes to InscriptionAdapter and notifies a DataSetChange.
     * @param list List of Inscriptions.
     */
    public void updateAdapter(List<Inscription> list) {
        mAdapter.updateData(list);
        mAdapter.notifyDataSetChanged();
        Log.d(TAG, "updateAdapter: Success.");
    }

    /**
     * Sets a placeholder view if no Inscriptions are available.
     */
    private void setViewIfEmpty(List list){
        if (list.isEmpty() || list == null) {
            noDataView.setVisibility(View.VISIBLE);
        }
        else{
            noDataView.setVisibility(View.GONE);
        }
    }
}


