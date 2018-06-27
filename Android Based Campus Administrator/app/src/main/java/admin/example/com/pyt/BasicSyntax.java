package admin.example.com.pyt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import admin.example.com.nav.R;

public class BasicSyntax extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		//getActionBar().setTitle("BasicSyntax");
		setContentView(R.layout.activity_basic_syntax);
	}
}
