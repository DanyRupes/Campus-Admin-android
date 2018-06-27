package admin.example.com.campus;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import admin.example.com.nav.R;

public class FileUpload extends AppCompatActivity implements View.OnClickListener {

	//this is the pic pdf code used in file chooser
	final static int PICK_PDF_CODE = 2342;
	private int STORAGE_PERMISSION_CODE = 23;

	//these are the views
	TextView textViewStatus;
	EditText editTextFilename;
	ProgressBar progressBar;
	private String uid,uname;

	//the firebase objects for storage and database
	StorageReference mStorageReference;
	DatabaseReference ref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_upload2);


		//getting firebase objects
		mStorageReference = FirebaseStorage.getInstance().getReference();
		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
		if (user != null) {
			uid = user.getUid();
			uname = user.getEmail();
		}
		final String path = "Users" + "/" + uname.split("@")[0];
		ref = FirebaseDatabase.getInstance().getReference(path + "/uploads/");


		//getting the views
		textViewStatus = (TextView) findViewById(R.id.textViewStatus);
		editTextFilename = (EditText) findViewById(R.id.editTextFileName);
		progressBar = (ProgressBar) findViewById(R.id.progressbar);

		//attaching listeners to views
		findViewById(R.id.buttonUploadFile).setOnClickListener(this);
		findViewById(R.id.textViewUploads).setOnClickListener(this);
	}

	private void requestStoragePermission(){

		if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
			//If the user has denied the permission previously your code will come to this block
			//Here you can explain why you need this permission
			//Explain here why you need this permission
		}

		//And finally ask for the permission
		ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
	}

	//This method will be called when the user will tap on allow or deny
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

		//Checking the request code of our request
		if(requestCode == STORAGE_PERMISSION_CODE){

			//If permission is granted
			if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

				//Displaying a toast
				Toast.makeText(this,"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();
			}else{
				//Displaying another toast if permission is not granted
				Toast.makeText(this,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
			}
		}
	}

	//this function will get the pdf from the storage
	private void getPDF() {
		//for greater than lolipop versions we need the permissions asked on runtime
		//so if the permission is not available user will go to the screen to allow storage permission
		/*
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
				Manifest.permission.READ_EXTERNAL_STORAGE)
				!= PackageManager.PERMISSION_GRANTED) {
			Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
					Uri.parse("package:" + getPackageName()));
			startActivity(intent);
			return;
		}
		*/

		//creating an intent for file chooser
		Intent intent = new Intent();
		intent.setType("application/pdf");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PDF_CODE);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//when the user choses the file
		if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
			//if a file is selected
			if (data.getData() != null) {
				//uploading the file
				uploadFile(data.getData());
			}else{
				Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
			}
		}
	}


	//this method is uploading the file
	//the code is same as the previous tutorial
	//so we are not explaining it
	private void uploadFile(Uri data) {
		progressBar.setVisibility(View.VISIBLE);
		StorageReference sRef = mStorageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + ".pdf");
		sRef.putFile(data)
				.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
					@SuppressWarnings("VisibleForTests")
					@Override
					public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
						progressBar.setVisibility(View.GONE);
						textViewStatus.setText("File Uploaded Successfully");

						Upload upload = new Upload(editTextFilename.getText().toString(), taskSnapshot.getDownloadUrl().toString());
						ref.child(ref.push().getKey()).setValue(upload);
					}
				})
				.addOnFailureListener(new OnFailureListener() {
					@Override
					public void onFailure(@NonNull Exception exception) {
						Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
					}
				})
				.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
					@SuppressWarnings("VisibleForTests")
					@Override
					public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
						double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
						textViewStatus.setText((int) progress + "% Uploading...");
					}
				});

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.buttonUploadFile:
				requestStoragePermission();
				getPDF();
				break;
			case R.id.textViewUploads:
				startActivity(new Intent(this, ViewUploadsActivity.class));
				break;
		}
	}
}