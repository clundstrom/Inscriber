package se.umu.chlu0125.inscriber.controllers;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import se.umu.chlu0125.inscriber.R;
import se.umu.chlu0125.inscriber.models.Inscription;


/**
 * @author: Christoffer Lundstrom
 * @date: 22/07/2019
 * <p>
 * Description: Handles RecyclerView of Users Inscriptions.
 */
public class InscriptionListFragment extends Fragment {

    private static final String TAG = "InscriptionListFragment";
    private static InscriptionListFragment mFragment;
    private RecyclerView mInscriptionRecyclerView;
    private InscriptionAdapter mAdapter;
    private List<Inscription> mDisplayCollection;
    private InscriptionService mService;

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
        mService = InscriptionService.getInstance();

        // Fetch data
        mDisplayCollection = mService.getInstance().getUserData(getActivity()).getCollection();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.inscription_recyclerview, container, false);

        mInscriptionRecyclerView = (RecyclerView) view.findViewById(R.id.inscription_recycler);
        mInscriptionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }


    /**
     * Internal class responsible for handling clicks and inflation of each Inscription in the RecyclerView.
     */
    private class InscriptionItem extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView mImage;
        private Inscription mInscription;

        private TextView mDate;
        private TextView mMessage;
        // TODO private ImageView mLocationPreview;


        public InscriptionItem(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.inscription_item, parent, false));

            mImage = itemView.findViewById(R.id.inscription_item_image);
            mDate = itemView.findViewById(R.id.inscription_item_date);
            mMessage = itemView.findViewById(R.id.inscription_item_message);
        }

        public void bind(Inscription inscription) {
            mInscription = inscription;

            mMessage.setText(inscription.getMessage());
            mImage.setImageResource(R.drawable.common_google_signin_btn_icon_dark);
            Date date = inscription.getDate().toDate();
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            mDate.setText(formatter.format(date));
        }

        @Override
        public void onClick(View v) {
            // When an item is clicked.
        }
    }


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

        public void updateData(List<Inscription> list){
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
            mDisplayCollection = mService.getInstance().getUserData(getActivity()).getCollection();
            mAdapter.updateData(mDisplayCollection);
            mAdapter.notifyDataSetChanged();
        }
        Log.d(TAG, "updateUI: UI updated.");
    }
}
