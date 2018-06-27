package admin.example.com.campus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import admin.example.com.SlidingActivity.SamplePrograms;
import admin.example.com.nav.R;

public class SampleContent extends AppCompatActivity implements View.OnClickListener{

    //String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_content);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Program Index");

        findViewById(R.id.relay1).setOnClickListener(this);
        findViewById(R.id.relay2).setOnClickListener(this);
        findViewById(R.id.relay3).setOnClickListener(this);
        findViewById(R.id.relay5).setOnClickListener(this);
        findViewById(R.id.relay6).setOnClickListener(this);
        findViewById(R.id.relay4).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.relay1:
                Intent intent = new Intent(SampleContent.this, SamplePrograms.class);
                intent.putExtra("message", "one");
                startActivity(intent);
                break;

            case R.id.relay2:
                Intent intent1 = new Intent(SampleContent.this, SamplePrograms.class);
                intent1.putExtra("message", "two");
                startActivity(intent1);
                break;

            case R.id.relay3:
                Intent intent3 = new Intent(SampleContent.this, SamplePrograms.class);
                intent3.putExtra("message", "three");
                startActivity(intent3);
                break;

            case R.id.relay5:
                Intent intent5 = new Intent(SampleContent.this, SamplePrograms.class);
                intent5.putExtra("message", "five");
                startActivity(intent5);
                break;

            case R.id.relay6:
                Intent intent6 = new Intent(SampleContent.this, SamplePrograms.class);
                intent6.putExtra("message", "six");
                startActivity(intent6);
                break;

            case R.id.relay4:
                Intent intent4 = new Intent(SampleContent.this, SamplePrograms.class);
                intent4.putExtra("message", "four");
                startActivity(intent4);
                break;
        }
    }
}
