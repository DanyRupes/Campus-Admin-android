package com.example.techiedany.campusadmin.dummy;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techiedany.campusadmin.R;
import com.example.techiedany.campusadmin.dummy.library.VegaLayoutManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nostra13.universalimageloader.utils.L;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ShowStudentDetailsActivity extends AppCompatActivity  {


    DatabaseReference databaseReference;
    public static final String Database_Path = "Student_Details_Database";
    ProgressDialog progressDialog;
    String NameHolder, NumberHolder;
    List<StudentDetails> list = new ArrayList<>();

    RecyclerView recyclerView;

    RecyclerView.Adapter adapter ;
    private ImageButton buttonChoose;

    private StorageReference storageReference;
    private static final int PICK_IMAGE_REQUEST = 234;

    private ImageView imageView;

    private String uname;
    private Uri filePath;
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_student_details);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        //today
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new VegaLayoutManager());






        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ShowStudentDetailsActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        progressDialog = new ProgressDialog(ShowStudentDetailsActivity.this);

        progressDialog.setMessage("Loading Data from Firebase Database");

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
    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void callPost(View view) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.activity_post);
        dialog.setTitle("Post Your Feeds");
        Button SubmitButton ;

        Button DisplayButton;

        final EditText NameEditText, PhoneNumberEditText;

        storageReference = FirebaseStorage.getInstance().getReference("FeedsImages");

        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);
        buttonChoose = (ImageButton) dialog.findViewById(R.id.imageButton);

        imageView = (ImageView) dialog.findViewById(R.id.imageView);
        SubmitButton = (Button)dialog.findViewById(R.id.submit);

        NameEditText = (EditText)dialog.findViewById(R.id.name);

        PhoneNumberEditText = (EditText)dialog.findViewById(R.id.phone_number);

        DisplayButton = (Button)dialog.findViewById(R.id.DisplayButton);
        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            uname = user.getEmail().split("@")[0];
            //Toast.makeText(getApplicationContext(),""+uname,Toast.LENGTH_LONG).show();
        }


        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final StudentDetails studentDetails = new StudentDetails();

                //GetDataFromEditText();
                NameHolder = NameEditText.getText().toString().trim();

                NumberHolder = PhoneNumberEditText.getText().toString().trim();

                //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();

                String dates =  DateFormat.getDateTimeInstance().format(date);
                // set to firebase
                studentDetails.setStudentName(NameHolder);
                studentDetails.setStudentPhoneNumber(NumberHolder);
                studentDetails.setDate(dates.toString());
                studentDetails.setUname(uname);
                if (filePath != null) {

                    StorageReference sRef = storageReference.child("FeedsImages" + System.currentTimeMillis() + ".jpg");

                    //StorageReference riversRef = storageReference.child("images/pic.jpg");
                    sRef.putFile(filePath)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    //if the upload is successfull
                                    //hiding the progress dialog
                                    //progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Image Uploaded ", Toast.LENGTH_LONG).show();
                                    studentDetails.setImg(taskSnapshot.getDownloadUrl().toString());
                                    //and displaying a success toast
                                    Toast.makeText(getApplicationContext(), " "+taskSnapshot.getDownloadUrl().toString() , Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    //if the upload is not successfull
                                    //hiding the progress dialog
                                    // progressDialog.dismiss();

                                    //and displaying error message
                                    Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    //calculating progress percentage
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                                    //displaying percentage in progress dialog
                                    //progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                                }
                            });
                }
                // Getting the ID from firebase database.
                String StudentRecordIDFromServer = databaseReference.push().getKey();


                // Adding the both name and number values using student details class object using ID.
                databaseReference.child(StudentRecordIDFromServer).setValue(studentDetails);

                // Showing Toast message after successfully data submit.
                Toast.makeText(ShowStudentDetailsActivity.this,"Data Inserted Successfully into Firebase Database", Toast.LENGTH_LONG).show();

                startActivity(new Intent(ShowStudentDetailsActivity.this,ShowStudentDetailsActivity.class));

                dialog.dismiss();
            }
        });

        DisplayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
/*
        WindowManager.LayoutParams lp = window.getAttributes();
        window  .setGravity(Gravity.LEFT | Gravity.TOP);

        lp.x = 500; // The new position of the X coordinates
        lp.y = 500; // The new position of the Y coordinates
        lp.width = 1000; // Width
        lp.height = 1000; // Height
        lp.alpha = 0.7f; // Transparency

        window.setAttributes(lp);
*/

   }
}
