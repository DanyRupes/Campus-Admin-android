package admin.example.com.campus;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import admin.example.com.nav.R;

public class ViewUploadsActivity extends AppCompatActivity {

	private static final String TAG = "ViewUploadActivity" ;
	//the listview
	ListView listView;

	//database reference to get uploads data
	DatabaseReference mDatabaseReference;
	private String uid,uname;

	//list to store uploads data
	List<Upload> uploadList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_uploads);

		uploadList = new ArrayList<>();
		listView = (ListView) findViewById(R.id.listView);


		//adding a clicklistener on listview
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				//getting the upload
				Upload upload = uploadList.get(i);

				//Opening the upload file in browser using the upload url
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(upload.getUrl()));
				Toast.makeText(getApplicationContext(),""+upload.getUrl(),Toast.LENGTH_LONG).show();
				startActivity(intent);
			}
		});

		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
		if (user != null) {
			uid = user.getUid();
			uname = user.getEmail();
		}
		final String path = "Users" + "/" + uname.split("@")[0];
		mDatabaseReference = FirebaseDatabase.getInstance().getReference(path + "/uploads/");

		//getting the database reference


		//retrieving upload data from firebase database
		mDatabaseReference.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
					Upload upload = postSnapshot.getValue(Upload.class);
					uploadList.add(upload);
				}

				String[] uploads = new String[uploadList.size()];

				for (int i = 0; i < uploads.length; i++)
				{
					uploads[i] = uploadList.get(i).getName();
				}

				//Toast.makeText(getApplicationContext(),""+uploads[1],Toast.LENGTH_LONG).show();

				//displaying it to list
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, uploads);
				listView.setAdapter(adapter);
                int count = listView.getCount();
                if(count == 0){
					finish();
					Toast.makeText(getApplicationContext(),"No Uploaded Documents",Toast.LENGTH_LONG).show();
				}


			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});
	}


}