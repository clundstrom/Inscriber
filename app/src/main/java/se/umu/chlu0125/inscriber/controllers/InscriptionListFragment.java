package se.umu.chlu0125.inscriber.controllers;

import android.os.Bundle;
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

import com.google.firebase.Timestamp;

import java.util.ArrayList;
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
    private RecyclerView mInscriptionRecyclerView;
    private InscriptionAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.inscription_recyclerview, container, false);

        mInscriptionRecyclerView = (RecyclerView) view.findViewById(R.id.inscription_recycler);
        mInscriptionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Inscription ins = new Inscription();
        ins.setMessage("Test");
        ins.setDate(new Timestamp(new Date()));

        Inscription ind = new Inscription();
        ind.setMessage("Test2");
        ind.setDate(new Timestamp(new Date()));

        List<Inscription> list = new ArrayList<>();

        list.add(ins);
        list.add(ins);
        list.add(ind);
        list.add(ind);


        mAdapter = new InscriptionAdapter(list); // insert list
        mInscriptionRecyclerView.setAdapter(mAdapter);
        return view;
    }


    /**
     * Internal class responsible for handling clicks and inflation of each Inscription in the RecyclerView.
     */
    private class InscriptionItem extends RecyclerView.ViewHolder implements View.OnClickListener {

       private Inscription mInscription;

       //views

        private TextView mTitle;
        private TextView mDate;
        private TextView mMessage;
        // TODO private ImageView mLocationPreview;


        public InscriptionItem(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.inscription_item, parent, false));

            mTitle = itemView.findViewById(R.id.inscription_item_title);
            mDate = itemView.findViewById(R.id.inscription_item_date);
            mMessage = itemView.findViewById(R.id.inscription_item_message);
        }

        public void bind(Inscription inscription) {
            mInscription = inscription;

            mMessage.setText(inscription.getMessage());
            mDate.setText(inscription.getDate().toString());

            // set attributes of the itemview here.
            // TitleTextView.settext (Inscription.message etc)
        }

        @Override
        public void onClick(View v) {
            // When an item is clicked.
        }
    }



    private class InscriptionAdapter extends RecyclerView.Adapter<InscriptionItem>{

        private List<Inscription> mInscriptions;


        public InscriptionAdapter(List<Inscription> list){
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
    }
}
