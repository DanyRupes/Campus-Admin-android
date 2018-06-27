package admin.example.com.campus;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import admin.example.com.nav.R;

public class Post extends AppCompatActivity {

    Button SubmitButton ;

    Button DisplayButton;

    EditText NameEditText, PhoneNumberEditText;

    // Declaring String variable ( In which we are storing firebase server URL ).
    public static final String Firebase_Server_URL = "https://insertdata-android-examples.firebaseio.com/";

    // Declaring String variables to store name & phone number get from EditText.
    String NameHolder, NumberHolder;

    Firebase firebase;

    DatabaseReference databaseReference;

    // Root Database Name for Firebase Database.
    public static final String Database_Path = "Student_Details_Database";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Firebase.setAndroidContext(Post.this);

        //firebase = new Firebase(Firebase_Server_URL);

        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

        SubmitButton = (Button)findViewById(R.id.submit);

        NameEditText = (EditText)findViewById(R.id.name);

        PhoneNumberEditText = (EditText)findViewById(R.id.phone_number);

        DisplayButton = (Button)findViewById(R.id.DisplayButton);

        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StudentDetails studentDetails = new StudentDetails();

                GetDataFromEditText();

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();

                // set to firebase
                studentDetails.setStudentName(NameHolder);
                studentDetails.setStudentPhoneNumber(NumberHolder);
                studentDetails.setDate(date.toString());

                // Getting the ID from firebase database.
                String StudentRecordIDFromServer = databaseReference.push().getKey();

                // Adding the both name and number values using student details class object using ID.
                databaseReference.child(StudentRecordIDFromServer).setValue(studentDetails);

                // Showing Toast message after successfully data submit.
                Toast.makeText(Post.this,"Data Inserted Successfully into Firebase Database", Toast.LENGTH_LONG).show();

            }
        });

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