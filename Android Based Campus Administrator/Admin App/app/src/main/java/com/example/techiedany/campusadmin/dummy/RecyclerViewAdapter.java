package com.example.techiedany.campusadmin.dummy;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.techiedany.campusadmin.R;

import java.util.Collections;
import java.util.List;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    List<StudentDetails> MainImageUploadInfoList;
    private String uname;
    private FirebaseAuth auth;


    public RecyclerViewAdapter(Context context, List<StudentDetails> TempList) {

        this.MainImageUploadInfoList = TempList;


        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            uname = user.getEmail().split("@")[0];
            //Toast.makeText(getApplicationContext(),""+uname,Toast.LENGTH_LONG).show();
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        StudentDetails studentDetails = MainImageUploadInfoList.get(position);

        holder.StudentNameTextView.setText(studentDetails.getUname());

        holder.StudentNumberTextView.setText(studentDetails.getStudentPhoneNumber());//description

        holder.ShowDate.setText(studentDetails.getDate());//date

        holder.ShowHeading.setText(studentDetails.getStudentName());//heading

        //holder.imageView3.setImageResource(studentDetails.getImg());//heading

        //Glide.with(context).load(studentDetails.getImg()).into(holder.imageView3);
        Picasso.with(context).load(studentDetails.getImg()).into(holder.imageView3);

    }

    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView StudentNameTextView;
        public TextView StudentNumberTextView;
        public TextView ShowDate;
        public TextView ShowHeading;
        public ImageView imageView3;

        public ViewHolder(View itemView) {

            super(itemView);

            StudentNameTextView = (TextView) itemView.findViewById(R.id.ShowStudentNameTextView);

            StudentNumberTextView = (TextView) itemView.findViewById(R.id.ShowStudentNumberTextView);

            ShowDate = (TextView) itemView.findViewById(R.id.ShowDate);

            ShowHeading = (TextView) itemView.findViewById(R.id.ShowHeading);

            imageView3 = (ImageView) itemView.findViewById(R.id.imageView3);
        }
    }
}