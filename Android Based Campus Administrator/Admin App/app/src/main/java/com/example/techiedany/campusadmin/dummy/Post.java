package com.example.techiedany.campusadmin.dummy;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.techiedany.campusadmin.R;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Post extends AppCompatActivity {

    Button SubmitButton ;

    Button DisplayButton;

    private ImageButton buttonChoose;

    private StorageReference storageReference;

    private ImageView imageView;

    private static final int PICK_IMAGE_REQUEST = 234;

    private  EditText NameEditText, PhoneNumberEditText;

    private Uri filePath;

    // Declaring String variables to store name & phone number get from EditText.
    String NameHolder, NumberHolder;

    DatabaseReference databaseReference;

    // Root Database Name for Firebase Database.
    public static final String Database_Path = "Student_Details_Database";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_post);
        Firebase.setAndroidContext(Post.this);
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        storageReference = FirebaseStorage.getInstance().getReference("FeedsImages");

        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

        SubmitButton = (Button)findViewById(R.id.submit);

        NameEditText = (EditText)findViewById(R.id.name);

        PhoneNumberEditText = (EditText)findViewById(R.id.phone_number);

        DisplayButton = (Button)findViewById(R.id.DisplayButton);

        buttonChoose = (ImageButton) findViewById(R.id.imageButton);

        imageView = (ImageView) findViewById(R.id.imageView);

        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });


        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            } else if (type.startsWith("image/")) {
                handleSendImage(intent); // Handle single image being sent
            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleSendMultipleImages(intent); // Handle multiple images being sent
            }
        } else {
            // Handle other intents, such as being started from the home screen
        }


        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final StudentDetails studentDetails = new StudentDetails();

                GetDataFromEditText();

                //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();

                String dates =  DateFormat.getDateTimeInstance().format(date);
                // set to firebase
                studentDetails.setStudentName(NameHolder);
                studentDetails.setStudentPhoneNumber(NumberHolder);
                studentDetails.setDate(dates.toString());
                if (filePath != null) {

                    StorageReference sRef = storageReference.child("Images" + System.currentTimeMillis() + ".jpg");

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
                Toast.makeText(Post.this,"Data Inserted Successfully into Firebase Database", Toast.LENGTH_LONG).show();


                finish();
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

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            PhoneNumberEditText.setText(sharedText);
        }
    }

    void handleSendImage(Intent intent) {
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            // Update UI to reflect image being shared
        }
    }

    void handleSendMultipleImages(Intent intent) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageUris != null) {
            // Update UI to reflect multiple images being shared
        }

        //firebase = new Firebase(Firebase_Server_URL);



        DisplayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Post.this, ShowStudentDetailsActivity.class);
                startActivity(intent);

            }
        });


    }

    public void GetDataFromEditText(){

        NameHolder = NameEditText.getText().toString().trim();

        NumberHolder = PhoneNumberEditText.getText().toString().trim();

    }

}