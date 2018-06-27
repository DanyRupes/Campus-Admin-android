package admin.example.com.campus;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.Inflater;

import admin.example.com.library.VegaLayoutManager;
import admin.example.com.nav.R;


public class RecView extends Fragment {

    Fragment frg;

    DatabaseReference databaseReference;

    private final static String FRAGMENT_TAG = "FRAGMENTB_TAG";

    ProgressDialog progressDialog;

    List<StudentDetails> list = new ArrayList<>();

    private SwipeRefreshLayout swipeRefreshLayout;

    RecyclerView recyclerView;

    RecyclerView.Adapter adapter ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
         View view =  inflater.inflate(R.layout.activity_show_student_details, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                LayoutInflater inflater = getLayoutInflater();
                inflater.inflate(R.layout.activity_show_student_details,null);
                swipeRefreshLayout.setRefreshing(false);
                RecView fragment2 = new RecView();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(getId(), fragment2);
                fragmentTransaction.commit();
            }
        });


        //FragmentTransaction ft = getFragmentManager().beginTransaction();
        //ft.detach(this).attach(this).commit();

        View positionView = view.findViewById(R.id.main_position_view);
        boolean immerse = Utils.immerseStatusBar(getActivity());
        boolean darkMode = Utils.setDarkMode(getActivity());
        if (immerse) {
            ViewGroup.LayoutParams lp = positionView.getLayoutParams();
            lp.height = Utils.getStatusBarHeight(getContext());
            positionView.setLayoutParams(lp);
            if (!darkMode) {
                positionView.setBackgroundColor(Color.BLACK);
            }
        } else {
            positionView.setVisibility(View.GONE);
        }


        //recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        //recyclerView.setLayoutManager(new VegaLayoutManager());




        recyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        //mLayoutManager.setReverseLayout(true);
        //mLayoutManager.setStackFromEnd(true);

        progressDialog = new ProgressDialog(getContext());

        progressDialog.setMessage("Loading Data for You....Please Wait !");

        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference(Post.Database_Path);



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    StudentDetails studentDetails = dataSnapshot.getValue(StudentDetails.class);

                    list.add(studentDetails);
                }
                List<StudentDetails> revlist = new ArrayList<>(list);

                 Collections.reverse(revlist);
//


                adapter = new RecyclerViewAdapter(getContext(), revlist);

                recyclerView.setAdapter(adapter);

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();

            }
        });

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("                H O M E");
    }

}
