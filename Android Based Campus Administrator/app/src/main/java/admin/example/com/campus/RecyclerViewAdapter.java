package admin.example.com.campus;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import admin.example.com.nav.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    List<StudentDetails> MainImageUploadInfoList;

    public RecyclerViewAdapter(Context context, List<StudentDetails> TempList) {

        this.MainImageUploadInfoList = TempList;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        StudentDetails studentDetails = MainImageUploadInfoList.get(position);

        holder.StudentNameTextView.setText(studentDetails.getUname());

        holder.ShowHeading.setText(studentDetails.getStudentName());

        holder.StudentNumberTextView.setText(studentDetails.getStudentPhoneNumber());

        holder.ShowDate.setText(studentDetails.getDate());

    }

    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView StudentNameTextView;
        public TextView ShowHeading;
        public TextView StudentNumberTextView;
        public TextView ShowDate;

        public ViewHolder(View itemView) {

            super(itemView);

            StudentNameTextView = (TextView) itemView.findViewById(R.id.ShowStudentNameTextView);

            ShowHeading = (TextView) itemView.findViewById(R.id.ShowHeading);

            StudentNumberTextView = (TextView) itemView.findViewById(R.id.ShowStudentNumberTextView);

            ShowDate = (TextView) itemView.findViewById(R.id.ShowDate);
        }
    }
}