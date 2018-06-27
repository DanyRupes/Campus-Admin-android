package admin.example.com.campus;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import admin.example.com.nav.R;

public class DetailsActivity extends AppCompatActivity{

    private static final int PICK_IMAGE_REQUEST = 234;
    private static final String TAG = "DetailsAct";
    private Uri filePath;
    private ImageView propic;
    private TextView tvn1,tvn2,tvn3,tvn4,tvn5,tvn6;
    private String uid,uname;
    private ImageView iv;
    private Button img,pdf,prof;
    private String imgURL  = "";
    public DatabaseReference ref;
    private StorageReference storageReference;
    private Uri mCropImageUri;
    private Context context = DetailsActivity.this;

    ArrayList<Retrive> retieves = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Replace with your own action",Toast.LENGTH_LONG).show();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(new Intent(DetailsActivity.this,EditProfile.class));
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        storageReference = FirebaseStorage.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            uid = user.getUid();
            uname = user.getEmail();
       }
       //Toast.makeText(getApplicationContext(),""+uname,Toast.LENGTH_LONG).show();
        final String path = "Users" + "/" + uname.split("@")[0] ;
        ref = FirebaseDatabase.getInstance().getReference(path);

        //toastMessage(ref.toString());
        img = (Button) findViewById(R.id.btnres);
        pdf = (Button) findViewById(R.id.btnpdf);
        prof = (Button) findViewById(R.id.edprof);
        propic= (ImageView) findViewById(R.id.circleImageView);
       // propic.setI
        //Glide.with(this).load("http://i.imgur.com/DvpvklR.png").into(propic);
        Picasso.with(this).load("http://i.imgur.com/DvpvklR.png").into(propic);

       // Toast.makeText(getApplicationContext(),new Retrive().getProPic(),Toast.LENGTH_LONG).show();
        tvn1 = findViewById(R.id.textView3);
        tvn2 = findViewById(R.id.textView6);
        tvn3 = findViewById(R.id.textView9);
        tvn4 = findViewById(R.id.textView14);
        tvn5 = findViewById(R.id.textView17);
        tvn6 = findViewById(R.id.textView19);

        tvn2.setText(uname);

        propic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelectImageClick(view);
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailsActivity.this, PicUpload.class));

            }
        });

        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailsActivity.this, FileUpload.class));
            }
        });

        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailsActivity.this,EditProfile.class));
                finish();
            }
        });
/*
        // Read from the database
        ref.child("ProPic").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Retrive value = dataSnapshot.getValue(Retrive.class);
                    retieves.add(value);
                    Picasso.with(context).load(retieves.get(0).getProPic()).into(propic);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

*/
        // Read from the database
        ref.child("Details").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Retrive value = dataSnapshot.getValue(Retrive.class);
                 //   value.setAvatar(ds.getValue(Retrive.class).getAvatar().toString());//set the name
                    retieves.add(value);

                    //Log.d(TAG, "Value is: " + value.getAvatar());
                    //toastMessage(retieves.get(0).getAvatar());
                    tvn1.setText(retieves.get(0).getAvatar());
                    tvn3.setText(retieves.get(0).getDept());
                    tvn4.setText(retieves.get(0).getYear());
                    tvn5.setText(retieves.get(0).getCGPA());
                    tvn6.setText(retieves.get(0).getContact());
                    //toastMessage(retieves.get(0).getDept());
                    if(retieves.get(0).getProPic() != null)
                         Picasso.with(context).load(retieves.get(0).getProPic()).into(propic);
                    else
                         Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(propic);
                  // Toast.makeText(getApplicationContext(),retieves.get(0).getProPic(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    public void onSelectImageClick(View view) {
        CropImage.startPickImageActivity(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // required permissions granted, start crop image activity
            startCropImageActivity(mCropImageUri);
        } else {
            Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Start crop image activity for the given image.
     */
    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }


    //handling the image chooser activity result
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
           // propic.setImageDrawable(null); //otherwise the imageView does not refresh after the first try
            //propic.setImageURI(imageUri);
            filePath = imageUri;
            uploadPic();
            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
               // ((ImageButton) findViewById(R.id.quick_start_cropped_image)).setImageURI(result.getUri());
                propic.setImageDrawable(null); //otherwise the imageView does not refresh after the first try
                propic.setImageURI(result.getUri());


                Toast.makeText(this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }




    private void uploadPic() {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            StorageReference sRef = storageReference.child(Constants.STORAGE_PATH_IMAGES + System.currentTimeMillis() + ".jpg");

            //StorageReference riversRef = storageReference.child("images/pic.jpg");
            sRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Image Uploaded ", Toast.LENGTH_LONG).show();
                            ref.child("Details").child("ProPic").setValue(taskSnapshot.getDownloadUrl().toString());

                            Retrive retrive = new Retrive();
                            retrive.RetriveProPic(taskSnapshot.getDownloadUrl().toString());

                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), " "+taskSnapshot.getDownloadUrl().toString() , Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

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
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
            Toast.makeText(getApplicationContext(),"Error Occured",Toast.LENGTH_LONG).show();
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
