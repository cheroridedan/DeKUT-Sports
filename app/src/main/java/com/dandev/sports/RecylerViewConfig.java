package com.dandev.sports;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.checkerframework.checker.units.qual.C;

import java.util.List;

public class RecylerViewConfig {

    private Context mContext;
    private EnrolledAdapter mEnrolledAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<EnrolledUsers> enrolledUsers, List<String> keys){

        mContext = context;
        mEnrolledAdapter = new EnrolledAdapter(enrolledUsers, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mEnrolledAdapter);

    }

    class EnrolledView extends RecyclerView.ViewHolder {

        private TextView displayRegNo, displayPhoneNumber, displayGender, displayGame, displayCategory;

        private String key;

        public EnrolledView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.enrolled_list, parent, false));

            displayRegNo = (TextView) itemView.findViewById(R.id.displayRegNo);
            displayPhoneNumber = (TextView) itemView.findViewById(R.id.displayPhoneNumber);
            displayGender = (TextView) itemView.findViewById(R.id.displayGender);
            displayGame = (TextView) itemView.findViewById(R.id.displayGame);
            displayCategory = (TextView) itemView.findViewById(R.id.displayCategory);

        }
        public void bind(EnrolledUsers enrolledUsers, String key){

            displayRegNo.setText(enrolledUsers.getRegNo());
            displayPhoneNumber.setText(enrolledUsers.getPhoneNumber());
            displayGender.setText(enrolledUsers.getGender());
            displayGame.setText(enrolledUsers.getGame());
            displayCategory.setText(enrolledUsers.getCategory());
            this.key = key;
        }

    }
    class EnrolledAdapter extends RecyclerView.Adapter<EnrolledView>{
        private List<EnrolledUsers> mEnrolledList;
        private List<String> mKeys;

        public EnrolledAdapter(List<EnrolledUsers> mEnrolledList, List<String> mKeys) {
            this.mEnrolledList = mEnrolledList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public EnrolledView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new EnrolledView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull EnrolledView holder, int position) {

            holder.bind(mEnrolledList.get(position), mKeys.get(position));

        }

        @Override
        public int getItemCount() {
            return mEnrolledList.size();
        }
    }
}
