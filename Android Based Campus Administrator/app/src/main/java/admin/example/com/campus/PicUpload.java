package admin.example.com.campus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import java.io.IOException;

import admin.example.com.nav.R;

public class PicUpload extends AppCompatActivity implements View.OnClickListener /*  implementing click listener */ {
	//a constant to track the file chooser intent
	private static final int PICK_IMAGE_REQUEST = 234;


	private StorageReference storageReference;
	private  DatabaseReference ref;

	//Buttons
	private Button buttonChoose;
	private Button buttonUpload;
	//private Button buttonFile;

	//ImageView
	private ImageView imageView;
	private String uid,uname;

	//a Uri object to store file path
	private Uri filePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_upload);

		storageReference = FirebaseStorage.getInstance().getReference();
		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
		if (user != null) {
			uid = user.getUid();
			uname = user.getEmail();
		}
		final String path = "Users" + "/" + uname.split("@")[0];
		ref = FirebaseDatabase.getInstance().getReference(path + "/images/");

		//getting views from layout
		buttonChoose = (Button) findViewById(R.id.buttonChoose);
		buttonUpload = (Button) findViewById(R.id.buttonUpload);
		//buttonFile = (Button) findViewById(R.id.buttonFile);

		imageView = (ImageView) findViewById(R.id.imageView);

		//attaching listener
		buttonChoose.setOnClickListener(this);
		buttonUpload.setOnClickListener(this);
		//buttonFile.setOnClickListener(this);
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

	@Override
	public void onClick(View view) {
		//if the clicked button is choose
		if (view == buttonChoose) {
			showFileChooser();
		}
		// if the clicked button is upload
		else if (view == buttonUpload) {
			uploadPic();
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
							ref.child(ref.push().getKey()).setValue(taskSnapshot.getDownloadUrl().toString());

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
}