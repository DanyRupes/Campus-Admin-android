package admin.example.com.campus;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.ArrayList;

import admin.example.com.nav.R;

public class EditProfile extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "EditProfile" ;
    EditText edt1, edt2, edt3, edt4, edt5;
    ImageView propic;
    DatabaseReference ref;
    String uid, uname;
    ArrayList<Retrive> retieves = new ArrayList<>();
    private Uri filePath;
    private Uri mCropImageUri;
    private StorageReference storageReference;
    private String url;
    private Context context = EditProfile.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
            uname = user.getEmail();
        }
        final String path = "Users" + "/" + uname.split("@")[0];
        ref = FirebaseDatabase.getInstance().getReference(path);
        storageReference = FirebaseStorage.getInstance().getReference();

        edt1 = (EditText) findViewById(R.id.et1);
        edt2 = (EditText) findViewById(R.id.et2);
        edt3 = (EditText) findViewById(R.id.et3);
        edt4 = (EditText) findViewById(R.id.et4);
        edt5 = (EditText) findViewById(R.id.et5);
        propic= (ImageView) findViewById(R.id.circleImageView);

        propic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelectImageClick(view);
            }
        });

        ref.child("Details").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Retrive value = dataSnapshot.getValue(Retrive.class);
                    //   value.setAvatar(ds.getValue(Retrive.class).getAvatar().toString());//set the name
                    retieves.add(value);

                    Log.d(TAG, "Value is: " + value.getAvatar());
                    //toastMessage(retieves.get(0).getAvatar());
                    edt1.setText(retieves.get(0).getAvatar());
                    edt2.setText(retieves.get(0).getDept());
                    edt3.setText(retieves.get(0).getYear());
                    edt4.setText(retieves.get(0).getCGPA());
                    edt5.setText(retieves.get(0).getContact());
                    //toastMessage(retieves.get(0).getDept());
                     Picasso.with(context).load(retieves.get(0).getProPic()).into(propic);
                     url=retieves.get(0).getProPic();
                    // Toast.makeText(getApplicationContext(),retieves.get(0).getProPic(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        findViewById(R.id.okbtn).setOnClickListener(this);
        findViewById(R.id.cancelbtn).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.okbtn:

                uploadFile();
                startActivity(new Intent(this,DetailsActivity.class));
                finish();

                break;
            case R.id.cancelbtn:

                startActivity(new Intent(this,DetailsActivity.class));
                Toast.makeText(getApplicationContext(),"Cancelled",Toast.LENGTH_LONG).show();
                finish();

                break;
        }
    }

    private void uploadFile() {
        Retrive retrive = new Retrive();

        retrive.setAvatar( edt1.getText().toString());
        retrive.setDept( edt2.getText().toString());
        retrive.setYear( edt3.getText().toString());
        retrive.setCGPA( edt4.getText().toString());
        retrive.setContact( edt5.getText().toString());


        retrive.RetriveProPic(url);
        //toastMessage("Image Url ............................................"+url);
      //  retrive.setProPic("");
//



        //Log.d(TAG, "uploadFile: ");
        ref.child("Details").setValue(retrive);


        //toastMessage(retrive.getAvatar());//retrive.getYear();
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

                            url = taskSnapshot.getDownloadUrl().toString();
                            //and displaying a success toast
                            //Toast.makeText(getApplicationContext(), " " + taskSnapshot.getDownloadUrl().toString(), Toast.LENGTH_LONG).show();
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
            Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_LONG).show();
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
