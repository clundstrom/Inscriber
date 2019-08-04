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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        ins.setMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        ins.setDate(new Timestamp(new Date()));

        Inscription ind = new Inscription();
        ind.setMessage("Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n" +
                "\n" +
                "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.");
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

        private final ImageView mImage;
        private Inscription mInscription;

       //views

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
