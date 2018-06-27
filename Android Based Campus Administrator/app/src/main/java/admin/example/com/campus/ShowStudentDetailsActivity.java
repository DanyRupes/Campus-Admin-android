package admin.example.com.campus;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import admin.example.com.library.VegaLayoutManager;
import admin.example.com.nav.R;

public class ShowStudentDetailsActivity extends AppCompatActivity {


    DatabaseReference databaseReference;

    ProgressDialog progressDialog;

    List<StudentDetails> list = new ArrayList<>();

    RecyclerView recyclerView;

    RecyclerView.Adapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_student_details);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        View positionView = findViewById(R.id.main_position_view);
        boolean immerse = Utils.immerseStatusBar(this);
        boolean darkMode = Utils.setDarkMode(this);
        if (immerse) {
            ViewGroup.LayoutParams lp = positionView.getLayoutParams();
            lp.height = Utils.getStatusBarHeight(this);
            positionView.setLayoutParams(lp);
            if (!darkMode) {
                positionView.setBackgroundColor(Color.BLACK);
            }
        } else {
            positionView.setVisibility(View.GONE);
        }


        // 2. toolbar
        //Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new VegaLayoutManager());



        recyclerView.setHasFixedSize(true);


        //LinearLayoutManager mLayoutManager = new LinearLayoutManager(ShowStudentDetailsActivity.this);
        //recyclerView.setLayoutManager(mLayoutManager);
        //mLayoutManager.setReverseLayout(true);
        //mLayoutManager.setStackFromEnd(true);


        progressDialog = new ProgressDialog(ShowStudentDetailsActivity.this);

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



                adapter = new RecyclerViewAdapter(ShowStudentDetailsActivity.this, list);

                recyclerView.setAdapter(adapter);

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();

            }
        });

    }



}
